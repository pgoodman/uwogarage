package org.uwogarage.views;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.uwogarage.models.ModelSet;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.documents.*;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.P;

/**
 * Login View, shows a login form and attempts to validate the input.
 * @version $Id$
 */
public class LoginView extends View<UserModel> {
    
    /**
     * View the login form. Takes in a delegate from the controller that actually
     * performs the log in operation.
     */
    static public JPanel view(final ModelSet<UserModel> users, final D<UserModel> responder) {
        
        // text fields, need them for later ;)
        final JTextField user_id = field.text(4, new AlphaNumDocument(4)),
                         password = field.pass(3, new AlphaDocument(3));
        
        // create the form
        return grid(
            grid.cell(label("User ID:"))
                .pos(0, 0)
                .anchor(0, 1, 0, 0)
                .margin(10, 10, 0, 10),
                
            grid.cell(user_id)
                .pos(1, 0)
                .anchor(0, 0, 0, 1)
                .margin(10, 10, 0, 0),
                
            grid.cell(label("Password:"))
                .pos(0, 1)
                .anchor(0, 1, 0, 0)
                .margin(10, 10, 10, 10),
                
            grid.cell(password)
                .pos(1, 1)
                .anchor(0, 0, 0, 1)
                .margin(10, 10, 0, 0),
                
            grid.cell(2, button("Login", new D<JButton>() {
                public void call(JButton b) {
                    
                    // try to find the user by id and password
                    UserModel u = users.filterOne(new P<UserModel>() {
                        public boolean call(UserModel u) {
                            return u.getId().equals(user_id.getText()) 
                                && u.getPassword().equals(password.getText());
                        }
                    });
                    
                    // if the user was found, then log them in
                    if(null != u) {
                        responder.call(u);
                        
                    // invalid user info, alter the user and clear the form
                    // values
                    } else {
                        dialog.alert(f, "Invalid User ID / Password combination.");
                        user_id.setText("");
                        password.setText("");
                    }
                }
            })).pos(0, 2).anchor(1, 1, 1, 1).margin(0, 0, 10, 0)
        );
    }
}
