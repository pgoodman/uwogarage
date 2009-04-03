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
     * @param c the component to be shown in the main view
     */
    static public void show(JComponent c) {
        
        Container which = f;
        
        // take the top context, defaults to taking the frame
        if(context.size() > 0) {
            Container top = context.peek(),
                      parent = (Container) top,
                      self = (Container) c;
            
            // if false then that means we are trying to add 'c' to one of its
            // children
            boolean set_which = true;
            
            try {
                
                // go look for 'c' within what's on the top of the context stack,
                // or go as far as we can with its parents until we're sure that
                // we're not adding a component to itself in one way or another
                while(null != parent) {
                    if(parent == self) {
                        which = context.size() > 1 ? context.get(context.size() - 2) : f;
                        set_which = false;
                        break;
                    }
                    
                    parent = parent.getParent();
                }
                
            } catch(Exception e) { }
            
            // we are not adding the component to itself, set the context on the
            // top of the stack to the one to add 'c' to.
            if(set_which) {
            
                // try to make the component we are adding as big as possible
                if(top instanceof JComponent)
                    c.setBounds(((JComponent) top).getVisibleRect());
                
                which = (Container) top;
            }
        }
        
        // if there is some sort of context to use then use it
        if(null != which) {
            if(which != c) {
                content.remove(which);
                content.add(which, c);
            }
            
            /*for(Container p : context) {
                if(p instanceof Window)
                    ((Window) p).pack();
                
                p.validate();
            }*/
            
            which.validate();
        }
    }
    
    /**
     * Push a context onto the context stack.
     * @param c the context to be pushed
     */
    static protected void pushContext(Container c) {
        context.push(c);
    }
    
    /**
     * Pop a context from the stack.
     */
    static protected void popContext() {
        try {
            context.pop();
        } catch(Exception e) { }
    }
    
    /**
     * Create the main frame of the program. This is important as it makes the 
     * main program frame instance available to all views.
     * @param title the title of the program frame
     */
    static public JFrame programFrame(String title) {
        return programFrame(title, null);
    }
    /**
     * Create the main frame of the program. This is important as it makes the 
     * main program frame instance available to all views.
     * @param title the title of the program frame
     * @param init_program_frame the delegate containing the method to be called from the frame
     * @return the main program frame
     */
    static public PFrame programFrame(String title, final D<PFrame> init_program_frame) {
        return SimpleGui.frame(title, new D<PFrame>() {
            public void call(PFrame program_frame) {
                
                // add the frame to the stack, it doesn't actually matter if it
                // inadvertently gets popped off
                pushContext(f = program_frame);
                
                if(null != init_program_frame) {
                    init_program_frame.call(program_frame);
                }
            }
        });
    }
}
