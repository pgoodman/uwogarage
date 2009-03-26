package org.uwogarage.views;

import javax.swing.JComponent;
import javax.swing.JPanel;

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
            t.add(c);
            //t.revalidate();
            //f.pack();
            f.validate();
        }
    }
    
    static public void setTabDelegate(D<Integer> d) {
        change_tab = d;
    }
    
    static protected void changeProgramTab(int i) {
        change_tab.call(new Integer(i));
    }
}
