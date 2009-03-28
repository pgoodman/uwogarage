package org.uwogarage.controllers;

import org.uwogarage.models.CategoryModel;
import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.views.View;
import org.uwogarage.views.admin.AddCategoryView;
import org.uwogarage.views.admin.ListCategoriesView;

/**
 * Deals with the creation and manipulation of categories.
 *
 * @version $Id$
 */
public class CategoryController extends Controller<CategoryModel> {

    private static final long serialVersionUID = -874405642977554718L;

    public CategoryController() {
        try {
            models.add(new CategoryModel("Antiques"));
            models.add(new CategoryModel("Art"));
            models.add(new CategoryModel("Collectables"));
            models.add(new CategoryModel("Children's Clothing/Toys"));
            models.add(new CategoryModel("Furniture"));
        } catch(Exception e) { }
    }
    
    /**
     * Show the form to add a category, then perform the insert when the form is
     * submitted successfully.
     */
    public void add() {
        View.show(AddCategoryView.view(models, new D<CategoryModel>() {
            public void call(CategoryModel category) {
                
                // add the newly created category in, this could have been in the
                // view but I decided it best to put that control with the
                // controller
                models.add(category);
            }
        }));
    }
    
    public void list() {
    	View.show(ListCategoriesView.view(
    		models, 
    		
    		// responder to delete the category
    		new D<CategoryModel>(){
	    		public void call(CategoryModel cat) {
	    		    
	    		    // remove the category from the main set
	    		    models.remove(cat);
	    	        
	    	        // removes category from all sales that use it
	    	        for (GarageSaleModel sale: d.garage_sale.getModels())
	    	            sale.categories.remove(cat);
	    		}
	    	}
    	));
    }
}
