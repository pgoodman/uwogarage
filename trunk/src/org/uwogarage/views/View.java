package org.uwogarage.views;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.uwogarage.util.functional.D;
import org.uwogarage.util.gui.SimpleGui;

/**
 * View class, represents a single component within the interface. Extends
 * SimpleGui, mostly out of laziness, as I am not interested in static importing
 * the class everywhere.
 * 
 * @author Peter Goodman
 * @version $Id$
 */
abstract public class View<T> extends SimpleGui {
    
    /**
     * The program frame.
     */
    static protected JFrame f;
    
    /**
     * Replace all current content in the program frame with a new view.
     */
    static public void show(JComponent c) {
        if(null != f) {
            content.remove(f);
            content.add(f, c);
            f.pack();
            f.validate();
        }
    }
    
    /**
     * Create the main frame of the program. This is important as it makes the 
     * main program frame instance available to all views.
     */
    public static JFrame programFrame(String title) {
        return programFrame(title, null);
    }
    public static JFrame programFrame(String title, final D<JFrame> init) {
        return SimpleGui.frame(title, new D<JFrame>() {
            public void call(JFrame program_frame) {
                f = program_frame;
                
                if(null != init) {
                    init.call(program_frame);
                }
            }
        });
    }
}
