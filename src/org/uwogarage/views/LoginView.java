package org.uwogarage.views;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
 * 
 * @version $Id$
 */
public class LoginView extends View {
    
    static protected int login_attempts = 0;
    
    // just somewhere to store a reference to the on_enter_adapter to make it
    // available to the getKeyAdapter() method
    static protected KeyAdapter on_enter_adapter;
    
    /**
     * Lazily get the key adapter that is used when enter is pressed.
     * @return
     */
    static protected KeyAdapter getKeyAdapter() {
        return on_enter_adapter;
    }
    
    /**
     * View the login form. Takes in a delegate from the controller that actually
     * performs the log in operation.
     */
    static public JPanel view(final ModelSet<UserModel> users, final D<UserModel> responder) {
        
        // text fields, need them for later ;)
        final JTextField user_id = field.text(4, new AlphaNumDocument(4)),
                         password = field.pass(3, new AlphaDocument(3));
        
        // the delegate that is called when the login button is clicked
        final D<JButton> on_click_delegate = new D<JButton>() {
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
                    
                    // remove the reference to the key adapter so that it can be
                    // garbage collected and so that the frame no longer calls
                    // this adapter, and subsequently this delegate (mutual
                    // dependency) when enter is pressed.
                    f.removeKeyListener(getKeyAdapter());
                    
                    // call the responder and get out of here!
                    responder.call(u);
                    
                // invalid user info, alter the user and clear the form
                // values
                } else {
                    
                    dialog.alert(f, 
                        "Invalid User ID / Password combination. You "+
                        "have "+(3 - ++login_attempts) +" attempt(s) left."
                    );
                    
                    if(login_attempts == 3) {
                        System.exit(0);
                    }
                    
                    user_id.setText("");
                    password.setText("");
                }
            }
        };
        
        // login button
        final JButton login_button = button("Login", on_click_delegate);
        
        // the adapter to listen to on-enter key presses
        KeyAdapter on_press_enter = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    on_click_delegate.call(login_button);
            }
        };
        
        // needed! This allows for the mutual dependency of on_press_enter and
        // on_click_delegate by making the key adapter available to the button
        // delegate lazily through the getKeyAdapter() static method
        on_enter_adapter = on_press_enter;
        
        // add in key listeners to *everything*.. ugh.
        f.addKeyListener(on_press_enter);
        login_button.addKeyListener(on_press_enter);
        user_id.addKeyListener(on_press_enter);
        password.addKeyListener(on_press_enter);
        
        // create the form
        return grid(
            grid.row(grid.cell(2, label("Log In")).margin(10, 10, 0, 10)),
            
            form.row(label("User ID:"),  user_id),
            form.row(label("Password:"), password),
            
            grid.row(    
                grid.cell(2, login_button).margin(10, 10, 10, 10)
            )
        );
    }
}
