package org.uwogarage.controllers;

import java.io.Serializable;

import org.uwogarage.Dispatcher;
import org.uwogarage.models.Model;
import org.uwogarage.models.ModelSet;
import org.uwogarage.models.UserModel;

/**
 * The Controller class responds to calls from a View and manipulates the 
 * appropriate models in the data store
 *
 * @author Nate Smith
 * @version $Id$
 */

abstract public class Controller<T extends Model> implements Serializable {
    
    private static final long serialVersionUID = -1709646925689208669L;

    // A collection of models this controller can manipulate
    protected ModelSet<T> models = new ModelSet<T>();
    
    // give the controller the ability to call the other controllers
    transient static protected Dispatcher d;
    
    // the currently logged in user
    transient static protected UserModel logged_user;
    
    /**
     * Set the controller dispatcher.
     * 
     * @param dispatcher
     */
    static public void setDispatcher(Dispatcher dispatcher) {
        d = dispatcher;
    }
    
    /**
     * Return the internal model set of the controller.
     * 
     * @return
     */
    public ModelSet<T> getModels() {
        return models;
    }
}
