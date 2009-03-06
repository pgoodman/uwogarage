package org.uwogarage.util;

/**
 * The Location class represents a location in the Garage Sale system
 *
 * @author Nate Smith
 * @version Version 0.1
 */

public class Location implements Immutable {
	// instance variables ***************************
	WayPoint way_point; // the location's way point
	String street;		// the location's street address
	String province;	// the location's province
	String city;		// the location's city

	/**
	 * Constructor for Location, sets the street, province, city
	 * @param s the street of the location
	 * @param p the province of the location
	 * @param c the city of the location
	 */
	public Location(String s, String p, String c)
	{
	}
	
	// ACCESSOR METHODS ***************************
	/**
	 * This method returns the street name of this location
	 * @return the street name of this location
	 */
	public int getStreet() {}

	/**
	 * This method returns the street name of this location
	 * @return the province of this location
	 */
	public int getProvince() {}

	/**
	 * This method returns the street name of this location
	 * @return this city of this location
	 */
	public int getCity() {}

	/**
	 * This method generates and returns the WayPoint for this location
	 * @return the way point for this location
	 */
	public int getWayPoint() {}
}
