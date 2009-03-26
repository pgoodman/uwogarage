package map;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.TileFactory;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.mapviewer.WaypointPainter;
import org.jdesktop.swingx.mapviewer.WaypointRenderer;
import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.UserModel;

import org.uwogarage.util.GeoPosition;

import com.sun.java.swing.plaf.motif.MotifBorders.BevelBorder;

/**
 * Class represents a map to use with the GarageSale app.
 * @author Me
 *
 */

public class MapPanel extends JXMapKit{
	
	private static final long serialVersionUID = 1L;

	// Get us a map provider to get Google maps.    
    GoogleMapsProvider map = new GoogleMapsProvider();
    
    // From here, set things up to get the main map viewer using this provider as a source.
    TileFactoryInfo tileProviderInfo = map.getTileProviderInfo();
    TileFactory tileFactory = new DefaultTileFactory (tileProviderInfo);
    
    //holds the waypoints
    Set<Waypoint> waypoints = new HashSet<Waypoint>();
    
    //paints the waypoints
    WaypointPainter painter = new WaypointPainter();
	    
    
    /**
     * Constructor initializes a map using the default parameters
     * from a particular user.
     * @param user
     */
    public MapPanel(UserModel user){
    	this(user.getGeoPosition(),user.getDefaultZoom());
    }
    
    
    /**
     * Constructor initializes map with the user's defaults as parameters.
     * @param defaultPosition user's default starting GeoPosition
     * @param defaultZoom user's default zoom level
     */   
    public MapPanel(GeoPosition defaultPosition, int defaultZoom){
    	
    	super();
    	    		
    	//set various defaults
        this.setTileFactory(tileFactory);
        this.setCenterPosition(new MapGeoPosition(defaultPosition.getLatitude(),defaultPosition.getLongitude()));
        this.setZoom(defaultZoom);
                
        //get rid of the mini-map
        this.setMiniMapVisible(false);
        
        //set a size to a nice square
        this.setPreferredSize(new Dimension(300,300));
        
        
        //visuals:
        
        this.setBorder(BorderFactory.createEtchedBorder(0));
        
        //set the thing that paints the map's waypoints
        this.getMainMap().setOverlayPainter(painter);
    	
        
        //you can change what the waypoints look like with this
        /*
        painter.setRenderer(new WaypointRenderer() {
            public boolean paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint wp) {
                g.setColor(Color.GREEN);
                Ellipse2D.Double circle = new Ellipse2D.Double(10, 10, 5, 5);

                g.draw(circle);
                return true;
            }
        });
        */        
        
        
        //add a mouse listener to react when waypoints are clicked
        this.getMainMap().addMouseListener(new MapMouseInputAdapter(this));
        
    	
    }
    
    

    /**
     * Adds a garage sale as a waypoint on the map
     */
    public void addGarageSale(GarageSaleModel sale){
    	Waypoint newWaypoint = new Waypoint(sale.getGeoPosition().getLatitude(),sale.getGeoPosition().getLongitude());
    	
    	waypoints.add(newWaypoint);
    	
        painter.setWaypoints(waypoints);        

    }
    
    public Set<Waypoint> getWaypoints(){
    	return waypoints;
    }
    
    

    


	

}
