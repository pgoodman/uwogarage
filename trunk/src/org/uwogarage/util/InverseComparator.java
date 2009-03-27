package org.uwogarage.util;

import java.util.Comparator;

/**
 * Invert comparator.
 */
final public class InverseComparator<T> implements Comparator<T> {
    
    protected Comparator<T> c;
    
    public InverseComparator(Comparator<T> cc) {
        c = cc;
    }
    
    /**
     * Invert the comparison by multiplying by -1, i.e. changing the order.
     */
    final public int compare(T o1, T o2) {
        return -1 * c.compare(o1, o2);
    }
    
}