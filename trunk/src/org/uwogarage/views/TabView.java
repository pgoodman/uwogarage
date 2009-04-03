package org.uwogarage.views;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.uwogarage.util.functional.D;

/**
 * The tab view is a view with tabs
 * @author Peter Goodman
 *
 */
abstract public class TabView extends View {
    static protected JPanel t;
    static private D<Integer> change_tab;
    
    /**
     * Set the panel this tabview should show
     * @param c the panel to be shown
     */
    static public void setProgramTab(JPanel c) {
        t = c;
    }
    
    /**
     * A Delegate that will force a change tab.
     * @param p the tabbed pane to be changed
     * @param num_tabs the number of tabs in the pane
     */
    static public void setTabDelegate(final JTabbedPane p, final int num_tabs) {
        change_tab = new D<Integer>() {
            public void call(Integer index) {
                
                // force a switch if necessary
                if(p.getSelectedIndex() == index)
                    p.setSelectedIndex((index+1) % num_tabs);
                
                p.setSelectedIndex(index);
            }
        };
    }
    
    /**
     * Change the tab
     * @param i the index of the tab to be changed to
     */
    static protected void changeProgramTab(int i) {
        change_tab.call(new Integer(i));
    }
}
