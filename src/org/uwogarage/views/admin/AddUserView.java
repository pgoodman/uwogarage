package org.uwogarage.views.admin;

import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import org.uwogarage.models.UserModel;
import org.uwogarage.util.documents.*;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.P;
import org.uwogarage.views.MapZoomSlider;
import org.uwogarage.views.View;

/**
 * View to add a user to the system.
 * 
 * @version $Id$
 */
public class AddUserView extends View {
    
    /**
     * Show the form to add a user.
     * 
     * @param user_exists A predicate that checks whether or not a supplied user
     *                    id is unique.
     * @param responder
     * @return
     */
    static public JPanel view(final P<String> id_is_unique, final D<UserModel> responder) {
        
        // create the text fields
        final JTextField user_id = field.text(4, new AlphaNumDocument(4)),
                         password = field.text(3, new AlphaDocument(3)),
                         first_name = field.text(20, new AlphaDocument(20)),
                         last_name = field.text(20, new AlphaDashDocument(20)),
                         phone_num = field.text(10, new NumDocument(10)),
                         start_lat = field.text(10, new RealNumDocument(10)),
                         start_lng = field.text(10, new RealNumDocument(10));
        
        // the slider to choose the start zoom level
        final JSlider start_zoom = MapZoomSlider.view();
        
        // set the default password to the form
        password.setText("aaa");
        
        // create the form
        return grid(
            grid.row(
                grid.cell(label("Add User")).margin(10, 10, 0, 10)
            ),
            
            form.row(label("User ID:"),         user_id),
            form.row(label("Password:"),        password),
            form.row(label("First Name:"),      first_name),
            form.row(label("Last Name:"),       last_name),
            form.row(label("Phone Number:"),    phone_num),
            form.row(label("Start Latitude:"),  start_lat),
            form.row(label("Start Longitude:"), start_lng),
            form.row(label("Start Zoom Level"), start_zoom),
            
            grid.row(
                grid.cell(2, button("Add", new D<JButton>() {
                    public void call(JButton b) {
                        
                        LinkedList<String> errors = new LinkedList<String>();
                        UserModel user = new UserModel();
                        
                        if(!id_is_unique.call(user_id.getText()))
                            errors.add("The user id must be unique.");
                        
                        else if(!user.setUserId(user_id.getText()))
                            errors.add("The user id must be 4 characters long.");
                        
                        if(!user.setPassword(password.getText()))
                            errors.add("The password must be 3 characters long.");
                        
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
                        
                        if(!user.setPhoneNumber(phone_num.getText()))
                            errors.add("The phone number must be 10 characters long.");
                        
                        if(!user.setDefaultLatLng(start_lat.getText(), start_lng.getText())) {
                            errors.add(
                                "The start latitude or longitude are incorrectly "+
                                "formatted."
                            );
                        }
                        
                        if(!user.setDefaultZoom(start_zoom.getValue()))
                            errors.add("The start zoom is out of range.");
                        
                        // there are errors, report them
                        if(!errors.isEmpty()) {
                            String error_str = "The following errors occurred:";
                            for(String error : errors)
                                error_str += "\n"+ error;
                            
                            dialog.alert(f, error_str);
                        
                        // no errors, yay, add the user in
                        } else {
                            responder.call(user);
                        }                        
                    }
                })).margin(10, 10, 10, 10)
            )
        );
    }
}
