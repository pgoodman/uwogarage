package org.uwogarage.util;

/**
 * The WayPoint class represents a way point in the Garage Sale system
 *
 * @author Nate Smith
 * @version Version 0.1
 */

public class WayPoint implements Immutable {
	// instance variables ***************************
	double latitude;		// the way point's latitude
	double longitude;		// the way point's longitude

	/**
	 * Constructor for Location, sets the latitude and longitude
	 * @param lat the way point's latitude
	 * @param lng the way point's longitude
	 */
	public Location(double lat, double lng)
	{
	}
	
	// ACCESSOR METHODS ***************************
	/**
	 * This method returns the latitude of this way point
	 * @return the way point's latitude
	 */
	public double getLat() {}

	/**
	 * This method returns the longitude of this way point
	 * @return the way point's longitude
	 */
	public double getLng() {}
}
