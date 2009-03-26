package org.uwogarage.views.admin;

import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.uwogarage.models.ModelSet;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.gui.GridCell;
import org.uwogarage.util.gui.SimpleGui.dialog;
import org.uwogarage.util.gui.SimpleGui.grid;
import org.uwogarage.views.TabView;
import org.uwogarage.views.View;

/**
 * @version $Id$
 */
public class ListUsersView extends TabView {
    
    static public JPanel view(ModelSet<UserModel> users, final D<UserModel> editResponder, final D<UserModel> deleteResponder) {
    	
        // nothing to show
        if(0 == users.size()) {
            return grid(grid.cell(label(
                "There are no users to list."
            )));
        }
        
        // allocate the rows
        GridCell[][] rows = new GridCell[users.size()][];
        int i = 0; 
        
        // create the rows
        for(final UserModel user : users) {
        	rows[i++] = grid.row(
        		
        		grid.cell(grid(
        				grid.cell(label(user.getId())).margin(10, 0, 0, 10), 
                		grid.cell(label(user.getFirstName()+ " " + user.getLastName())).margin(10, 0, 0, 10),
                		grid.cell(button("Edit", new D<JButton>(){
                			public void call(JButton b){
                				// EDIT USER
                				editResponder.call(user);
                			}
                		})).margin(0, 0, 0, 10),
                		grid.cell(button("Delete", new D<JButton>(){
                			public void call(JButton b){
                				if (!dialog.confirm(f, "Are you sure you want to delete this user?"))
                					return;
    					
                				deleteResponder.call(user);
                				dialog.alert(f, "The user has been deleted.");
                				changeProgramTab(1);
                			}
                		})).margin(0, 0, 0, 10)
        		)).anchor(1, 0, 0, 1)
        		
        	); 
        }
        
        return grid( 
        	rows
        );
    }
}
