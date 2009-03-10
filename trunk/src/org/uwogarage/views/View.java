package org.uwogarage.views;

import javax.swing.JFrame;

/**
 * View class, represents a single component within the interface. Extends
 * SimpleGui, mostly out of laziness, as I am not interested in static importing
 * the class everywhere.
 * 
 * @author Peter Goodman
 *
 */
abstract public class View extends org.uwogarage.util.gui.SimpleGui {
    
    /**
     * Show the view. Views are meant to be able to respond with some sort of
     * objects to the controllers calling them.
     * 
     * @param <T>
     * @param f
     * @return
     */
    abstract public <T> T view(JFrame f);
}
