package org.uwogarage.controllers;

import java.util.Collection;

import javax.swing.JFrame;
import org.uwogarage.Dispatcher;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.gui.SimpleGui.content;
import org.uwogarage.views.View;

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
	
	/**
	 * This method inserts a new model into the data tore
	 */
	protected void insert(T model) {
	    if(!models.contains(model)) {
	        models.add(model);
	    }
	}
	
	/**
	 * This method removes a model from the data store
	 * @param model the model to be removed
	 */
	protected boolean remove(T model) {
	    return models.remove(model);
	}
}
