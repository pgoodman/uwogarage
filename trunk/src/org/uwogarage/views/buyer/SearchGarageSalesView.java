package org.uwogarage.views.buyer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.uwogarage.util.documents.NumDocument;
import org.uwogarage.util.documents.RealNumDocument;
import org.uwogarage.views.View;

/**
 * View to insert criteria for a garage sale search.
 * 
 * @version $Id$
 */
public class SearchGarageSalesView extends View {
    
    // indexes for the enabled and search_criteria_boxes arrays
    static protected final int RADIUS = 0,
                               USER_ID = 1,
                               DATE = 2,
                               CATEGORY = 3,
                               RATING = 4;
    
    // the criteria tab pane
    final JLayeredPane layered_pane = new JLayeredPane();
    final JTabbedPane tab_pane = new JTabbedPane();
    
    // the search button
    final JButton search_button = button("Search");
    
    // check boxes to enable/disable search criteria
    protected JCheckBox[] search_criteria_boxes = new JCheckBox[] {
        new JCheckBox("Radius"),
        new JCheckBox("User ID"),
        new JCheckBox("Date"),
        new JCheckBox("Category"),
        new JCheckBox("Rating")
    };
    
    // the panels holding the search criteria options
    protected JPanel[] search_criteria_tabs = new JPanel[] {
        viewRadius(),
        viewUserId(),
        viewDate(),
        viewCategory(),
        viewRating()
    };
    
    // know if any criteria are selected
    protected int num_selected_criteria = 0;
    
    /**
     * Constructor, disable all the tab content by default.
     */
    public SearchGarageSalesView() {
        
        // create the listener to enabled/disable the search button. It loops
        // over all the check boxes each time, and only changes the search 
        // button when it is in the wrong state.
        ChangeListener listener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                num_selected_criteria = 0;
                for(JCheckBox box : search_criteria_boxes) {
                    if(box.isSelected())
                        ++num_selected_criteria;
                }
                
                if(search_button.isEnabled() != (num_selected_criteria > 0))
                    search_button.setEnabled(num_selected_criteria > 0);
            }
        };
        
        for(JCheckBox box : search_criteria_boxes)
            box.addChangeListener(listener);
    }
    
    /**
     * Search criteria for finding a sale within a given radius of some latitude
     * and longitude.
     * 
     * @return
     */
    protected JPanel viewRadius() {
        
        final JTextField lat = field.text(11, new RealNumDocument(11)),
                         lng = field.text(11, new RealNumDocument(11)),
                         radius = field.text(4, new NumDocument(4));
        
        return grid(
            grid.row(grid.cell(
                label("Use this criteria to find sales within a given radius of")
            )),
            grid.row(grid.cell(label("a specific latitude and longitude."))),
            grid.row(grid.cell(grid(
                form.row(label("Latitude:"), lat),
                form.row(label("Longitude:"), lng),
                form.row(label("Radius"), grid(
                    grid.cell(radius).pos(0, 0),
                    grid.cell(label(" km")).pos(1, 0)
                ))
            )).margin(10, 9, 9, 9))
        );
    }
    
    /**
     * Search criteria for finding a sale created by a specific user with the
     * given id.
     * 
     * @return
     */
    protected JPanel viewUserId() {
        return grid(
            grid.cell(label("User ID"))
        );
    }
    
    /**
     * Search criteria for finding a sale taking place on a specific date or
     * between a given date range.
     * 
     * @return
     */
    protected JPanel viewDate() {
        return grid(
            grid.cell(label("Date"))
        );
    }
    
    /**
     * Search criteria for finding sales categories with the checked categories.
     * 
     * @return
     */
    protected JPanel viewCategory() {
        return grid(
            grid.cell(label("Category"))
        );
    }
    
    /**
     * Search criteria for finding sales with a specific rating or with a rating
     * within a given range.
     * 
     * @return
     */
    protected JPanel viewRating() {
        return grid(
            grid.cell(label("Rating"))
        );
    }
    
    public JPanel view() {
        Dimension dims = new Dimension(400, 200);
        
        tab_pane.setPreferredSize(dims);
        
        // set up the default tabs with empty panels
        tab_pane.addTab("Radius", search_criteria_tabs[RADIUS]);
        tab_pane.addTab("User ID", search_criteria_tabs[USER_ID]);
        tab_pane.addTab("Date", search_criteria_tabs[DATE]);
        tab_pane.addTab("Category", search_criteria_tabs[CATEGORY]);
        tab_pane.addTab("Rating", search_criteria_tabs[RATING]);
                
        // create the GUI
        return grid(
                
            // make the check box selection to enable/disable various criteria
            grid.cell(fieldset("Criteria", grid(
                grid.row(grid.cell(label("Check a box to include the associated"))),
                grid.row(grid.cell(label("search criteria information in the search."))),
                grid.row(grid.cell(grid(
                    grid.row(grid.cell(search_criteria_boxes[RADIUS]).anchor(1, 0, 0, 1)),
                    grid.row(grid.cell(search_criteria_boxes[USER_ID]).anchor(1, 0, 0, 1)),
                    grid.row(grid.cell(search_criteria_boxes[DATE]).anchor(1, 0, 0, 1)),
                    grid.row(grid.cell(search_criteria_boxes[CATEGORY]).anchor(1, 0, 0, 1)),
                    grid.row(grid.cell(search_criteria_boxes[RATING]).anchor(1, 0, 0, 1))
                )).margin(10, 0, 0, 0))
            ))).pos(0, 0),
            
            grid.cell(tab_pane).fill(1, 1).pos(1, 0),
            grid.cell(2, search_button).pos(0, 1)
        );
    }
}
