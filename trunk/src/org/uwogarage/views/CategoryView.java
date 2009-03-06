package org.uwogarage.views;

/**
 * The CategoryView class handles creating the views for the CategoryModels
 *
 * @author Nate Smith
 * @version Version 0.1
 */

public class CategoryView implements View {
	
	/**
	 * Constructor for CategoryView
	 */
	public CategoryView()
	{
	}
	
	/**
	 * This method creates the view for adding a CategoryModel to the system
	 * @param f the frame to display the view in
	 * @return the data inputted into the view
	 */
	public Response<CategoryModel> add(JFrame f) {}
	/**
	 * This method creates the view for editing a CategoryModel to the system
	 * @param f the frame to display the view in
	 * @return the data inputted into the view
	 */
    public Response<CategoryModel> edit(JFrame f) {}
	/**
	 * This method creates the view for adding a CategoryModel to the system
	 * @param f the frame to display the view in
	 * @return the data inputted into the view (a delete confirmation)
	 */
    public Response<CategoryModel> delete(JFrame f) {}
	/**
	 * This method creates a view for listing some CategoryModels
	 * @param f the frame to display the view in
	 * @param objs the CategoryModels to list
	 */
	public void list(JFrame f, Collection<CategoryModel> objs) {}
}
