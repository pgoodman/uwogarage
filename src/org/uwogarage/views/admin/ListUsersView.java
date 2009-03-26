package org.uwogarage.views.admin;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.uwogarage.models.ModelSet;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.functional.D;
import org.uwogarage.util.gui.GridCell;
import org.uwogarage.util.gui.SimpleGui.dialog;
import org.uwogarage.util.gui.SimpleGui.grid;
import org.uwogarage.views.TabView;
import org.uwogarage.views.View;

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
   
    
	class SortButtonDelegate extends D<JButton>{
		
		Set<UserModel> uu;
		Comparator<UserModel> cc; 
		D<UserModel> ee; 
		D<UserModel> dd;
		
		public SortButtonDelegate (Set<UserModel> u, Comparator<UserModel> c, 
				D<UserModel> e, D<UserModel> d){
			uu = u;
			cc = c;
			ee = e;
			dd = d;
			
		}
		public void call(JButton b){
			// SORT FIRST NAME
				rows.removeAll();
				rows.add(createRows(sortModels(uu, cc), ee, dd));
				rows.validate();
			}
		}
	// This class will reverse the sorting order of the set
	class InverseSort implements Comparator<UserModel> {
		
		protected Comparator<UserModel> temp;
		
		public InverseSort(Comparator<UserModel> c) {
			temp = c;
		}
		
		public int compare(UserModel o1, UserModel o2) {
			return -1 * temp.compare(o1, o2);
		}
		
	}
	
	// returns sorted set
	protected Set<UserModel> sortModels (Set<UserModel> usrs, Comparator<UserModel> c) {
		TreeSet<UserModel> sorted = new TreeSet<UserModel>(c);
		
		for (UserModel user : usrs) {
			sorted.add(user);
		}
		
		return sorted;
	}
	
    public JPanel view(final ModelSet<UserModel> users, final D<UserModel> editResponder, final D<UserModel> deleteResponder) {
    	

        
        /*SortedSet set_fname = new TreeSet(first_name_sort);
        SortedSet set_lname = new TreeSet(last_name_sort);
        SortedSet set_id = new TreeSet(id_sort);
        
        SortedSet set_fname_inv = new TreeSet(new InverseSort(first_name_sort));
        SortedSet set_lname_inv = new TreeSet(new InverseSort(last_name_sort));
        SortedSet set_id_inv = new TreeSet(new InverseSort(id_sort));*/
        
        // nothing to show
        if(0 == users.size()) {
            return grid(grid.cell(label(
                "There are no users to list."
            )));
        }
         
        rows.add(createRows(users, editResponder, deleteResponder));
        return grid( 
        		grid.row(
    				grid.cell(grid(
    					grid.cell(button("/\\", new SortButtonDelegate(users, first_name_sort, editResponder, deleteResponder))), 
    					grid.cell(button("\\/", new SortButtonDelegate(users, new InverseSort(first_name_sort), editResponder, deleteResponder)))
    				)),
    				grid.cell(grid(
    					grid.cell(button("/\\", new SortButtonDelegate(users, last_name_sort, editResponder, deleteResponder))), 
    					grid.cell(button("\\/", new SortButtonDelegate(users, new InverseSort(last_name_sort), editResponder, deleteResponder)))
    				)),
    				grid.cell(grid(
    					grid.cell(button("/\\", new SortButtonDelegate(users, id_sort, editResponder, deleteResponder))), 
    					grid.cell(button("\\/", new SortButtonDelegate(users, new InverseSort(id_sort), editResponder, deleteResponder)))
    				))
    			),
        		grid.row(grid.cell(rows)));
        
    }
    
    public JPanel createRows (Set<UserModel> users, final D<UserModel> editResponder, final D<UserModel> deleteResponder) 
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
                				editResponder.call(user);
                			}
                		})).margin(0, 0, 0, 10),
                		grid.cell(button("Delete", new D<JButton>(){
                			public void call(JButton b){
                				if (!dialog.confirm(f, "Are you sure you want to delete this user?"))
                					return;
    					
                				deleteResponder.call(user);
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
