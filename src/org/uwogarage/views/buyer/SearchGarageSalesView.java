package org.uwogarage.views.buyer;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.uwogarage.views.View;

public class SearchGarageSalesView extends View {
    
    public JPanel view() {
        final JTabbedPane pane = new JTabbedPane();
        
        // set up the default tabs with empty panels
        pane.addTab("Radius", new JPanel());
        pane.addTab("User ID", new JPanel());
        pane.addTab("Date", new JPanel());
        pane.addTab("Category", new JPanel());
        pane.addTab("Rating", new JPanel());
        pane.addTab("All Sales", new JPanel());
        
        return grid(
            
        );
    }
}
