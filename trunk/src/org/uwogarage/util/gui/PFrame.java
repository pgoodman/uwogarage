package org.uwogarage.util.gui;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

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
    
    private boolean _min_size_added = false;
    
    /**
     * Constructor.
     * @param title
     */
    public PFrame(String title) {
        super(title);
    }
    
    /**
     * Set the minimum size of the frame.
     */
    public void setMinimumSize(Dimension d) {
        
        _min_width = d.width;
        _min_height = d.height;
        
        if(_min_size_added)
            return;
        
        final PFrame self = this;
        
        // enforce a minimum size on the frame                
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Dimension old_size = self.getSize();
                
                boolean resize_width = old_size.width < _min_width,
                        resize_height = old_size.height < _min_height;
                
                if(resize_width || resize_height) {
                    self.setSize(new Dimension(
                        resize_width ? _min_width : old_size.width,
                        resize_height ? _min_height : old_size.height
                    ));
                }
            }
        });
    }
}
