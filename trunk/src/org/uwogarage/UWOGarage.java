package org.uwogarage;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import org.uwogarage.util.functional.D;
import org.uwogarage.util.gui.SimpleGui.menu;
import org.uwogarage.views.View;

import static org.uwogarage.util.gui.SimpleGui.*;

/**
 * UWO Garage Sale program.
 * 
 * @version $Id$
 */
public class UWOGarage {
    
	/**
	 * Run the program.
	 */
	public static void main(String[] args) {
		
	    // TODO Recognize command line -admin arg
	    
	    // a nice OS X setting :D
	    if(System.getProperty("os.name").toLowerCase().startsWith("mac")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
	    
	    // instantiate all of the controllers and make them available to eachother
	    // through this dispatcher instance
	    final Dispatcher dispatch_to = new Dispatcher();
	    
	    // minimum dimensions for the program
	    final Dimension min_dimensions = new Dimension(750, 750);
	    
	    // run the GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                // create the program frame that the entire program will run in
                View.programFrame("UWO Garage", new D<JFrame>() {
                    public void call(final JFrame f) {
                        
                        // set the program frame dimensions
                        f.setMinimumSize(min_dimensions);
                        f.setPreferredSize(min_dimensions);
                        
                        // create the main program menu for our application
                        menu(f,
                            menu.dd("File",
                                menu.item("Quit", new D<JMenuItem>() {
                                    public void call(JMenuItem i) {
                                        System.exit(0);
                                    }
                                })
                            ),
                            menu.dd("Theme",
                                menu.item("Default", new D<JMenuItem>() {
                                    public void call(JMenuItem item) {
                                        laf.theme(f, 
                                            UIManager.getSystemLookAndFeelClassName()
                                        );
                                    }
                                }),
                                menu.item("Motif", new D<JMenuItem>() {
                                    public void call(JMenuItem item) {
                                        laf.theme(f,
                                            "com.sun.java.swing.plaf.motif.MotifLookAndFeel"
                                        );
                                    }
                                }),
                                menu.item("Ocean", new D<JMenuItem>() {
                                    public void call(JMenuItem item) {
                                        laf.theme(f,
                                            UIManager.getCrossPlatformLookAndFeelClassName()
                                        );
                                    }
                                })
                            ),
                            menu.dd("Help",
                                menu.item("Help Contents")
                            )
                        );
                        
                        // call the login action of the user controller
                        dispatch_to.user.login();
                    }
                });
            }
        });
	}

}
