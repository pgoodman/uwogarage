package org.uwogarage.controllers;

import java.util.Collection;
import org.uwogarage.Dispatcher;

/**
 * The Controller class responds to calls from a View and manipulates the 
 * appropriate models in the data store
 *
 * @author Nate Smith
 * @version $Id$
 */

abstract public class Controller<T> {
    
    // A collection of models this controller can manipulate
	protected Collection<T> models;
	
	// give the controller the ability to call the other controllers
	protected Dispatcher dispatch;
	
	/**
	 * Constructor, bring in the dispatcher.
	 * @param d
	 */
	public Controller(Dispatcher d) {
	    dispatch = d;
	}
	
	/**
	 * This displays the add view for a model.
	 */
	abstract public void add();
	
	/**
	 * This method shows the view to edit a given model.
	 */
	abstract public void edit(T model);
	
	/**
     * This method updates a model in the data store
     * @param f a function to manipulate the object
     */
    abstract protected void update(T model);
	
	
	
	
}
