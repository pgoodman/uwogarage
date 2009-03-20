package org.uwogarage.views.seller;

import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.uwogarage.models.CategoryModel;
import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.ModelSet;
import org.uwogarage.util.Location;
import org.uwogarage.util.StringUtil;
import org.uwogarage.util.functional.D;


/**
 * View to edit a garage sale.
 * 
 * @author Peter Goodman
 * @version $Id$
 */
public class EditGarageSaleView extends AddGarageSaleView {
    
    /**
     * Show the form to edit a garage sale.
     * 
     * @param responder
     * @return
     */
    public JPanel view(final GarageSaleModel sale, 
                     ModelSet<CategoryModel> all_categories, 
                    final D<GarageSaleModel> responder) {
        
        Location loc = sale.getLocation();
        String[] pos = sale.getGuiGeoPosition();
        Calendar time = sale.getTime();
        
        int hours = time.get(Calendar.HOUR);
        if(0 == hours) hours = 12;
        
        // set the default values
        street.setText(loc.getStreet());
        city.setText(loc.getCity());
        year.setText(String.valueOf(time.get(Calendar.YEAR)));
        month.setText(StringUtil.padLeft(String.valueOf(time.get(Calendar.MONTH)+1), '0', 2));
        day.setText(StringUtil.padLeft(String.valueOf(time.get(Calendar.DAY_OF_MONTH)), '0', 2));
        hour.setText(StringUtil.padLeft(String.valueOf(hours), '0', 2));
        minute.setText(StringUtil.padLeft(String.valueOf(time.get(Calendar.MINUTE)), '0', 2));
        time_am.setSelected(time.get(Calendar.AM_PM) == Calendar.AM);
        time_pm.setSelected(time.get(Calendar.AM_PM) == Calendar.PM);
        note.setText(sale.getNote());
        province.setSelectedItem(loc.getProvince());
        lat.setText(pos[0]);
        lng.setText(pos[1]);
        
        // lay out the form
        return grid(
            grid.row(
                grid.cell(2, label("Edit Garage Sale")).margin(10, 10, 10, 10)
            ),
            
            // build the sections of the form
            grid.row(
                addressSection(),
                dateAndTimeSection()
            ),
            grid.row(
                categoriesSection(all_categories, sale.getCategories()),
                extraInfoSection()
            ),
            
            // submit button
            grid.row(
                grid.cell(2, button("Update", new D<JButton>() {
                    public void call(JButton b) {
                        processInput(sale, responder);
                    }
                }))
            )
        );
    }
}
