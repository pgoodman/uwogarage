package map;

import org.jdesktop.swingx.mapviewer.GeoPosition;

/**
 * This is a wrapper class, to avoid conflicts between the mapviewer "GeoPosition" class and the
 * org.uwogarage.util GeoPosition class.
 * @author Me
 *
 */
public class MapGeoPosition extends GeoPosition{
	
	MapGeoPosition(double latitude, double longitude){
		super(latitude, longitude);
	}


}
