package org.uwogarage.views;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;

/**
 * 
 * @author petergoodman
 * @version $Id$
 */
public class LoginView extends View<UserModel> {
    
    public void view(JFrame f, D<UserModel> responder) {
        
        final JTextField user_name = text_field(),
                         password = text_field();
        
        content.add(f, grid(
            grid.cell(label("User Name:")),
            grid.cell(user_name),
            grid.cell(label("Password:")),
            grid.cell(password),
            grid.cell(button("Login", new D<JButton>() {
                public void call(JButton b) {
                    
                }
            }))
        ));
    }
}
