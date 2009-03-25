package org.uwogarage;

import java.io.Serializable;

import org.uwogarage.controllers.*;

/**
 * This is really just a super simplification for the needs of this assignment 
 * to be able to call controllers from within other controllers.
 * 
 * @version $Id$
 */
final public class Dispatcher implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 9999999L;
    
    
    final public UserController user = new UserController();
    final public CategoryController category = new CategoryController();
    final public GarageSaleController garage_sale = new GarageSaleController();
    final public RatingController rating = new RatingController();
}