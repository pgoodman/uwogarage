package org.uwogarage.util;

/**
 * The WayPoint class represents a way point in the Garage Sale system
 *
 * @author Nate Smith
 * @version $Id$
 */

public class WayPoint implements Immutable {
    
	double latitude;		// the way point's latitude
	double longitude;		// the way point's longitude

	/**
	 * Constructor for Location, sets the latitude and longitude
	 * @param lat the way point's latitude
	 * @param lng the way point's longitude
	 */
	public WayPoint(double lat, double lng) {
	    latitude = lat;
	    longitude = lng;
	}
	
	/**
	 * This method returns the latitude of this way point
	 * @return the way point's latitude
	 */
	public double getLat() {
	    return latitude;
	}

	/**
	 * This method returns the longitude of this way point
	 * @return the way point's longitude
	 */
	public double getLng() {
	    return longitude;
	}
}
