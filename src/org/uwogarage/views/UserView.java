package org.uwogarage.views;

/**
 * The UserView class handles creating the views for the UserModels
 *
 * @author Nate Smith
 * @version Version 0.1
 */

public class UserView implements View {
	
	/**
	 * Constructor for UserView
	 */
	public UserView()
	{
	}
	
	/**
	 * This method creates the view for adding a UserModel to the system
	 * @param f the frame to display the view in
	 * @return the data inputted into the view
	 */
	public Response<UserModel> add(JFrame f) {}
	/**
	 * This method creates the view for editing a UserModel to the system
	 * @param f the frame to display the view in
	 * @return the data inputted into the view
	 */
    public Response<UserModel> edit(JFrame f) {}
	/**
	 * This method creates the view for adding a UserModel to the system
	 * @param f the frame to display the view in
	 * @return the data inputted into the view (a delete confirmation)
	 */
    public Response<UserModel> delete(JFrame f) {}
	/**
	 * This method creates a view for listing some UserModels
	 * @param f the frame to display the view in
	 * @param objs the UserModels to list
	 */
	public void list(JFrame f, Collection<UserModel> objs) {}
	/**
	 * This method creates a view for logging into the system
	 * @param f the frame to display the view in
	 * @return the data inputted into the view
	 */
	public Response<UserModel> login(JFrame f) {}
	/**
	 * This method creates a view for updating a user's password
	 * @param f the frame to display the view in
	 * @return the data inputted into the view
	 */
	public Response<UserModel> updatePassword(JFrame f) {}
	/**
	 * This method creates a view for selecting a theme
	 * @param f the frame to display the view in
	 * @return the data inputted into the view (theme choice)
	 */
	public Response<UserModel> selectTheme(JFrame f) {}
}
