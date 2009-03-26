package org.uwogarage.views;

import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.uwogarage.models.CategoryModel;
import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.util.Location;
import org.uwogarage.util.StringUtil;
import org.uwogarage.util.gui.GridCell;

/**
 * View a single garage sale.
 * 
 * @version $Id$
 */
public class GarageSaleView extends TabView {
    static public JPanel view(GarageSaleModel sale) {
        
        JTextArea note = new JTextArea(sale.getNote());
        note.setEnabled(false);
        
        // make a list of categories
        GridCell[][] categories = new GridCell[sale.categories.size()][];
        int i = 0;
        for(CategoryModel category : sale.categories) {
            categories[i++] = grid.row(
                grid.cell(label(category.getName()))
                    .anchor(1, 0, 0, 1)
                    .margin(0, 10, 0, 10)
            );
        }
        
        Location address = sale.getLocation();
        Calendar cal = sale.getTime();
        
        String[] days = {
            "Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"
        };
        String[] months = {
            "January","February","March","April","May","June","July","August",
            "September","October","November","December"
        };
        String[] am_or_pm = {"am", "pm"};
        
        // create the GUI for showing all garage sale info
        return grid(
                
            // address
            grid.cell(fieldset("Address", grid(
                grid.row(
                    grid.cell(label("Street:")).anchor(1, 0, 0, 1),
                    grid.cell(label(address.getStreet()))
                        .anchor(1, 0, 0, 1)
                        .margin(0, 0, 0, 10)
                ),
                grid.row(
                    grid.cell(label("City:")).anchor(1, 0, 0, 1),
                    grid.cell(label(address.getCity()))
                        .anchor(1, 0, 0, 1)
                        .margin(0, 0, 0, 10)
                ),
                grid.row(
                    grid.cell(label("Province:")).anchor(1, 0, 0, 1),
                    grid.cell(label(address.getProvince()))
                        .anchor(1, 0, 0, 1)
                        .margin(0, 0, 0, 10)
                ),
                
                // map showing where the sale is
                grid.row(
                    grid.cell(2, label("map here...")).margin(10, 10, 10, 10)
                )
            )), 3).pos(1, 0).fill(1, 1),
            
            // date and time
            grid.cell(fieldset("Date / Time", grid(
                grid.row(
                    grid.cell(label("Date:")).anchor(1, 0, 0, 1),
                    grid.cell(label(
                        days[cal.get(Calendar.DAY_OF_WEEK)] +", "+
                        months[cal.get(Calendar.MONTH)] +" "+
                        cal.get(Calendar.DAY_OF_MONTH)
                    )).anchor(1, 0, 0, 1).margin(0, 0, 0, 10)
                ),
                grid.row(
                    grid.cell(label("Time:")),
                    grid.cell(label(
                        (cal.get(Calendar.HOUR)+1) +":"+
                        StringUtil.padLeft(
                            String.valueOf(cal.get(Calendar.MINUTE)), 
                            '0', 
                            2
                        ) +" "+
                        am_or_pm[cal.get(Calendar.AM_PM)] +" "+
                        Location.PROVINCE_TIME_ZONE_CODES.get(
                            address.getProvince()
                        )
                    )).anchor(1, 0, 0, 1).margin(0, 0, 0, 10)
                )
            ))).pos(0, 0).fill(1, 1),
            
            // list of categories
            grid.cell(fieldset("Categories",
                categories.length > 0
                ? grid(categories) 
                : grid(grid.cell(label("Not Categorized.")))
            )).pos(0, 1).fill(1, 1),
            
            // note
            grid.cell(fieldset("Notes", note))
                .pos(0, 2)
                .fill(1, 1),
            // rating
            grid.cell(fieldset("Rating", label(String.valueOf(sale.getRating()))))
                .pos(0,3)
                .fill(1,1)
        );
    }
}
