package org.uwogarage.controllers;

import javax.swing.JFrame;
import org.uwogarage.Dispatcher;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.views.LoginView;

/**
 * The UserController class responds to calls from a View and manipulates  
 * in UserModels in the datastore
 *
 * @author Nate Smith
 * @version $Id$
 */
public class UserController extends Controller<UserModel> {
	
	public UserController(Dispatcher d, JFrame f) {
        super(d, f);
    }
	
    /**
	 * Show the login view.
	 */
	public void login() {
	    view(new LoginView(), new D<UserModel>() {
            public void call(UserModel arg1) {
                // TODO Check if login info is correct and if so show something
                //      different.
            }	        
	    });
	}
	
	/**
	 * Validate a user's login information.
	 */
	protected boolean loginInfoIsValid() {
	    return false;
	}
	
	/**
	 * Display the view for updating a user's password and then updates the 
	 * UserModel
	 */
	public void updatePassword() {}
	
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
