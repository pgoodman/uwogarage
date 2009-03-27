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
    public void save(GarageSaleModel sale, int rating) {
        for (RatingModel a_rating : sale.ratings) {
            if (a_rating.getUser() == logged_user) {
                a_rating.setRating(rating);
                d.garage_sale.view(sale);
                return;
            }
        }
        RatingModel therating = new RatingModel(rating, logged_user, sale);
        sale.ratings.add(therating);
        logged_user.ratings.add(therating);
        models.add(therating);
        d.garage_sale.view(sale);
    }

    public void edit(GarageSaleModel sale, int rating) {
        
        d.garage_sale.view(sale);
    }
    
    public void delete(RatingModel rating) {
        
    }
}
