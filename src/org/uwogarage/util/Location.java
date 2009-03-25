package org.uwogarage.util;

import java.io.Serializable;
import java.util.Hashtable;

import org.uwogarage.util.functional.D;


/**
 * The Location class represents a location in the Garage Sale system
 *
 * TODO Test for invalid/out-of-range coordinates.
 *
 * @version $Id$
 */

public class Location implements Serializable {
    
    private static final long serialVersionUID = 31337L;
    
	String street; // the location's street address
	String province; // the location's province
	String city; // the location's city
	
	// province codes for various locations
	final static public String[] PROVINCE_CODES = {
        "AB", "BC", "MB", "NB", "NF", "NL", "NS", "ON", "PE", "QC", "SK"
    };
	
	// corresponding time zone codes for the province codes, if only Java had
	// JSON...
	final static public Hashtable<String,String> 
	PROVINCE_TIME_ZONE_CODES = ImmHashtable.getInstance(10, new D<Hashtable<String,String>>() {
	    public void call(Hashtable<String,String> table) {
	        table.put("AB", "MDT");
	        table.put("BC", "PDT");
	        table.put("MB", "CDT");
	        table.put("NB", "ADT");
	        table.put("NL", "NDT");
	        table.put("NS", "ADT");
	        table.put("ON", "EDT");
	        table.put("PE", "ADT");
	        table.put("QC", "EDT");
	        table.put("SK", "CST"); // most of Saskatchewan, at least
	    }
	});
	
	/**
	 * Constructor for Location, sets the street, province, city
	 * @param s the street of the location
	 * @param p the province of the location
	 * @param c the city of the location
	 */
	public Location(String s, String p, String c) throws Exception {
	    street = s;
	    province = p;
	    city = c;
	    
	    if(!isValidProvinceCode(p))
	        throw new Exception("Bad province.");
	}
	
	/**
	 * This method returns the street name of this location
	 * @return the street name of this location
	 */
	public String getStreet() {
	    return street;
	}

	/**
	 * This method returns the street name of this location
	 * @return the province of this location
	 */
	public String getProvince() {
	    return province;
	}

	/**
	 * This method returns the street name of this location
	 * @return this city of this location
	 */
	public String getCity() {
	    return city;
	}
	
	/**
	 * Check if a province code is valid.
	 * 
	 * @param code
	 * @return
	 */
	static public boolean isValidProvinceCode(String code) {
	    return PROVINCE_TIME_ZONE_CODES.containsKey(code);
	}
}
