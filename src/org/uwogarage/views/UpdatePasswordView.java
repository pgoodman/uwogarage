package org.uwogarage.views;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.uwogarage.models.UserModel;
import org.uwogarage.util.documents.AlphaDocument;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.F0;


/**
 * View for when a user needs to update their password after an admin reset.
 * 
 * @version $Id$
 */
public class UpdatePasswordView extends View {
    
    static public JPanel view(final UserModel user, final F0 responder) {
        
        String hello_message = (
            "The administrative user has reset your password. Please validate "+
            "your current password and insert a new one."
        );
        
        // password fields, need them for later ;)
        final JTextField old_password = field.pass(4, new AlphaDocument(3)),
                         new_password = field.pass(4, new AlphaDocument(3));
        
        return grid(
            grid.cell(2, label(hello_message))
                .pos(0, 0)
                .margin(10, 10, 10, 10),
            
            grid.cell(label("Old Password:"))
                .pos(0, 1)
                .anchor(0, 1, 0, 0)
                .margin(0, 10, 0, 10),
                
            grid.cell(old_password)
                .pos(1, 1)
                .anchor(0, 0, 0, 1)
                .margin(0, 10, 0, 0),
                
            grid.cell(label("New Password:"))
                .pos(0, 2)
                .anchor(0, 1, 0, 0)
                .margin(10, 10, 10, 10),
                
            grid.cell(new_password)
                .pos(1, 2)
                .anchor(0, 0, 0, 1)
                .margin(10, 10, 0, 0),
                
            grid.cell(2, button("Change Password", new D<JButton>() {
                public void call(JButton b) {
                    
                    String new_pass = new_password.getText();
                    
                    // check that the old password is correct
                    if(!user.getPassword().equals(old_password.getText())) {
                        dialog.alert(f, 
                            "The old password that you have supplied is incorrect."
                        );
                    
                    // old password is correct, attempt to set the new one
                    } else if(!user.setPassword(new_pass)) {
                        dialog.alert(f, 
                            "Your new password must be three characters long "+
                            "and contain the letters a-z or A-Z."
                        );
                    
                    // continue on
                    } else {
                        responder.call();
                    }
                }
            })).pos(0, 2).anchor(1, 1, 1, 1).margin(0, 0, 10, 0)
        );
    }
}
