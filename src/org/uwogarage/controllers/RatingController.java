package org.uwogarage.controllers;

import javax.swing.JFrame;
import org.uwogarage.Dispatcher;
import org.uwogarage.models.RatingModel;

/**
 * The RatingController class responds to calls from a View and manipulates  
 * in RatingModels in the datastore
 *
 * @author Nate Smith
 * @version $Id$
 */
public class RatingController extends Controller<RatingModel> {

    public RatingController(Dispatcher d) {
        super(d);
    }
    
    /**
     * Show the view to rate a garage sale.
     */
    public void add() {
        // TODO Auto-generated method stub
        
    }

    public void edit(RatingModel model) {
        // TODO Auto-generated method stub
        
    }

    protected void update(RatingModel model) {
        // TODO Auto-generated method stub
        
    }
}