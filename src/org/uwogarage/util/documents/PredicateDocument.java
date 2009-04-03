package org.uwogarage.util.documents;

import java.awt.Toolkit;
import javax.swing.text.*;

/**
 * A document format that only allows any input, and that input is modifiable
 * 
 * @author Peter Goodman
 * @version $Id$
 */
abstract public class PredicateDocument extends PlainDocument {
    
    private static final long serialVersionUID = 1L;
    protected int max_length = Integer.MAX_VALUE;
    
    public PredicateDocument() { }
    /**
     * An overloaded constructor that sets the maximum length of the document
     * @param ml the maximum allowable length of the document
     */
    public PredicateDocument(int ml) {
        max_length = ml;
    }
    
    /**
     * Checks whether a given character is valid
     * @param ch the character to be checked
     * @return whether or not the character was valid
     */
    abstract protected boolean charIsValid(char ch);
    
    /**
     * Inserts a given string into a document.
     * @param offset the offset of the string to be inserted
     * @param str the string to be inserted
     * @param a the attributes for the inserted content
     */
    public void insertString(int offset, String str, AttributeSet a) {        
        if(offset < max_length && charIsValid(str.charAt(0))) {
            try {
                super.insertString(offset, str, a);
            } catch (BadLocationException e) { }
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
