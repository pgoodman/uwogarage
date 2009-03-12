package org.uwogarage.util.documents;

import java.awt.Toolkit;
import javax.swing.text.*;

/**
 * A document format that only allows numbers to be put in. This is used for
 * text fields that only accept numeric input.
 * 
 * @author Peter Goodman
 */
public class AlphaNumDocument extends PlainDocument {
    
    private static final long serialVersionUID = 1L;
    
    private int max_length = Integer.MAX_VALUE;
    
    public AlphaNumDocument() { }
    public AlphaNumDocument(int ml) {
        max_length = ml;
    }
    
    public void insertString(int offset, String str, AttributeSet a) {
        char ch = str.charAt(0);
        
        if(offset < max_length && ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'z'))) {
            try {
                super.insertString(offset, str, a);
            } catch (BadLocationException e) { }
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }
}