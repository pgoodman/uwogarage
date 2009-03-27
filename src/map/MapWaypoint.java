package map;

import org.jdesktop.swingx.mapviewer.Waypoint;
import org.uwogarage.models.GarageSaleModel;

/**
 * Class represents a Waypoint for use with the garagesale map: it contains a reference to
 * the GarageSaleModel associated with its location.
 * 
 * @author ECormie
 *
 */
public class MapWaypoint extends Waypoint{
	
	//the GarageSale this Waypoint is pointing to
	GarageSaleModel sale;
	
	/**
	 * Constructor creates a Waypoint from a GarageSaleModel.
	 * @param sale the GarageSaleModel that this Waypoint represents
	 */
	MapWaypoint(GarageSaleModel sale){
		super(sale.getGeoPosition().getLatitude(),sale.getGeoPosition().getLongitude());
		this.sale = sale;		
	}
	
	/**
	 * Gets the GarageSaleModel associated with this Waypoint
	 * @return this Waypoint's GarageSaleModel
	 */
	public GarageSaleModel getGarageSale(){
		return sale;
	}

}
