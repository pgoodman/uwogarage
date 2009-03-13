package org.uwogarage.controllers;

import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.util.functional.D;
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
    public void bulkAdd() {}

    /**
     * This method displays the appropriate view to bulk delete garage sales, 
     * processes the input, and stores the rating
     */
    public void bulkDelete() {
        
    }
    
    public void add() {
        TabView.show(AddGarageSaleView.view(new D<GarageSaleModel>() {
            public void call(GarageSaleModel sale) {
                
            }
        }));
    }

    public void edit(GarageSaleModel model) {
        // TODO Auto-generated method stub
    }
    
    public void manage() {
        
    }
    
    public void search() {
        
    }
    
    public void list() {
        
    }
}
