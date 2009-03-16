package org.uwogarage.controllers;

import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.views.GarageSaleView;
import org.uwogarage.views.TabView;
import org.uwogarage.views.seller.AddGarageSaleView;

/**
 * The GarageSaleController class responds to calls from a View and manipulates  
 * in GarageSaleModels in the datastore
 *
 * @author Nate Smith
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
            d.category.getModels(),
            new D<GarageSaleModel>() {
                public void call(GarageSaleModel sale) {
                    models.add(sale);
                    view(sale);
                }
            }
        ));
    }

    public void edit(GarageSaleModel model) {
        // TODO Auto-generated method stub
    }
    
    public void manage() {
        // TODO
    }
    
    public void search() {
        // TODO
    }
    
    public void list() {
        // TODO
    }
    
    /**
     * View a single garage sale.
     * 
     * @param sale
     */
    public void view(GarageSaleModel sale) {
        System.out.printf("Viewing single garage sale...");
        TabView.show(GarageSaleView.view(sale));
    }
}
