package org.uwogarage.util.documents;

/**
 * A Document that allows any characters.
 * 
 * @author Peter Goodman
 * @verison $Id$
 */
public class AnyDocument extends PredicateDocument {
    private static final long serialVersionUID = 1L;

    public AnyDocument() { }
    public AnyDocument(int ml) {
        max_length = ml;
    }
    
    protected boolean charIsValid(char ch) {
        return true;
    }
}
