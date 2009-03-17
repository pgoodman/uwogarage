package org.uwogarage.views.seller;

import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.uwogarage.models.CategoryModel;
import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.ModelSet;
import org.uwogarage.util.GeoPosition;
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
        GeoPosition pos = sale.getGeoPosition();
        Calendar time = sale.getTime();
        
        // TODO get this right
        int hours = time.get(Calendar.HOUR);
        if(0 == hours) hours = 12; else --hours;
        
        // set the default values
        street.setText(loc.getStreet());
        city.setText(loc.getCity());
        year.setText(String.valueOf(time.get(Calendar.YEAR)));
        month.setText(StringUtil.padLeft(String.valueOf(time.get(Calendar.YEAR)), '0', 2));
        day.setText(StringUtil.padLeft(String.valueOf(time.get(Calendar.DAY_OF_MONTH)), '0', 2));
        hour.setText(StringUtil.padLeft(String.valueOf(hours), '0', 2));
        minute.setText(StringUtil.padLeft(String.valueOf(time.get(Calendar.MINUTE)), '0', 2));
        time_am.setSelected(time.get(Calendar.AM_PM) == Calendar.AM);
        time_pm.setSelected(time.get(Calendar.AM_PM) == Calendar.PM);
        note.setText(sale.getNote());
        province.setSelectedItem(loc.getProvince());
        
        // TODO make sure that these end up being formatted correctly
        lat.setText(String.valueOf(pos.getLatitude()));
        lng.setText(String.valueOf(pos.getLongitude()));
        
        // lay out the form
        return grid(
            grid.row(
                grid.cell(label("Edit Garage Sale")).margin(10, 10, 10, 10)
            ),
            
            // build the sections of the form
            addressSection(),
            dateAndTimeSection(),
            categoriesSection(all_categories, sale.getCategories()),
            extraInfoSection(),
            
            // submit button
            grid.row(
                grid.cell(button("Update", new D<JButton>() {
                    public void call(JButton b) {
                        processInput(sale, responder);
                    }
                }))
            )
        );
    }
}
