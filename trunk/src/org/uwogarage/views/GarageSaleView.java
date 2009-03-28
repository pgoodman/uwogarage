package org.uwogarage.views;


import java.awt.Container;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import org.uwogarage.UWOGarage;
import org.uwogarage.models.CategoryModel;
import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.RatingModel;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.Location;
import org.uwogarage.util.StringUtil;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.D2;
import org.uwogarage.util.functional.F;
import org.uwogarage.util.gui.GridCell;

/**
 * View a single garage sale.
 * 
 * @version $Id$
 */
public class GarageSaleView extends TabView {
    
    /**
     * Make the box to show the current rating, how the user rated the sale,
     * and also the button to go and add/update the rating.
     * 
     * @param sale
     * @param user
     * @param rate_responder
     * @return
     */
    static public GridCell ratingBox(final GarageSaleModel sale, 
                                           final UserModel user,
                         final D2<GarageSaleModel,Integer> rate_responder) {
        
        RatingModel user_rating = sale.getRatingFrom(user);
        LinkedList<GridCell[]> rows = new LinkedList<GridCell[]>();
        
        rows.add(grid.row(grid.cell(label("Overall Rating"))));
        rows.add(grid.row(grid.cell(label(String.valueOf(sale.getRating())))));
        
        // if the user has rated this sale then show what their vote was
        if(null != user_rating) {
            rows.add(grid.row(grid.cell(label("Your Rating"))));
            rows.add(grid.row(grid.cell(
                label(String.valueOf(sale.getRatingFrom(user).getRating())))
            ));
        }
        
        // don't allow a user to rate their own sale
        if(sale.user != user) {
            rows.add(grid.row(grid.cell(
                button("Rate Sale", new D<JButton>() {
                    public void call(JButton btn) {
                        dialog.modal(f, "Rate Sale", new F<JDialog, Container>() {
                            public Container call(final JDialog dialog) {
                                
                                // figure out the default rating to set
                                RatingModel user_rating = sale.getRatingFrom(user);
                                int default_rating = (
                                    (null == user_rating) 
                                     ? 3 
                                     : user_rating.getRating()
                                );
                                
                                // create the slider
                                final JSlider slider = Slider.view(1, 5, default_rating, null);
                                
                                // create the gui to make a rating 
                                return grid(
                                    grid.cell(2, slider).margin(10, 10, 10, 10),
                                    
                                    // save the rating
                                    grid.cell(button("Rate", new D<JButton>() {
                                        public void call(JButton btn) {
                                            
                                            rate_responder.call(
                                                sale, 
                                                slider.getValue()
                                            );
                                            dialog.setVisible(false);
                                        }
                                    })).pos(0, 1).margin(10, 0, 10, 10).anchor(1, 1, 0, 0),
                                    
                                    // cancel rating button
                                    grid.cell(button("Cancel", new D<JButton>() {
                                        public void call(JButton b) {
                                            dialog.setVisible(false);
                                            dialog.dispose();
                                        }
                                    })).pos(1, 1).margin(10, 10, 10, 0).anchor(1, 0, 0, 1)
                                );
                            }
                        }); // show the dialog
                    }
                }
            ))));
        }
        
        return grid.cell(fieldset("Rating", grid(rows))).pos(0,3).fill(1,1);
    }
    
