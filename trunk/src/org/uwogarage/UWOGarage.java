package org.uwogarage;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.F0;

import static org.uwogarage.util.gui.SimpleGui.menu;
import static org.uwogarage.util.gui.SimpleGui.laf;
import org.uwogarage.views.View;

/**
 * UWO Garage Sale program.
 * 
 * @version $Id$
 */
public class UWOGarage {
    
    // the delegate that is called when the game is quit. It is stored here
    // so that both the quit menu item and the closing button can call on it.
    static protected F0 quit_program_delegate = new F0() {
        public void call() {
            
            // TODO Save the models to the file system
            
            System.exit(0);
        }
    };
    
    /**
     * Set up the GUI.
     * 
     * @param is_admin
     */
    static protected void init(boolean is_admin) {
        
        // a nice OS X setting :D
        if(System.getProperty("os.name").toLowerCase().startsWith("mac")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
        
        // instantiate all of the controllers and make them available to eachother
        // through this dispatcher instance
        final Dispatcher dispatch_to = new Dispatcher();
                
        // create the program frame that the entire program will run in
        View.programFrame("UWO Garage", new D<JFrame>() {
            public void call(final JFrame f) {
                
                f.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent arg0) {
                        quit_program_delegate.call();
                    }
                });
                
                // create the main program menu for our application
                menu(f,
                    menu.dd("File",
                        menu.item("Quit", new D<JMenuItem>() {
                            public void call(JMenuItem i) {
                                quit_program_delegate.call();
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
    
	/**
	 * Run the program.
	 */
	public static void main(String[] args) {
	    
	    // run the GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                // TODO Recognize command line -admin arg
                init(false);
            }
        });
	}

}
