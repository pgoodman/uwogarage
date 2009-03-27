package map;

import org.jdesktop.swingx.mapviewer.GeoPosition;

/**
 * This class renames GeoPosition to avoid conflicts between the mapviewer "GeoPosition" class and the
 * org.uwogarage.util "GeoPosition" class.
 * 
 * (I know this whole project was supposed to use the mapviewer's GeoPosition class... I didn't want to
 * change the other GeoPosition class right now since I don't know if everyone has the map libraries
 * working.)
 * 
 * @author ECormie
 *
 */
public class MapGeoPosition extends GeoPosition{
	
	/**
	 * Constructor just passes parameters to superclass.
	 * @param latitude
	 * @param longitude
	 */
	MapGeoPosition(double latitude, double longitude){
		super(latitude, longitude);
	}


}
