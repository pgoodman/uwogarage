package org.uwogarage.views.admin;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.uwogarage.models.CategoryModel;
import org.uwogarage.models.ModelSet;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.gui.GridCell;
import org.uwogarage.views.TabView;

/**
 * Show a list of categories, along with a delete button for each.
 * 
 * @version $Id$
 */
public class ListCategoriesView extends TabView {
    
    static public JPanel view(ModelSet<CategoryModel> categories, 
                               final D<CategoryModel> responder) {
        
        GridCell[][] rows = new GridCell[categories.size()][];
        int i = 0;
        
        // create the rows of categories.
        for (final CategoryModel category : categories)
        {
        	rows[i++] = grid.row(
				grid.cell(label(category.getName())).anchor(1, 0, 0, 1),
				
				// delete button
				grid.cell(button("Delete", new D<JButton>(){
					public void call(JButton b){
						if (!dialog.confirm(f, "Are you sure you want to delete this category?"))
							return;
						
						responder.call(category);
						dialog.alert(f, "The category has been deleted.");
						changeProgramTab(3);
					}
				})).margin(0, 0, 0, 10)
        	);
        	
        }
    	return grid(rows);
    }
}
