package org.uwogarage.views.seller;

import javax.swing.*;

import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.GarageSaleModelSet;
import org.uwogarage.models.ModelSet;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.D2;
import org.uwogarage.views.TabView;


/**
 * @version $Id$
 */
public class BulkAddGarageSaleView extends TabView {
	
    static public JPanel view(final UserModel logged_user, final D2<Boolean,ModelSet<GarageSaleModel>> responder) {
        
        final JFileChooser chooser = new JFileChooser();
        final JCheckBox delete_prev_checkbox = new JCheckBox("Delete all previous sales?");
    
        // create the gui to do bulk loading
        return grid(
            grid.row(grid.cell(delete_prev_checkbox).margin(10, 0, 10, 0)),
            grid.row(grid.cell(button("Click to choose a file bulk add sales.", 
                new D<JButton>(){
    				public void call(JButton arg1) {
    					
    				    // a file wasn't selected
    					if (JFileChooser.APPROVE_OPTION != chooser.showOpenDialog(f))
    						return;
    
    					try {
    					    // give the responder the loaded sales
    						responder.call(
    						    delete_prev_checkbox.isSelected(),
    						    GarageSaleModelSet.loadFromFile(
        							logged_user,
        							chooser.getSelectedFile()
        						)
    						);
    						
    						// if it makes it down here then it means that no
    						// errors happened!
    						dialog.alert(f, "The garage sales have been added.");
    						changeProgramTab(1); // go to: my sales
    					
    					// a parsing error occurred, alert the user to it.
    					} catch (Exception e) {
    					    e.printStackTrace();
    						dialog.alert(f, e.getMessage());
    					}
    				}
                }
            )))
        );
    }
}
