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
        
        // password fields, need them for later ;)
        final JTextField old_password = field.pass(3, new AlphaDocument(3)),
                         new_password = field.pass(3, new AlphaDocument(3));
        
        // create the form, the text makes it extra wide so I nest two grids
        return grid(
            grid.row(
                grid.cell(label(
                    "The administrative user has reset your password. \n"
                )).margin(10, 10, 0, 10)
            ),
            grid.row(
                grid.cell(label(
                    "Please validate your current password and insert a new one."
                )).margin(10, 10, 10, 10)
            ),
            grid.row(
                grid.cell(grid(
                    form.row(label("Old Password:"), old_password),
                    form.row(label("New Password:"), new_password)
                ))
            ),
            grid.row( 
                grid.cell(button("Update My Password", new D<JButton>() {
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
                                "Your new password must be three characters "+
                                "long and contain alphabetical characters."
                            );
                        
                        // continue on
                        } else {
                            responder.call();
                        }
                    }
                })).anchor(1, 1, 1, 1).margin(0, 0, 10, 0)
            )
        );
    }
}
