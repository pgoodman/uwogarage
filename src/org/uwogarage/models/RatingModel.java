package org.uwogarage.models;

/**
 * The RatingModel class represents a rating in the Garage Sale system
 *
 * @author Nate Smith
 * @version $Id$
 */

public class RatingModel implements Model {
    
    private static final long serialVersionUID = 80081355L;
    
    protected int rating;               // the value of this rating
	protected UserModel user;			// the user owns this rating
	protected GarageSaleModel sale;   // the sale that this rating belongs to

	/**
	 * Constructor for RatingModel, inserts rating and assigns it to a garage 
	 * sale and user
	 */
	public RatingModel(int r, UserModel u, GarageSaleModel s) {
	    rating = Math.abs(r) % 6; // make sure it's range 0 to 5
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
