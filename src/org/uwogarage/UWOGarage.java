package org.uwogarage;

import static org.uwogarage.util.gui.SimpleGui.frame;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

import org.uwogarage.util.functional.D;
import org.uwogarage.util.gui.SimpleGui.menu;
import org.uwogarage.views.View;
import static org.uwogarage.util.gui.SimpleGui.*;

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
                        final Dispatcher d = new Dispatcher();
                        
                        menu(f,
                            menu.dd("File",
                                menu.item("Quit", new D<JMenuItem>() {
                                    public void call(JMenuItem i) {
                                        System.exit(0);
                                    }
                                })
                            ),
                            menu.dd("Theme"),
                            menu.dd("Help",
                                menu.item("Help Contents")
                            )
                        );
                        
                        d.user.login();
                    }
                });
            }
        });
	}

}
