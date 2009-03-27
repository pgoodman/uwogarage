package map;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.WaypointPainter;

import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;

//import org.uwogarage.util.GeoPosition;
import org.jdesktop.swingx.mapviewer.GeoPosition;

/**
 * Class represents a panel with a map in it, which stores the locations
 * of GarageSaleModels as Waypoints.
 * 
 * @author ECormie
 */
public class MapPanel extends JXMapKit {
	
	private static final long serialVersionUID = 1L;
    
    //holds the waypoints
	private Set<MapWaypoint> waypoints = new HashSet<MapWaypoint>();
    
    //paints the waypoints
	private WaypointPainter<JXMapViewer> painter = new WaypointPainter<JXMapViewer>();
	
    
    /**
     * Constructor initializes a map using the default position and zoom
     * from the parameter user.
     * @param user the user whose defaults should be used
     */ 
	public MapPanel(UserModel user, D<Set<GarageSaleModel>> d){
    	
    	super();
    	    		
    	//get the user's coordinates
    	String[] coords = user.getStartCoords();
    	GeoPosition defaultPosition = new GeoPosition(Double.parseDouble( coords[0]), Double.parseDouble( coords[1]));
    	GoogleMapsProvider map = new GoogleMapsProvider();
    	
    	//get the user's default zoom level
    	int defaultZoom = user.getDefaultZoom();
    	
    	//set various defaults
        this.setTileFactory(new DefaultTileFactory(map.getTileProviderInfo()));
        this.setCenterPosition(defaultPosition);
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
    		// Create a new MapWaypoint, add it to the waypoint set and then 
    	    // the the map's waypoint painter
    	    MapWaypoint point = new MapWaypoint(sale);
    	    waypoints.add(point);
    		painter.getWaypoints().add(point);
    		
    		//this causes the map to refresh and show new waypoints
    		this.setVisible(false);
    		this.setVisible(true);
    	}
    	//In the event of an exception, just don't add anything to the map.
    	catch(Exception e){
    	    
    	}
    }
    
    /**
     * Return the set of custom waypoints.
     * @return
     */
    public Set<MapWaypoint> getWaypoints() {
        return waypoints;
    }
}
