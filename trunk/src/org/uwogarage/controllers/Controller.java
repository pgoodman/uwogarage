package org.uwogarage.controllers;

import org.uwogarage.Dispatcher;
import org.uwogarage.models.ModelSet;
import org.uwogarage.models.UserModel;

/**
 * The Controller class responds to calls from a View and manipulates the 
 * appropriate models in the data store
 *
 * @author Nate Smith
 * @version $Id$
 */

abstract public class Controller<T> {
    
    // A collection of models this controller can manipulate
    protected ModelSet<T> models;
    
    // give the controller the ability to call the other controllers
    static protected Dispatcher d;
    
    // the currently logged in user
    static protected UserModel logged_user;
    
    /**
     * Constructor, bring in the dispatcher.
     * @param d
     */
    public Controller() {
        models = new ModelSet<T>();
    }
    
    static public void setDispatcher(Dispatcher dispatcher) {
        d = dispatcher;
    }
}
