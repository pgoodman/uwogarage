package org.uwogarage.views.buyer;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.ModelSet;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.Location;
import org.uwogarage.util.StringUtil;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.D2;
import org.uwogarage.util.functional.F;
import org.uwogarage.util.gui.GridCell;
import org.uwogarage.views.GarageSaleView;
import org.uwogarage.views.View;

import map.*;

/**
 * Show a reduced form of a garage sale list.
 * 
 * @author petergoodman
 * @version $Id$
 */
public class SearchResultsGarageSalesView extends View {
    
    /**
     * Show the dialog that pops up when a waypoint is clicked.
     * @param sales
     */
    static protected void viewWaypointSales(final ModelSet<GarageSaleModel> sales,
                                            final UserModel logged_user,
                                            final D2<GarageSaleModel,Integer> rate_responder) {
        
        dialog.modal(f, "Viewing Sales", new F<JDialog,Container>() {
            public Container call(JDialog d) {
                
                final JTabbedPane pane = new JTabbedPane();
                JPanel first = null, 
                       each;
                
                int i = 1;
                for(GarageSaleModel sale : sales) {
                    
                    each = GarageSaleView.view(
                        sale, 
                        logged_user, 
                        rate_responder
                    );
                    
                    if(null == first)
                        first = each;
                    
                    pane.add(String.valueOf(i++), each);
                };
                
                pushContext(first);
                
                pane.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        popContext();
                        pushContext((JPanel) pane.getComponentAt(pane.getSelectedIndex()));
                    }
                });
                
                d.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        popContext();
                    }
                });
                
                return pane;
            }
        });
    }
    
    /**
     * Show the main search results and the map.
     * @param sales
     * @param view_responder
     * @param user
     * @return
     */
    static public JPanel view(ModelSet<GarageSaleModel> sales, 
                               final D<GarageSaleModel> view_responder,
                               final D2<GarageSaleModel,Integer> rate_responder,
                               final UserModel user) {
        
        // instantiate the map, and give it a delegate to call for when a 
        // waypoint is clicked.
    	MapPanel map = new MapPanel(user, new D<ModelSet<GarageSaleModel>>() {
    	    public void call(ModelSet<GarageSaleModel> sales) {
    	        viewWaypointSales(sales, user, rate_responder);
    	    }
    	});
    	
        // nothing to show
        if(0 == sales.size()) {
            return grid(grid.cell(label(
                "There are no garage sales to list."
            )));
        }
        
        // allocate the rows
        GridCell[][] rows = new GridCell[sales.size()][];
        Calendar date;
        int i = 0, 
            hour;
        
        // sort the sales by date, newer ones will appear earlier in the list
        GarageSaleModel sorted_sales[] = sales.toArray(new GarageSaleModel[sales.size()]);
        Arrays.sort(sorted_sales, new Comparator<GarageSaleModel>() {
            public int compare(GarageSaleModel a, GarageSaleModel b) {
                return a.getTime().getTime().before(b.getTime().getTime()) ? 1 : -1;
            }
        });
        
        // create the rows
        for(final GarageSaleModel sale : sorted_sales) {
            
            // add the sale into the map
        	map.addGarageSale(sale);
        	
        	// do non-map things
            date = sale.getTime();
            hour = date.get(Calendar.HOUR);
            
            if(hour == 0) hour = 12;
            
            rows[i++] = grid.row(
                    
                // location
                grid.cell(label(sale.getLocation().getStreet()))
                    .margin(10, 10, 0, 10).anchor(1, 0, 0, 1),
                    
                // the date the sale is happening on
                grid.cell(label(
                    date.get(Calendar.YEAR) +"/"+
                    (date.get(Calendar.MONTH)+1) +"/"+
                    date.get(Calendar.DAY_OF_MONTH) +
                    " at "+
                    hour +":"+
                    StringUtil.padLeft(String.valueOf(date.get(Calendar.MINUTE)), '0', 2) +
                    (date.get(Calendar.AM_PM) == Calendar.AM ? "am" : "pm") +" "+
                    Location.PROVINCE_TIME_ZONE_CODES.get(sale.getLocation().getProvince())
                )).margin(10, 10, 0, 0).anchor(1, 0, 0, 1),
                
                // view button
                grid.cell(button("View", new D<JButton>() {
                    public void call(JButton b) {
                        view_responder.call(sale);
                    }
                }))
            );
        }
        
        // make it scrollable
        JScrollPane pane = new JScrollPane(
            grid(rows),
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        //JScrollPane map_pane = new JScrollPane(map);
        
        pane.setPreferredSize(new Dimension(450, 300));
        
        // put the gui together
        return grid(
            grid.cell(pane).pos(0, 0).margin(10, 0, 10, 10),
            grid.cell(map).pos(1, 0).margin(10, 10, 10, 0)
        );
    }
}
