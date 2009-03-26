package org.uwogarage.views;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.uwogarage.util.functional.D;
import org.uwogarage.util.gui.PFrame;
import org.uwogarage.util.gui.SimpleGui;

/**
 * View class, represents a single component within the interface. Extends
 * SimpleGui, mostly out of laziness, as I am not interested in static importing
 * the class everywhere.
 * 
 * @author Peter Goodman
 * @version $Id$
 */
abstract public class View extends SimpleGui {
    
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
            //f.pack();
            f.validate();
        }
    }
    
    /**
     * Create the main frame of the program. This is important as it makes the 
     * main program frame instance available to all views.
     */
    static public JFrame programFrame(String title) {
        return programFrame(title, null);
    }
    static public PFrame programFrame(String title, final D<PFrame> init_program_frame) {
        
        return SimpleGui.frame(title, new D<PFrame>() {
            
            public void call(PFrame program_frame) {
                f = program_frame;
                
                if(null != init_program_frame) {
                    init_program_frame.call(program_frame);
                }
            }
        });
    }
}
