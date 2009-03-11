package org.uwogarage.models;

import java.util.Date;
import org.uwogarage.util.Location;

/**
 * The GarageSaleModel class represents a garage sale in the Garage Sale system
 *
 * @author Nate Smith
 * @version $Id$
 */

public class GarageSaleModel implements Model {

	Location location;      // the garage sale's location
	int rating;             // the garage sale's rating
	ModelSet<CategoryModel> categories; // the garage sale's category
	Date date;              // the garage sale's date
	String note;            // the garage sale's note
	
	/**
	 * Constructor for GarageSaleModel
	 */
	public GarageSaleModel() {
	    
	}

	/**
	 * This method returns the garage sale's categories
	 * @return the garage sale's categories
	 */
	ModelSet<CategoryModel> getCategories() {
	    return categories;
	}
	
	/**
	 * This method returns the garage sale's location
	 * @return the garage sale's location
	 */
	public Location getLocation() {
	    return location;
	}
	
	/**
	 * This method returns the garage sale's date
	 * @return the garage sale's date
	 */
	public Date getDate() {
	    return date;
	}
	/**
	 * This method returns the garage sale's note
	 * @return the garage sale's note
	 */
	public String getNote() {
	    return note;
	}
	
	/**
	 * This method sets the garage sale's location
	 * @param loc the garage sale's new default location
	 */
	void setLocation(Location loc) {
	    location = loc;
	}
	
	/**
	 * This method sets the garage sale's date
	 * @param date the garage sale's date
	 */
	void setDate(Date d) {
	    date = d;
	}
	
	/**
	 * This method sets the garage sale's note
	 * @param note the garage sale's note
	 */
	void setNote(String n) {
	    note = n;
	}
	
	/**
	 * This method adds a category to the garage sale
	 * @param cat the category to be added
	 */
	void addCategory(CategoryModel cat) {
	    categories.add(cat);
	}
	
	/**
	 * This method removes a category from the garage sale
	 * @param cat the category to be removed
	 */
	void removeCategory(CategoryModel cat) {
	    categories.remove(cat);
	}
}
