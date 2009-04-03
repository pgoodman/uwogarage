package org.uwogarage;

import java.io.Serializable;

import org.uwogarage.controllers.*;

/**
 * This is really just a super simplification for the needs of this assignment 
 * to be able to call controllers from within other controllers.
 * 
 * @version $Id$
 * @author Peter Goodman
 */
final public class Dispatcher implements Serializable {
    
    // By serializing the dispatcher, we maintain all of the state of the program (super, super simple!)
    private static final long serialVersionUID = 9999999L;
    
    // Store all the controllers within the class
    final public UserController user = new UserController();
    final public CategoryController category = new CategoryController();
    final public GarageSaleController garage_sale = new GarageSaleController();
    final public RatingController rating = new RatingController();
}