package org.uwogarage.views;

import javax.swing.JButton;

import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.gui.GridCell;

/**
 * Show the basic info of a garage sale in a listing of sales / search results /
 * etc.
 * 
 * @version $Id$
 */
public class GarageSaleListItemView extends View {
    
    /**
     * 
     * @param user Currently logged in user.
     * @param sale Sale to be shown.
     * @param view_responder
     * @param edit_responder
     * @param delete_responder
     * @return
     */
    static public GridCell[] view(UserModel user, 
                      final GarageSaleModel sale,
                   final D<GarageSaleModel> view_responder,
                   final D<GarageSaleModel> edit_responder,
                   final D<GarageSaleModel> delete_responder) {
        
        // button for viewing a single sale in the list
        JButton view_button = button("view", new D<JButton>() {
            public void call(JButton b) {
                view_responder.call(sale);
            }
        });
        
        // button for editing a single sale in the list
        JButton delete_button = button("delete", new D<JButton>() {
            public void call(JButton b) {
                if(dialog.confirm(f, "Are you sure you want to delete this garage sale?"))
                    delete_responder.call(sale);
            }
        });
        
        // button for deleting a single sale in the list
        JButton edit_button = button("edit", new D<JButton>() {
            public void call(JButton b) {
                edit_responder.call(sale);
            }
        });
        
        // create this list item
        return grid.row(
            grid.cell(label(sale.getLocation().getStreet()))
                .margin(10, 10, 0, 10),
            grid.cell(label(sale.getTime().getTime().toString()))
                .margin(10, 10, 0, 0),
            grid.cell(
                sale.getUser() == user
                ? grid(
                      grid.cell(view_button),
                      grid.cell(edit_button),
                      grid.cell(delete_button)                      
                  )
                : label("")
            ).margin(10, 10, 0, 0)
        );
    }
}
