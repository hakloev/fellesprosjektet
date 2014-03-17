package models;

public interface NetInterface {

	/**
	 * Initializes this object with data from server.
	 * Always use refresh if this object has already been initialized
	 */
	public void initialize();
	
	/**
	 * Refreshes this object from the server
	 */
	public void refresh();
	
	/**
	 * Saves this object to the server
	 */
	public void save();
	
	/**
	 * Deletes this object from the server
	 */
	public void delete();

}
