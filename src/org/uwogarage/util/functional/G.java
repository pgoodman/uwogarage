package org.uwogarage.util.functional;

/**
 * Generator:
 * This is sort of the twin of a delegate, it does work to return a value rather
 * than take in one and cause side-effects. Useful as an anonymous factory.
 * 
 * @author Peter Goodman
 *
 */
public interface G<A> {
    public A call();
}
