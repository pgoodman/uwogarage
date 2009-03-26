package org.uwogarage.controllers;

import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.ModelSet;
import org.uwogarage.models.RatingModel;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.F0;
import org.uwogarage.util.functional.P;
import org.uwogarage.views.LoginView;
import org.uwogarage.views.TabView;
import org.uwogarage.views.UpdatePasswordView;
import org.uwogarage.views.UserControlPanelView;
import org.uwogarage.views.UserInfoView;
import org.uwogarage.views.View;
import org.uwogarage.views.admin.AddUserView;
import org.uwogarage.views.admin.AdminControlPanelView;
import org.uwogarage.views.admin.EditUserView;
import org.uwogarage.views.admin.ListUsersView;
 
/**
 * The UserController class responds to calls from a View and manipulates  
 * in UserModels in the data store
 *
 * @version $Id$
 */
public class UserController extends Controller<UserModel> {
    
    private static final long serialVersionUID = -1190876232059058102L;

    public UserController() {
        
        UserModel test_user = new UserModel();
        test_user.setFirstName("Test");
        test_user.setLastName("User");
        test_user.setUserId("aaaa");
        test_user.setPassword("aaa");
        test_user.setPhoneNumber("519", "933", "0204");
        models.add(test_user);
        
        test_user = new UserModel();
        test_user.setFirstName("Test");
        test_user.setLastName("User");
        test_user.setUserId("bbbb");
        test_user.setPassword("bbb");
        test_user.setPhoneNumber("519", "933", "0204");
        models.add(test_user);
    }
    
    /**
     * Add a user.
     */
    public void add() {
        TabView.show((new AddUserView()).view(
            
            // predicate to check if the user id that the admin supplied already
            // exists or not
            new P<String>() {
              public boolean call(String id) {
                  // make sure user id is unique
                  for (UserModel user : models) {
                      if (user.getId().equals(id)) {
                          return false;
                      }
                  }
                  return true;
              }
            },
            
            // add the user in if all input is valid and return to the admin
            // panel
            new D<UserModel>() {
                public void call(UserModel user) {
                    
                    // add the newly created user in, this could have been in the
                    // view but I decided it best to put that control with the
                    // controller
                    models.add(user);
                    
                    // return to the admin panel
                    adminPanel();
                }
            }
        ));
    }
    
    /**
     * List all users. From here, Admin can sort, edit, and delete users
     */
    public void list(){
     	
    	TabView.show(ListUsersView.view(models, 
    		new D<UserModel>(){
    			// responder for editing users
    			public void call(UserModel user) {
    				edit(user); 
    			}
    		}, 
    		new D<UserModel>(){
    			// responder for deleting users
    			public void call(UserModel user) {
    				ModelSet<GarageSaleModel> allSales = d.garage_sale.getModels();
    				for (GarageSaleModel sale: user.sales)
    					// delete all the user's garage sales
    					allSales.remove(sale);
    				
    				ModelSet<RatingModel> allRatings = d.rating.getModels();
    				for (RatingModel rating: user.ratings)
    					// delete all the user's ratings
    					allRatings.remove(rating);
    				
    				models.remove(user);
    				
    			}
    		}
    	));
    	
    }
    
        
    /**
     * Edit a user.
     */
    public void edit(UserModel user) {
        // show the edit view for the user
        TabView.show((new EditUserView()).view(user,
            new F0() { 
                public void call() {

                    // return to the admin panel
                    adminPanel();
                }
            }     
        ));
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
                if(user.isUsingDefaultPass()) {
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
                logged_user.setUsingDefaultPass(false);
                controlPanel();
            }
        }));
    }
    
    /**
     * Display the main user control panel.
     */
    public void controlPanel() {
        View.show(UserControlPanelView.view(logged_user,
            new F0() { public void call() { stats(); }},
            new F0() { public void call() { 
                d.garage_sale.list(logged_user.sales); 
            }},
            new F0() { public void call() { d.garage_sale.add(); }},
            new F0() { public void call() { d.garage_sale.bulkAdd(); }},
            new F0() { public void call() { d.garage_sale.search(); }},
            new F0() { public void call() { 
                d.garage_sale.list(d.garage_sale.getModels()); 
            }}
        ));
    }
    
    public void adminPanel() {
    	View.show(AdminControlPanelView.view(
    	    new F0() { public void call() { add(); }},
    	    new F0() { public void call() { list(); }},
    	    new F0() { public void call() { d.category.add(); }},
    	    new F0() { public void call() { d.category.list(); }}
    	));
    
    }
    
    /**
     * Show this user some information/statistics about their activity.
     */
    public void stats() {
        TabView.show(UserInfoView.view(logged_user));
    }
}
