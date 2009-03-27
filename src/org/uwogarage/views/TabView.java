package org.uwogarage.views;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.uwogarage.util.functional.D;

abstract public class TabView extends View {
    static protected JPanel t;
    static private D<Integer> change_tab;
    
    static public void setProgramTab(JPanel c) {
        t = c;
    }
    
    static public void show(JComponent c) {
        if(null != t) {
            t.removeAll();
            //t.validate();
            c.setBounds(t.getVisibleRect());
            t.add(c);
            //t.revalidate();
            //f.pack();
            f.validate();
        }
    }
    
    /**
     * A Delegate that will force a change tab.
     * @param p
     * @param num_tabs
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
    
    static protected void changeProgramTab(int i) {
        System.out.println(i);
        change_tab.call(new Integer(i));
    }
}
