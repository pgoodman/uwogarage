package org.uwogarage.views;

/**
 * The GarageSaleView class handles creating the views for the GarageSaleModels
 *
 * @author Nate Smith
 * @version Version 0.1
 */

public class GarageSaleView implements View {
	
	/**
	 * Constructor for GarageSaleView
	 */
	public GarageSaleView()
	{
	}
	
	/**
	 * This method creates the view for adding a GarageSaleModel to the system
	 * @param f the frame to display the view in
	 * @return the data inputted into the view
	 */
	public Response<GarageSaleModel> add(JFrame f) {}
	/**
	 * This method creates the view for editing a GarageSaleModel to the system
	 * @param f the frame to display the view in
	 * @return the data inputted into the view
	 */
    public Response<GarageSaleModel> edit(JFrame f) {}
	/**
	 * This method creates the view for adding a GarageSaleModel to the system
	 * @param f the frame to display the view in
	 * @return the data inputted into the view (a delete confirmation)
	 */
    public Response<GarageSaleModel> delete(JFrame f) {}
	/**
	 * This method creates a view for listing some GarageSaleModels
	 * @param f the frame to display the view in
	 * @param objs the GarageSaleModels to list
	 */
	public void list(JFrame f, Collection<GarageSaleModel> objs) {}
}
