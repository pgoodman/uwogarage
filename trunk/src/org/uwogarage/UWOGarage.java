package org.uwogarage;

import static org.uwogarage.util.gui.SimpleGui.frame;
import javax.swing.JFrame;
import org.uwogarage.util.functional.D;

/**
 * The main program.
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
	    
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame("UWO Garage", new D<JFrame>() {
                    public void call(JFrame f) {
                        
                        View.setFrame(f);
                        
                        Dispatcher d = new Dispatcher();
                        d.user.login();
                    }
                });
            }
        });
	}

}
