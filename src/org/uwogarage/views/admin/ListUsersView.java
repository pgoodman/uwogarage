package org.uwogarage.views.admin;

import javax.swing.JPanel;

import org.uwogarage.models.ModelSet;
import org.uwogarage.models.UserModel;
import org.uwogarage.views.View;

/**
 * @version $Id$
 */
public class ListUsersView extends View {
    
    static public JPanel view(ModelSet<UserModel> users) {
        return grid(
        );
    }
}
