package org.uwogarage.views;

import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.uwogarage.models.CategoryModel;
import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.Location;
import org.uwogarage.util.StringUtil;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.gui.GridCell;

/**
 * View a single garage sale.
 * 
 * @version $Id$
 */
public class GarageSaleView extends TabView {
    static public JPanel view(GarageSaleModel sale, UserModel logged_user) {
        
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
        
        final GridCell rating_box;
        final GridCell print_box;
        
        if (sale.getRatingFrom(logged_user) == 0.0) {
            rating_box = grid.cell(fieldset("Rating",
                    grid(
                        grid.cell( 
                            label("Overall Rating")
                        ).pos(0, 1),
                        grid.cell( 
                            label(String.valueOf(sale.getRating()))
                        ).pos(0, 2),
                        grid.cell(
                            button("Rate Sale", new D<JButton>() {
                                public void call(JButton btn) {
                                    // TODO show the rating pop up
                                }
                            }
                        )).pos(0, 3)
                    )
                )).pos(0,3).fill(1,1);
        } else {
            rating_box = grid.cell(fieldset("Rating",
                    grid(
                        grid.cell( 
                            label("Overall Rating")
                        ).pos(0, 1),
                        grid.cell( 
                            label(String.valueOf(sale.getRating()))
                        ).pos(0, 2),
                        grid.cell( 
                            label("Your Rating")
                        ).pos(0, 3),
                        grid.cell( 
                            label(String.valueOf(sale.getRatingFrom(logged_user)))
                        ).pos(0, 4),
                        grid.cell(
                            button("Edit Rating", new D<JButton>() {
                                public void call(JButton btn) {
                                    // TODO show the rating pop up
                                }
                            }
                        )).pos(0, 5)
                    )
                )).pos(0,3).fill(1,1);
        }
        
        print_box = grid.cell(fieldset("Print",
                grid(
                   
                   grid.cell(
                        button("Print this Sale", new D<JButton>() {
                            public void call(JButton btn) {
                                // TODO show the rating pop up
                            }
                        }
                    )).pos(0, 3)
                )
            )).pos(0,4).fill(1,1);
        
        Location address = sale.getLocation();
        Calendar cal = sale.getTime();
        
        String[] days = {
            "","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"
        };
        String[] months = {
            "January","February","March","April","May","June","July","August",
            "September","October","November","December"
        };
        String[] am_or_pm = {"am", "pm"};
        
        int cal_hour = cal.get(Calendar.HOUR);
        if(cal_hour == 0) cal_hour = 12;
        
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
                
                // TODO map showing where the sale is
                grid.row(
                    grid.cell(2, label("map here...")).margin(10, 10, 10, 10)
                )
            )), 5).pos(1, 0).fill(1, 1),
            
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
                        (cal_hour) +":"+
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
            grid.cell(fieldset("Notes", 
            	sale.getNote().length() == 0
            	? label("No note")
            	: note
            )).pos(0, 2).fill(1, 1),
            
            rating_box, print_box
            

            
            
        );
    }
}
