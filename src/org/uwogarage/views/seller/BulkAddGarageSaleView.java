package org.uwogarage.views.seller;

import javax.swing.*;

import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.GarageSaleModelSet;
import org.uwogarage.models.ModelSet;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.D2;
import org.uwogarage.util.functional.F0;
import org.uwogarage.views.TabView;


/**
 * @version $Id$
 */
public class BulkAddGarageSaleView extends TabView {
	
    static public JPanel view(final UserModel logged_user, final D2<ModelSet<GarageSaleModel>,F0> responder) {
        
    	
        final JFileChooser chooser = new JFileChooser();
        final JCheckBox delete_prev_checkbox = new JCheckBox("Delete all previous sales?");
        
        
        // TODO provide option to either remove existing garage sales
        // or to add bulk loaded garage sales info to existing garage sales
        
        return grid(
            grid.row(grid.cell(delete_prev_checkbox)),
            grid.row(grid.cell(button("Choose a File", new D<JButton>(){
				public void call(JButton arg1) {
					
				    // a file wasn't selected
					if (JFileChooser.APPROVE_OPTION != chooser.showOpenDialog(f))
						return;

					try {
						responder.call(
						        
						    // give the responder the loaded sales
						    GarageSaleModelSet.loadFromFile(
    							logged_user,
    							chooser.getSelectedFile()
						    ),
						    
						    // give the controller a responder that will toggle 
						    // a change in the current tab in the view
						    new F0() {
						        public void call() {
						            changeProgramTab(1);
						        }
						    }
						);
					
					// a parsing error occurred, alert the user to it.
					} catch (Exception e) {
					    e.printStackTrace();
						dialog.alert(f, e.getMessage());
					}
				}
            })))
        );
    }
}
