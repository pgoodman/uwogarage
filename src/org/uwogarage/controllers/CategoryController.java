package org.uwogarage.controllers;

import org.uwogarage.Dispatcher;
import org.uwogarage.models.CategoryModel;

/**
 * The CategoryController class responds to calls from a View and manipulates  
 * in Strings in the data store (as categories are nothing more than strings,
 * no extra info is needed).
 *
 * @author Nate Smith
 * @version $Id$
 */


public class CategoryController extends Controller<String> {

    public CategoryController(Dispatcher d) {
        super(d);
    }

    public void add() {
        
    }
    
    // leave unimplemented
    public void edit(String model) { }
    protected void update(String model) { }
}
