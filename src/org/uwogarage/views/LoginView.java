package org.uwogarage.views;

import javax.swing.JButton;
import javax.swing.JTextField;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.documents.*;
import org.uwogarage.util.functional.D;

/**
 * 
 * @author petergoodman
 * @version $Id$
 */
public class LoginView extends View<UserModel> {
    public void view(D<UserModel> responder) {
        
        final JTextField user_name = text_field(4, new AlphaNumDocument()),
                         password = text_field(3, new AlphaDocument());
        
        content.add(f, grid(
            grid.cell(label("User ID:"))
                .pos(0, 0).anchor(0, 1, 0, 0).margin(10, 10, 0, 10),
            grid.cell(user_name)
                .pos(1, 0).anchor(0, 0, 0, 1).margin(10, 10, 0, 0),
            grid.cell(label("Password:"))
                .pos(0, 1).anchor(0, 1, 0, 0).margin(10, 10, 10, 10),
            grid.cell(password)
                .pos(1, 1).anchor(0, 0, 0, 1).margin(10, 10, 0, 0),
            grid.cell(2, button("Login", new D<JButton>() {
                public void call(JButton b) {
                    
                }
            })).pos(0, 2).anchor(1, 1, 1, 1).margin(0, 0, 10, 0)
        ));
    }
}
