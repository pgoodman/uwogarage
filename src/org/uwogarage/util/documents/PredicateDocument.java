package org.uwogarage.util.documents;

import java.awt.Toolkit;
import javax.swing.text.*;

/**
 * A document format that only allows numbers to be put in. This is used for
 * text fields that only accept numeric input.
 * 
 * @author Peter Goodman
 * @version $Id$
 */
abstract public class PredicateDocument extends PlainDocument {
    
    private static final long serialVersionUID = 1L;
    protected int max_length = Integer.MAX_VALUE;
    
    public PredicateDocument() { }
    public PredicateDocument(int ml) {
        max_length = ml;
    }
    
    abstract protected boolean charIsValid(char ch);
    
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
