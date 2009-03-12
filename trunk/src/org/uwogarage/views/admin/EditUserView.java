package org.uwogarage.views.admin;

import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import org.uwogarage.models.UserModel;
import org.uwogarage.util.documents.*;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.F0;
import org.uwogarage.views.MapZoomSlider;
import org.uwogarage.views.View;

/**
 * View to add a user to the system.
 * 
 * @version $Id$
 */
public class EditUserView extends View {
    
    /**
     * Show the form to add a user.
     * 
     * @param user_exists A predicate that checks whether or not a supplied user
     *                    id is unique.
     * @param responder
     * @return
     */
    static public JPanel view(final UserModel user, final F0 responder) {
        
        // create the text fields
        final JTextField first_name = field.text(20, new AlphaDocument(20)),
                         last_name = field.text(20, new AlphaDashDocument(20)),
                         phone_num = field.text(10, new NumDocument(10)),
                         start_lat = field.text(10, new RealNumDocument(10)),
                         start_lng = field.text(10, new RealNumDocument(10));
        
        // is this user's password being reset?
        final JCheckBox reset_pass = new JCheckBox();
        
        // the slider to choose the start zoom level
        final JSlider start_zoom = MapZoomSlider.view();
        
        // set all of the default values
        reset_pass.setSelected(user.hasDefaultPass());
        start_zoom.setValue(user.getDefaultZoom());
        first_name.setText(user.getFirstName());
        last_name.setText(user.getLastName());
        phone_num.setText(user.getPhoneNumber());
        start_lat.setText(String.valueOf(user.getDefaultLat()));
        start_lat.setText(String.valueOf(user.getDefaultLng()));
        
        // create the form
        return grid(
            grid.row(
                grid.cell(label("Edit User")).margin(10, 10, 0, 10)
            ),
            
            form.row(label("Reset Password?"),  reset_pass),
            form.row(label("First Name:"),      first_name),
            form.row(label("Last Name:"),       last_name),
            form.row(label("Phone Number:"),    phone_num),
            form.row(label("Start Latitude:"),  start_lat),
            form.row(label("Start Longitude:"), start_lng),
            form.row(label("Start Zoom Level"), start_zoom),
            
            grid.row(
                grid.cell(2, button("Update", new D<JButton>() {
                    public void call(JButton b) {
                        
                        LinkedList<String> errors = new LinkedList<String>();
                        UserModel user = new UserModel();
                        
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
                        
                        // no errors, user has been updated so respond
                        } else {
                            
                            if(reset_pass.isSelected())
                                user.setPassword(null);
                            
                            responder.call();
                        }                        
                    }
                })).margin(10, 10, 10, 10)
            )
        );
    }
}
