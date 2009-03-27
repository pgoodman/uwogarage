package org.uwogarage.views.admin;

import java.util.Arrays;
import java.util.Comparator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.uwogarage.UWOGarage;
import org.uwogarage.models.ModelSet;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.gui.GridCell;
import org.uwogarage.util.InverseComparator;
import org.uwogarage.views.TabView;

/**
 * Shows the a users list, which can be sorted by id, first name, or last name.
 * 
 * @version $Id$
 */
public class ListUsersView extends TabView {
	
    /**
     * Comparator to sort by first name in non-descending order.
     */
    protected Comparator<UserModel> first_name_sort = new Comparator<UserModel>(){
    	public int compare(UserModel a, UserModel b) {
    		return a.getFirstName().compareTo(b.getFirstName());
		}
    };
    
    /**
     * Comparator to sort by last name in non-descending order.
     */
    protected Comparator<UserModel> last_name_sort = new Comparator<UserModel>(){
    	public int compare(UserModel a, UserModel b) {
    		return a.getLastName().compareTo(b.getLastName());
		}
    };
    
    /**
     * Comparator to sort by user id in non-descending order.
     */
    protected Comparator<UserModel> id_sort = new Comparator<UserModel>(){
    	public int compare(UserModel a, UserModel b) {
    		return a.getId().compareTo(b.getId());
		}
    };
	
    // the panel that will hold the list of users. referenced here so we can
    // swap in a new lists (i.e. when we re-sort) at will by changing the panel's
    // contents
    protected final JPanel rows = new JPanel();
    
    // the overall set of users that will be sorted.
    protected ModelSet<UserModel> users;
    
    // the edit and delete responders to be called when either the edit and delete
    // user buttons are called.
    protected D<UserModel> edit_responder,
                           delete_responder;

    // store the up/down buttons
    protected GridCell[] sort_buttons;	
	
	/**
	 * Constructor.
	 */
	public ListUsersView(ModelSet<UserModel> u, D<UserModel> e, D<UserModel> d) {
	    users = u;
	    edit_responder = e;
	    delete_responder = d;
	    
	    // get the icons that the sort buttons need
	    ImageIcon up_arrow = icon("files/up-arrow.gif", UWOGarage.class),
                  down_arrow = icon("files/down-arrow.gif", UWOGarage.class);
	    
	    // create the sort buttons
	    sort_buttons = grid.row(
	            
	        // up/down arrows for sorting id
	        grid.cell(grid(
                grid.cell(button(up_arrow, makeSortDelegate(id_sort))), 
                grid.cell(button(down_arrow, makeSortDelegate(
                    new InverseComparator<UserModel>(id_sort)
                )))
            )).margin(0, 0, 10, 20),
            
            // up/down arrows for sorting by first name
            grid.cell(grid(
                grid.cell(button(up_arrow, makeSortDelegate(
                    first_name_sort
                ))), 
                grid.cell(button(down_arrow, makeSortDelegate(
                    new InverseComparator<UserModel>(first_name_sort)
                )))
            )).margin(0, 0, 10, 10),
            
            // up/down arrows for sorting by last name
            grid.cell(grid(
                grid.cell(button(up_arrow, makeSortDelegate(
                    last_name_sort
                ))), 
                grid.cell(button(down_arrow, makeSortDelegate(
                    new InverseComparator<UserModel>(last_name_sort)
                )))
            )).margin(0, 0, 10, 20)            
        );
	}
	
    /**
     * Create the delegate to be called by a specific button used to toggle
     * sorting,
     * 
     * @param cc The comparator used to sort with.
     * @return
     */
    protected D<JButton> makeSortDelegate(final Comparator<UserModel> cc) {
        return new D<JButton>() {
            public void call(JButton b){
                rows.removeAll();
                rows.add(createRows(sortModels(cc)));
                rows.validate();
            }
        };
    }
	
    /**
     * Sort a set of models into an ordered set.
     * 
     * @param usrs
     * @param c
     * @return
     */
	protected UserModel[] sortModels (Comparator<UserModel> c) {
	    UserModel sorted[] = users.toArray(new UserModel[users.size()]);
		Arrays.sort(sorted, c);		
		return sorted;
	}
	
	/**
	 * Create the list users view.
	 * @param users
	 * @param editResponder
	 * @param deleteResponder
	 * @return
	 */
    public JPanel view() {
        
        // nothing to show
        if(0 == users.size()) {
            return grid(grid.cell(label(
                "There are no users to list."
            )));
        }
         
        // sort the users by their id by default
        rows.add(createRows(sortModels(id_sort)));
        
        return grid(grid.cell(rows));
        
    }
    
    public JPanel createRows (UserModel[] users) 
    {
    	// allocate the rows
        GridCell[][] rows = new GridCell[users.length+2][];
        int i = 2; 
        
        // headers
        rows[0] = grid.row(
            grid.cell(label("ID")).margin(10, 0, 0, 10),
            grid.cell(label("First Name")).margin(10, 0, 0, 20),
            grid.cell(label("Last Name")).margin(10, 0, 0, 20)
        );
        
        // set the sort buttons to be the first row
        rows[1] = sort_buttons;
        
        // create the rows
        for(final UserModel user : users) {
        	rows[i++] = grid.row(
        		grid.cell(label(user.getId())),
                grid.cell(label(user.getFirstName())),
                grid.cell(label(user.getLastName())).margin(10, 0, 0, 20),
                grid.cell(grid(
                    grid.cell(button("Edit", new D<JButton>(){
            			public void call(JButton b){
            				// EDIT USER
            				edit_responder.call(user);
            			}
            		})).margin(0, 0, 0, 10),
            		grid.cell(button("Delete", new D<JButton>(){
            			public void call(JButton b){
            				if (!dialog.confirm(f, "Are you sure you want to delete this user?"))
            					return;
					
            				delete_responder.call(user);
            				dialog.alert(f, "The user has been deleted.");
            				changeProgramTab(1);
            			}
            		})).margin(0, 0, 0, 10)
        		)).anchor(1, 0, 0, 1)
        		
        	); 
        }
        
        return grid(rows);
    }
}
