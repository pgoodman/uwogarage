package org.uwogarage.views.buyer;

import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.uwogarage.models.CategoryModel;
import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.ModelSet;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.GeoPosition;
import org.uwogarage.util.StringUtil;
import org.uwogarage.util.documents.AlphaNumDocument;
import org.uwogarage.util.documents.NumDocument;
import org.uwogarage.util.documents.RealNumDocument;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.F0;
import org.uwogarage.util.functional.P;
import org.uwogarage.views.ListCategoriesView;
import org.uwogarage.views.ListGarageSalesReducedView;
import org.uwogarage.views.Slider;
import org.uwogarage.views.View;

/**
 * View to insert criteria for a garage sale search.
 * 
 * TODO Test all of the functionality!!!
 * 
 * @author Peter Goodman
 * @version $Id$
 */
public class SearchGarageSalesView extends View {
    
    // indexes for the enabled and search_criteria_boxes arrays
    static protected final int RADIUS = 0,
                               USER_ID = 1,
                               DATE = 2,
                               CATEGORY = 3,
                               USER_RATING = 4,
                               SALE_RATING = 5;
    
    // the criteria tab pane
    protected JTabbedPane tab_pane = new JTabbedPane();
    
    // the search button
    protected JButton search_button;
    
    // all input fields
    protected SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
    
    protected JTextField lat = field.text(11, new RealNumDocument(11)),
                         lng = field.text(11, new RealNumDocument(11)),
                         radius = field.text(4, new NumDocument(4)),
                         
                         // user id
                         user_id = field.text(4, new AlphaNumDocument(4));
                         
                         // y/m/d for search on a single day 
    protected JFormattedTextField date = field.text(10, fmt),
                                  start_date = field.text(10, fmt),
                                  end_date = field.text(10, fmt);
    
    protected JRadioButton 
    date_specific = new JRadioButton("... on a specific date.", true),
    date_range = new JRadioButton("... within a date range (inclusive).", false);
    
    private String[] quantifiers = { "exactly", "or less", "or more" }; 
    protected JComboBox user_quantifier = new JComboBox(quantifiers),
                        sale_quantifier = new JComboBox(quantifiers);
    
    /*protected JTextField user_rating = field.text(1, new NumDocument(1)),
                         sale_rating = field.text(1, new NumDocument(1));
    */
    
    protected JSlider user_rating = Slider.view(0, 5, 0, null),
                      sale_rating = Slider.view(0, 5, 0, null);
    
    // list of selected categories
    protected ModelSet<CategoryModel> selected_categories = new ModelSet<CategoryModel>(); 
    
    // list of input validation and predicate building functions
    protected LinkedList<P<GarageSaleModel>> predicates = new LinkedList<P<GarageSaleModel>>();
    protected LinkedList<F0> predicate_builders = new LinkedList<F0>(); 
    protected LinkedList<String> predicate_build_errors = new LinkedList<String>();
    
    // check boxes to enable/disable search criteria
    protected JCheckBox[] search_criteria_boxes = new JCheckBox[] {
        new JCheckBox("Radius"),
        new JCheckBox("User ID"),
        new JCheckBox("Date"),
        new JCheckBox("Category"),
        new JCheckBox("User Rating"),
        new JCheckBox("Sale Rating"),
    };
    
    // the panels holding the search criteria options
    protected JPanel[] search_criteria_tabs;
    
    // know if any criteria are selected
    protected int num_selected_criteria = 0;
    
