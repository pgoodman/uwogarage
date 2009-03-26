package org.uwogarage.controllers;

import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.ModelSet;
import org.uwogarage.models.RatingModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.D2;
import org.uwogarage.views.GarageSaleView;
import org.uwogarage.views.ListGarageSalesView;
import org.uwogarage.views.TabView;
import org.uwogarage.views.buyer.SearchGarageSalesView;
import org.uwogarage.views.seller.AddGarageSaleView;
import org.uwogarage.views.seller.BulkAddGarageSaleView;
import org.uwogarage.views.seller.EditGarageSaleView;

/** 
 * The GarageSaleController class responds to calls from a View and manipulates  
 * in GarageSaleModels in the data store
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
    		new D2<Boolean,ModelSet<GarageSaleModel>>() {
				public void call(Boolean remove_old_sales, ModelSet<GarageSaleModel> sales) {
				    
				    // remove the old sales
				    if(remove_old_sales) {
				        for(GarageSaleModel sale : logged_user.sales)
				            deleteSale(sale);
				    }
				    
				    // loop over the sales that the bulk load parser returned
				    // and add them to the model set
					for (GarageSaleModel sale : sales) {
					    models.add(sale);
					    logged_user.sales.add(sale);
					}
				}
	    	}
    	));
    	
    }
    
    /**
     * Add a garage sale
     */
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
    
    /**
     * Edit a garage sale.
     * @param sale
     */
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
    
    /**
     * Delete a single garage sale. This delete removes a garage sale and all of
     * its ratings.
     * @param sale
     */
    protected void deleteSale(GarageSaleModel sale) {
        ModelSet<RatingModel> ratings = sale.ratings;
        
        // remove the ratings from the ratings controller
        d.rating.getModels().removeAll(ratings);
        
        // remove the ratings from each user
        for(RatingModel rating : ratings)
            rating.getUser().ratings.remove(rating);
        
        // remove the sale from the user that owns it
        sale.user.sales.remove(sale);
        
        // remove it from the garage sale controller
        models.remove(sale);
    }
    
    /**
     * Delete a garage sale.
     * @param sale_list
     * @param sale
     */
    public void delete(ModelSet<GarageSaleModel> sale_list, GarageSaleModel sale) {
        deleteSale(sale);
        list(sale_list);
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
     * Search for some garage sales.
     */
    public void search() {
        TabView.show((new SearchGarageSalesView(d.category.getModels())).view(
            models,
            new D<GarageSaleModel>() {
                public void call(GarageSaleModel sale) {
                    view(sale);
                }
            }
        ));
    }
    
    /**
     * Display a list of garage sales.
     * @param sales
     */
    public void list(final ModelSet<GarageSaleModel> sales) {

    	TabView.show(ListGarageSalesView.view(
            logged_user,
            sales,
            
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
                    delete(sales, sale);
                }
            }
        ));
    }
}
