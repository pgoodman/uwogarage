package org.uwogarage.views;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * View class, represents a single component within the interface. Extends
 * SimpleGui, mostly out of laziness, as I am not interested in static importing
 * the class everywhere.
 * 
 * @author Peter Goodman
 * @version $Id$
 */
abstract public class View<T> extends org.uwogarage.util.gui.SimpleGui {
    
    static protected JFrame f;
    
    /**
     * Set the program frame.
     */
    public static void setFrame(JFrame frame) {
        if(null == frame) {
            throw new NullPointerException("Frame is required.");
        }
        f = frame;
    }
    
    /**
     * Show a given view. A View method is given a delegate responder that
     * expects an argument of a certain type. This is so that the controller can
     * keep all control, but so that it doesn't need to wait on the view to do
     * something and instead the view can toggle a change in the controller
     * without any real access to it.
     * 
     * The alternative to a responder, and this was the original idea, was to
     * have views return an object of a specific type. This, however, would be
     * very cumbersome as it would require all views to be in some way or another
     * Modal and that would really be annoying to work with.
     * 
     * Thus, instead of returning an object of generic type T, a view is given a
     * delegate and passes its object of type T directly to delegate that the
     * controller gave the view so that it can handle the object. I.e. control
     * stays with the controller.
     */
    //public JComponent view(D<T> responder) { return null; }
    //public JComponent view() { return null; }
    
    /**
     * Replace all current content in the program frame with a new view.
     * @param responder
     */
    static public void replace(JComponent c) {
        if(null != f) {
            content.remove(f);
            content.add(f, c);
            f.validate();
        }
    }
}