    /**
     * Constructor, disable all the tab content by default.
     */
    public SearchGarageSalesView(ModelSet<CategoryModel> categories) {
        
        search_criteria_tabs = new JPanel[] {
            viewRadius(),
            viewUserId(),
            viewDate(),
            viewCategory(categories),
            viewUserRating(),
            viewSaleRating()
        };
        
        // create the listener to enabled/disable the search button. It loops
        // over all the check boxes each time, and only changes the search 
        // button when it is in the wrong state.
        ChangeListener listener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                num_selected_criteria = 0;
                int i = 0;
                
                for(JCheckBox box : search_criteria_boxes) {
                    if(box.isSelected()) {
                        ++num_selected_criteria;
                    
                        // this is ugly.
                        if(box == (JCheckBox) e.getSource())
                            tab_pane.setSelectedIndex(i);
                    }
                    
                    i++;
                }
                
                if(search_button.isEnabled() != (num_selected_criteria > 0))
                    search_button.setEnabled(num_selected_criteria > 0);
                
                
            }
        };
        
        for(JCheckBox box : search_criteria_boxes)
            box.addChangeListener(listener);
    }
    
    /**
     * Search criteria for finding a sale within a given radius of some latitude
     * and longitude.
     * 
     * @return
     */
    protected JPanel viewRadius() {
        
        // perform input checking and create the necessary predicate
        predicate_builders.add(new F0() {
            public void call() {
                
                // general error checking
                if(!search_criteria_boxes[RADIUS].isSelected())
                    return;
                
                if(!lat.getText().matches("-?([0-9]{1,3})\\.([0-9]{3,6})")) {
                    predicate_build_errors.add(
                        "Radius: please supply a valid latitude."
                    );
                }
                if(!lng.getText().matches("-?([0-9]{1,3})\\.([0-9]{3,6})")) {
                    predicate_build_errors.add(
                        "Radius: please supply a valid longitude."
                    );
                }
                if(!radius.getText().matches("\\d+")) {
                    predicate_build_errors.add(
                        "Radius: please supply a valid radius."
                    );
                }
                
                // ignore creating any predicates if *any* errors exist for any
                // of the criteria
                if(predicate_build_errors.size() > 0)
                    return;
                
                // get the primitive values of the text fields for the
                // predicate
                final double _latitude = Double.parseDouble(lat.getText()), 
                             _longitude = Double.parseDouble(lng.getText()),
                             _radius = Double.parseDouble(radius.getText());
                
                // create the predicate to match sales within a radius of a
                // specific (latitude, longitude) coordinate pair. this uses
                // an approximation to find the distance between the two
                // coordinate pairs (as opposed to the great circle distance
                // formula)
                predicates.add(new P<GarageSaleModel>() {
                    public boolean call(GarageSaleModel sale) {
                        
                        GeoPosition pos = sale.getGeoPosition();
                        
                        double x = 69.1 * (pos.getLatitude() - _latitude),
                               y = 53.0 * (pos.getLongitude() - _longitude) * Math.cos(_latitude / 57.3);
                        
                        return Math.abs(Math.sqrt(x*x + y*y) * 0.621371192) <= _radius;
                    }
                });
            }
        });
        
        // build the gui for the radius criteria
        return grid(
            grid.row(grid.cell(
                label("Use this criteria to find sales within a given radius of")
            )),
            grid.row(grid.cell(label("a specific latitude and longitude."))),
            grid.row(grid.cell(grid(
                form.row(label("Latitude:"), lat),
                form.row(label("Longitude:"), lng),
                form.row(label("Radius"), grid(
                    grid.cell(radius).pos(0, 0),
                    grid.cell(label(" km")).pos(1, 0)
                ))
            )).margin(10, 0, 0, 0))
        );
    }
    
    /**
     * Search criteria for finding a sale created by a specific user with the
     * given id.
     * 
     * @return
     */
    protected JPanel viewUserId() {
        
        // perform input checking and create the necessary predicate
        predicate_builders.add(new F0() {
            public void call() {
                
                if(!search_criteria_boxes[USER_ID].isSelected())
                    return;
                
                // error check the 
                if(!user_id.getText().matches("[0-9a-zA-Z]{4}")) {
                    predicate_build_errors.add(
                        "User ID: User IDs must be four characters long and "+
                        "comprised of alphanumeric characters."
                    );
                }
                
                // ignore creating any predicates if *any* errors exist for any
                // of the criteria
                if(predicate_build_errors.size() > 0)
                    return;
                
                // the user id predicate
                predicates.add(new P<GarageSaleModel>() {
                    public boolean call(GarageSaleModel sale) {
                        return sale.user.getId().equals(user_id.getText());
                    }
                });
            }
        });
        
        // build the gui for the user id criteria
        return grid(
            grid.row(grid.cell(
                label("Use this criteria to find sales created by a given user.")
            )),
            grid.row(grid.cell(grid(
                form.row(label("User ID:"), user_id)
            )).margin(10, 0, 0, 0))
        );
    }
    
    /**
     * Search criteria for finding a sale taking place on a specific date or
     * between a given date range.
     * 
     * @return
     */
    protected JPanel viewDate() {
        
        // perform input checking and create the necessary predicate. For the
        // specific date, we turn it into a problem of a date range by using one
        // date as the start of the day, and the other as the end of the day.
        predicate_builders.add(new F0() {
            public void call() {
                
                if(!search_criteria_boxes[DATE].isSelected())
                    return;
                
                // in yyyy/mm/dd format
                String date_pattern = (
                    "([12]\\d{3})/((0?[1-9])|10|11|12)/((0?[1-9])|[12]\\d|3[01])"
                );
                
                SimpleDateFormat date_format = new SimpleDateFormat("yyyy/MM/dd");
                
                Calendar start_cal = Calendar.getInstance(),
                         end_cal = Calendar.getInstance();
                
                try {
                
                    // checking a specific date
                    if(date_specific.isSelected()) {
                    
                        if(!date.getText().matches(date_pattern)) {
                            predicate_build_errors.add(
                                "Date: Please supply a valid specific date, formatted as: yyyy/mm/dd."
                            );
                        }
                        
                        start_cal.setTime(date_format.parse(date.getText()));
                        end_cal.setTime(date_format.parse(date.getText()));
                    
                    // checking between two dates
                    } else {
                        if(!start_date.getText().matches(date_pattern)) {
                            predicate_build_errors.add(
                                "Date: Please supply a valid specific date, formatted as: yyyy/mm/dd."
                            );
                        }
                        if(!end_date.getText().matches(date_pattern)) {
                            predicate_build_errors.add(
                                "Date: Please supply a valid specific date, formatted as: yyyy/mm/dd."
                            );
                        }
                        
                        start_cal.setTime(date_format.parse(start_date.getText()));
                        end_cal.setTime(date_format.parse(end_date.getText()));
                    }
                
                } catch(Exception e) {
                    predicate_build_errors.add("Date: Unabled to parse the dates.");
                }
                
                // ignore creating any predicates if *any* errors exist for any
                // of the criteria
                if(predicate_build_errors.size() > 0)
                    return;
                
                // set it to the start of the start day, this should fall over to
                // the previous day
                start_cal.set(Calendar.MINUTE, 59);
                start_cal.set(Calendar.HOUR_OF_DAY, -1);
                
                // set it to the end of the end day, this should fall over to
                // the next day
                end_cal.set(Calendar.MINUTE, 59);
                end_cal.set(Calendar.HOUR_OF_DAY, 24);
                
                // convert to dates
                final Date _start_date = start_cal.getTime(),
                           _end_date = end_cal.getTime();
                
                // the date predicate, transformed into the equivalent of a
                // before and after check, regardless of if we are considering a
                // specific date or a date range.
                predicates.add(new P<GarageSaleModel>() {
                    public boolean call(GarageSaleModel sale) {
                        Date sale_date = sale.getTime().getTime();
                        return (true
                            && _start_date.before(sale_date) 
                            && _end_date.after(sale_date)
                        );
                    }
                });
            }
        });
        
        ButtonGroup group = new ButtonGroup();
        group.add(date_specific);
        group.add(date_range);
        
        // build the gui for the date criteria
        return grid(
            grid.row(grid.cell(
                label("Use this criteria to find sales...")
            )),
            grid.row(grid.cell(date_specific).anchor(1, 0, 0, 1).margin(10, 0, 0, 0)),
            grid.row(grid.cell(grid(
                form.row(label("Date (yyyy/mm/dd):"), date)
            ))),
            grid.row(grid.cell(date_range).anchor(1, 0, 0, 1).margin(10, 0, 0, 0)),
            grid.row(grid.cell(grid(
                form.row(label("Start Date (yyyy/mm/dd):"), start_date),
                form.row(label("End Date (yyyy/mm/dd):"), end_date)
            )))
        );
    }
    
    /**
     * Search criteria for finding sales categories with the checked categories.
     * 
     * @return
     */
    protected JPanel viewCategory(ModelSet<CategoryModel> categories) {
        
        // perform input checking and create the necessary predicate
        predicate_builders.add(new F0() {
            public void call() {
                
                if(!search_criteria_boxes[CATEGORY].isSelected())
                    return;
                
                // ignore creating any predicates if *any* errors exist for any
                // of the criteria
                if(predicate_build_errors.size() > 0)
                    return;
                
                // Check that a given sale has all of the selected categories
                predicates.add(new P<GarageSaleModel>() {
                    public boolean call(GarageSaleModel sale) {
                        
                        ModelSet<CategoryModel> c = sale.categories;
                        
                        for(CategoryModel category : selected_categories) {
                            if(!c.contains(category))
                                return false;
                        }
                        
                        return true;
                    }
                });
            }
        });
        
        // build this gui for the category criteria
        return grid(
            grid.row(grid.cell(
                label("Use this criteria to find sales that are categorized")
            )),
            grid.row(grid.cell(
                label("with the checked categories.")
            )),
            grid.row(grid.cell(
                ListCategoriesView.view(
                    categories, 
                    new ModelSet<CategoryModel>(), 
                    selected_categories
                )
            ).margin(10, 0, 0, 0))
        );
    }
    
    /**
     * Search criteria for finding sales created by users ranked in a particular
     * way.
     * 
     * @return
     */
    protected JPanel viewUserRating() {
        
        // perform input checking and create the necessary predicate
        predicate_builders.add(new F0() {
            public void call() {
                
                if(!search_criteria_boxes[USER_RATING].isSelected())
                    return;
                
                // ignore creating any predicates if *any* errors exist for any
                // of the criteria
                if(predicate_build_errors.size() > 0)
                    return;
                
                final int _modifier = user_quantifier.getSelectedIndex(),
                          _rating = user_rating.getValue();
                
                // Check that a given sale has all of the selected categories
                predicates.add(new P<GarageSaleModel>() {
                    public boolean call(GarageSaleModel sale) {
                        float rating = sale.user.getRating();
                        
                        switch(_modifier) {
                            case 0: return rating == (int)_rating;
                            case 1: return rating <= _rating;
                            case 2: return rating >= _rating;
                        }
                        
                        return false;
                    }
                });
            }
        });
        
        // build the gui for the user rating criteria
        return grid(
            grid.cell(label(
                "Use this criteria to find sales created by users with a "+
                "particular rating."
            )).pos(0, 0),
            grid.cell(user_rating).pos(0, 1).margin(10, 0, 0, 0),
            grid.cell(user_quantifier).pos(0, 2).margin(10, 0, 0, 0)
        );
    }
    
    /**
     * Search criteria for finding sales ranked in a particular way.
     * 
     * @return
     */
    protected JPanel viewSaleRating() {
        
        // perform input checking and create the necessary predicate
        predicate_builders.add(new F0() {
            public void call() {
                
                if(!search_criteria_boxes[SALE_RATING].isSelected())
                    return;
                
                // ignore creating any predicates if *any* errors exist for any
                // of the criteria
                if(predicate_build_errors.size() > 0)
                    return;
                
                final int _modifier = sale_quantifier.getSelectedIndex(),
                          _rating = sale_rating.getValue();
                
                // Check that a given sale has all of the selected categories
                predicates.add(new P<GarageSaleModel>() {
                    public boolean call(GarageSaleModel sale) {
                        float rating = sale.getRating();
                        
                        switch(_modifier) {
                            case 0: return rating == (int)_rating;
                            case 1: return rating <= _rating;
                            case 2: return rating >= _rating;
                        }
                        
                        return false;
                    }
                });
            }
        });
        
        return grid(
            grid.cell(label(
                "Use this criteria to find sales with a particular rating."
            )).pos(0, 0),
            grid.cell(sale_rating).pos(0, 1).margin(10, 0, 0, 0),
            grid.cell(sale_quantifier).pos(0, 2).margin(10, 0, 0, 0)
        );
    }
    
    /**
     * Create the view that allows the user to input search criteria and then
     * perform a live search on the garage sales.
     * 
     * @return
     */
    public JPanel view(final ModelSet<GarageSaleModel> sales, 
                              final D<GarageSaleModel> view_responder,
                              final UserModel user) {
        
        Dimension dims = new Dimension(600, 250);
        final JPanel search_results = new JPanel();
        
        tab_pane.setPreferredSize(dims);
        
        // set up the default tabs with empty panels
        tab_pane.addTab("Radius", search_criteria_tabs[RADIUS]);
        tab_pane.addTab("User ID", search_criteria_tabs[USER_ID]);
        tab_pane.addTab("Date", search_criteria_tabs[DATE]);
        tab_pane.addTab("Category", search_criteria_tabs[CATEGORY]);
        tab_pane.addTab("User Rating", search_criteria_tabs[USER_RATING]);
        tab_pane.addTab("Sale Rating", search_criteria_tabs[SALE_RATING]);
                
        // the search button, gets things started
        search_button = button("Search", new D<JButton>() {
            public void call(JButton b) {
                
                predicates.clear();
                predicate_build_errors.clear();
                
                // perform all error checking and build the predicates
                for(F0 builder : predicate_builders)
                    builder.call();
                
                // there are are errors, alert them and then stop
                if(0 < predicate_build_errors.size()) {
                    dialog.alert(f,
                        "The following errors occurred: \n\n"+
                        StringUtil.join('\n', predicate_build_errors)
                    );
                    return;
                }
                
                search_button.setText("Searching...");
                search_button.setEnabled(false);
                search_button.validate();
                
                // build the final predicate that will be used for the search
                P<GarageSaleModel> p = new P.TRUE<GarageSaleModel>();
                for(P<GarageSaleModel> sp : predicates)
                    p = p.and(sp);
                
                // find and show the search results!
                search_results.removeAll();
                search_results.add(fieldset("Search Results",
                    ListGarageSalesReducedView.view(
                        sales.filter(p), // filter the sales!
                        view_responder,
                        user
                    )
                ));
                
                search_results.validate();
                search_button.setText("Search");
                search_button.setEnabled(true);
                f.pack();
            }
        });
        
        search_button.setEnabled(false);
        
        // create the GUI
        return grid(
                
            // make the check box selection to enable/disable various criteria
            grid.cell(fieldset("Criteria", grid(
                grid.row(grid.cell(label("Check a box to include the associated"))),
                grid.row(grid.cell(label("search criteria information in the search."))),
                grid.row(grid.cell(grid(
                    grid.row(grid.cell(search_criteria_boxes[RADIUS]).anchor(1, 0, 0, 1)),
                    grid.row(grid.cell(search_criteria_boxes[USER_ID]).anchor(1, 0, 0, 1)),
                    grid.row(grid.cell(search_criteria_boxes[DATE]).anchor(1, 0, 0, 1)),
                    grid.row(grid.cell(search_criteria_boxes[CATEGORY]).anchor(1, 0, 0, 1)),
                    grid.row(grid.cell(search_criteria_boxes[USER_RATING]).anchor(1, 0, 0, 1)),
                    grid.row(grid.cell(search_criteria_boxes[SALE_RATING]).anchor(1, 0, 0, 1))
                )).margin(10, 0, 0, 0))
            ))).pos(0, 0).fill(1, 1),
            
            // the tabbed search criteria
            grid.cell(tab_pane).margin(0, 0, 0, 10).fill(1, 1).pos(1, 0),
            
            // add in the search button
            grid.cell(2, search_button).pos(0, 1),
            
            // add in the search results panel
            grid.cell(2, search_results).pos(0, 2)
        );
    }
    
    /**
     * Perform input validation.
     */
    public boolean validateInput() {
        return false;
    }
}
