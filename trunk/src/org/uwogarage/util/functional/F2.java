package org.uwogarage.util.functional;

/**
 * Function:
 * A function of two arguments and a single return value.
 * 
 * @author Peter Goodman
 * @version $Id$
 *
 * @param <I1> input type 1
 * @param <I2> input type 2
 * @param <O> output type
 */
abstract public class F2<I1,I2,O> {
    
    /**
     * Call the function.
     */
    abstract public O call(I1 a, I2 b);
    
    /**
     * Chainable currying.
     */
    public F<I2,O> applyFirst(I1 a) {
        return F2.curry1(a, this);
    }
    
    /**
     * Chainable currying.
     */
    public F<I1,O> applySecond(I2 b) {
        return F2.curry2(b, this);
    }
    
    /**
     * Curry the first argument of a two-argument function. This method
     * returns a single argument function.
     */
    static public <A,B,C> F<B,C> curry1(final A a, final F2<A,B,C> fn) {
        return new F<B,C>() {
            public C call(B b) {
                return fn.call(a, b);
            }
        };
    }
    
   /**
    * Curry the second argument of a two-argument function. This method
    * returns a single argument function.
    */
   static public <A,B,C> F<A,C> curry2(final B b, final F2<A,B,C> fn) {
       return new F<A,C>() {
           public C call(A a) {
               return fn.call(a, b);
           }
       };
   }
   
   /**
    * Restructure a function of two arguments into a function of one argument.
    * @param <A>
    * @param <B>
    * @param <C>
    * @param fn
    * @return
    */
   static public <A,B,C> F<Tuple<A,B>,C> restructure(final F2<A,B,C> fn) {
       return new F<Tuple<A,B>,C>() {
           public C call(Tuple<A,B> a) {
               return fn.call(a.first(), a.second());
           }
       };
   }
}

