package org.uwogarage.views;

import javax.swing.JPanel;

abstract public class TabView extends View {
    static protected JPanel t;
    
    static public JPanel programTab() {
        return t = new JPanel();
    }
}
