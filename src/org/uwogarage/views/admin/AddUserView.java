package org.uwogarage.views.admin;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import org.uwogarage.models.UserModel;
import org.uwogarage.util.documents.*;
import org.uwogarage.util.functional.D;
import org.uwogarage.views.MapZoomSlider;
import org.uwogarage.views.View;

/**
 * View to add a user to the system.
 * 
 * @version $Id$
 */
public class AddUserView extends View {
    static public JPanel view() {
        
        // create the text fields
        final JTextField user_id = field.text(4, new AlphaNumDocument(4)),
                         password = field.text(3, new AlphaDocument(3)),
                         first_name = field.text(20, new AlphaDocument(20)),
                         last_name = field.text(20, new AlphaDashDocument(20)),
                         phone_num = field.text(10, new NumDocument(10)),
                         start_lat = field.text(10, new RealNumDocument(10)),
                         start_lng = field.text(10, new RealNumDocument(10));
        
        final JSlider start_zoom = MapZoomSlider.view();
        
        // set the default password to the form
        password.setText("aaa");
        
        // create the form
        return grid(
            grid.row(
                grid.cell(label("Add User")).margin(10, 10, 0, 10)
            ),
            form.row(label("User ID:"),         user_id),
            form.row(label("Password:"),        password),
            form.row(label("First Name:"),      first_name),
            form.row(label("Last Name:"),       last_name),
            form.row(label("Phone Number:"),    phone_num),
            form.row(label("Start Latitude:"),  start_lat),
            form.row(label("Start Longitude:"), start_lng),
            form.row(label("Start Zoom Level"), start_zoom),
            grid.row(
                grid.cell(2, button("Add", new D<JButton>() {
                    public void call(JButton b) {
                        // TODO form checking
                    }
                })).margin(10, 10, 10, 10)
            )
        );
    }
}
