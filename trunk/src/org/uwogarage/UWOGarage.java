package org.uwogarage;

import static org.uwogarage.util.gui.SimpleGui.frame;
import javax.swing.JFrame;
import org.uwogarage.controllers.*;
import org.uwogarage.util.functional.D;

/**
 * 
 * @version $Id$
 */
public class UWOGarage {
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	    // TODO: recognize command line -admin arg
	    
	    if(System.getProperty("os.name").toLowerCase().startsWith("mac")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
	    
	    // very simple class for dealing with all of the various controllers.
	    final Dispatcher d = Dispatcher.getInstance();
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame("UWO Garage", new D<JFrame>() {
                    public void call(JFrame f) {
                        
                    }
                });
            }
        });
	}

}
