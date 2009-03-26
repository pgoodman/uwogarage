package org.uwogarage.views.admin;

import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.uwogarage.models.UserModel;
import org.uwogarage.util.GeoPosition;
import org.uwogarage.util.StringUtil;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.F0;

/**
 * View to edit an existing user of the system.
 * 
 * @version $Id$
 */
public class EditUserView extends AddUserView {
    
    /**
     * Show the form to add a user.
     * 
     * @param user_exists A predicate that checks whether or not a supplied user
     *                    id is unique.
     * @param responder
     * @return
     */
    public JPanel view(final UserModel user, final F0 responder) {
        
        String[] phone = user.getPhoneNumber();
        String[] user_geo = user.getStartCoords();
        
        // is the user's password being reset?
        final JCheckBox reset_pass = new JCheckBox();
        
        // set all of the default values
        reset_pass.setSelected(user.isUsingDefaultPass());
        start_zoom.setValue(user.getDefaultZoom());
        first_name.setText(user.getFirstName());
        last_name.setText(user.getLastName());
        phone_area.setText(phone[0]);
        phone_first.setText(phone[1]);
        phone_rest.setText(phone[2]);
        start_lat.setText(user_geo[0]);
        start_lng.setText(user_geo[1]);
        
        // create the form
        return grid(
            grid.row(
                grid.cell(label("Edit User")).margin(10, 10, 0, 10)
            ),
            
            form.row(label("Reset Password?"),  reset_pass),
            form.row(label("First Name:"),      first_name),
            form.row(label("Last Name:"),       last_name),
            form.row(label("Phone Number:"),    grid(
                grid.cell(phone_area),
                grid.cell(label("-")),
                grid.cell(phone_first),
                grid.cell(label("-")),
                grid.cell(phone_rest)
            )),
            form.row(label("Start Latitude:"),  start_lat),
            form.row(label("Start Longitude:"), start_lng),
            form.row(label("Start Zoom Level"), start_zoom),
            
            // deal with form submission
            grid.row(
                grid.cell(2, button("Update", new D<JButton>() {
                    public void call(JButton b) {
                        
                        LinkedList<String> errors = new LinkedList<String>();
                        
                        // collect the input errors common to adding/editing a 
                        // user
                        collectInputErrors(user, errors);
                        
                        // there are errors, report them
                        if(!errors.isEmpty()) {
                            dialog.alert(f,
                                "Some fields were not saved because of the "+
                                "following errors:\n\n"+
                                StringUtil.join('\n', errors)
                            );
                        
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
