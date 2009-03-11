package org.uwogarage.controllers;

import org.uwogarage.Dispatcher;
import org.uwogarage.models.UserModel;

/**
 * The UserController class responds to calls from a View and manipulates  
 * in UserModels in the datastore
 *
 * @author Nate Smith
 * @version $Id$
 */

public class UserController extends Controller<UserModel> {
	
	public UserController(Dispatcher d) {
        super(d);
        // TODO Auto-generated constructor stub
    }
	
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

    public void add() {
        // TODO Auto-generated method stub
        
    }

    public void edit(UserModel model) {
        // TODO Auto-generated method stub
        
    }
    
    protected void update(UserModel model) {
        // TODO Auto-generated method stub
        
    }
	
}
