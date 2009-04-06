package org.uwogarage.models;

/**
 * The UserModel class represents a user in the Garage Sale system
 *
 * @version $Id$
 * @author Randy Sousa
 */

public class UserModel implements Model {
    
    private static final long serialVersionUID = -6386361639399051382L;

    protected String first_name = "", // the user's first name
	                 last_name = "", // the user's last name
	                 pass = "aaa", // the user's pass, default 'aaa'
	                 user_id = "", // the user's id
	                 phone[] = {"", "", ""}; // the user's phone
	
    // the start geo position coordinates for this user
	protected String start_coords[] = {"0.000", "0.000"};
	
	protected boolean reset_pass = true; // was the user's password was reset
	
	protected int start_zoom = 15; // the user's default zoom level
	
	// the user's ratings
	final public ModelSet<RatingModel> ratings = new ModelSet<RatingModel>();
	
	// the user's sales
	final public ModelSet<GarageSaleModel> sales = new ModelSet<GarageSaleModel>();
	
	/**
	 * Get the user ID.
	 * @return the user id
	 */
	public String getId() {
	    return user_id;
	}
	
	/**
     * Get the password.
     * @return the password
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
	 * @return the user's default latitude
	 */
	public String[] getStartCoords() {
	    return start_coords;
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
	 * @return the user's average garage sale rating
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
	 * Check if the user's password should be changed, i.e. the user was just
	 * created or the user's password was just reset by the administrator.
	 * @return true if the user's password should be reset
	 */
	public boolean isUsingDefaultPass() {
	    return reset_pass;
	}
	
	/**
	 * Set whether or not this user is using the default password.
	 * @param r whether or not the user is using the default password
	 */
	public void setUsingDefaultPass(boolean r) {
	    reset_pass = r;
	}
	
	/**
	 * Set the user id.
	 * @return true if the user id was valid and set
	 */
	public boolean setUserId(String p) {
	    if(!p.matches("[0-9a-zA-Z]{4}")) {
            return false;
        }
	    
        user_id = p;
        return true;
	}
	
	/**
	 * Set the password. 
	 * @return false if the password is incorrectly formatted.
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
	 * Set the user's first name
	 * 
	 * @param n The user's first name
	 * @return false if the name is incorrectly formatted.
	 */
	public boolean setFirstName(String n) {
	    if(!n.matches("[a-zA-Z]{1,20}")) {
	        return false;
	    }
	    
	    first_name = n;
	    return true;
	}
	
	/**
	 * Set the user's last name
	 * 
	 * @param n The user's last name
	 * @return false if the name is incorrectly formatted.
	 */
	public boolean setLastName(String n) {
	    if(!n.matches("[a-zA-Z-]{1,20}")) {
            return false;
        }
        
        last_name = n;
        return true;
	}
	
	/**
	 * Set the user's phone number
	 * 
	 * @param phoneNumber the user's phone number
	 * @return false if the supplied number is incorrectly formatted.
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
	 * Set the user's default latitude and longitude
	 * 
	 * @param lng the user's longitude
	 * @param lng the user's latitude
	 * @return false if either of the latitude or longitude are incorrectly formatted.
	 */
	public boolean setGeoPosition(String lat, String lng) {
	    String regex = "-?([0-9]{1,3})\\.([0-9]{3,6})";
	    
	    if(!lat.matches(regex) || !lng.matches(regex)) {
	        return false;
	    }
	    
	    double dlat = Double.parseDouble(lat),
	    	   dlng = Double.parseDouble(lng);
	    
	    if(Math.abs(dlat) > 90D && Math.abs(dlng) > 180D)
	    	return false;
	    
	    start_coords[0] = lat;
	    start_coords[1] = lng;
	    
	    return true;
	}
	
	/**
	 * Set the start/default zoom level for the user,
	 * 
	 * @param level the user's default level
	 * @return false if the zoom level is out of range.
	 */
	public boolean setDefaultZoom(int level) {
	    if(level < 1 || level > 15)
	        return false;
	    
	    start_zoom = level;
	    return true;
	}
	
	/**
	 * Add a garage sale to the one's that this user has created.
	 * @param the sale to be added
	 */
	public void addGarageSale(GarageSaleModel sale) {
	    sales.add(sale);
	}
	
	/**
	 * Add a rating to the one's that this use has created.
	 * @param the rating to be added
	 */
	public void addRating(RatingModel rating) {
	    ratings.add(rating);
	}
}
