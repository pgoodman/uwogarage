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
    
    static public JPanel view(final UserModel user,
                final F0 control_panel, final F0 add_sale, final F0 bulk_add,
                final F0 my_sales, final F0 search) {
        
        final JTabbedPane pane = new JTabbedPane();
        JPanel tab_content = TabView.programTab();
        
        pane.add("Control Panel", tab_content);
        pane.add("Add Sale", tab_content);
        pane.add("Bulk Add", tab_content);
        pane.add("My Sales", tab_content);
        pane.add("Search", tab_content);
        
        pane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                switch(pane.getSelectedIndex()) {
                    case 0: control_panel.call(); break;
                    case 1: add_sale.call(); break;
                    case 2: bulk_add.call(); break;
                    case 3: my_sales.call(); break;
                    case 4: search.call(); break;
                }
            }
        });
        
        return grid(
            grid.cell(label("Welcome, "+ user.getFirstName() +" "+ user.getLastName()))
                .pos(0, 0)
                .margin(10, 10, 10, 10),
            
            grid.cell(pane).pos(0, 1).fill(1, 1)
        );
    }
}
