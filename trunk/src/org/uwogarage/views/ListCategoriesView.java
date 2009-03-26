package org.uwogarage.views;

import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.uwogarage.models.CategoryModel;
import org.uwogarage.models.ModelSet;
import org.uwogarage.util.gui.GridCell;

/**
 * Show a list of categories.
 * 
 * @version $Id$
 */
public class ListCategoriesView extends View {
    
    /**
     * Create a check box list of the categories and store the clicked ones in 
     * the responder_categories set.
     * 
     * @param in_categories
     * @param responder_categories
     * @return
     */
    static public JScrollPane view(ModelSet<CategoryModel> in_categories,
                        ModelSet<CategoryModel> selected_categories,
                        final ModelSet<CategoryModel> responder_categories) {
        
        GridCell[][] category_boxes = new GridCell[in_categories.size()][];
        int i = 0;
        
        // set the default select set
        if(null == selected_categories)
            selected_categories = new ModelSet<CategoryModel>();
        
        for(final CategoryModel category : in_categories) {
            final JCheckBox box = new JCheckBox(category.getName());
            
            // select it
            if(selected_categories.contains(category))
                box.setSelected(true);
            
            // add a listener to add or remove the category from the responder
            // set
            box.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    if(box.isSelected())
                        responder_categories.add(category);
                    else
                        responder_categories.remove(category);
                }
            });
            
            category_boxes[i++] = grid.row(grid.cell(box).anchor(1, 0, 0, 1));
        }
        
        // create a scroll pane to contain the grid so that they don't screw up
        // any other views
        JScrollPane pane = new JScrollPane(
            grid(category_boxes),
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        
        pane.setPreferredSize(new Dimension(300, 130));
        
        return pane;
    }
}
