package org.uwogarage.views.admin;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.F0;
import org.uwogarage.views.TabView;
import org.uwogarage.views.View;

public class AdminControlPanelView extends View {

	
	 /**
     * Show the Admin Control Panel view.
     * 
     * @param user
     * @param my_info
     * @param add_sale
     * @param bulk_add
     * @param my_sales
     * @param search
     * @return
     */
    static public JPanel view(final F0 add_user, final F0 list_users, 
    		final F0 add_category){
        
        final JTabbedPane pane = new JTabbedPane();
        
        // set up the default tabs with empty panels
        pane.addTab("Add User", new JPanel());
        pane.addTab("List Users", new JPanel());
        pane.addTab("Add Category", new JPanel());
        pane.addTab("List Categories", new JPanel());
        
        
        // create the change listener that will refresh the content of each tab
        // per state change
        pane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                
                // change the current tab accessible through the TabView, in the
                // same way that the program frame is accessible through View
                TabView.setProgramTab(
                    (JPanel) pane.getComponentAt(pane.getSelectedIndex())
                );
                
                // call the various responders
                switch(pane.getSelectedIndex()) {
                    case 0: add_user.call(); break;
                    case 1: list_users.call(); break;
                    case 2: add_category.call(); break;
                    
                                     
                    //case 3: bulk_add.call(); break;
                    //case 4: search.call(); break;
                    //case 5: view_all.call(); break;
                }
            }
        });
        
        // load up the basic control panel view, need to do 2 calls to force a
        // state change.. ugh.
        pane.setSelectedIndex(1);
        pane.setSelectedIndex(0);
        
        // return the new view
        return grid(
            grid.cell(label("Admin Panel"))
                .pos(0, 0)
                .margin(10, 10, 10, 10),
            
            grid.cell(pane).pos(0, 1).fill(1, 1).margin(0, 10, 10, 10)
        );
    }
}

