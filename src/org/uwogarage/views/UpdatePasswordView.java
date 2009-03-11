package org.uwogarage.views;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.uwogarage.models.UserModel;
import org.uwogarage.util.documents.AlphaNumDocument;
import org.uwogarage.util.functional.D;

public class UpdatePasswordView extends View<UserModel> {
    
    static public JPanel view(UserModel user) {
        
        String msg = (
            "The administrative user has reset your password. Please validate "+
            "your current password and insert a new one."
        );
        
        // password fields, need them for later ;)
        final JTextField old_password = field.pass(4, new AlphaNumDocument(4)),
                         new_password = field.pass(4, new AlphaNumDocument(4));
        
        return grid(
            grid.cell(2, label(msg)),
            
            grid.cell(label("Old Password:"))
                .pos(0, 0)
                .anchor(0, 1, 0, 0)
                .margin(10, 10, 0, 10),
                
            grid.cell(old_password)
                .pos(1, 0)
                .anchor(0, 0, 0, 1)
                .margin(10, 10, 0, 0),
                
            grid.cell(label("New Password:"))
                .pos(0, 1)
                .anchor(0, 1, 0, 0)
                .margin(10, 10, 10, 10),
                
            grid.cell(new_password)
                .pos(1, 1)
                .anchor(0, 0, 0, 1)
                .margin(10, 10, 0, 0),
                
            grid.cell(2, button("Change Password", new D<JButton>() {
                public void call(JButton b) {
                                        
                }
            })).pos(0, 2).anchor(1, 1, 1, 1).margin(0, 0, 10, 0)
        );
    }
}
