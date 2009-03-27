package org.uwogarage.views.admin;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.uwogarage.models.ModelSet;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.gui.GridCell;
import org.uwogarage.views.TabView;

/**
 * @version $Id$
 */
public class ListUsersView extends TabView {
	
    protected Comparator<UserModel> first_name_sort = new Comparator<UserModel>(){
    	public int compare(UserModel a, UserModel b) {
    		return a.getFirstName().compareTo(b.getFirstName());
		}
    };
    
    protected Comparator<UserModel> last_name_sort = new Comparator<UserModel>(){
    	public int compare(UserModel a, UserModel b) {
    		return a.getLastName().compareTo(b.getLastName());
		}
    };
    
    protected Comparator<UserModel> id_sort = new Comparator<UserModel>(){
    	public int compare(UserModel a, UserModel b) {
    		return a.getId().compareTo(b.getId());
		}
    };
	
    protected final JPanel rows = new JPanel();
    protected ModelSet<UserModel> users;
    protected D<UserModel> edit_responder,
                           delete_responder;
	
	/**
	 * Invert a sort comparator.
	 * @author petergoodman
	 */
	class InverseSort<T> implements Comparator<T> {
		
		protected Comparator<T> temp;
		
		public InverseSort(Comparator<T> c) {
			temp = c;
		}
		
		public int compare(T o1, T o2) {
			return -1 * temp.compare(o1, o2);
		}
		
	}
	
	/**
	 * Constructor.
	 */
	public ListUsersView(ModelSet<UserModel> u, D<UserModel> e, D<UserModel> d) {
	    users = u;
	    edit_responder = e;
	    delete_responder = d;
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
	protected Set<UserModel> sortModels (Comparator<UserModel> c) {
		TreeSet<UserModel> sorted = new TreeSet<UserModel>(c);
		
		for (UserModel user : users) {
			sorted.add(user);
		}
		
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
         
        rows.add(createRows(users));
        return grid( 
        		grid.row(
    				grid.cell(grid(
    					grid.cell(button("/\\", makeSortDelegate(
    					    first_name_sort
    					))), 
    					grid.cell(button("\\/", makeSortDelegate(
    					    new InverseSort<UserModel>(first_name_sort)
    					)))
    				)),
    				grid.cell(grid(
    					grid.cell(button("/\\", makeSortDelegate(
    					    last_name_sort
    					))), 
    					grid.cell(button("\\/", makeSortDelegate(
    					    new InverseSort<UserModel>(last_name_sort)
    					)))
    				)),
    				grid.cell(grid(
    					grid.cell(button("/\\", makeSortDelegate(id_sort))), 
    					grid.cell(button("\\/", makeSortDelegate(
    					    new InverseSort<UserModel>(id_sort)
    					)))
    				))
    			),
        		grid.row(grid.cell(rows)));
        
    }
    
    public JPanel createRows (Set<UserModel> users) 
    {
    	// allocate the rows
        GridCell[][] rows = new GridCell[users.size()][];
        int i = 0; 
                
        // create the rows
        for(final UserModel user : users) {
        	rows[i++] = grid.row(
        		
        		grid.cell(grid(
        				grid.cell(label(user.getId())).margin(10, 0, 0, 10), 
                		grid.cell(label(user.getFirstName())).margin(10, 0, 0, 10),
                		grid.cell(label(user.getLastName())).margin(10, 0, 0, 10),
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
