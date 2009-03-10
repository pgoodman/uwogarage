package org.uwogarage.util.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import org.uwogarage.util.functional.Tuple;

/**
 * GridCell:
 * Maintain a component and its associated grid bag constraints. This class
 * also makes it easier to modify the simple parts of the GridBagConstraints.
 * Finally, this class provides a more meaningful name with respect to the grid
 * SimpleGUI functions.
 * 
 * @author Peter Goodman
 */
public class GridCell extends Tuple<Component,GridBagConstraints> {

    public GridCell(Component a, GridBagConstraints b) {
        super(a, b);
    }
    
    /**
     * Internal padding of a grid cell.
     */
    public GridCell padding(int x, int y) {
        b.ipadx = x;
        b.ipady = y;
        return this;
    }
    
    /**
     * External padding of a grid cell.
     */
    public GridCell margin(int top, int right, int bottom, int left) {
        b.insets = new Insets(top, left, bottom, right);
        return this;
    }
    
    /**
     * Top-left position in grid.
     */
    public GridCell pos(int x, int y) {
        b.gridx = x;
        b.gridy = y;
        return this;
    }
    
    /**
     * Set the row (x) and column (y) weights.
     */
    public GridCell weight(double x, double y) {
        b.weightx = x;
        b.weighty = y;
        return this;
    }
    
    /**
     * Set the fill of the current cell.
     */
    public GridCell fill(int h, int v) {
        
        h = h & 1;
        v = v & 1;
        int f = GridBagConstraints.NONE;
        
        // figure out how to fill
        if(h+v == 2)
            f = GridBagConstraints.BOTH;
        else if(h == 1)
            f = GridBagConstraints.HORIZONTAL;
        else if(v == 1)
            f = GridBagConstraints.VERTICAL;
        
        b.fill = f;
        
        // set the weights
        if (f == GridBagConstraints.HORIZONTAL || f == GridBagConstraints.BOTH) 
            b.weightx = 1.0;
        
        if (f == GridBagConstraints.VERTICAL || f == GridBagConstraints.BOTH)
            b.weighty = 1.0;
        
        return this;
    }
    
    /**
     * Anchor the component within its cell. This takes effect when the cell is
     * larger than the component within. This system uses combinations of the
     * n (north), e (east), s (south), and w (west) values. Centering means it
     * has all n,e,s,w, positions set. The rest are obvious.
     */
    public GridCell anchor(int n, int e, int s, int w) {
        
        // make sure n, e, s, and w are all 0 or 1
        n = n & 1;
        e = e & 1;
        s = s & 1;
        w = w & 1;
        
        // now, figure out where to anchor this grid cell
        if(n+e+s+w == 4)
            b.anchor = GridBagConstraints.CENTER;
        else if(n+e == 2)
            b.anchor = GridBagConstraints.NORTHEAST;
        else if(n+w == 2)
            b.anchor = GridBagConstraints.NORTHWEST;
        else if(s+e == 2)
            b.anchor = GridBagConstraints.SOUTHEAST;
        else if(s+w == 2)
            b.anchor = GridBagConstraints.SOUTHWEST;
        else if(n == 1)
            b.anchor = GridBagConstraints.NORTH;
        else if(e == 1)
            b.anchor = GridBagConstraints.EAST;
        else if(s == 1)
            b.anchor = GridBagConstraints.SOUTH;
        else if(w == 1)
            b.anchor = GridBagConstraints.WEST;
        
        return this;
    }
}
