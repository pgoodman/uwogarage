package org.uwogarage.views;

import javax.swing.JPanel;

import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.ModelSet;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.gui.GridCell;

/**
 * Show a list of garage sales.
 * 
 * @version $Id$
 */
public class ListGarageSalesView extends View {
    
    /**
     * Show the listing.
     * 
     * @param user Currently logged in user
     * @param sales Sales to list
     * @param view_responder
     * @param edit_responder
     * @param delete_responder
     * @return
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
        
        // create the rows
        for(GarageSaleModel sale : sales) {
            rows[i++] = GarageSaleListItemView.view(
                user, 
                sale, 
                view_responder, 
                edit_responder, 
                delete_responder
            );
        }
        
        return grid(rows);
    }
}
