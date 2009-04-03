package org.uwogarage.util;

import java.util.Arrays;
import java.util.Collection;

/**
 * General string utilities.
 * 
 * @author Peter Goodman
 * @version $Id$
 */
abstract public class StringUtil {
    
    /**
     * Pad an input string on the left side  up to total_length with the pad char.
     * 
     * @param input the string to pad
     * @param pad the character to pad it with
     * @param total_length the total length of the desired padded string
     * @return the padded input string
     */
    static public String padLeft(String input, char pad, int total_length) {
        if(input.length() >= total_length)
            return input;
        
        StringBuffer ret = new StringBuffer(total_length);
        
        for(int i = 0; i < total_length - input.length(); ++i)
            ret.append(pad);
        
        ret.append(input);
        
        return ret.toString();
    }
    
    /**
     * Well-behaved string join using variable argument list.
     * 
     * @param join the character to join the strings with
     * @param first the first string to be joined
     * @param rest the rest of the strings to be joined
     * @return the joined string
     */
    static public String join(char join, String first, String ... rest) {
        
        int i = first.length(), 
            length = 0;
        
        // get the total length of the string
        for(; i < rest.length; length += rest[i++].length())
            ;
        
        // note: length + (rest.length - 1) + 1, removed redundancy :D
        StringBuffer ret = new StringBuffer(length + rest.length);
        
        ret.append(first);
        for(String r : rest) {
            ret.append(join);
            ret.append(r);
        }
        
        return ret.toString();
    }
    
    /**
     * String joins using collections.
     * 
     * @param join the character to join the strings with
     * @param strs the strings to be joined
     * @return the joined string
     */
    static public String join(char join, String[] strs) {
        if(null == strs || strs.length == 0)
            return "";
        
        return join(join, Arrays.asList(strs));
    }
    static public String join(char join, Collection<String> strs) {
        if(null == strs || strs.size() == 0)
            return "";
        
        StringBuffer ret = new StringBuffer();
        boolean do_join = false;
        
        for(String r : strs) {
            if(do_join) ret.append(join);
            ret.append(r);
            do_join = true;
        }
        
        return ret.toString();
    }
}
