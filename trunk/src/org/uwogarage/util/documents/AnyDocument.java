package org.uwogarage.util.documents;

/**
 * A Document that allows any characters.
 * 
 * @author Peter Goodman
 * @version $Id$
 */
public class AnyDocument extends PredicateDocument {
    private static final long serialVersionUID = 1L;

    public AnyDocument() { }
    /**
     * An overloaded constructor that sets the maximum length of the document
     * @param ml the maximum allowable length of the document
     */
    public AnyDocument(int ml) {
        max_length = ml;
    }
    
    /**
     * Checks whether a given character is valid
     * @param ch the character to be checked
     * @return whether or not the character was valid
     */
    protected boolean charIsValid(char ch) {
        return true;
    }
}
