package map;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.MouseInputAdapter;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.mapviewer.Waypoint;

public class MapMouseInputAdapter extends MouseInputAdapter {

	MapPanel parent;

	MapMouseInputAdapter(MapPanel parent) {
		super();
		this.parent = parent;
	}

	public void mouseClicked(MouseEvent e) {

		// Get the screen point of mouse click.
		Point pt = e.getPoint();

		// get the map of the parent MapPanel
		JXMapViewer map = parent.getMainMap();

		// get an iterator of the parent's waypoints
		Set<Waypoint> waypoints = parent.getWaypoints();
		Iterator<Waypoint> waypointIt = waypoints.iterator();

		//go through each waypoint
		while (waypointIt.hasNext()) {

			//get this waypoint
			Waypoint currentWaypoint = waypointIt.next();
			
			// Get the pixel coordinates of this waypoint from the map.
			
			Point2D point = map.getTileFactory().geoToPixel(
					currentWaypoint.getPosition(), map.getZoom());

			// Adjust the pixel coordinates to their relative position on
			// screen.

			Rectangle bounds = map.getViewportBounds();
			int x = (int) (point.getX() - bounds.getX());
			int y = (int) (point.getY() - bounds.getY());

			// Create a bounding rectangle around the waypoint, and see if the
			// mouse click occured
			// within its boundaries.

			Rectangle rect = new Rectangle(x - 5, y - 5, 50, 50);
			if (rect.contains(pt)) {
				JFrame newframe = new JFrame("Waypoint");
				JLabel newlabel = new JLabel("Waypoint at (43.005, -81.275)");
				newframe.getContentPane().add(newlabel);
				newframe.pack();
				newframe.setVisible(true);
			}

		}

	}

}
