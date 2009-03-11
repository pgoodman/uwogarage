package org.uwogarage.util.functional;

/**
 * Tuple:
 * A pair of objects.
 * 
 * @author Peter Goodman
 * @version $Id$
 */
public class Tuple<A,B> {
    
    protected A a;
    protected B b;
    
    protected Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }
    
    public A first() {
        return this.a;
    }
    
    public B second() {
        return this.b;
    }
    
    public static <X,Y> Tuple<X,Y> tuple(X a, Y b) {
        return new Tuple<X,Y>(a, b);
    }
}