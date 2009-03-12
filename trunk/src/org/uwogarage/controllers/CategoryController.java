package org.uwogarage.controllers;

import org.uwogarage.models.CategoryModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.views.View;
import org.uwogarage.views.admin.AddCategoryView;

/**
 * The CategoryController class responds to calls from a View and manipulates  
 * in Strings in the data store (as categories are nothing more than strings,
 * no extra info is needed).
 *
 * @version $Id$
 */


public class CategoryController extends Controller<CategoryModel> {

    public void add() {
        View.show(AddCategoryView.view(models, new D<CategoryModel>() {
            public void call(CategoryModel category) {
                models.add(category);
                
                // TODO Return to main admin menu or do something
            }
        }));
    }
    
    // leave unimplemented
    public void edit(String model) { }
}
