package org.uwogarage.util;

/**
 * The Location class represents a location in the Garage Sale system
 *
 * @author Nate Smith
 * @version $Id$
 */

public class Location {
    
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
	    street = s;
	    province = p;
	    city = c;
	}
	
	// ACCESSOR METHODS ***************************
	/**
	 * This method returns the street name of this location
	 * @return the street name of this location
	 */
	public String getStreet() {
	    return street;
	}

	/**
	 * This method returns the street name of this location
	 * @return the province of this location
	 */
	public String getProvince() {
	    return province;
	}

	/**
	 * This method returns the street name of this location
	 * @return this city of this location
	 */
	public String getCity() {
	    return city;
	}

	/**
	 * This method generates and returns the WayPoint for this location
	 * @return the way point for this location
	 */
	public WayPoint getWayPoint() {
	    return way_point;
	}
}
