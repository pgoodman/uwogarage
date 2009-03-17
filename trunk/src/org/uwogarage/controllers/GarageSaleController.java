package org.uwogarage.controllers;

import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.views.GarageSaleView;
import org.uwogarage.views.ListGarageSalesView;
import org.uwogarage.views.TabView;
import org.uwogarage.views.seller.AddGarageSaleView;
import org.uwogarage.views.seller.EditGarageSaleView;

/**
 * The GarageSaleController class responds to calls from a View and manipulates  
 * in GarageSaleModels in the datastore
 *
 * @version $Id$
 */

public class GarageSaleController extends Controller<GarageSaleModel> {

    /**
     * This method displays the appropriate view to rate a garage sale, 
     * processes the input, and stores the rating
     */
    public void rate() {}
    
    /**
     * This method displays the appropriate view to bulk add garage sales, 
     * processes the input, and stores the rating
     */
    public void bulkAdd() {
        
    }
    
    public void add() {
        TabView.show((new AddGarageSaleView()).view(
            logged_user,
            d.category.getModels(),
            new D<GarageSaleModel>() {
                public void call(GarageSaleModel sale) {
                    logged_user.addGarageSale(sale);
                    models.add(sale);
                    view(sale);
                }
            }
        ));
    }

    public void edit(GarageSaleModel sale) {
        TabView.show((new EditGarageSaleView()).view(
                sale,
                d.category.getModels(),
                new D<GarageSaleModel>() {
                    public void call(GarageSaleModel sale) {
                        logged_user.addGarageSale(sale);
                        models.add(sale);
                        view(sale);
                    }
                }
            ));
    }
    
    public void delete(GarageSaleModel sale) {
        
    }
    
    /**
     * View a single garage sale.
     * 
     * @param sale
     */
    public void view(GarageSaleModel sale) {
        TabView.show(GarageSaleView.view(sale));
    }
    
    /**
     * Manage the sales for this particular user.
     */
    public void manage() {
        TabView.show(ListGarageSalesView.view(
            logged_user,
            logged_user.getGarageSales(),
            
            // view sale
            new D<GarageSaleModel>() {
                public void call(GarageSaleModel sale) {
                    view(sale);
                }
            },
            
            // edit sale
            new D<GarageSaleModel>() {
                public void call(GarageSaleModel sale) {
                    edit(sale);
                }
            },
            
            // delete sale
            new D<GarageSaleModel>() {
                public void call(GarageSaleModel sale) {
                    delete(sale);
                }
            }
        ));
    }
    
    public void search() {
        // TODO
    }
    
    public void list() {
        // TODO
    }
}
