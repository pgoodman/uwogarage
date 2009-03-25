package org.uwogarage.util;

import java.io.Serializable;

/**
 * A coordinate in the real world, composed of a latitude and a longitude.
 * 
 * !!! This is temporary until the GeoPosition of JXMapViewer replaces it.
 *
 * @version $Id$
 */

public class GeoPosition implements Serializable {
    
    private static final long serialVersionUID = 1217728619553425334L;
    
    private double latitude = 0D, // the way point's latitude
	               longitude = 0D; // the way point's longitude
	
	/**
	 * Constructor for Location, sets the latitude and longitude
	 * @param lat the way point's latitude
	 * @param lng the way point's longitude
	 */
	public GeoPosition(double lat, double lng) {
	    latitude = lat;
	    longitude = lng;
	}
	
	/**
	 * This method returns the latitude of this way point
	 * @return the way point's latitude
	 */
	public double getLatitude() {
	    return latitude;
	}

	/**
	 * This method returns the longitude of this way point
	 * @return the way point's longitude
	 */
	public double getLongitude() {
	    return longitude;
	}
}
