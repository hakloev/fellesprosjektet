package models;

import controllers.LogoutException;

public interface NetInterface {

	/**
	 * Initializes this object with data from server.
	 * Always use refresh if this object has already been initialized
	 * @throws LogoutException 
	 */
	public void initialize() throws LogoutException;
	
	/**
	 * Refreshes this object from the server
	 * @throws LogoutException 
	 */
	public void refresh() throws LogoutException;
	
	/**
	 * Saves this object to the server
	 */
	public void save();
	
	/**
	 * Deletes this object from the server
	 */
	public void delete();

}
