package org.uwogarage.util.functional;

/**
 * Delegate:
 * A delegate is a function that causes side-effects.
 * 
 * @author Peter Goodman
 * @version $Id$
 *
 * @param <A>
 */
public abstract class D<A> {
    
    abstract public void call(A arg1);
    
    /**
     * A delegate that will always do nothing.
     */
    final static public class DO_NOTHING<X> extends D<X> {
        final public void call(X x) { }
    }
    
    /**
     * Delegate combinator, perform delegates in sequence with the same input.
     * @param next
     * @return
     */
    public D<A> thenDo(final D<A> next) {
        final D<A> self = this;
        return new D<A>() {
            public void call(A param) {
                self.call(param);
                next.call(param);
            }
        };
    }
}