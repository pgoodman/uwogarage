package org.uwogarage.views;

import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.uwogarage.util.functional.D;

/**
 * Create the slider for map zoom level.
 * 
 * @author Peter Goodman
 * @version $Id$
 */
public class MapZoomSlider extends View {
    
    /**
     * Create a map slider.
     * @return
     */
    static public JSlider view() {
        return view(null);
    }
    static public JSlider view(final D<JSlider> on_slide) {
        
        final JSlider slider = new JSlider(JSlider.HORIZONTAL, 4, 23, 4);
        Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>(2);
        
        slider.setMinorTickSpacing(10);
        slider.setPaintLabels(true);
        
        labels.put(new Integer(4), label("-"));
        labels.put(new Integer(23), label("+"));
        
        slider.setLabelTable(labels);
        
        // add in the on_slide state change callback
        if(null != on_slide) {
            slider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    on_slide.call(slider);                        
                }
            });
        }
        
        return slider;
    }
}
