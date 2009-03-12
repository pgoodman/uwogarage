package org.uwogarage.models;

import org.uwogarage.util.Immutable;

/**
 * The CategoryModel class represents a category in the Garage Sale system
 *
 * @author Nate Smith
 * @version $Id$
 */

public class CategoryModel implements Model, Immutable {
    
	private String name; // the category's name

	/**
	 * Constructor, inserts the name of the category
	 */
	public CategoryModel(String n) throws Exception {
	    if(!n.matches("[a-zA-Z0-9 ]{1,50}")) {
	        throw new Exception();
	    }
	    
	    name = n;
	}
	
	/**
	 * Get the category name.
	 * @return this category's name
	 */
	public String getName() {
	    return name;
	}
}