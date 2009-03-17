package org.uwogarage.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.uwogarage.util.GeoPosition;
import org.uwogarage.util.Location;

/**
 * The GarageSaleModel class represents a garage sale in the Garage Sale system
 *
 * @version $Id$
 */

public class GarageSaleModel implements Model {
    
    final UserModel user; // the user that created this garage sale
    final Calendar time; // the garage sale's date and time information
    ModelSet<CategoryModel> categories; // the garage sale's categories
	Location location; // the garage sale's location
	GeoPosition position; // the location's geo position
	String note = ""; // the garage sale's note
	
	int rating_sum = 0, // the sum of the ratings for this garage sale
	    num_ratings = 0; // the number of ratings cast
	
	public GarageSaleModel(UserModel u) {
	    user = u;
	    categories = new ModelSet<CategoryModel>();
	    time = Calendar.getInstance();
	}
	
	/**
	 * This method returns the garage sale's categories
	 * @return the garage sale's categories
	 */
	public ModelSet<CategoryModel> getCategories() {
	    return categories;
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
	
	/**
	 * This method returns the garage sale's date
	 * @return the garage sale's date
	 */
	public Calendar getTime() {
	    return time;
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
	    return num_ratings > 0 ? (rating_sum / num_ratings) : 0;
	}
	
	/**
	 * Get the user that owns this model.
	 */
	public UserModel getUser() {
	    return user;
	}
	
	/**
	 * Check if this garage sale has been rated yet.
	 * @return
	 */
	public boolean isRated() {
	    return num_ratings > 0;
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
	public boolean setTime(Calendar c) {
	    if(null == c)
	        return false;
	    
	    time.setTime(c.getTime());
	    return true;
	}
	public boolean setTime(Date d) {
	    
	    // get the start of today :D
	    Calendar today = Calendar.getInstance();
	    today.set(Calendar.HOUR, 0);
	    today.set(Calendar.MINUTE, 0);
	    
	    if(null == d || d.before(today.getTime()))
	        return false;
	    
	    // overwrite the time
	    time.setTime(d);
	    return true;
	}
	public boolean setTime(String s) {
	    try {
	        return setTime(
	            (new SimpleDateFormat("yyyy MM dd hh mm a z")).parse(s)
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
        categories = cs;
    }
}
