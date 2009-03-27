package org.uwogarage.views;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.uwogarage.UWOGarage;
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

    static public JPanel view(final GarageSaleModel sale, final UserModel logged_user) {
        
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
        
        final String sale_date = days[cal.get(Calendar.DAY_OF_WEEK)] +", "+
        	months[cal.get(Calendar.MONTH)] +" "+
        	cal.get(Calendar.DAY_OF_MONTH);
        
        int cal_hour = cal.get(Calendar.HOUR);
        if(cal_hour == 0) cal_hour = 12;
        
        final String sale_time = (cal_hour) +":"+
        StringUtil.padLeft(
                String.valueOf(cal.get(Calendar.MINUTE)), 
                '0', 
                2
            ) +" "+
            am_or_pm[cal.get(Calendar.AM_PM)] +" "+
            Location.PROVINCE_TIME_ZONE_CODES.get(
                address.getProvince()
            );
        
        
        
        // make a list of categories
        GridCell[][] categories = new GridCell[sale.categories.size()][];
        int i = 0;
        
        String cat_sep = "";
        final StringBuffer all_categories = new StringBuffer();
        
        for(CategoryModel category : sale.categories) {
        	all_categories.append(cat_sep + category.getName());
            
        	categories[i++] = grid.row(
                grid.cell(label(category.getName()))
                    .anchor(1, 0, 0, 1)
                    .margin(0, 10, 0, 10)
            );
        	
            cat_sep = ", ";
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
                            public void call(JButton btn)  {
                            	String web_page = "http://google.ca";
                            	
                            	URL loc = UWOGarage.class.getResource("files/garagesale.html"),
                            		template = UWOGarage.class.getResource("files/garagesale-template.html");
                            	
                            	if(null != loc) {
	                            	web_page = loc.getFile()
	                            				  .replace('/', '\\')
	                            				  .substring(1)
	                            				  .replaceAll("%20", " ");
                            	}
                            	
                            	/*try {
                            		//BufferedReader in_fp = new BufferedReader(new InputStreamReader(template.openStream()));
                            		BufferedWriter out_fp = new BufferedWriter(new FileWriter(web_page));
                            		
                            		// read the file into a string
                            		
                            		try {
                            			while(true)
                            				file_contents.append(in_fp.readLine());
                            		} catch(IOException e) { }
                            		
                            		in_fp.close();
                            		in_fp = null;
                            		
                            		out_fp.write(String.format(file_contents.toString(),
                            		    "Sale name here",
                            		    "Date here",
                            		    "Time here",
                            		    "Category list here..",
                            		    "Rating here"
                            		));
                            		
                            		out_fp.close();
                            		out_fp = null;
                            		
                            	} catch(IOException e) {
                            		System.out.println("Couldn't convert template.");
                            		e.printStackTrace();
                            	}*/
                            	StringBuffer fc = new StringBuffer();
                            	
                            	fc.append("<table><tr><td><p><h1>Your Garage Sale</h1><h3>When</h3><p>");
                            	fc.append(sale_date);
                            	fc.append(" at ");
                            	fc.append(sale_time);
                            	fc.append("</p><h3>Categories</h3><p>");
                            	fc.append(all_categories.toString());
                            	fc.append("</p><h3>Overall Rating</h3><p>");
                            	fc.append(String.valueOf(sale.getRating()));
                            	fc.append("</p></td></tr></table>");
                            	
                            	try {
                            		BufferedWriter out_fp = new BufferedWriter(new FileWriter(web_page));
                            		out_fp.write(fc.toString());
                            		out_fp.close();
                            	} catch(IOException e) { }
                            	
                            	String web[] ={"cmd", "/c", "start", "iexplore.exe", "-nohome", web_page};
                            	
                            	Process p;
                            	
                            	try{
                            		p = Runtime.getRuntime().exec(web);
                    			}
                    			catch(Exception e){
                    				e.printStackTrace();
								
                    			}
                            }
                        }
                        
                    )).pos(0, 3)
                )
            )).pos(0,4).fill(1,1);
        
        
        
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
                        sale_date
                    )).anchor(1, 0, 0, 1).margin(0, 0, 0, 10)
                ),
                grid.row(
                    grid.cell(label("Time:")),
                    grid.cell(label(
                        sale_time
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
