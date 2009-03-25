package org.uwogarage.models;

import org.uwogarage.util.GeoPosition;

/**
 * The UserModel class represents a user in the Garage Sale system
 *
 * @version $Id$
 */

public class UserModel implements Model {
    
    private static final long serialVersionUID = 45535L;
    
	protected String first_name = "", // the user's first name
	                 last_name = "", // the user's last name
	                 pass = "aaa", // the user's pass, default 'aaa'
	                 user_id = "", // the user's id
	                 phone[] = {"", "", ""}; // the user's phone
	
	protected GeoPosition start_location;
	
	protected boolean reset_pass = true; // was the user's password was reset
	
	protected int start_zoom = 4; // the user's default zoom level
	
	// the user's ratings
	protected ModelSet<RatingModel> ratings = new ModelSet<RatingModel>();
	
	// the user's sales
	protected ModelSet<GarageSaleModel> sales = new ModelSet<GarageSaleModel>();
	
	/**
	 * Get the user ID.
	 */
	public String getId() {
	    return user_id;
	}
	
	/**
     * Get the password.
     */
    public String getPassword() {
        return pass;
    }
	
	/**
	 * This method will return the user's first name.
	 * 
	 * @return the user's full name.
	 */
	public String getFirstName() {
	    return first_name;
	}
	
	/**
     * This method will return the user's last name.
     * 
     * @return the user's full name.
     */
    public String getLastName() {
        return last_name;
    }
	
	/**
	 * This method will return the user's phone number.
	 * 
	 * @return the user's phone number
	 */
	public String[] getPhoneNumber() {
	    return phone;
	}
	
	/**
	 * Get the user's default latitude.
	 */
	public GeoPosition getGeoPosition() {
	    return start_location;
	}

	/**
	 * This method will return the user's default zoom level
	 * 
	 * @return the user's default zoom level
	 */
	public int getDefaultZoom() {
	    return start_zoom;
	}
	
	/**
	 * Get the user's average garage sale rating. This calculates it on the spot
	 * from the user's garage sales.
	 */
	public float getRating() {
	    int rating_sum = 0;
	    int num_ratings = 0;
	    
	    for(GarageSaleModel sale : sales) {
	        if(!sale.isRated())
	            continue;
	        
	        rating_sum += sale.getRating();
	        ++num_ratings;
	    }
	    
	    return num_ratings > 0 ? (rating_sum / num_ratings) : 0;
	}
	
	/**
	 * Get this user's garage sales.
	 */
	public ModelSet<GarageSaleModel> getGarageSales() {
	    return sales;
	}
	
	/**
	 * Check if the user's password should be changed, i.e. the user was just
	 * created or the user's password was just reset by the administrator.
	 */
	public boolean hasDefaultPass() {
	    return reset_pass;
	}
	
	/**
	 * Set the user id.
	 */
	public boolean setUserId(String p) {
	    if(!p.matches("[0-9a-zA-Z]{4}")) {
            return false;
        }
	    
        user_id = p;
        return true;
	}
	
	/**
	 * Set the password. Returns false if the password is incorrectly formatted.
	 */
	public boolean setPassword(String p) {
	    
	    if(null == p) {
	        p = "aaa";
	        reset_pass = true;
	    }
	    
	    if(!p.matches("[a-zA-Z]{3}")) {
	        return false;
	    }
	    
	    pass = p;
	    return true;
	}
	
	/**
	 * Set the user's first name, returns false if the name is incorrectly
	 * formatted.
	 * 
	 * @param n The user's first name
	 */
	public boolean setFirstName(String n) {
	    if(!n.matches("[a-zA-Z]{1,20}")) {
	        return false;
	    }
	    
	    first_name = n;
	    return true;
	}
	
	/**
	 * Set the user's last name, returns false if the name is incorrectly 
	 * formatted.
	 * 
	 * @param n The user's last name
	 */
	public boolean setLastName(String n) {
	    if(!n.matches("[a-zA-Z-]{1,20}")) {
            return false;
        }
        
        last_name = n;
        return true;
	}
	
	/**
	 * Set the user's phone number, returns false if the supplied number is
	 * incorrectly formatted.
	 * 
	 * @param phoneNumber the user's phone number
	 */
	public boolean setPhoneNumber(String area, String first, String rest) {
	    if(!(area+first+rest).matches("[0-9]{10}")) {
	        return false;
	    }
	    
	    phone[0] = area;
	    phone[1] = first;
	    phone[2] = rest;
	    
	    return true;
	}
	
	/**
	 * Set the user's default latitude and longitude, returns false if either of 
	 * the latitude or longitude are incorrectly formatted.
	 * 
	 * @param lng the user's longitude
	 * @param lng the user's latitude
	 */
	public boolean setGeoPosition(String lat, String lng) {
	    String regex = "-?([0-9]{1,3})\\.([0-9]{3,6})";
	    
	    if(!lat.matches(regex) || !lng.matches(regex)) {
	        return false;
	    }
	    
	    start_location = new GeoPosition(
            Double.parseDouble(lat),
            Double.parseDouble(lng)
	    );
	    
	    return true;
	}
	
	/**
	 * Set the start/default zoom level for the user, returns false if the zoom
	 * level is out of range.
	 * 
	 * @param level the user's default level
	 */
	public boolean setDefaultZoom(int level) {
	    if(level < 4 || level > 23)
	        return false;
	    
	    start_zoom = level;
	    return true;
	}
	
	/**
	 * Add a garage sale to the one's that this user has created.
	 */
	public void addGarageSale(GarageSaleModel sale) {
	    sales.add(sale);
	}
	
	/**
	 * Add a rating to the one's that this use has created.
	 */
	public void addRating(RatingModel rating) {
	    ratings.add(rating);
	}
}
