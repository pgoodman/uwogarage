package org.uwogarage.models;

/**
 * The UserModel class represents a user in the Garage Sale system
 *
 * @version $Id$
 */

public class UserModel implements Model {
    
	protected String first_name,      // the user's first name
	                 last_name,       // the user's last name
	                 pass = "aaa",    // the user's pass, default 'aaa'
	                 user_id,         // the user's id
	                 phone;           // the user's phone
	
	protected double start_lat,       // the user's default latitude
	                 start_lng;       // the user's default longitude
	
	protected boolean reset_pass = true; // was the user's password was reset
	
	protected int start_zoom,         // the user's default zoom level
	              average_rating;     // the user's average rating
	
	
	protected ModelSet<RatingModel> ratings; // the user's ratings
	protected ModelSet<GarageSaleModel> sales; // the user's sales
	
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
	 * This method will return the user's full name.
	 * 
	 * @return the user's full name.
	 */
	public String getFullName() {
	    return first_name +" "+ last_name;
	}
	
	/**
	 * This method will return the user's phone number.
	 * 
	 * @return the user's phone number
	 */
	public String getPhoneNumber() {
	    return phone;
	}
	
	/**
	 * This method will return the user's default latitude and longitude.
	 * 
	 * @return a tuple containing the user's latitude and longitude
	 */
	public double[] getDefaultLatLng() {
	    return new double[] {start_lat, start_lng};
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
	 * Check if the user's password should be changed, i.e. the user was just
	 * created or the user's password was just reset by the administrator.
	 */
	public boolean hasDefaultPass() {
	    return reset_pass;
	}
	
	/**
	 * Set the password. Returns false if the password is incorrectly formatted.
	 */
	public boolean setPassword(String p) {
	    
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
	    if(!n.matches("[a-zA-Z]{1, 20}")) {
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
	    if(!n.matches("[a-zA-Z-]{1, 20}")) {
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
	public boolean setPhoneNumber(String n) {
	    if(!n.matches("[0-9]{10}")) {
	        return false;
	    }
	    
	    phone = n;
	    return true;
	}
	
	/**
	 * Set the user's default latitude and longitude, returns false if either of 
	 * the latitude or longitude are incorrectly formatted.
	 * 
	 * @param lng the user's longitude
	 * @param lng the user's latitude
	 */
	public boolean setDefaultLatLng(String lat, String lng) {
	    String regex = "-?([0-9]{1,3})\\.([0-9]{3,6})";
	    
	    if(!lat.matches(regex) || !lng.matches(regex)) {
	        return false;
	    }
	    
	    start_lat = Double.parseDouble(lat);
	    start_lng = Double.parseDouble(lng);
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
	
}
