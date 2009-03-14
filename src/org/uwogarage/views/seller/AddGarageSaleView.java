package org.uwogarage.views.seller;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.views.View;

/**
 * @version $Id$
 */
public class AddGarageSaleView extends View {
    static public JPanel view(D<GarageSaleModel> responder) {
        
        // set up the main text boxes
        final JTextField street = field.text(),
                         city = field.text(),
                         province = field.text(),
                         year = field.text(),
                         month = field.text(),
                         day = field.text(),
                         hour = field.text(),
                         minutes = field.text();
        
        // set up the time AM/PM fields
        final JRadioButton time_am = new JRadioButton("am", true),
                           time_pm = new JRadioButton("pm", false);
        
        ButtonGroup time_buttons = new ButtonGroup();
        time_buttons.add(time_am);
        time_buttons.add(time_pm);
        
        // lay out the form
        return grid(
            grid.row(
                grid.cell(label("Add Garage Sale")).margin(10, 10, 10, 10)
            ),
            
            grid.row(
                grid.cell(label("Address")).margin(0, 10, 10, 10)
            ),
            
            form.row(label("Street:"), street),
            form.row(label("City:"), city),
            form.row(label("Province:"), province),
            
            grid.row(
                grid.cell(label("Date/Time")).margin(20, 10, 10, 10)
            ),
            form.row(label("Date (yyyy/mm/dd):"), grid(
                grid.cell(year),
                grid.cell(label("/")),
                grid.cell(month),
                grid.cell(label("/")),
                grid.cell(day)
            )),
            form.row(label("Time:"), grid(
                grid.cell(hour),
                grid.cell(label(":")),
                grid.cell(minutes),
                grid.cell(time_am),
                grid.cell(time_pm)
            ))
        );
    }
}
