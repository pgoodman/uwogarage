package org.uwogarage.models;

/**
 * The UserModel class represents a user in the Garage Sale system
 *
 * @author Nate Smith
 * @version $Id$
 */

public class UserModel implements Model {
    
	protected String first_name,		// the user's first name
	                 last_name,       // the user's last name
	                 pass,            // the user's pass
	                 user_id,         // the user's id
	                 phone;           // the user's phone
	
	protected double start_lat,       // the user's default latitude
	                 start_lng;      // the user's default longitude
	
	protected int start_zoom,         // the user's default zoom level
	              average_rating;     // the user's average rating
	
	
	protected ModelSet<RatingModel> ratings;  // the user's ratings
	protected ModelSet<GarageSaleModel> sales;  // the user's sales
	
	/**
	 * Get the user ID.
	 */
	public String getId() {
	    return user_id;
	}
	
	/**
     * Get the password.
     */
    public String getPass() {
        return pass;
    }
	
	/**
	 * This method will return the user's full name
	 * @return the user's full name.
	 */
	public String getFullName() {
	    return first_name +" "+ last_name;
	}
	
	/**
	 * This method will return the user's phone number
	 * @return the user's phone number
	 */
	public String getPhoneNumber() {
	    return phone;
	}
	
	/**
	 * This method will return the user's default latitude and longitude
	 * @return a tuple containing the user's latitude and longitude
	 */
	public double[] getDefaultLatLng() {
	    return new double[] {start_lat, start_lng};
	}

	/**
	 * This method will return the user's default zoom level
	 * @return the user's default zoom level
	 */
	public int getDefaultZoom() {
	    return start_zoom;
	}
	
	/**
	 * This method sets the user's first name
	 * @param firstName the user's first name
	 */
	public void setFirstName(String firstName) {
	    first_name = firstName;
	}
	
	/**
	 * This method sets the user's last name
	 * @param lastLame the user's last name
	 */
	public void setLastName(String lastName) {
	    last_name = lastName;
	}
	
	/**
	 * This method sets the user's phone number
	 * @param phoneNumber the user's phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
	    phone = phoneNumber;
	}
	
	/**
	 * This method sets the user's default latitude and longitude
	 * @param lng the user's longitude
	 * @param lng the user's latitude
	 */
	public void setDefaultLatLng(double lat, double lng) {
	    start_lat = lat;
	    start_lng = lng;
	}
	
	/**
	 * This method sets the user's default zoom level
	 * @param level the user's default level
	 */
	public void setDefaultZoom(int level) {
	    start_zoom = level;
	}
	
}
