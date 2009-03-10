package org.uwogarage.util.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import javax.swing.*;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.text.Document;
import org.uwogarage.util.functional.*;

/**
 * Simplification of the general uses of certain swing objects. Using these
 * functions, GUIs can be built recursively, which is convenient because it fits
 * well with the underlying tree nature of the architecture.
 * 
 * Part of the idea of this class is that most functions have a return value so
 * that even when constructs are created recursively, they can also be accessed
 * through their return value.
 * 
 * @author Peter Goodman
 */
abstract public class SimpleGui {
    
    /**
     * Create and return a JFrame object. Requires a Delegate to
     * initialize the frame with any content before being shown.
     * 
     * @param title The title of the window.
     * @param init Delegate to initialize the frame.
     * @return JFrame
     */
    public static JFrame frame(String title) {
        return frame(title, null);
    }
    public static JFrame frame(String title, D<JFrame> init) {
        
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // initialize and display the frame
        if(null != init)
            init.call(frame);
        
        frame.pack();
        frame.setVisible(true);

        return frame;
    }
    
    /**
     * Create and return a JPanel object. JPanel's are lightweight.
     */
    public static JPanel panel() {
        return panel(null);
    }
    public static JPanel panel(D<JPanel> init) {
        JPanel panel = new JPanel();
        
        if(null != init)
            init.call(panel);
        
        return panel;
    }
    
