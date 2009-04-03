package org.uwogarage.util.documents;

/**
 * A document format that only allows real numbers to be put in.
 * 
 * @author Peter Goodman
 * @version $Id$
 */
public class RealNumDocument extends PredicateDocument {
    
    private static final long serialVersionUID = 1L;

    public RealNumDocument() { }
    /**
     * An overloaded constructor that sets the maximum length of the document
     * @param ml the maximum allowable length of the document
     */
    public RealNumDocument(int ml) {
        max_length = ml;
    }
    
    /**
     * Checks whether a given character is valid
     * @param ch the character to be checked
     * @return whether or not the character was valid
     */
    protected boolean charIsValid(char ch) {
        return (ch >= '0' && ch <= '9') || ch == '.' || ch == '-';
    }
}