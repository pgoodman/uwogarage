package org.uwogarage.views.admin;

import java.util.Calendar;

import javax.swing.JPanel;

import org.uwogarage.models.ModelSet;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.gui.GridCell;
import org.uwogarage.util.gui.SimpleGui.grid;
import org.uwogarage.views.View;

/**
 * @version $Id$
 */
public class ListUsersView extends View {
    
    static public JPanel view(ModelSet<UserModel> users) {
    	
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
        		grid.cell(label(user.getId())).margin(10, 0, 0, 10), 
        		grid.cell(label(user.getFirstName()+ " " + user.getLastName())).margin(10, 0, 0, 10)
        	); 
        }
        
        return grid( 
        	rows
        );
    }
}
