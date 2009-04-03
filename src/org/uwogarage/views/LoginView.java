package org.uwogarage.views;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
 * @author Peter Goodman
 */
public class LoginView extends View {
    
    static protected int login_attempts = 0;
    
    /**
     * View the login form. Takes in a delegate from the controller that actually
     * performs the log in operation.
     * @param users the users that are allowed to log in
     * @param buyer_responder the responder for the buyer radio button
     * @param seller_responder the responder for the seller radio button
     * @return a panel containing the login form
     */
    static public JPanel view(final ModelSet<UserModel> users, 
                                     final D<UserModel> buyer_responder,
                                     final D<UserModel> seller_responder) {
        
        // text fields, need them for later ;)
        final JTextField user_id = field.text(4, new AlphaNumDocument(4)),
                         password = field.pass(3, new AlphaDocument(3));
        
        ButtonGroup group = new ButtonGroup();
        final JRadioButton as_buyer = new JRadioButton("Buyer"),
                           as_seller = new JRadioButton("Seller");
        
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
                    
                    // call the responder and get out of here!
                    if(as_buyer.isSelected())
                        buyer_responder.call(u);
                    else
                        seller_responder.call(u);
                    
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
        final JButton login_button = button("Log In", on_click_delegate);
        
        // the adapter to listen to on-enter key presses
        KeyAdapter on_press_enter = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    on_click_delegate.call(login_button);
            }
        };
        
        // add in key listeners to *everything*.. ugh.
        login_button.addKeyListener(on_press_enter);
        user_id.addKeyListener(on_press_enter);
        password.addKeyListener(on_press_enter);
        as_seller.addKeyListener(on_press_enter);
        as_buyer.addKeyListener(on_press_enter);
        
        group.add(as_buyer);
        group.add(as_seller);
        
        // select by default
        as_buyer.setSelected(true);
        
        // create the form
        return grid(
            grid.row(grid.cell(2, label("Log In")).margin(10, 10, 0, 10)),
            
            form.row(label("User ID:"),  user_id),
            form.row(label("Password:"), password),
            form.row(label("Log in as:"), grid(
                grid.cell(as_buyer),
                grid.cell(as_seller).pos(0, 1)
            )),
            grid.row(    
                grid.cell(2, login_button).margin(10, 10, 10, 10)
            )
        );
    }
}
