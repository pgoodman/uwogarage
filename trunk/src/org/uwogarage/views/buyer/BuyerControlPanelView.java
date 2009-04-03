package org.uwogarage.views.buyer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.F0;
import org.uwogarage.views.TabView;

/**
 * Show the main menu for buyers/sellers.
 * 
 * @version $Id$
 */
public class BuyerControlPanelView extends TabView {
    
    /**
     * Show the User Control Panel view.
     * @param user the buyer
     * @param my_info the responder to be called when the user clicks the my information tab
     * @param search the responder to be called when the user clicks the search tab
     * @param view_all the responder to be called when the user clicks the view all tab
     * @param log_out the responder to be called when the user clicks the log out button
     * @return the user control panel view
     */
    static public JPanel view(final UserModel user, final F0 my_info,
                final F0 search, final F0 view_all, final F0 log_out) {
        
        final JTabbedPane pane = new JTabbedPane();
        
        // set up the default tabs with empty panels
        pane.addTab("My Info", new JPanel());
        pane.addTab("Search", new JPanel());
        pane.addTab("All Sales", new JPanel());
        
        // give the tab view
        setTabDelegate(pane, 3);
        
        // create the change listener that will refresh the content of each tab
        // per state change
        pane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                
                // change the current tab accessible through the TabView, in the
                // same way that the program frame is accessible through View
                popContext();
                pushContext((JPanel) pane.getComponentAt(pane.getSelectedIndex()));
                
                // call the various responders
                switch(pane.getSelectedIndex()) {
                    case 0: my_info.call(); break;
                    case 1: search.call(); break;
                    case 2: view_all.call(); break;
                }
            }
        });
        
        // load up the basic control panel view, need to do 2 calls to force a
        // state change.. ugh.
        pushContext((JPanel) pane.getComponentAt(0));
        changeProgramTab(0);
        
        // return the new view
        return grid(
            grid.cell(label(
                "Welcome, "+ user.getFirstName() +" "+ user.getLastName() +
                " to the buyer control panel."
            )).pos(0, 0).margin(10, 10, 10, 20).anchor(1, 0, 0, 1),
            
            grid.cell(button("Log Out", new D<JButton>() {
                public void call(JButton b) {
                    popContext();
                    log_out.call();
                }
            })).pos(1, 0).anchor(1, 1, 0, 0).margin(10, 10, 10, 10),
            
            grid.cell(2, pane).pos(0, 1).fill(1, 1).margin(0, 10, 10, 10)
        );
    }
}
