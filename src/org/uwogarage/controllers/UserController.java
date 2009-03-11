package org.uwogarage.controllers;

import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.views.LoginView;
import org.uwogarage.views.UpdatePasswordView;
import org.uwogarage.views.View;

/**
 * The UserController class responds to calls from a View and manipulates  
 * in UserModels in the data store
 *
 * @author Nate Smith
 * @version $Id$
 */
public class UserController extends Controller<UserModel> {
	
    /**
	 * Show the login view.
	 */
    public void login() {
        
        View.replace(LoginView.view(models, new D<UserModel>() {
	        public void call(UserModel user) {
	            
	            // set the user as the current user that is logged in, thus
	            // making it available to all controllers
	            logged_user = user;
	            
	            // this is either the first time logging in or the admin reset
	            // this user's password, show the view to change passwords
                if(user.resetPassword()) {
                    updatePassword();
                } else {
                    
                }
            }
        }));
    }
	
	/**
	 * Display the view for updating a user's password and then updates the 
	 * UserModel
	 */
	public void updatePassword() {
	    View.replace(UpdatePasswordView.view(logged_user));
	}
	
	/**
	 * Display the view to add a user.
	 */
    public void add() {
        // TODO Auto-generated method stub
    }
    
    /**
     * Display the view to edit a user.
     */
    public void edit(UserModel model) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * Update a particular user model.
     */
    protected void update(UserModel model) {
        // TODO Auto-generated method stub
    }
}
