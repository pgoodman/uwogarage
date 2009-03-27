package org.uwogarage.models;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Date;

import org.uwogarage.util.GeoPosition;
import org.uwogarage.util.Location;

/**
 * The GarageSaleModel class represents a garage sale in the Garage Sale system
 *
 * @version $Id$
 */

public class GarageSaleModel implements Model {

    private static final long serialVersionUID = 8274261212454764504L;
    
    final public UserModel user; // the user that created this garage sale    
    
    // the garage sale's categories
    final public ModelSet<CategoryModel> categories = new ModelSet<CategoryModel>();
    
    // the ratings that this user has made
    final public ModelSet<RatingModel> ratings = new ModelSet<RatingModel>();
    
    // the garage sale's date and time information
    final protected GregorianCalendar time;
    
	Location location; // the garage sale's location
	GeoPosition position; // the location's geo position
	
	String note = "", // the garage sale's note
	       positions[]; // this is out of laziness
	
	/**
	 * Constructor, set the user and time.
	 * @param u
	 */
	public GarageSaleModel(UserModel u) {
	    user = u;
	    time = new GregorianCalendar();
	}
	
	/**
	 * This method returns the garage sale's location
	 * @return the garage sale's location
	 */
	public Location getLocation() {
	    return location;
	}
	
	/**
	 * Get the sale's geo position
	 */
	public GeoPosition getGeoPosition() {
	    return position;
	}
	
	public String[] getGuiGeoPosition() {
	    return positions;
	}
	
	/**
	 * This method returns the garage sale's date
	 * @return the garage sale's date
	 */
	public GregorianCalendar getTime() {
	    return (GregorianCalendar) time.clone();
	}
	
	/**
	 * This method returns the garage sale's note
	 * @return the garage sale's note
	 */
	public String getNote() {
	    return note;
	}
	
	/**
	 * Get the rating of this garage sale.
	 * @return
	 */
	public float getRating() {
	    int size = ratings.size(),
	        sum = 0;
	    
	    if(size == 0)
	        return 0F;
	    
	    for(RatingModel rating : ratings)
	        sum += rating.rating;
	    
	    return sum / size;
	}
	
	/**
	 * Check if this garage sale has been rated yet.
	 * @return
	 */
	public boolean isRated() {
	    return ratings.size() > 0;
	}
	
	/**
	 * Get the average rating for this garage sale.
	 */
	
	/**
	 * This method sets the garage sale's location
	 * @param loc the garage sale's new default location
	 */
	public boolean setLocation(Location loc) {
	    if(null == loc)
	        return false;
	    
	    location = loc;
	    return true;
	}
	
	/**
	 * Set the date of the garage sale. Expects a date stored in a string formatted
	 * in the following way: yyyy MM dd hh mm a z. Date parsing is done using
	 * SimpleDateFormat.
	 * 
	 * @param date
	 */
	public boolean setTime(GregorianCalendar c) {
	    if(null == c)
	        return false;
	    
	    time.setTime(c.getTime());
	    return true;
	}
	public boolean setTime(Date d) {
	    
	    // get the start of today :D
	    GregorianCalendar today = new GregorianCalendar();
	    today.set(GregorianCalendar.HOUR, 0);
	    today.set(GregorianCalendar.MINUTE, 0);
	    
	    if(null == d || d.before(today.getTime()))
	        return false;
	    
	    // overwrite the time
	    time.setTime(d);
	    return true;
	}
	public boolean setTime(String s) {
	    try {
	        return setTime(
	            (new SimpleDateFormat("yyyy MM dd kk mm")).parse(s)
	        );
	    } catch(Exception e) {
	        return false;
	    }
	}
	
	/**
	 * This method sets the garage sale's note
	 * @param note the garage sale's note
	 */
	public boolean setNote(String n) {
	    n = (null == n) ? "" : n;
	    
	    if(n.length() > 200)
	        return false;
	    
	    note = n;
	    
	    return true;
	}
	
	/**
     * Set the user's default latitude and longitude, returns false if either of 
     * the latitude or longitude are incorrectly formatted.
     * 
     * @param lng the user's longitude
     * @param lng the user's latitude
     */
    public boolean setGeoPosition(String lat, String lng) {
        String regex = "-?([0-9]{1,3})\\.([0-9]{3,6})";
        
        if(!lat.matches(regex) || !lng.matches(regex)) {
            return false;
        }
        
        // lazy version for GUI :P
        positions = new String[] {lat, lng};
        
        // version for API
        position = new GeoPosition(
            Double.parseDouble(lat),
            Double.parseDouble(lng)
        );
        
        return true;
    }
    
    /**
     * Set the categories of this garage sale.
     * @param cs
     */
    public void setCategories(ModelSet<CategoryModel> cs) {
        categories.clear();
        categories.addAll(cs);
    }
}
