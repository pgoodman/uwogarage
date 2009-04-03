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
     * @param min the minimum value of the slider
     * @param max the maximum value of the slider
     * @param initial the initial value of the slider
     * @param on_slide the delegate containing the method to be called when the slider is 'slid'
     * @return a JSlider with the necessary settings
     */
    static public JSlider view(int min, int max, int initial, final D<JSlider> on_slide) {
        
        if(initial < min) initial = min;
        if(initial > max) initial = max;

        final JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, initial);
        Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>(2);
        
        slider.setMinorTickSpacing(1);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        
        labels.put(new Integer(min), label(String.valueOf(min)));
        labels.put(new Integer(max), label(String.valueOf(max)));
        
        int step = (int)Math.ceil((max - min) / 4);
        for(int i = min+step; i < max; i += step)
            labels.put(new Integer(i), label(String.valueOf(i)));
        
        
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
