package org.uwogarage.util.functional;

/**
 * Function:
 * A function is meant to represent something that takes one input and returns
 * one output with no side-effects.
 * 
 * @author Peter Goodman
 * @version $Id$
 *
 * @param <I> input type
 * @param <O> output type
 */
abstract public class F<I,O> {
    
    /**
     * Call the function.
     */
    abstract public O call(I val);
    
    /**
     * Chained function composition combinator.
     */
    public <C> F<I,C> followedBy(F<O,C> outer) {
        return F.compose(outer, this);
    }
    
    /**
     * Function composition combinator. The order of arguments is outer(inner( .. )).
     */
    static public <A,B,C> F<A,C> compose(final F<B,C> outer, final F<A,B> inner) {
        return new F<A,C>() {
            public C call(A param) {
                return outer.call(inner.call(param));
            }
        };
    }
}