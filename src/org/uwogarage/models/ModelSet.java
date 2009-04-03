package org.uwogarage.models;

import java.io.Serializable;
import java.util.HashSet;
import org.uwogarage.util.functional.P;

/**
 * A set of models
 * 
 * @author Peter Goodman
 * @version $Id$
 * 
 * @param <T>
 */
public class ModelSet<T> extends HashSet<T> implements Serializable {
    
    private static final long serialVersionUID = -6666978152729940262L;

    /**
     * Filter out specific models in the model collection.
     * @param filter_fn Filter predicate
     * @return
     */
    public ModelSet<T> filter(P<T> filter_fn) {
        ModelSet<T> filtered_models = new ModelSet<T>();
        for(T model : this) {
            if(filter_fn.call(model)) {
                filtered_models.add(model);
            }
        }
        
        return filtered_models;
    }
    
    /**
     * Filter out a specific model in a collection.
     */
    public T filterOne(P<T> filter_fn) {
        for(T model : this) {
            if(filter_fn.call(model)) {
                return model;
            }
        }
        return null;
    }
    
    /**
     * Type-safe clone.
     */
    public ModelSet<T> clone() {
        ModelSet<T> cloned_set = new ModelSet<T>();
        
        for(T item : this)
            cloned_set.add(item);
        
        return cloned_set;
    }
}
