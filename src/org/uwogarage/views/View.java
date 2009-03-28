package org.uwogarage.views;

import java.awt.Container;
import java.util.Stack;

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
     * GUI context stack, used to let the GUIs allow controllers to affect
     * only individual parts of a gui.
     */
    static private Stack<Container> context = new Stack<Container>();
    
    /**
     * Replace all current content in the program frame with a new view. The
     * tricky part about managing contexts is that we need to make sure that we
     * don't add a component to itself as it is likely that we will have added
     * a context while building its parent.
     */
    static public void show(JComponent c) {
        
        Container which = f;
        
        // take the top context, defaults to taking the frame
        if(context.size() > 0) {
            Container top = context.peek(),
                      parent = (Container) top,
                      self = (Container) c;
            
            boolean set_which = true;
            
            try {
                while(null != parent) {
                    if(parent == self) {
                        which = context.size() > 1 ? context.get(context.size() - 2) : f;
                        set_which = false;
                        break;
                    }
                    
                    parent = parent.getParent();
                }
                
            } catch(Exception e) { }
            
            if(set_which) {
            
                // try to make the component we are adding as big as possible
                if(top instanceof JComponent)
                    c.setBounds(((JComponent) top).getVisibleRect());
                
                which = (Container) top;
            }
        }
        
        // if there is some sort of container to use, use it
        if(null != which) {
            if(which != c) {
                content.remove(which);
                content.add(which, c);
            }
            which.validate();
        }
    }
    
    static protected void pushContext(Container c) {
        context.push(c);
    }
    
    static protected void popContext() {
        try {
            context.pop();
        } catch(Exception e) { }
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
