package org.uwogarage.views.buyer;

import java.awt.Dimension;
import java.util.Calendar;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.ModelSet;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.Location;
import org.uwogarage.util.StringUtil;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.gui.GridCell;
import org.uwogarage.views.View;

import map.*;

/**
 * Show a reduced form of a garage sale list.
 * 
 * @author petergoodman
 * @version $Id$
 */
public class ListGarageSalesReducedView extends View {
    
    static public JPanel view(ModelSet<GarageSaleModel> sales, 
                               final D<GarageSaleModel> view_responder,
                               UserModel user) {
        
        // instantiate the map, and give it a delegate to call for when a 
        // waypoint is clicked.
    	MapPanel map = new MapPanel(user, new D<Set<GarageSaleModel>>() {
    	    public void call(Set<GarageSaleModel> sales) {
    	        
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
        
        // create the rows
        for(final GarageSaleModel sale : sales) {
            
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
        
        pane.setPreferredSize(new Dimension(450, 300));
        
        // put the gui together
        return grid(
            grid.cell(pane).pos(0, 0).margin(10, 0, 10, 10),
            grid.cell(map).pos(1, 0)
        );
    }
}
