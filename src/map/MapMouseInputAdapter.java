package map;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.swingx.JXMapViewer;
import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.ModelSet;
import org.uwogarage.util.functional.D;

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
	private MapPanel parent;
	
	private D<ModelSet<GarageSaleModel>> on_click_responder;
	private ModelSet<GarageSaleModel> responder_set = new ModelSet<GarageSaleModel>();

	/**
	 * Constructor uses default superclass constructor, and sets
	 * the parent MapPanel.
	 * @param parent the MapPanel using this MouseInputAdapter
	 * @param r the responder called that let the main GUI build a window on demand.
	 */
	public MapMouseInputAdapter(MapPanel parent, D<ModelSet<GarageSaleModel>> r) {
		super();
		this.parent = parent;
		on_click_responder = r;
	}

	/**
	 * When a point on the map is clicked, this handler figures out
	 * whether the point clicked was on a Waypoint. If so, it displays
	 * a window with information about the garage sale at that point.
	 */
	public void mouseClicked(MouseEvent e) {
	    
	    // get the map of the parent MapPanel
        JXMapViewer map = parent.getMainMap();
	    
		// Get the screen point of mouse click.
		Point pt = e.getPoint();
		
		Point2D point;
		Rectangle rect,
                  bounds = map.getViewportBounds();
		
		//go through each waypoint
		responder_set.clear();
		for(MapWaypoint currentWaypoint : parent.getWaypoints()) {
					    
			//get the pixel coordinates of this waypoint from the map.			
			point = map.getTileFactory().geoToPixel(
			    currentWaypoint.getPosition(), 
			    map.getZoom()
			);

			// adjust the pixel coordinates to their relative position on
			// screen.
			int x = (int) (point.getX() - bounds.getX());
			int y = (int) (point.getY() - bounds.getY());

			// Create a bounding rectangle around the waypoint, and see if the
			// mouse click occurred within its boundaries. The waypoint icon
			// is 20x34, and the point we end up getting is at the base of its
			// tip, hence -10 and -34
			rect = new Rectangle(x - 10, y - 34, 20, 34);
			
			if (rect.contains(pt))
			    responder_set.add(currentWaypoint.getGarageSale());
			
			/*	
			//get the garage sale associated with this waypoint
			sale = ;
			
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
			newframe.setVisible(true);*/
		}
		
		// only call the responder if necessary
		if(responder_set.size() > 0)
		    on_click_responder.call(responder_set);
	}
}