    /**
     * Show the box with the print button in it, and create the file to be
     * printed.
     * 
     * @param sale_date
     * @param sale_time
     * @param all_categories
     * @param rating
     * @return
     */
    static public GridCell printBox(final String sale_date, 
                                    final String sale_time, 
                                    final String all_categories, 
                                    final String rating) {
        
        return grid.cell(fieldset("Print", grid(
           grid.cell(
                button("Print this Sale", new D<JButton>() {
                    public void call(JButton btn)  {
                        String web_page = "http://google.ca",
                               os_name = System.getProperty("os.name").toLowerCase();
                        
                        // locate the file that we want to replace with the html
                        // for this sale
                        URL loc = UWOGarage.class.getResource(
                            "files/garagesale.html"
                        );
                        
                        if(null != loc) {
                            
                            // the the absolute location of the file
                            web_page = loc.getFile()
                                          .substring(1)
                                          .replaceAll("%20", " ");
                            
                            // if it's windows, change forward slashes to backslashes
                            if(os_name.startsWith("Windows"))
                                web_page = web_page.replace('/', '\\');
                            
                            // start creating our file contents
                            StringBuffer fc = new StringBuffer();
                            
                            fc.append(
                                "<table><tr><td><p><h1>Your Garage Sale</h1>"+
                                "<h3>When</h3><p>"
                            );
                            fc.append(sale_date);
                            fc.append(" at ");
                            fc.append(sale_time);
                            fc.append("</p><h3>Categories</h3><p>");
                            fc.append(all_categories);
                            fc.append("</p><h3>Overall Rating</h3><p>");
                            fc.append(rating);
                            fc.append("</p></td></tr></table>");
                            
                            // write the contents to file
                            try {
                                BufferedWriter out_fp = new BufferedWriter(
                                    new FileWriter(web_page)
                                );
                                out_fp.write(fc.toString());
                                out_fp.close();
                            } catch(IOException e) { }
                        }
                        
                        
                        String[] cmd = null;
                        
                        if(os_name.startsWith("win")) {
                            cmd = new String[] {
                                "cmd", "/c", "start", "iexplore.exe", 
                                "-nohome", web_page
                            };
                        } else if(os_name.startsWith("mac")) {
                            cmd = new String[] {
                                "open",
                                web_page
                            };
                        }
                        
                        if(null != cmd) {
                            try {
                                Runtime.getRuntime().exec(cmd);
                            } catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                
            )).pos(0, 3)
        )
        )).pos(0,4).fill(1,1);
    }
    
    static public JPanel view(final GarageSaleModel sale, 
                              final UserModel logged_user, 
                              final D2<GarageSaleModel,Integer> rate_responder) {
        
        JTextArea note = new JTextArea(sale.getNote());
        note.setEnabled(false);
        
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
        
        // the date of the sale
        final String sale_date = (
            days[cal.get(Calendar.DAY_OF_WEEK)] +", "+
        	months[cal.get(Calendar.MONTH)] +" "+
        	cal.get(Calendar.DAY_OF_MONTH)
        );
        
        int cal_hour = cal.get(Calendar.HOUR);
        if(cal_hour == 0) cal_hour = 12;
        
        // the time of the sale
        final String sale_time = (
            cal_hour +":"+ StringUtil.padLeft(
                String.valueOf(cal.get(Calendar.MINUTE)), 
                '0', 
                 2
            ) +
            " "+ am_or_pm[cal.get(Calendar.AM_PM)] +" "+ 
            Location.PROVINCE_TIME_ZONE_CODES.get(
                address.getProvince()
            )
        );
        
        // make a list of categories
        GridCell[][] categories = new GridCell[sale.categories.size()][];
        String cat_sep = "";
        StringBuffer all_categories = new StringBuffer();
        int i = 0;
        
        // create a row of categories, and also create a comma-separated list of
        // categories for the print box
        for(CategoryModel category : sale.categories) {
        	all_categories.append(cat_sep + category.getName());
        	categories[i++] = grid.row(
                grid.cell(label(category.getName()))
                    .anchor(1, 0, 0, 1)
                    .margin(0, 10, 0, 10)
            );
        	
            cat_sep = ", ";
        }
        
        UserModel user = sale.user;
        
        // create the GUI for showing all garage sale info
        return grid(
            
            // seller information
            grid.row(
                grid.cell(2, fieldset("Seller Information", grid(
                    form.row(label("Name:"), label(
                        user.getFirstName() +" "+ user.getLastName()
                    )),
                    form.row(label("Telephone:"), label(
                        StringUtil.join('-', user.getPhoneNumber())
                    )),
                    form.row(label("Average Sale Rating:"), label(
                        String.valueOf(user.getRating())
                    ))
                ))).fill(1, 1)
            ),
            
            // address
            grid.row(    
                grid.cell(fieldset("Location", grid(
                    form.row(label("Street:"), label(address.getStreet())),
                    form.row(label("City:"), label(address.getCity())),
                    form.row(label("Province:"), label(address.getProvince()))
                ))).fill(1, 1),
                
                // date and time
                grid.cell(fieldset("Date / Time", grid(
                    form.row(label("Date:"), label(sale_date)),
                    form.row(label("Time:"), label(sale_time))
                ))).fill(1, 1)
            ),
            grid.row(
                
                // list of categories
                grid.cell(fieldset("Categories",
                    categories.length > 0
                    ? grid(categories) 
                    : grid(grid.cell(label("Not Categorized.")))
                )).fill(1, 1),
                
                // note
                grid.cell(fieldset("Notes", 
                	sale.getNote().length() == 0
                	? label("No note")
                	: note
                )).fill(1, 1)
            ),
            
            grid.row(
                
                // user ratings
                ratingBox(sale, logged_user, rate_responder), 
                
                // print the sale
                printBox(
                    sale_date,
                    sale_time,
                    all_categories.toString(),
                    String.valueOf(sale.getRating())
                )
            )
        );
    }
}
