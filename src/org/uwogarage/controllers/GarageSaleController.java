package org.uwogarage.controllers;

import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.ModelSet;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.D2;
import org.uwogarage.util.functional.F0;
import org.uwogarage.views.GarageSaleView;
import org.uwogarage.views.ListGarageSalesView;
import org.uwogarage.views.TabView;
import org.uwogarage.views.buyer.SearchGarageSalesView;
import org.uwogarage.views.seller.AddGarageSaleView;
import org.uwogarage.views.seller.BulkAddGarageSaleView;
import org.uwogarage.views.seller.EditGarageSaleView;

/** 
 * The GarageSaleController class responds to calls from a View and manipulates  
 * in GarageSaleModels in the datastore
 *
 * @version $Id$
 */

public class GarageSaleController extends Controller<GarageSaleModel> {

    private static final long serialVersionUID = -2833369045533139215L;

    /**
     * This method displays the appropriate view to bulk add garage sales, 
     * processes the input, and stores the rating
     */
    public void bulkAdd() {
        
    	TabView.show(BulkAddGarageSaleView.view(
    		logged_user,
    		new D2<ModelSet<GarageSaleModel>,F0>() {
				public void call(ModelSet<GarageSaleModel> sales, F0 change_tab_responder) {
				    
				    ModelSet<GarageSaleModel> user_sales = logged_user.getGarageSales();
				    
				    // loop over the sales that the bulk load parser returned
				    // and add them to the model set
					for (GarageSaleModel sale : sales) {
						user_sales.add(sale);
					    models.add(sale);
					}
					
					// toggle a change tab, which will eventually toggle a change
					// in control flow!
					change_tab_responder.call();
				}
	    	}
    	));
    	
    }
    
    public void add() {
        TabView.show((new AddGarageSaleView()).view(
            logged_user,
            d.category.getModels(),
            
            // pass a delegate to the view that takes in the garage sale to be
            // added
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
                
                // pass a delegate to the view that takes in the updated garage
                // sale
                new D<GarageSaleModel>() {
                    public void call(GarageSaleModel sale) {
                        view(sale);
                    }
                }
            ));
    }
    
    public void delete(GarageSaleModel sale) {
    	models.remove(sale); 
    }
    
    /**
     * View a single garage sale.
     * 
     * @param sale
     */
    public void view(GarageSaleModel sale) {
        TabView.show(GarageSaleView.view(sale));
    }
    
    public void search() {
        TabView.show((new SearchGarageSalesView(d.category.getModels())).view(
            models
        ));
    }
    
    public void list(ModelSet<GarageSaleModel> categories) {

    	TabView.show(ListGarageSalesView.view(
            logged_user,
            categories,
            
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
}
