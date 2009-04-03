package org.uwogarage.views.admin;

import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.uwogarage.models.UserModel;
import org.uwogarage.util.StringUtil;
import org.uwogarage.util.functional.D;

/**
 * View to edit an existing user of the system.
 * 
 * @version $Id$
 * @author Peter Goodman
 */
public class EditUserView extends AddUserView {
    
    /**
     * Show the form to edit a user.
     * @param user the user to be edited
     * @return the panel containing the edit user form
     */
    public JPanel view(final UserModel user) {
        
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
        return grid(fieldset("Edit User", grid(
            
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
            grid.row(grid.cell(2, grid(
                grid.cell(button("Update", new D<JButton>() {
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
                            
                            dialog.alert(f, "The user has been updated.");
                            changeProgramTab(1);
                        }                        
                    }
                })).margin(0, 10, 0, 0),
                
                grid.cell(button("Cancel", new D<JButton>() {
                    public void call(JButton b) {
                        
                            changeProgramTab(1);                        
                    }
                }))
            )).margin(10, 10, 10, 10))
        )));
    }
}
