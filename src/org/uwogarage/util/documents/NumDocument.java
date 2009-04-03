package org.uwogarage.util.documents;

/**
 * A document format that only allows numbers to be put in. This is used for
 * text fields that only accept numeric input.
 * 
 * @author Peter Goodman
 * @version $Id$
 */
public class NumDocument extends PredicateDocument {
    
    private static final long serialVersionUID = 1L;

    public NumDocument() { }
    /**
     * An overloaded constructor that sets the maximum length of the document
     * @param ml the maximum allowable length of the document
     */
    public NumDocument(int ml) {
        max_length = ml;
    }
    
    /**
     * Checks whether a given character is valid
     * @param ch the character to be checked
     * @return whether or not the character was valid
     */
    protected boolean charIsValid(char ch) {
        return ch >= '0' && ch <= '9';
    }
}