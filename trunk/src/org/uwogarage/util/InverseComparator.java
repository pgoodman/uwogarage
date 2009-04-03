package org.uwogarage.util;

import java.util.Comparator;

/**
 * Invert comparator.
 * @author Peter Goodman
 */
final public class InverseComparator<T> implements Comparator<T> {
    
    protected Comparator<T> c;
    
    /**
     * The constructor sets the contained comparator
     * @param cc the comparator to be stored
     */
    public InverseComparator(Comparator<T> cc) {
        c = cc;
    }
    
    /**
     * Invert the comparison by multiplying by -1, i.e. changing the order.
     * @return the inverted comparator
     */
    final public int compare(T o1, T o2) {
        return -1 * c.compare(o1, o2);
    }
    
}