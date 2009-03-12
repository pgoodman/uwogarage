package org.uwogarage.controllers;

import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.F0;
import org.uwogarage.views.LoginView;
import org.uwogarage.views.UpdatePasswordView;
import org.uwogarage.views.View;

/**
 * The UserController class responds to calls from a View and manipulates  
 * in UserModels in the data store
 *
 * @version $Id$
 */
public class UserController extends Controller<UserModel> {
    
    public UserController() {
        
        UserModel test_user = new UserModel();
        test_user.setFirstName("Peter");
        test_user.setLastName("Goodman");
        test_user.setUserId("aaaa");
        test_user.setPassword("aaa");
        test_user.setPhoneNumber("5199330204");
        
        models.add(test_user);
    }
    
    /**
     * Show the login view.
     */
    public void login() {
        View.show(LoginView.view(models, new D<UserModel>() {
            public void call(UserModel user) {
                
                // set the user as the current user that is logged in, thus
                // making it available to all controllers
                logged_user = user;
                
                // this is either the first time logging in or the admin reset
                // this user's password, show the view to change passwords
                if(user.hasDefaultPass()) {
                    updatePassword();
                
                // no password reset required, show the now logged in user the
                // main menu
                } else {
                    // TODO Go to some sort of main menu
                }
            }
        }));
    }
    
    /**
     * Display the view for updating a user's password and then updates the 
     * UserModel
     */
    public void updatePassword() {
        View.show(UpdatePasswordView.view(logged_user, new F0() {
            
            // the password has been updated, go back to the main menu
            public void call() {
                // TODO Go to some sort of main menu
            }
        }));
    }
}
