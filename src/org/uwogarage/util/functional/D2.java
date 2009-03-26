package org.uwogarage.util.functional;

/**
 * A Delegate on two arguments.
 * 
 * @author petergoodman
 *
 * @param <A>
 * @param <B>
 */
public interface D2<A,B> {
    public void call(A a, B b);
}
