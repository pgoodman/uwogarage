package org.uwogarage.controllers;

import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.F0;
import org.uwogarage.views.LoginView;
import org.uwogarage.views.TabView;
import org.uwogarage.views.UpdatePasswordView;
import org.uwogarage.views.UserControlPanelView;
import org.uwogarage.views.UserInfoView;
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
        test_user.setFirstName("Test");
        test_user.setLastName("User");
        test_user.setUserId("aaaa");
        test_user.setPassword("aaa");
        test_user.setPhoneNumber("519", "933", "0204");
        
        models.add(test_user);
    }
    
    /**
     * Add a user.
     */
    public void add() {
        
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
                    controlPanel();
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
                controlPanel();
            }
        }));
    }
    
    /**
     * Display the main user control panel.
     */
    public void controlPanel() {
        View.show(UserControlPanelView.view(logged_user,
            new F0() { public void call() { stats(); } },
            new F0() { public void call() { d.garage_sale.add(); } },
            new F0() { public void call() { d.garage_sale.bulkAdd(); } },
            new F0() { public void call() { d.garage_sale.manage(); } },
            new F0() { public void call() { } }
        ));
    }
    
    /**
     * Show this user some information/statistics about their activity.
     */
    public void stats() {
        TabView.show(UserInfoView.view(logged_user));
    }
}
