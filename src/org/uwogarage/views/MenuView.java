package org.uwogarage.views;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import org.uwogarage.util.functional.D;

/**
 * View showing the main menu.
 * @author petergoodman
 *
 */
public class MenuView extends View {

    public <T> T view(JFrame f) {
        
        menu_bar(f,
            menu("File",
                menu_item("Quit", new D<JMenuItem>() {
                    public void call(JMenuItem i) {
                        System.exit(0);
                    }
                })
            ),
            menu("Help",
                menu_item("Help Contents")
            )
        );
        return null;
    }
    
}
