package org.uwogarage.controllers;

/**
 * The Controller class responds to calls from a View and manipulates the appropriate models 
 * in the datastore
 *
 * @author Nate Smith
 * @version Version 0.1
 */

public class Controller<T> {
	// instance variables ***************************
	private Collection<T> models;	// A collection of models this controller 
									// can manipulate

	/**
	 * Constructor for Controller
	 */
	public Controller()
	{
	}
	
	// CONTROLLER ACTIONS ***************************
	/**
	 * This displays the add view for a model
	 */
	public void add() {}
	/**
	 * This method loads a model with a specified id and it's respective edit 
	 * view.
	 * @param model_id the id of the model to edit
	 */
	public void edit(int model_id) {}
	/**
	 * This method inserts a new model into the datastore
	 */
	private void insert(G<T> generator) {}
	/**
	 * This method updates a model in the datastore
	 * @param f a function to manipulate the object
	 */
	private void update(F<T,I> f) {}
	/**
	 * This method removes a model from the datastore
	 * @param model the model to be removed
	 */
	private void remove(T model) {}
	/**
	 * This method filters the objects in the collection
	 */
	public void filter() {}
}
