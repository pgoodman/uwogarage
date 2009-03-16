package org.uwogarage.util.documents;

/**
 * A document format that only allows real numbers to be put in. This is used for
 * text fields that only accept numeric input.
 * 
 * @author Peter Goodman
 * @version $Id$
 */
public class RealNumDocument extends PredicateDocument {
    
    private static final long serialVersionUID = 1L;

    public RealNumDocument() { }
    public RealNumDocument(int ml) {
        max_length = ml;
    }
    
    protected boolean charIsValid(char ch) {
        return (ch >= '0' && ch <= '9') || ch == '.' || ch == '-';
    }
}