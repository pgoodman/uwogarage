package org.uwogarage.util.functional;

/**
 * Predicate:
 * A predicate is a boolean test that checks whether or not its single argument
 * meets the defined conditions. Predicates can be combined using 'and', 'or', 
 * 'nor', 'nand'; Predicates can also be negated using 'not'.
 * 
 * @author Peter Goodman
 * @version $Id$
 */
abstract public class P<T> {
    
    abstract public boolean call(T arg);
    
    /**
     * Allow convenient chaining of P combinators. 
     */
    public P<T> and(final P<T> a) {
        return P.and(this, a);
    }
    
    /**
     * Allow convenient chaining of P combinators. 
     */
    public P<T> or(final P<T> a) {
        return P.or(this, a);
    }
    
    /**
     * NOR combinator.
     */
    public P<T> nor(final P<T> a) {
        return P.not(P.or(this, a));
    }
    
    /**
     * NAND combinator.
     */
    public P<T> nand(final P<T> a) {
        return P.not(P.and(this, a));
    }
    
    /**
     * P AND combinator.
     */
    static public <I> P<I> and(final P<I> a, final P<I> b) {
        return new P<I>() {
            public boolean call(I param) {
                return a.call(param) && b.call(param);
            }
        };
    }
    
    /**
     * P OR combinator.
     */
    static public <I> P<I> or(final P<I> a, final P<I> b) {
        return new P<I>() {
            public boolean call(I param) {
                return a.call(param) || b.call(param);
            }
        };
    }
    
    /**
     * Unary NOT operator.
     */
    static public <I> P<I> not(final P<I> a) {
        return new P<I>() {
            public boolean call(I param) {
                return !a.call(param);
            }
        };
    }
}