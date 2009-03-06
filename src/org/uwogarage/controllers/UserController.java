package org.uwogarage.controllers;

/**
 * The UserController class responds to calls from a View and manipulates  
 * in UserModels in the datastore
 *
 * @author Nate Smith
 * @version Version 0.1
 */

public class UserController extends Controller {

	/**
	 * Constructor for UserController
	 */
	public UserController()
	{
	}
	
	// CONTROLLER ACTIONS ***************************
	/**
	 * This method displays the appropriate view after user validation
	 * @param b whether or not the user is valid
	 */
	public void validateUser(boolean b) {}
	/**
	 * This method displays the view for updating a user's password and then 
	 * updates the UserModel
	 */
	public void updatePassword() {}
	/**
	 * This method allows access to the appropriate mode for the current user
	 */
	public void allowAccess() {}
	/**
	 * This method denies access to the appropriate mode for the current user
	 */
	public void denyAccess() {}
	
}
