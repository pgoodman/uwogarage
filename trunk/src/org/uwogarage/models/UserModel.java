package org.uwogarage.models;

/**
 * The UserModel class represents a user in the Garage Sale system
 *
 * @author Nate Smith
 * @version Version 0.1
 */

public class UserModel implements Model {
	// instance variables ***************************
	String first_name;		// the user's first name
	String last_name;       // the user's last name
	String pass;            // the user's pass
	String user_id;         // the user's id
	String phone;           // the user's phone
	double start_lat;       // the user's default latitude
	double start_long;      // the user's default longitude
	int start_zoom;         // the user's default zoom level
	RatingModel[] ratings;  // the user's ratings
	GarageSaleModel sales;  // the user's sales
	int average_rating;     // the user's average rating

	/**
	 * Constructor for UserModel
	 */
	public UserModel()
	{
	}
	
	// ACCESSOR METHODS ***************************
	/**
	 * This method will return the user's full name
	 * @return the user's full name.
	 */
	public String getFullName() {}
	
	/**
	 * This method will return the user's phone number
	 * @return the user's phone number
	 */
	public String getPhoneNumber() {}
	
	/**
	 * This method will return the user's default latitude and longitude
	 * @return a tuple containing the user's latitude and longitude
	 */
	public Tuple<Integer,Integer> getDefaultLatLng() {}

	/**
	 * This method will return the user's default zoom level
	 * @return the user's default zoom level
	 */
	public int getDefaultZoom() {}
	
	// MUTATOR METHODS ***************************
	/**
	 * This method sets the user's first name
	 * @param firstName the user's first name
	 */
	public void setFirstName(String firstName) {}
	/**
	 * This method sets the user's last name
	 * @param lastLame the user's last name
	 */
	public void setLastName(String lastName) {}
	/**
	 * This method sets the user's phone number
	 * @param phoneNumber the user's phone number
	 */
	public void setPhoneNumber(String phoneNumber) {}
	/**
	 * This method sets the user's default latitude and longitude
	 * @param lng the user's longitude
	 * @param lng the user's latitude
	 */
	public void setDefaultLatLng(int lat, int lng) {}
	/**
	 * This method sets the user's default zoom level
	 * @param level the user's default level
	 */
	public void setDefaultZoom(int level) {}
	
}
