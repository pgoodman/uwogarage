package org.uwogarage.util.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Peter Frame, fixes the minimum size bug in JFrames.
 * 
 * @author petergoodman
 * @version $Id$
 */
public class PFrame extends JFrame {

    private static final long serialVersionUID = 2846599726095926330L;

    private int _min_width = 0,
                _min_height = 0;
    
    private boolean _min_size_added = false,
                    _min_size_too_big = false;
    
    final private JPanel _scroll_content_pane = new JPanel();
    private JScrollPane _scroll_port = new JScrollPane(
        _scroll_content_pane,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
    );
    
    /**
     * Constructor.
     * @param title
     */
    public PFrame(String title) {
        super(title);
    }
    
    /**
     * Return the content pane to add stuff to.
     */
    public Container getContentPane() {
        if(_min_size_too_big)
            return _scroll_content_pane;
        
        return super.getContentPane();
    }
    
    /**
     * Set the minimum size of the frame.
     */
    public void setMinimumSize(Dimension d) {
        
        _min_width = d.width;
        _min_height = d.height;
        
        // this pframe
        final PFrame self = this;

        // Get the current screen size
        final Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize(),
                        preferred_size;
        
        // record whether or not the minimum size extends past the screen size
        _min_size_too_big = false;
        if(screen_size.width < _min_width || screen_size.height < _min_height) {
            _min_size_too_big = true;
            _min_width = Math.min(screen_size.width, _min_width);
            _min_height = Math.min(screen_size.height, _min_height);
            
            preferred_size = new Dimension(_min_width, _min_height);
            
            // set the default sizes
            this.setPreferredSize(preferred_size);
            _scroll_port.setPreferredSize(preferred_size);
            _scroll_content_pane.setBounds(_scroll_port.getVisibleRect());
            
            // add in the scroller
            super.getContentPane().add(_scroll_port);
        }
        
        if(_min_size_added)
            return;
        
        // enforce a minimum size on the frame                
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Dimension old_size = self.getSize();
                boolean resize_width = old_size.width < _min_width,
                        resize_height = old_size.height < _min_height;
                
                Dimension new_size = new Dimension(
                    Math.max(_min_width, old_size.width),
                    Math.max(_min_height, old_size.height)
                );
                
                if(_min_size_too_big) {
                    _scroll_port.setPreferredSize(new_size);
                    _scroll_content_pane.setBounds(_scroll_port.getVisibleRect());
                    //_scroll_content_pane.setBounds(_scroll_port.getBounds());
                }
                
                if(resize_width || resize_height) {
                    self.setSize(new_size);
                }
            }
        });
    }
}
