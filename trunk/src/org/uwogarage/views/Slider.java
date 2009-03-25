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
public class Slider extends View {
    
    /**
     * Create a map slider.
     * @return
     */
    static public JSlider view(int min, int max, int initial, final D<JSlider> on_slide) {
        
        final JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, initial);
        Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>(2);
        
        slider.setMinorTickSpacing(1);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        
        labels.put(new Integer(min), label("-"));
        labels.put(new Integer(max), label("+"));
        
        slider.setLabelTable(labels);
        
        // add in the on_slide state change delegate
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
