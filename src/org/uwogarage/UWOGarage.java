package org.uwogarage;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import org.uwogarage.controllers.Controller;
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
    
    // make a hidden (at least on UNIX) file to store the current state of the
    // program
    static protected String fname = ".uwo-garage-db";
    
    // the delegate that is called when the game is quit. It is stored here
    // so that both the quit menu item and the closing button can call on it.
    static protected D<Dispatcher> quit_program_delegate = new D<Dispatcher>() {
        public void call(Dispatcher d) {
            
            try {
                (new ObjectOutputStream(new FileOutputStream(fname)))
                    .writeObject(d);
            } catch(Exception e) {
                System.out.println("Error: Failed to save information to database.");
            }
                
            System.exit(0);
        }
    };
    
    /**
     * Get the dispatcher that the controllers will use.
     * 
     * @return
     */
    static protected Dispatcher getDispatcher() {
        try {
            return (Dispatcher) ((new ObjectInputStream(new FileInputStream(fname)))
                .readObject());
        } catch(Exception e) {
            return new Dispatcher();
        }
    }
    
    /**
     * Set up the GUI.
     * 
     * @param is_admin
     */
    static protected void init(final boolean is_admin) {
        
        // a nice OS X setting :D
        if(System.getProperty("os.name").toLowerCase().startsWith("mac")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
        
        // instantiate all of the controllers and make them available to each
        // other through this dispatcher instance
        final Dispatcher dispatch_to = getDispatcher();
        Controller.setDispatcher(dispatch_to);        
        
        // create the program frame that the entire program will run in
        View.programFrame("UWO Garage", new D<JFrame>() {
            public void call(final JFrame f) {
            	f.setMinimumSize(new Dimension(905, 500));
                f.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent arg0) {
                        quit_program_delegate.call(dispatch_to);
                    }
                });
                
                // create the main program menu for our application
                menu(f,
                    menu.dd("File",
                        menu.item("Quit", new D<JMenuItem>() {
                            public void call(JMenuItem i) {
                                quit_program_delegate.call(dispatch_to);
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
                
                // go to the admin panel if we are an admin else call the login 
                // action of the user controller
                if (is_admin)
                	dispatch_to.user.adminPanel();
                else
                    dispatch_to.user.login();
            }
        });
    }
    
	/**
	 * Run the program.
	 */
	public static void main(final String[] args) {
	    
	    // run the GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                // Recognize command line -admin arg
                init((args.length > 0) ? args[0].equals("-admin") : false);
            }
        });
	}

}
