package org.uwogarage.views.admin;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.uwogarage.models.CategoryModel;
import org.uwogarage.models.ModelSet;
import org.uwogarage.util.documents.WordDocument;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.P;
import org.uwogarage.views.View;

/**
 * @version $Id$
 */
public class AddCategoryView extends View<CategoryModel> {
    
    static public JPanel view(final ModelSet<CategoryModel> categories, final D<CategoryModel> responder) {
        
        // category name text field
        final JTextField cat_name = field.text(50, new WordDocument(50));
        
        // the predicate to filter out any categories, so that we can make sure
        // that the category being added is unique
        final P<CategoryModel> uniqueness_pred = new P<CategoryModel>() {
            public boolean call(CategoryModel c) {
                return cat_name.getText().equals(c.getName());
            }
        };
        
        // the form
        return grid(
            grid.cell(2, label("Add Categoery")).pos(0, 0),
            
            grid.cell(label("Category Name:"))
                .pos(0, 0)
                .anchor(0, 1, 0, 0)
                .margin(10, 10, 0, 10),
            
            grid.cell(cat_name)
                .pos(1, 0)
                .anchor(0, 0, 0, 1)
                .margin(10, 10, 0, 0),
            
            grid.cell(2, button("Add", new D<JButton>() {
                public void call(JButton b) {
                    
                    // this is not a unique category
                    if(null != categories.filterOne(uniqueness_pred)) {
                        dialog.alert(f,
                            "A category with the same name already exists."
                        );
                    
                    // try to create the category, if an exception is thrown then
                    // the category name was incorrectly formatted
                    } else {
                        try {
                            responder.call(new CategoryModel(cat_name.getText()));
                            
                        } catch(Exception e) {
                            dialog.alert(f, 
                                "The category name was incorrectly formatted. "+
                                "Category names must be between 1 and 50 characters "+
                                "and can have letters, numbers, and spaces."
                            );
                        }
                    }
                }
            })).pos(0, 2).margin(19, 10, 10, 10)
        );
    }
}
