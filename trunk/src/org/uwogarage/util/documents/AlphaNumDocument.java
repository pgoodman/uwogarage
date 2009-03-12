package org.uwogarage.util.documents;

/**
 * A document format that only allows numbers to be put in. This is used for
 * text fields that only accept numeric input.
 * 
 * @author Peter Goodman
 * @version $Id$
 */
public class AlphaNumDocument extends PredicateDocument {
    
    private static final long serialVersionUID = 1L;

    public AlphaNumDocument() { }
    public AlphaNumDocument(int ml) {
        max_length = ml;
    }
    
    protected boolean charIsValid(char ch) {
        return (ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'z');
    }
}
