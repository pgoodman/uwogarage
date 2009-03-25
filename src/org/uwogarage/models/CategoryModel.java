package org.uwogarage.models;

/**
 * The CategoryModel class represents a category in the Garage Sale system
 *
 * @version $Id$
 */

public class CategoryModel implements Model {
    
    private static final long serialVersionUID = 1337L;
    
	private String name = ""; // the category's name

	/**
	 * Constructor, inserts the name of the category
	 */
	public CategoryModel(String n) throws Exception {
	    if(!n.matches(".{1,50}")) {
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
