package org.uwogarage.util.functional;

/**
 * AND and OR boolean operators. These act as expect with boolean values;
 * however, they work like Python's boolean operators with other types of
 * objects.
 * 
 * @author Peter Goodman
 */
public abstract class Boolean {
    
    /**
     * If all parameters are non-null then return the last one, otherwise return
     * null.
     * 
     * @param <T>
     * @param first
     * @param rest
     * @return
     */
    static public <T> T and(T first, T ... rest) {
        
        if(null != first && rest.length > 0) {
            int i = 0;
            for(T p : rest) {
                if(null == p)
                    return null;
            }
            
            return rest[i];
        }
        
        return first;
    }
    static public boolean and(boolean first, boolean ... rest) {
        if(first && rest.length > 0) {
            for(boolean b : rest) {
                if(!b)
                    return false;
            }
            return true;
        }
        return false;
    }
    
    /**
     * Return the first non-null parameter, if all are null then null is 
     * returned.
     * @param <T>
     * @param first
     * @param rest
     * @return
     */
    static public <T> T or(T first, T ... rest) {
        if(null != first)
            return first;
        for(T p : rest) {
            if(null != p)
                return p;
        }
        
        return null;
    }
    static public boolean or(boolean first, boolean ... rest) {
        if(first)
            return true;
        for(boolean b : rest) {
            if(b)
                return true;
        }
        return false;
    }
}
