package org.uwogarage.views;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.F0;

/**
 * Show the main menu for buyers/sellers.
 * 
 * @version $Id$
 */
public class UserControlPanelView extends View {

    
    /**
     * Show the User Control Panel view.
     * 
     * @param user
     * @param my_info
     * @param add_sale
     * @param bulk_add
     * @param my_sales
     * @param search
     * @return
     */
    static public JPanel view(final UserModel user,
                final F0 my_info, final F0 add_sale, final F0 bulk_add,
                final F0 my_sales, final F0 search) {
        
        final JTabbedPane pane = new JTabbedPane();
        
        // set up the default tabs with empty panels
        pane.addTab("My Info", new JPanel());
        pane.addTab("My Sales", new JPanel());
        pane.addTab("Add Sale", new JPanel());
        pane.addTab("Bulk Add", new JPanel());
        pane.addTab("Search", new JPanel());
        
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
                    case 0: my_info.call(); break;
                    case 1: my_sales.call(); break;
                    case 2: add_sale.call(); break;
                    case 3: bulk_add.call(); break;
                    case 4: search.call(); break;
                }
            }
        });
        
        // load up the basic control panel view, need to do 2 calls to force a
        // state change.. ugh.
        pane.setSelectedIndex(1);
        pane.setSelectedIndex(0);
        
        // return the new view
        return grid(
            grid.cell(label("Welcome, "+ user.getFirstName() +" "+ user.getLastName()))
                .pos(0, 0)
                .margin(10, 10, 10, 10),
            
            grid.cell(pane).pos(0, 1).fill(1, 1)
        );
    }
}
