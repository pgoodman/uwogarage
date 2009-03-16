package org.uwogarage.views.seller;

import java.awt.Color;
import java.util.Calendar;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.uwogarage.models.CategoryModel;
import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.ModelSet;
import org.uwogarage.util.Location;
import org.uwogarage.util.documents.AnyDocument;
import org.uwogarage.util.documents.NumDocument;
import org.uwogarage.util.documents.RealNumDocument;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.gui.GridCell;
import org.uwogarage.views.CategoryListView;
import org.uwogarage.views.View;


/**
 * Create the view to add a garage sale.
 * 
 * @author Peter Goodman
 * @version $Id$
 */
public class AddGarageSaleView extends View {
    
    // set up the main text boxes
    protected JTextField street = field.text(20, new AnyDocument(50)),
                         city = field.text(20, new AnyDocument(20)),
                         year = field.text(4, new NumDocument(4)),
                         month = field.text(2, new NumDocument(2)),
                         day = field.text(2, new NumDocument(2)),
                         hour = field.text(2, new NumDocument(2)),
                         minute = field.text(2, new NumDocument(2)),
                         lat = field.text(11, new RealNumDocument(11)),
                         lng = field.text(11, new RealNumDocument(11));
    
    protected JTextArea note = new JTextArea(new AnyDocument(200));
    
    // set up the time AM/PM fields
    protected JRadioButton time_am = new JRadioButton("am", true),
                           time_pm = new JRadioButton("pm", false);
    protected ButtonGroup time_buttons = new ButtonGroup();
    
    // the selected categories
    ModelSet<CategoryModel> categories = new ModelSet<CategoryModel>();
    
    // set up the provinces list
    JComboBox province = new JComboBox(Location.PROVINCE_CODES);
    
    /**
     * Left pad a string with zeros.
     * @param input
     * @param total_length
     * @return
     */
    private String zeroPad(String input, int total_length) {
        
        if(input.length() >= total_length)
            return input;
        
        StringBuffer ret = new StringBuffer(total_length);
        
        for(int i = 0; i < total_length - input.length(); ++i)
            ret.append('0');
        
        ret.append(input);
        
        return ret.toString();
    }
    
    /**
     * This pre-processes some of the form fields to put them in mostly valid 
     * states before error checking. This just allows for us to ignore some
     * of the more tedious error checking on some of the more complex fields.
     * 
     * Calendar was used to do equivalences on the month/day/year but not the
     * hour/minutes as it deals with hours from 0-12 instead of 1-12, which is a
     * bit annoying for humans to think about.
     * 
     * Thus, somewhat unfortunately, the hour/min equivalences don't affect
     * the y/m/d values.
     */
    protected void preProcessInput() {        
        
        Calendar then = Calendar.getInstance();
        int curr_year = then.get(Calendar.YEAR);
            
        // get the integer values for month/day
        int y = Math.abs(0 == year.getText().length() ? curr_year : Integer.parseInt(year.getText())),
            d = Math.abs(0 == day.getText().length() ? then.get(Calendar.DAY_OF_MONTH) : Integer.parseInt(day.getText())),
            m = Math.abs(0 == month.getText().length() ? then.get(Calendar.MONTH)+1 : Integer.parseInt(month.getText()));
        
        // perform equivalence transformations on the year/month/day
        if(y < curr_year) y = curr_year;
        if(m < 1) m = 1;
        if(d < 1) d = 1;
        
        // get the integer values for hours/minutes
        int h = Math.abs((0 == hour.getText().length()) ? 1 : Integer.parseInt(hour.getText())),
            min = Math.abs((0 == minute.getText().length()) ? 0 : Integer.parseInt(minute.getText()));
        
        // perform equivalence transformations on the input given for 
        // hours/minutes. The times are equivalent as the hours/minutes are added
        // correctly, and the am/pm is adjusted accordingly.
        if(min > 60) h += (int)(min / 60);
        if(h > 12) {
            int res = (int)(h / 12) & 1;            
            time_am.setSelected(1 == res);
            time_pm.setSelected(1 == res);
            ++h;
        }
        
        min = min % 60;
        h = h % 13; // note: the 13 is intentional
        if(h < 1) h = 1;
        
        // set the month, day
        hour.setText(zeroPad(String.valueOf(h), 2));
        minute.setText(zeroPad(String.valueOf(min), 2));
        
        // use the Calendar class to perform the proper equivalence transformations
        // for us on year/month/day
        then.set(Calendar.YEAR, y);
        then.set(Calendar.MONTH, m);
        then.set(Calendar.DAY_OF_MONTH, d);
        
        // pad or fill in values for the left out fields
        year.setText(String.valueOf(then.get(Calendar.YEAR)));
        month.setText(zeroPad(String.valueOf(then.get(Calendar.MONTH)), 2));
        day.setText(zeroPad(String.valueOf(then.get(Calendar.DAY_OF_MONTH)), 2));
    }
    
    /**
     * Create the form section for inputting address information.
     * 
     * @return
     */
    protected GridCell[] addressSection() {
        return grid.row(
            grid.cell(fieldset("Address", grid(
                form.row(label("Address:"), street),
                form.row(label("City:"), city),
                form.row(label("Province:"), province),
                form.row(label("Latitude:"), lat),
                form.row(label("Longitude:"), lng)
            ))).margin(0, 10, 10, 10).anchor(0, 0, 0, 1).fill(1, 1)
        );
    }
    
