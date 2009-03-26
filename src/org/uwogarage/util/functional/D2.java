package org.uwogarage.util.functional;

/**
 * A Delegate on two arguments.
 * 
 * @author petergoodman
 *
 * @param <A>
 * @param <B>
 */
public abstract class D2<A,B> {
   
    /**
     * A delegate that will always do nothing.
     */
    final static public class DO_NOTHING<X,Y> extends D2<X,Y> {
        final public void call(X x, Y y) { }
    }
    
    /**
     * Do something!
     * 
     * @param a
     * @param b
     */
    abstract public void call(A a, B b);
}
