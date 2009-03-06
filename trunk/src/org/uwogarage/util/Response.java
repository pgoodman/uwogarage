package org.uwogarage.util;

/**
 * The Response is sent back from a view containing user input
 *
 * @author Nate Smith
 * @version Version 0.1
 */

public class Response<T> {
	// instance variables ***************************
	T obj;	// The object containing the input
	
	/**
	 * Constructor for Response
	 */
	public Response()
	{
	}
	
	/**
	 * This method returns the object contained in Response
	 * @return the object contained in this response
	 */
	public T getObject() {}
}
