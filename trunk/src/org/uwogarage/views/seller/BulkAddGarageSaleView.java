package org.uwogarage.views.seller;

import javax.swing.JPanel;

import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.ModelSet;
import org.uwogarage.util.functional.D;
import org.uwogarage.views.View;

/**
 * @version $Id$
 */
public class BulkAddGarageSaleView extends View {
    static public JPanel view(D<ModelSet<GarageSaleModel>> responder) {
        return grid(
            
        );
    }
}