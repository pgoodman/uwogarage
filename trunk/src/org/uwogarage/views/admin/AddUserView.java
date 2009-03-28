package org.uwogarage.views.admin;

import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import org.uwogarage.models.UserModel;
import org.uwogarage.util.StringUtil;
import org.uwogarage.util.documents.*;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.P;
import org.uwogarage.views.Slider;
import org.uwogarage.views.TabView;

/**
 * View to add a user to the system.
 * 
 * @version $Id$
 */
public class AddUserView extends TabView {
    
    // create the text fields
    final JTextField user_id = field.text(4, new AlphaNumDocument(4)),
                     password = field.text(3, new AlphaDocument(3)),
                     first_name = field.text(20, new AlphaDocument(20)),
                     last_name = field.text(20, new AlphaDashDocument(20)),
                     phone_area = field.text(3, new NumDocument(3)),
                     phone_first = field.text(3, new NumDocument(3)),
                     phone_rest = field.text(4, new NumDocument(4)),
                     start_lat = field.text(10, new RealNumDocument(10)),
                     start_lng = field.text(10, new RealNumDocument(10));
    
    // the slider to choose the start zoom level
    final JSlider start_zoom = Slider.view(1, 15, 15, null);
    
    /**
     * Collect the common input errors to adding and editing users.
     * 
     * @param user
     * @param errors
     */
    protected void collectInputErrors(UserModel user, LinkedList<String> errors) {
               
        if(!user.setFirstName(first_name.getText())) {
            errors.add(
                "The first name must be between 1 and 20 "+
                "characters long."
            );
        }
        
        if(!user.setLastName(last_name.getText())) {
            errors.add(
                "The last name must be between 1 and 20 "+
                "characters long."
            );
        }
        
        if(!user.setPhoneNumber(phone_area.getText(), phone_first.getText(), phone_rest.getText()))
            errors.add("The phone number must be 10 digits long.");
        
        if(!user.setGeoPosition(start_lat.getText(), start_lng.getText())) {
            errors.add(
                "The start latitude or longitude are incorrectly "+
                "formatted."
            );
        }
        
        if(!user.setDefaultZoom(start_zoom.getValue()))
            errors.add("The start zoom is out of range.");
    }
    
    /**
     * Show the form to add a user.
     * 
     * @param id_is_unique A predicate that checks whether or not a supplied user
     *                     id is unique.
     * @param responder
     * @return
     */
    public JPanel view(final P<String> id_is_unique, final D<UserModel> responder) {
        
        // the slider to choose the start zoom level
        final JSlider start_zoom = Slider.view(1, 15, 15, null);
        
        // set the default password to the form
        password.setText("aaa");
        
        // create the form
        return grid(grid.cell(fieldset("Add User", grid(

            grid.row(
                grid.cell(label("Add User")).margin(10, 10, 0, 10)
            ),
            
            form.row(label("User ID:"), user_id),
            form.row(label("Password:"), password),
            form.row(label("First Name:"), first_name),
            form.row(label("Last Name:"), last_name),
            form.row(label("Phone Number:"), grid(
                grid.cell(phone_area),
                grid.cell(label("-")),
                grid.cell(phone_first),
                grid.cell(label("-")),
                grid.cell(phone_rest)
            )),
            form.row(label("Start Latitude:"), start_lat),
            form.row(label("Start Longitude:"), start_lng),
            form.row(label("Start Zoom Level"), start_zoom),
            
            // deal with form submission
            grid.row(
                grid.cell(2, button("Add", new D<JButton>() {
                    public void call(JButton b) {
                        
                        // create a new user model and use it to validate the
                        // input, also collect errors in a list
                        LinkedList<String> errors = new LinkedList<String>();
                        UserModel user = new UserModel();
                        
                        // check the user id
                        if(!id_is_unique.call(user_id.getText()))
                            errors.add("The user id must be unique.");

                        else if(!user.setUserId(user_id.getText()))
                            errors.add("The user id must be 4 characters long.");
                        
                        if(!user.setPassword(password.getText()))
                            errors.add("The password must be 3 characters long.");
                        
                        // collect common input errors
                        collectInputErrors(user, errors);
                        
                        // there are errors, report them
                        if(!errors.isEmpty()) {
                            dialog.alert(f,
                                "The following errors occurred:\n\n"+
                                StringUtil.join('\n', errors)
                            );
                        
                        // no errors (hooray!), add the user in
                        } else {
                            responder.call(user);
                            dialog.alert(f, "The user has been added."); 
                            changeProgramTab(1); 
                        }                        
                    }
                })).margin(10, 10, 10, 10)
            )
        ))));
    }
}
