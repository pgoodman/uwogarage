package org.uwogarage.views;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.ModelSet;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.views.seller.AddGarageSaleView;
import org.uwogarage.views.seller.BulkAddGarageSaleView;
import org.uwogarage.views.seller.FindGarageSalesView;

/**
 * Show the main menu for buyers/sellers.
 * 
 * @version $Id$
 */
public class UserControlPanel extends View {
    
    static public JPanel view(final UserModel user, final ModelSet<GarageSaleModel> sales) {
        JTabbedPane pane = new JTabbedPane();
        
        pane.add("Control Panel", stats());
        
        pane.add("Add Sale", AddGarageSaleView.view(new D<GarageSaleModel>() {
            public void call(GarageSaleModel s) {
                
            }
        }));
        pane.add("Bulk Add", BulkAddGarageSaleView.view(new D<ModelSet<GarageSaleModel>>() {
            public void call(ModelSet<GarageSaleModel> new_sales) {
                
            }
        }));
        
        pane.add("My Sales", BulkAddGarageSaleView.view(new D<ModelSet<GarageSaleModel>>() {
            public void call(ModelSet<GarageSaleModel> new_sales) {
                
            }
        }));
        
        pane.add("Search", FindGarageSalesView.view(new D<ModelSet<GarageSaleModel>>() {
            public void call(ModelSet<GarageSaleModel> new_sales) {
                
            }
        }));
        
        return grid(
            grid.cell(label("Welcome, "+ user.getFirstName() +" "+ user.getLastName()))
                .pos(0, 0)
                .margin(10, 10, 10, 10),
            
            grid.cell(pane).pos(0, 1).fill(1, 1)
        );
    }
    
    static public JPanel stats() {
        return grid(
        
        );
    }
}
