package org.uwogarage.util;

/**
 * A Response is a generic container object, most useful for carrying a result
 * back from a View to a Controller.
 *
 * @author Nate Smith
 * @version Version 0.1
 */

public class Response<T> {
	
	T obj;	// The object containing the input
	
	/**
	 * Constructor for Response
	 */
	public Response(T o) {
	    obj = o;
	}
	
	/**
	 * This method returns the object contained in Response
	 * @return the object contained in this response
	 */
	public T getObject() {
	    return obj;
	}
}
