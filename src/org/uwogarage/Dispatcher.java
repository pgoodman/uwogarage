package org.uwogarage;

import javax.swing.JFrame;

import org.uwogarage.controllers.*;

/**
 * Singleton Dispatcher class. This is really just a super simplification
 * for the needs of this assignment to be able to call controllers from
 * within convenient places.
 * 
 * Even though the dispatcher class enforces itself to be a singleton, it is
 * advised to not use it as such as singleton's are generally evil. Although 
 * less convenient in appearance, it should ideally be passed to functions.
 * 
 * @author petergoodman
 * @version $Id$
 */
final public class Dispatcher {
    
    protected static Dispatcher inst;
    
    final public UserController user;
    final public CategoryController category;
    final public GarageSaleController garage_sale;
    final public RatingController rating;
    
    protected Dispatcher(JFrame f) {
        user = new UserController(this, f);
        category = new CategoryController(this, f);
        garage_sale = new GarageSaleController(this, f);
        rating = new RatingController(this, f);
    }
    
    /**
     * Get the dispatcher instance.
     * @return
     */
    static public Dispatcher getInstance(JFrame f) {
        if(inst != null)
            return inst;
        
        return inst = new Dispatcher(f);
    }    
}