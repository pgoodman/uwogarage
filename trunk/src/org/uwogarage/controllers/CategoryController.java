package org.uwogarage.controllers;

import org.uwogarage.models.CategoryModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.views.View;
import org.uwogarage.views.admin.AddCategoryView;

/**
 * Deals with the creation and manipulation of categories.
 *
 * @version $Id$
 */
public class CategoryController extends Controller<CategoryModel> {
    
    public CategoryController() {
        try {
            models.add(new CategoryModel("Test Category 1"));
            models.add(new CategoryModel("Test Category 2"));
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
                
                // TODO Return to main admin menu or do something
                
            }
        }));
    }
    
    // leave unimplemented
    public void edit(String model) { }
}
