package org.uwogarage.util;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * The Location class represents a location in the Garage Sale system
 *
 * TODO Test for invalid/out-of-range coordinates.
 *
 * @version $Id$
 */

public class Location implements Serializable {
    
    private static final long serialVersionUID = -5873674039555881531L;
    
    String street; // the location's street address
	String province; // the location's province
	String city; // the location's city
	
	// province codes for various locations
	final static public String[] PROVINCE_CODES = {
        "AB", "BC", "MB", "NB", "NF", "NL", "NS", "ON", "PE", "QC", "SK"
    };
	
	// corresponding time zone codes for the province codes, if only Java had
	// JSON...
	final static public Hashtable<String,String> PROVINCE_TIME_ZONE_CODES;
	
	// initialize static components of class
	static {
	    PROVINCE_TIME_ZONE_CODES = new Hashtable<String,String>();
	    PROVINCE_TIME_ZONE_CODES.put("AB", "MDT");
	    PROVINCE_TIME_ZONE_CODES.put("BC", "PDT");
	    PROVINCE_TIME_ZONE_CODES.put("MB", "CDT");
	    PROVINCE_TIME_ZONE_CODES.put("NB", "ADT");
	    PROVINCE_TIME_ZONE_CODES.put("NL", "NDT");
	    PROVINCE_TIME_ZONE_CODES.put("NS", "ADT");
	    PROVINCE_TIME_ZONE_CODES.put("ON", "EDT");
	    PROVINCE_TIME_ZONE_CODES.put("PE", "ADT");
	    PROVINCE_TIME_ZONE_CODES.put("QC", "EDT");
	    PROVINCE_TIME_ZONE_CODES.put("SK", "CST"); // most of Saskatchewan, at least
	}
	
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
