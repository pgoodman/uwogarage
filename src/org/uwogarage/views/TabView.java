package org.uwogarage.views;

import javax.swing.JComponent;
import javax.swing.JPanel;

abstract public class TabView extends View {
    static protected JPanel t;
    
    static public void setProgramTab(JPanel c) {
        t = c;
    }
    
    static public void show(JComponent c) {
        if(null != t) {
            t.removeAll();
            t.validate();
            t.add(c);
            //t.validate();
            f.pack();
            f.validate();
        }
    }
}
