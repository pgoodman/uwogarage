package org.uwogarage.util.documents;

/**
 * A document format that only allows numbers, letters, and dashes to be put in.
 * 
 * @author Peter Goodman
 * @version $Id$
 */
public class AlphaDashDocument extends PredicateDocument {
    
    private static final long serialVersionUID = 1L;

    public AlphaDashDocument() { }
    /**
     * An overloaded constructor that sets the maximum length of the document
     * @param ml the maximum allowable length of the document
     */
    public AlphaDashDocument(int ml) {
        max_length = ml;
    }
    
    /**
     * Checks whether a given character is valid
     * @param ch the character to be checked
     * @return whether or not the character was valid
     */
    protected boolean charIsValid(char ch) {
        return (ch >= 'A' && ch <= 'z') || ch == '-';
    }
}
