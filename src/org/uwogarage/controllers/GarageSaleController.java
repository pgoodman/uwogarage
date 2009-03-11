package org.uwogarage.controllers;

import javax.swing.JFrame;
import org.uwogarage.Dispatcher;
import org.uwogarage.models.GarageSaleModel;

/**
 * The GarageSaleController class responds to calls from a View and manipulates  
 * in GarageSaleModels in the datastore
 *
 * @author Nate Smith
 * @version $Id$
 */

public class GarageSaleController extends Controller<GarageSaleModel> {
	
	public GarageSaleController(Dispatcher d, JFrame f) {
        super(d, f);
    }

    /**
	 * This method displays the appropriate view to rate a garage sale, 
	 * processes the input, and stores the rating
	 */
	public void rate() {}
	
	/**
	 * This method displays the appropriate view to bulk add garage sales, 
	 * processes the input, and stores the rating
	 */
	public void bulkAdd() {}

	/**
	 * This method displays the appropriate view to bulk delete garage sales, 
	 * processes the input, and stores the rating
	 */
	public void bulkDelete() {}

    public void add() {
        // TODO Auto-generated method stub
        
    }

    public void edit(GarageSaleModel model) {
        // TODO Auto-generated method stub
        
    }

    protected void update(GarageSaleModel model) {
        // TODO Auto-generated method stub
        
    }
}
