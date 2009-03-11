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
    public void insertString(int offs, String str, AttributeSet a) {
        char ch = str.charAt(0);
        
        if((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'z')) {
            try {
                super.insertString(offs, str, a);
            } catch (BadLocationException e) { }
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
