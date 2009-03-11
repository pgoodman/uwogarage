package org.uwogarage.views;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import org.uwogarage.util.functional.D;

/**
 * View showing the main menu.
 * @author petergoodman
 * @version $Id$
 */
public class MenuView extends View<Object> {
    
    public MenuView(JFrame frame) {
        super(frame);
    }

    public void view() {
        menu(f,
            menu.dd("File",
                menu.item("Quit", new D<JMenuItem>() {
                    public void call(JMenuItem i) {
                        System.exit(0);
                    }
                })
            ),
            menu.dd("Help",
                menu.item("Help Contents")
            )
        );
    }
    
}
