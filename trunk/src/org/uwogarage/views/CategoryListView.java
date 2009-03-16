package org.uwogarage.views;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
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
public class CategoryListView extends View {
    
    /**
     * Create a check box list of the categories and store the clicked ones in 
     * the responder_categories set.
     * 
     * @param in_categories
     * @param responder_categories
     * @return
     */
    static public JPanel view(ModelSet<CategoryModel> in_categories, 
                        final ModelSet<CategoryModel> responder_categories) {
        GridCell[] category_boxes = new GridCell[in_categories.size()];
        
        ButtonGroup group = new ButtonGroup();
        
        for(final CategoryModel category : in_categories) {
            final JCheckBox box = new JCheckBox(category.getName());
            group.add(box);
            
            box.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    if(box.isSelected())
                        responder_categories.add(category);
                    else
                        responder_categories.remove(category);
                }
            });
        }
        
        return grid(category_boxes);
    }
}
