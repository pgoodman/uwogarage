package org.uwogarage.controllers;

import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.RatingModel;

/**
 * The RatingController class responds to calls from a View and manipulates  
 * in RatingModels in the datastore
 *
 * @author Nate Smith
 * @version $Id$
 */
public class RatingController extends Controller<RatingModel> {
    
    private static final long serialVersionUID = -1654586445037807445L;

    /**
     * Show the view to rate a garage sale.
     */
    public void add(GarageSaleModel sale, int rating) {
        // TODO Auto-generated method stub
    }

    public void edit(RatingModel rating) {
        // TODO Auto-generated method stub
        
    }
    
    public void delete(RatingModel rating) {
        
    }
}
