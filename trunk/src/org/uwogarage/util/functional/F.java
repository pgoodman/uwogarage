package org.uwogarage.util.functional;

/**
 * Function:
 * A function is meant to represent something that takes one input and returns
 * one output with no side-effects.
 * 
 * @author Peter Goodman
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
    
    /**
     * Transform a function that takes a pair into a function of two arguments.
     * De-structuring the Tuple argument is convenient because it then means we
     * can curry either of the elements of the tuple.
     */
    static public <A,B,C> F2<A,B,C> destructure(final F<Tuple<A,B>,C> fn) {
        return new F2<A,B,C>() {
            public C call(A a, B b) {
                return fn.call(new Tuple<A,B>(a, b));
            }
        };
    }
}