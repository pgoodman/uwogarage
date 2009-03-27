package map;

import java.awt.Dimension;

import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.TileFactory;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;
import org.jdesktop.swingx.mapviewer.WaypointPainter;
import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.UserModel;

import org.uwogarage.util.GeoPosition;

/**
 * Class represents a panel with a map in it, which stores the locations
 * of GarageSaleModels as Waypoints.
 * @author ECormie
 */

public class MapPanel extends JXMapKit{
	
	private static final long serialVersionUID = 1L;

	// Get us a map provider to get Google maps.    
	private GoogleMapsProvider map = new GoogleMapsProvider();
    
    // From here, set things up to get the main map viewer using this provider as a source.
	private TileFactoryInfo tileProviderInfo = map.getTileProviderInfo();
	private TileFactory tileFactory = new DefaultTileFactory (tileProviderInfo);
    
    //holds the waypoints
	private Set<MapWaypoint> waypoints = new HashSet<MapWaypoint>();
    
    //paints the waypoints
	private WaypointPainter painter = new WaypointPainter();
	        
    
    /**
     * Constructor initializes a map using the default position and zoom
     * from the parameter user.
     * @param user the user whose defaults should be used
     */
    public MapPanel(UserModel user){
    	//call the other constructor
    	this(user.getGeoPosition(),user.getDefaultZoom());
    }
    
    
    /**
     * Constructor initializes a MapPanel with the given defaults as parameters.
     * @param defaultPosition user's default starting GeoPosition
     * @param defaultZoom user's default zoom level
     */   
    private MapPanel(GeoPosition defaultPosition, int defaultZoom){
    	
    	super();
    	    		
    	//set various defaults
        this.setTileFactory(tileFactory);
        this.setCenterPosition(new MapGeoPosition(defaultPosition.getLatitude(),defaultPosition.getLongitude()));
        this.setZoom(defaultZoom);
                        
        //set the thing that paints the map's waypoints
        this.getMainMap().setOverlayPainter(painter);
    	     
        //add a mouse listener to react when waypoints are clicked
        this.getMainMap().addMouseListener(new MapMouseInputAdapter(this));
        
        //----------visuals-------------
        
        //get rid of the mini-map
        this.setMiniMapVisible(false);
        
        //set the default size to a nice square
        this.setPreferredSize(new Dimension(300,300));
        
        //add a border        
        this.setBorder(BorderFactory.createEtchedBorder(0));
                
    	
    }
    
    
    /**
     * Adds a garage sale as a waypoint on the map
     * @param sale the GarageSaleModel to add to the map as a waypoint
     */
    public void addGarageSale(GarageSaleModel sale){  
    	try{
    		//Create a new MapWaypoint, add it to the waypoint set and then the the map's waypoint painter
    		MapWaypoint newWaypoint = new MapWaypoint(sale);    	
    		waypoints.add(newWaypoint);    	
    		painter.setWaypoints(waypoints);
    		
    		//this causes the map to refresh and show new waypoints
    		this.setVisible(false);
    		this.setVisible(true);
    	}
    	//In the event of an exception, just don't add anything to the map.
    	catch(Exception e){    		
    	}
    }
    
    /**
     * Gets the set of waypoints
     * @return the set of MapWaypoints that are being displayed by this map
     */
    public Set<MapWaypoint> getWaypoints(){
    	return waypoints;
    }
    
    	

}