    /**
     * Create the form section for inputting date/time information.
     * 
     * @return
     */
    protected GridCell[] dateAndTimeSection() {
        return grid.row(
            grid.cell(fieldset("Date/Time", grid(
                form.row(label("Date (yyyy/mm/dd):"), grid(
                    grid.cell(year),
                    grid.cell(label(" / ")),
                    grid.cell(month),
                    grid.cell(label(" / ")),
                    grid.cell(day)
                )),
                form.row(label("Time:"), grid(
                    grid.cell(hour),
                    grid.cell(label(" : ")),
                    grid.cell(minute),
                    grid.cell(time_am),
                    grid.cell(time_pm)
                ))
            ))).margin(20, 10, 10, 10).anchor(0, 0, 0, 1).fill(1, 1)
        );
    }
    
    /**
     * Create the categories section of the form.
     */
    protected GridCell[] categoriesSection(ModelSet<CategoryModel> all_categories, ModelSet<CategoryModel> return_categories) {
        
        return grid.row(
            grid.cell(fieldset("Categories", grid(
                grid.cell(
                    all_categories.isEmpty() 
                    ? label("There are no categories to choose from.")
                    : CategoryListView.view(
                          all_categories, 
                          return_categories
                      )
                 )
            ))).margin(20, 10, 10, 10).anchor(0, 0, 0, 1).fill(1, 1)
        );
    }
    
    /**
     * Create the section for putting in any extra information.
     * 
     * @return
     */
    protected GridCell[] extraInfoSection() {
        
        note.setColumns(20);
        note.setRows(1);
        note.setLineWrap(true);
        note.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        return grid.row(
            grid.cell(fieldset("Extra Information (not required)", grid(
                form.row(label("Note:"), note)
            ))).margin(20, 10, 10, 10).anchor(0, 0, 0, 1).fill(1, 1)
        );
    }
    
    /**
     * View a garage sale.
     * 
     * @param responder
     * @return
     */
    public JPanel view(ModelSet<CategoryModel> all_categories, final D<GarageSaleModel> responder) {
        
        time_buttons.add(time_am);
        time_buttons.add(time_pm);
        
        // lay out the form
        return grid(
            grid.row(
                grid.cell(label("Add Garage Sale")).margin(10, 10, 10, 10)
            ),
            
            addressSection(),
            dateAndTimeSection(),
            categoriesSection(all_categories, categories),
            extraInfoSection(),
            
            grid.row(
                grid.cell(button("Add", new D<JButton>() {
                    public void call(JButton b) {
                        
                        preProcessInput();
                        
                        // confirmation message to let the user know that some
                        // of their input might have been adjusted / reformmated
                        String confirm_msg = (
                            "The date and time fields may have automatically been "+
                            "given equivalent but corrected default values. Are "+
                            "all fields correct?"
                        );
                        
                        // we confirm because of the pre-processing done to the
                        // form to weed out some error checking
                        if(!dialog.confirm(f, confirm_msg))
                            return;
                        
                        // the sale that we are attempting to create and also a
                        // list of errors that might have built up
                        GarageSaleModel sale = new GarageSaleModel();
                        LinkedList<String> errors = new LinkedList<String>();
                        
                        try {
                            
                            String prov = (String) province.getSelectedItem(),
                                   stre = street.getText(),
                                   citi = city.getText();
                            
                            // this creates a large date string that will be parsed
                            // to 
                            String sale_date = (
                                year.getText() +" "+
                                month.getText() +" "+
                                day.getText() +" "+
                                String.valueOf(Integer.parseInt(hour.getText())-1) 
                                +" "+ minute.getText() +" "+
                                (time_am.isSelected() ? "AM " : "PM ") +
                                Location.PROVINCE_TIME_ZONE_CODES.get(prov)
                            );
                            
                            // make sure the date parses and is sometime that is 
                            // either now or in the future
                            if(!sale.setDate(sale_date)) {
                                errors.add(
                                    "Please make sure that you have supplied "+
                                    "valid future date and time for your garage "+
                                    "sale."
                                );
                            }
                            
                            if(stre.length() == 0)
                                errors.add("Your street address cannot be left blank.");
                            
                            if(citi.length() == 0)
                                errors.add("Your city cannot be left blank.");
                            
                            if(!sale.setGeoPosition(lat.getText(), lng.getText())) {
                                errors.add(
                                    "Please insert valid geographic position "+
                                    "coordinates."
                                );
                            }
                                                        
                            sale.setLocation(new Location(stre, prov, citi));
                            sale.setNote(note.getText());
                            sale.setCategories(categories);
                            
                        } catch(Exception e) {
                            errors.add("Invalid province selected.");
                        }
                        
                        // there are errors, report them
                        if(!errors.isEmpty()) {
                            String error_str = "The following errors occurred:\n";
                            for(String error : errors)
                                error_str += "\n"+ error;
                            
                            dialog.alert(f, error_str);
                        
                        // done, no errors left :D
                        } else {
                            responder.call(sale);
                        }
                    }
                }))
            )
        );
    }
}
