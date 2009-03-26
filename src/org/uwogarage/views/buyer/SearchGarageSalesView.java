package org.uwogarage.views.buyer;

import java.awt.Dimension;
import java.text.SimpleDateFormat;
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
import org.uwogarage.util.documents.AlphaNumDocument;
import org.uwogarage.util.documents.NumDocument;
import org.uwogarage.util.documents.RealNumDocument;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.functional.F;
import org.uwogarage.util.functional.F0;
import org.uwogarage.util.functional.G;
import org.uwogarage.util.functional.P;
import org.uwogarage.views.ListCategoriesView;
import org.uwogarage.views.Slider;
import org.uwogarage.views.View;

/**
 * View to insert criteria for a garage sale search.
 * 
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
    //protected LinkedList<F<P<GarageSaleModel>,P<GarageSaleModel>>> 
    //predicate_builders = new LinkedList<F<P<GarageSaleModel>,P<GarageSaleModel>>>();
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
                for(JCheckBox box : search_criteria_boxes) {
                    if(box.isSelected())
                        ++num_selected_criteria;
                }
                
                if(search_button.isEnabled() != (num_selected_criteria > 0))
                    search_button.setEnabled(num_selected_criteria > 0);
            }
        };
        
        for(JCheckBox box : search_criteria_boxes)
            box.addChangeListener(listener);
    }
    /*
    protected class PredicateBuilder extends F<P<GarageSaleModel>,P<GarageSaleModel>> {
        
        private G<P<GarageSaleModel>> pred_maker;
        private int which;
        
        public PredicateBuilder(int w, G<P<GarageSaleModel>> p) {
            pred_maker = p;
            which = w;
        }
        
        public P<GarageSaleModel> call(P<GarageSaleModel> p) {
            if(!search_criteria_boxes[which].isSelected())
                return p;
            
            return p.and(pred_maker.call());
        }
    }*/
    
    /**
     * Search criteria for finding a sale within a given radius of some latitude
     * and longitude.
     * 
     * @return
     */
    protected JPanel viewRadius() {
        /*
        predicate_builders.add(new PredicateBuilder(RADIUS, new G<P<GarageSaleModel>>() {
            public P<GarageSaleModel> call() {
                
            }
        }));*/
        
        // perform input checking and create the necessary predicate
        predicate_builders.add(new F0() {
            public void call() {
                
                // general error checking
                
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
                             _longitude = Double.parseDouble(lng.getText());
                final int _radius = Integer.parseInt(radius.getText());
                
                // create the predicate to match sales within a radius of a
                // specific (latitude, longitude) coordinate pair
                predicates.add(new P<GarageSaleModel>() {
                    public boolean call(GarageSaleModel sale) {
                        return true;
                    }
                });
            }
        });
        
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
        
        ButtonGroup group = new ButtonGroup();
        group.add(date_specific);
        group.add(date_range);
        
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
    public JPanel view(final ModelSet<GarageSaleModel> sales) {
        Dimension dims = new Dimension(600, 250);
        
        tab_pane.setPreferredSize(dims);
        
        // set up the default tabs with empty panels
        tab_pane.addTab("Radius", search_criteria_tabs[RADIUS]);
        tab_pane.addTab("User ID", search_criteria_tabs[USER_ID]);
        tab_pane.addTab("Date", search_criteria_tabs[DATE]);
        tab_pane.addTab("Category", search_criteria_tabs[CATEGORY]);
        tab_pane.addTab("User Rating", search_criteria_tabs[USER_RATING]);
        tab_pane.addTab("Sale Rating", search_criteria_tabs[SALE_RATING]);
                
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
            grid.cell(2, search_button = button("Search", new D<JButton>() {
                public void call(JButton b) {
                    if(!validateInput())
                        return;
                    
                }
            })).pos(0, 1)
        );
    }
    
    /**
     * Perform input validation.
     */
    public boolean validateInput() {
        return false;
    }
}
