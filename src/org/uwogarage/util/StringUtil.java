package org.uwogarage.util;

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
     * @param input
     * @param pad
     * @param total_length
     * @return
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
     * Join an array of strings by a single char.
     * 
     * @param join
     * @param strs
     * @return
     */
    static public String join(char join, String ... strs) {
        
        if(strs.length == 0)
            return "";
        
        int i = 0, 
            length = 0;
        
        // get the total length of the string
        for(; i < strs.length; length += strs[i++].length());
        
        StringBuffer ret = new StringBuffer(length + (strs.length - 1));
        
        ret.append(strs[0]);
        for(i = 1; i < strs.length; ret.append(join), ret.append(strs[i++]));
        
        return ret.toString();
    }
    static public String join(char join, Collection<String> strs) {
        if(strs.size() == 0)
            return "";
        
        String[] str_array = new String[strs.size()];
        int i = 0;
        for(String str : strs)
            str_array[i++] = str;
        
        return join(join, str_array);
    }
}
