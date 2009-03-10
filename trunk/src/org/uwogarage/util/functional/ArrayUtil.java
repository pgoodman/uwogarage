package org.uwogarage.util.functional;

import java.lang.reflect.Array;
import java.util.Stack;

/**
 * Some simple functions that are helpful when working with arrays.
 * 
 * @author Peter Goodman
 */
public class ArrayUtil {
    
    /**
     * Return a generic array of type T[] from a Stack<T>.
     */
    @SuppressWarnings("unchecked")
    static protected <T> T[] stack_to_array(Stack<T> elm_stack) {
        
        if(!elm_stack.isEmpty()) {
            return elm_stack.toArray(
                (T[]) Array.newInstance(
                    elm_stack.firstElement().getClass(),
                    elm_stack.size()
                )
            );
        }
        
        // TODO: this will still throw a null-pointer exception
        return (T[]) pack();
    }
    
    /**
     * Pack items of a given type into an array.
     * @param <T>
     * @param items
     * @return
     */
    static public <T> T[] pack(T ... items) {
        return items;
    }
    
    /**
     * Map a function onto an array of items and return the mapped array.
     * 
     * @param <I> input type
     * @param <O> output type
     * @param elms
     * @param fn
     * @param new_elms
     * @return
     */
    static public <I,O> O[] map(I[] old_elms, F<I,O> fn) {
        Stack<O> elm_stack = new Stack<O>();
        if(null != old_elms) {
            for(I elm : old_elms)
                elm_stack.push(fn.call(elm));
        }
        return stack_to_array(elm_stack);
    }
    
    /**
     * Filter elements from an array using a predicate and return the filtered
     * array.
     */
    static public <T> T[] filter(T[] old_elms, P<T> fn) {
        Stack<T> elm_stack = new Stack<T>();
        if(null != old_elms) {
            for(T elm : old_elms) {
                if(fn.call(elm))
                    elm_stack.push(elm);
            }
        }
        return stack_to_array(elm_stack);
    }
    
    /**
     * Flatten a two-dimensional array into a one-dimensional array.
     * 
     * @param <T> type of elements in the array
     * @param elms
     * @param new_elms
     * @return
     */
    static public <T> T[] flatten(T[][] old_elms) {
        Stack<T> elm_stack = new Stack<T>();
        if(null != old_elms) {
            for(T[] sub_elms : old_elms) {
                for(T elm : sub_elms)
                    elm_stack.push(elm);
            }
        }
        return stack_to_array(elm_stack);
    }
    
    /**
     * Reduce an array.
     * @param <I>
     * @param <O>
     * @param old_elms
     * @param initial_val
     * @param fn
     * @return
     */
    static public <I,O> O reduce(I[] old_elms, O initial_val, F2<I,O,O> fn) {
        O reduced_val = initial_val;
        if(null != old_elms) {
            for(I elm : old_elms)
                reduced_val = fn.call(elm, reduced_val);
        }
        return reduced_val;
    }
}
