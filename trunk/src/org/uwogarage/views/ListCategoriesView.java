package org.uwogarage.views;

import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
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
                        final ModelSet<CategoryModel> selected_categories,
                        final ModelSet<CategoryModel> responder_categories) {
        
        GridCell[][] category_boxes = new GridCell[in_categories.size()][];
        int i = 0;
        
        for(final CategoryModel category : in_categories) {
            final JCheckBox box = new JCheckBox(category.getName());
            
            // select it
            if(null != selected_categories && selected_categories.contains(category))
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
        
        JPanel category_list = grid(category_boxes);
        category_list.setPreferredSize(new Dimension(300, 150));
        
        return new JScrollPane(
            category_list, 
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
    }
}
