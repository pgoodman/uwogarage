package org.uwogarage.models;

/**
 * The RatingModel class represents a rating in the Garage Sale system
 *
 * @author Nate Smith
 * @version $Id$
 */

public class RatingModel implements Model {
    
    protected int rating = 0;               // the value of this rating
	protected UserModel user;			// the user owns this rating
	protected GarageSaleModel sale;   // the sale that this rating belongs to

	/**
	 * Constructor for RatingModel, inserts rating and assigns it to a garage 
	 * sale and user
	 */
	public RatingModel(int r, UserModel u, GarageSaleModel s) {
	    rating = r;
	    user = u;
	    sale = s;
	}
	

	/**
	 * This method returns the rating
	 * @return this rating's rating
	 */
	public int getRating() {
	    return rating;
	}
}
