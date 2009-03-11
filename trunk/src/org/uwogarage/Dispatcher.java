package org.uwogarage;

import javax.swing.JFrame;

import org.uwogarage.controllers.*;

/**
 * This is really just a super simplification for the needs of this assignment 
 * to be able to call controllers from within other controllers.
 * 
 * @version $Id$
 */
final public class Dispatcher {
    
    final public UserController user;
    final public CategoryController category;
    final public GarageSaleController garage_sale;
    final public RatingController rating;
    
    public Dispatcher() {
        
        Controller.setDispatcher(this);
        
        user = new UserController();
        category = new CategoryController();
        garage_sale = new GarageSaleController();
        rating = new RatingController();
    }
}