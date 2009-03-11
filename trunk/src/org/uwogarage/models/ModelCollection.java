package org.uwogarage.models;

import java.util.LinkedList;

import org.uwogarage.util.functional.P;

public class ModelCollection<T> extends LinkedList<T> {
    
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     */
    public ModelCollection() {
        
    }
    
    /**
     * Filter out specific models in the model collection.
     * @param filter_fn Filter predicate
     * @return
     */
    public LinkedList<T> filter(P<T> filter_fn) {
        ModelCollection<T> filtered_models = new ModelCollection<T>();
        for(T model : this) {
            if(filter_fn.call(model)) {
                filtered_models.add(model);
            }
        }
        
        return filtered_models;
    }
}
