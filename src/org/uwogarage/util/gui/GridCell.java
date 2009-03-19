package org.uwogarage.util.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * GridCell:
 * Maintain a component and its associated grid bag constraints. This class
 * also makes it easier to modify the simple parts of the GridBagConstraints.
 * Finally, this class provides a more meaningful name with respect to the grid
 * SimpleGUI functions.
 * 
 * @author Peter Goodman
 * @version $Id$
 */
public class GridCell {
    
    final public Component first;
    final public GridBagConstraints second;
    
    public GridCell(Component aa, GridBagConstraints bb) {
        first = aa;
        second = bb;
    }
    
    /**
     * Internal padding of a grid cell.
     */
    public GridCell padding(int x, int y) {
        second.ipadx = x;
        second.ipady = y;
        return this;
    }
    
    /**
     * External padding of a grid cell.
     */
    public GridCell margin(int top, int right, int bottom, int left) {
        second.insets = new Insets(top, left, bottom, right);
        return this;
    }
    
    /**
     * Top-left position in grid.
     */
    public GridCell pos(int x, int y) {
        second.gridx = x;
        second.gridy = y;
        return this;
    }
    
    /**
     * Set the row (x) and column (y) weights.
     */
    public GridCell weight(double x, double y) {
        second.weightx = x;
        second.weighty = y;
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
        
        second.fill = f;
        
        // set the weights
        if (f == GridBagConstraints.HORIZONTAL || f == GridBagConstraints.BOTH) 
            second.weightx = 1.0;
        
        if (f == GridBagConstraints.VERTICAL || f == GridBagConstraints.BOTH)
            second.weighty = 1.0;
        
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
            second.anchor = GridBagConstraints.CENTER;
        else if(n+e == 2)
            second.anchor = GridBagConstraints.NORTHEAST;
        else if(n+w == 2)
            second.anchor = GridBagConstraints.NORTHWEST;
        else if(s+e == 2)
            second.anchor = GridBagConstraints.SOUTHEAST;
        else if(s+w == 2)
            second.anchor = GridBagConstraints.SOUTHWEST;
        else if(n == 1)
            second.anchor = GridBagConstraints.NORTH;
        else if(e == 1)
            second.anchor = GridBagConstraints.EAST;
        else if(s == 1)
            second.anchor = GridBagConstraints.SOUTH;
        else if(w == 1)
            second.anchor = GridBagConstraints.WEST;
        
        return this;
    }
}