    /**
     * Create a simple button.
     */
    public static JButton button() {
        return button_hook(new JButton(), null);
    }
    public static JButton button(D<JButton> on_click) {
        return button_hook(new JButton(), on_click);
    }
    public static JButton button(String name) {
        return button(name, null);
    }
    public static JButton button(String name, D<JButton> on_click) {
        return button_hook(new JButton(name), on_click);
    }
    protected static <B extends AbstractButton> B button_hook(final B btn, final D<B> on_click) {
        btn.setEnabled(on_click != null);
        
        // add an on-click event listener if one was passed
        if (btn.isEnabled()) {
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    on_click.call(btn);
                }
            });
        }
        
        return btn;
    }
    
    /**
     * Generic method to add a set of components to another.
     * @return
     */
    protected static <C extends JComponent, I extends JComponent> C 
    add_items_to_component(C component, I items[]) {
        for(I item : items) {
            if(null != item)
                component.add(item);
        }
        return component;
    }
    
    /**
     * Create a menu bar. Accepts a variable number of menus.
     * 
     * @param itms
     * @return JMenuBar
     */
    public static JMenuBar menu(final JFrame frame, JMenu ... menus) {
        JMenuBar bar = add_items_to_component(new JMenuBar(), menus);
        frame.setJMenuBar(bar);
        return bar;
    }
    
    public static class menu {
        
        /**
         * Create a menu. Accepts a variable number of menu items.
         * 
         * @return JMenu
         */
        public static JMenu dd(String name, JMenuItem ... items) {
            return add_items_to_component(new JMenu(name), items);
        }

        /**
         * Create a menu item.
         * 
         * @param args
         */
        public static JMenuItem item(final String text) {
            return item(text, null);
        }

        public static JMenuItem item(String text, final D<JMenuItem> on_click) {
            return button_hook(new JMenuItem(text), on_click);
        }
    }
    
    /**
     * Create a simple label.
     * 
     * @param args
     */
    public static JLabel label(final String label_name) {
        return label(label_name, JLabel.LEFT);
    }
    public static JLabel label(final String label_name, int text_align) {
        return new JLabel(label_name, text_align);
    }
    
    /**
     * Get the content pane for a container. This is one example of where the 
     * Swing libraries really fail; the developers should have isolated the 
     * getContentPane method in an interface so that it's not guess work.
     */
    protected static Container content_get_pane(Container c) {
        if(c instanceof JFrame)
            return((JFrame) c).getContentPane();
        else if(c instanceof JDialog)
            return ((JDialog) c).getContentPane();
        
        return c;
    }
    
    public static class content {
        /**
         * Add something to the content pane of a component.
         */
        public static <T extends JComponent> T add(Container parent, T child) {
            content_get_pane(parent).add(child);
            return child;
        }
        
        /**
         * Remove something from the content pane of a component.
         */
        public static <T extends JComponent> void remove(Container parent, T child) {
            content_get_pane(parent).remove(child);
        }
        public static void content_remove(Container parent) {
            content_get_pane(parent).removeAll();
        }
    }
    
    /**
     * Create a grid bag layout.
     */
    public static JPanel grid(GridCell ... cells) {
        return grid(new JPanel(), cells);
    }
    public static <T extends Container> T grid(T pane, GridCell ... cells) {
        Container c = content_get_pane(pane);
        
        c.setLayout(new GridBagLayout());
        
        for(GridCell cell : cells) {
            if(null != cell)
                c.add(cell.first(), cell.second());
        }
        
        return pane;
    }
    public static JPanel grid(GridCell[] cells_first, GridCell ... cells_rest) {
        JPanel pane = grid(cells_first);
        
        for(GridCell cell : cells_rest) {
            if(null != cell)
                pane.add(cell.first(), cell.second());
        }
        
        return pane;
    }
    
    public static class grid {
    
        /**
         * Create and return a simple grid cell. A Simple grid cell knows how much it spans
         * to the left and right.
         */
        public static GridCell cell(Component item) {
            return cell(1, item, 1);
        }
        
        public static GridCell cell(int width, Component item) {
            return cell(width, item, 1);
        }
        
        public static GridCell cell(Component item, int height) {
            return cell(1, item, height);
        }
        
        public static GridCell cell(int width, Component item, int height) {
            GridBagConstraints c = new GridBagConstraints();
            c.gridwidth = width;
            c.gridheight = height;
            return new GridCell(item, c);
        }
    }
    
    /**
     * Create an icon. By default, the icon location will be relative to the
     * SimpleGUI class; however, a Class object can be passed in to make it
     * relative to whatever class one chooses.
     */
    public static JLabel icon(String loc) {
        return icon(loc, SimpleGui.class);
    }
    public static <T> JLabel icon(String loc, Class<T> c) {
        URL icon_url = c.getResource(loc);
        if (null != icon_url)
            return new JLabel(new ImageIcon(icon_url));
        
        System.err.println("Couldn't find icon: " + loc);
        return new JLabel();
    }
    
    /**
     * Create an alert dialog box.
     */
    public static void alert(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message);
    }
    
    /**
     * Create a prompt.
     */
    public static String question(Component parent, String question) {
        try {
            return JOptionPane.showInputDialog(
                parent,
                question,
                "",
                JOptionPane.QUESTION_MESSAGE
            );
        } catch(HeadlessException e) {
            return "";
        }
    }
    
    /**
     * Create a custom dialog box. The dialog takes a function of one argument
     * that can further initialize the dialog box and also must return what is
     * to be the dialog's content pane.
     */
    public static JDialog dialog(JFrame frame, String title, F<JDialog,Container> content_init) {
        final JDialog dialog = new JDialog(frame, title);
        
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setContentPane(content_init.call(dialog));
        dialog.pack();
        dialog.setVisible(true);

        return dialog;
    }
    public static JDialog modal(JFrame frame, String title, F<JDialog,Container> content_init) {
        JDialog d = dialog(frame, title, content_init);
        d.setModal(true);
        return d;
    }
    
    /**
     * Create a text box.
     */
    public static JTextField text_field() {
        return new JTextField();
    }
    public static JTextField text_field(int columns) {
        return new JTextField(columns);
    }
    public static JTextField text_field(int columns, Document doc) {
        return new JTextField(doc, "", columns);
    }
    
    public static class laf {
    
        /**
         * Set the look and feel.
         */
        public static void theme(JFrame frame, String class_loc) {
            try {
                UIManager.setLookAndFeel(class_loc);
                SwingUtilities.updateComponentTreeUI(frame);
                frame.pack();
            } catch(Exception e) {
                alert(frame, 
                    "An error occured while trying to switch the look and feel."
                );
            }
        }
        
        /**
         * Load up a Synth skin given an XML file location. By default the file 
         * location will be relative to the SimpleGUI class; however, one can pass a 
         * Class instance to change what class the location is relative to.
         */
        public static void skin(JFrame frame, String xml_loc) {
            skin(frame, xml_loc, SimpleGui.class);
        }
        public static <T> void skin(JFrame frame, String xml_loc, Class<T> c) {
            try {
                SynthLookAndFeel synth = new SynthLookAndFeel();
                InputStream stream = c.getResourceAsStream(xml_loc);
                synth.load(stream, c);
                UIManager.setLookAndFeel(synth);
                SwingUtilities.updateComponentTreeUI(frame);
                frame.pack();
            } catch(Exception e) {
                alert(frame, 
                    "An error occured while trying to switch the look and feel."
                );
            }
        }
    }
    
    /**
     * Create the GUI in a thread.
     * 
     * @param args
     */
    public static <T> void gui_run(final T gui, final D<T> fn) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                fn.call(gui);
            }
        });
    }
}

