package org.uwogarage.views.seller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.GarageSaleModelSet;
import org.uwogarage.models.ModelSet;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.views.View;


/**
 * @version $Id$
 */
public class BulkAddGarageSaleView extends View {
	
    static public JPanel view(final UserModel logged_user, final D<ModelSet<GarageSaleModel>> responder) {
        
    	
        final JFileChooser chooser = new JFileChooser();
        chooser.addActionListener(new ActionListener(){

        	public void actionPerformed(ActionEvent e) {
        	    //Handle open button action.
        	    
    	       System.out.println(e);
        	   
        	}       	
        });
        
        // TODO provide option to either remove existing garage sales
        // or to add bulk loaded garage sales info to existing garage sales
        
        return grid(
            grid.cell(button("choose file", new D<JButton>(){
				public void call(JButton arg1) {
					
					if (JFileChooser.APPROVE_OPTION != chooser.showOpenDialog(f)) {
						return;
					}
					try {
						
						responder.call(GarageSaleModelSet.loadFromFile(
							logged_user,
							chooser.getSelectedFile()
						));
					}
					catch (Exception e) {
						dialog.alert(f, e.getMessage());
					}
					
				}
            	
            }))
        );
    }
}
