package org.uwogarage.models;

/**
 * The GarageSaleModel class represents a garage sale in the Garage Sale system
 *
 * @author Nate Smith
 * @version $Id$
 */

public class GarageSaleModel implements Model {
	// instance variables ***************************
	Location location;      // the garage sale's location
	int rating;             // the garage sale's rating
	CategoryModel category; // the garage sale's category
	Date date;              // the garage sale's date
	String note;            // the garage sale's note
	
	/**
	 * Constructor for GarageSaleModel
	 */
	public GarageSaleModel()
	{
	}
	
	// ACCESSOR METHODS ***************************
	/**
	 * This method returns the garage sale's categories
	 * @return the garage sale's categories
	 */
	CategoryModel getCategories() {}
	/**
	 * This method returns the garage sale's location
	 * @return the garage sale's location
	 */
	Location getLocation() {}
	/**
	 * This method returns the garage sale's date
	 * @return the garage sale's date
	 */
	Date getDate() {}
	/**
	 * This method returns the garage sale's note
	 * @return the garage sale's note
	 */
	String getNote() {}
	
	// MUTATOR METHODS ***************************
	/**
	 * This method sets the garage sale's location
	 * @param loc the garage sale's new default location
	 */
	void setLocation(Location loc) {}
	/**
	 * This method sets the garage sale's date
	 * @param date the garage sale's date
	 */
	void setDate(Date date) {}
	/**
	 * This method sets the garage sale's note
	 * @param note the garage sale's note
	 */
	void setNote(String note) {}
	/**
	 * This method adds a category to the garage sale
	 * @param cat the category to be added
	 */
	void addCategory(CategoryModel cat) {}
	/**
	 * This method removes a category from the garage sale
	 * @param cat the category to be removed
	 */
	void removeCategory(CategoryModel cat) {}
}
