package map;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Set;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import org.jdesktop.swingx.JXMapViewer;
import org.uwogarage.models.GarageSaleModel;

/**
 * MouseInputAdapter for use with the MapPanel class. It figures
 * out whether mouse clicks are on waypoints, and displays information
 * about their associated garage sales.
 * 
 * @author ECormie
 *
 */
public class MapMouseInputAdapter extends MouseInputAdapter {

	//the MapPanel whose map is using this MouseInputAdapter
	MapPanel parent;

	/**
	 * Constructor uses default superclass constructor, and sets
	 * the parent MapPanel.
	 * @param parent the MapPanel using this MouseInputAdapter
	 */
	MapMouseInputAdapter(MapPanel parent) {
		super();
		this.parent = parent;
	}

	/**
	 * When a point on the map is clicked, this handler figures out
	 * whether the point clicked was on a Waypoint. If so, it displays
	 * a window with information about the garage sale at that point.
	 */
	public void mouseClicked(MouseEvent e) {

		// Get the screen point of mouse click.
		Point pt = e.getPoint();

		// get the map of the parent MapPanel
		JXMapViewer map = parent.getMainMap();

		// get an iterator of the parent's waypoints
		Set<MapWaypoint> waypoints = parent.getWaypoints();
		Iterator<MapWaypoint> waypointIt = waypoints.iterator();

		//go through each waypoint
		while (waypointIt.hasNext()) {

			//get this waypoint
			MapWaypoint currentWaypoint = waypointIt.next();
							
			//get the pixel coordinates of this waypoint from the map.			
			Point2D point = map.getTileFactory().geoToPixel(
					currentWaypoint.getPosition(), map.getZoom());

			// adjust the pixel coordinates to their relative position on
			// screen.
			Rectangle bounds = map.getViewportBounds();
			int x = (int) (point.getX() - bounds.getX());
			int y = (int) (point.getY() - bounds.getY());

			// Create a bounding rectangle around the waypoint, and see if the
			// mouse click occured within its boundaries
			Rectangle rect = new Rectangle(x - 5, y - 5, 50, 50);
			if (rect.contains(pt)) {
				
				//get the garage sale associated with this waypoint
				GarageSaleModel sale = currentWaypoint.getGarageSale();
				
				//get various info about this garage sale
				String address = sale.getLocation().getStreet() + ", " + sale.getLocation().getCity() + ", " + sale.getLocation().getProvince();
				String seller = sale.user.getFirstName() + " " + sale.user.getLastName();
				
				//get phone number
				String[] phone = sale.user.getPhoneNumber();
				String phoneFull = "";
				for(int i = 0; i < phone.length; i++){					
					phoneFull += phone[i];					
					if(i != phone.length -1){
						phoneFull += "-";
					}
				}
	
				//create a new frame
				JFrame newframe = new JFrame("Garage Sale at " + address);
				newframe.setPreferredSize(new Dimension(260,110));
				
				//create a new panel
				JPanel panel = new JPanel();
				panel.setLayout(new GridLayout(4,1));
				
				//create JLabels for the above fields
				JLabel titleL = new JLabel("INFORMATION:");
				JLabel addressL = new JLabel("Address: " + address);
				JLabel sellerL = new JLabel("Seller:" + seller);
				JLabel phoneL = new JLabel("Phone Number: " + phoneFull);
	
				//add all these JLabels to the panel
				panel.add(titleL);
				panel.add(addressL);
				panel.add(sellerL);
				panel.add(phoneL);
				
				//give the panel some padding
				panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
				
				//display the frame
				newframe.getContentPane().add(panel);
				newframe.pack();
				newframe.setLocationRelativeTo(parent);
				newframe.setVisible(true);
			}
			
			

		}

	}

}
