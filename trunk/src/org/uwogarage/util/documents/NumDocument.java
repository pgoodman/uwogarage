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
    public NumDocument(int ml) {
        max_length = ml;
    }
    
    protected boolean charIsValid(char ch) {
        return ch >= '0' && ch <= '9';
    }
}