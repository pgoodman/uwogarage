package org.uwogarage.views;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.ModelSet;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.gui.GridCell;

/**
 * Show a list of garage sales.
 * 
 * @version $Id$
 * @author Peter Goodman
 */
public class ListGarageSalesView extends View {
    
    /**
     * Show the listing.
     * 
     * @param user Currently logged in user
     * @param sales Sales to list
     * @param view_responder responder for the view
     * @param edit_responder responder for the edit button
     * @param delete_responder responder for the delete button
     * @return a panel containing the listing information
     */
    static public JPanel view(UserModel user,
              ModelSet<GarageSaleModel> sales,
               final D<GarageSaleModel> view_responder,
               final D<GarageSaleModel> edit_responder,
               final D<GarageSaleModel> delete_responder) {
        
        // nothing to show
        if(0 == sales.size()) {
            return grid(grid.cell(label(
                "There are no garage sales to list."
            )));
        }
        
        // allocate the rows
        GridCell[][] rows = new GridCell[sales.size()][];
        int i = 0;
        
        // sort the sales by date, newer ones will appear earlier in the list
        GarageSaleModel sorted_sales[] = sales.toArray(new GarageSaleModel[sales.size()]);
        Arrays.sort(sorted_sales, new Comparator<GarageSaleModel>() {
            public int compare(GarageSaleModel a, GarageSaleModel b) {
                return a.getTime().getTime().before(b.getTime().getTime()) ? 1 : -1;
            }
        });
        
        // create the rows
        for(GarageSaleModel sale : sorted_sales) {
            rows[i++] = GarageSaleListItemView.view(
                user, 
                sale, 
                view_responder, 
                edit_responder, 
                delete_responder
            );
        }
        
        JScrollPane pane = new JScrollPane(
            grid(rows),
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        pane.setPreferredSize(new Dimension(600, 400));
        
        return grid(
            grid.cell(fieldset("Note", label(
                "Garage sales that have now past will not be editable."
            ))).pos(0, 0),
            grid.cell(pane).pos(0, 1).margin(10, 10, 10, 10)
        );
    }
}
