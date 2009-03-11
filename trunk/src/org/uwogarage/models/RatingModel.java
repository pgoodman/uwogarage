package org.uwogarage.models;

/**
 * The RatingModel class represents a rating in the Garage Sale system
 *
 * @author Nate Smith
 * @version $Id$
 */

public class RatingModel implements Model {
	// instance variables ***************************
	UserModel user;			// the user owns this rating
	int rating;				// the value of this rating
	GarageSaleModel sale;   // the sale that this rating belongs to

	/**
	 * Constructor for RatingModel, inserts rating and assigns it to a garage 
	 * sale and user
	 */
	public RatingModel(int r, UserModel u, GarageSaleModel s)
	{
	}
	
	// ACCESSOR METHODS ***************************
	/**
	 * This method returns the rating
	 * @return this rating's rating
	 */
	public int getRating() {}
}
