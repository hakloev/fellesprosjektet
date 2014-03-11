package controllers;

/**
 * Created by Håkon Ødegård Løvdal on 11/03/14.
 */
public class DBQuery implements DBMethods {

	public DBQuery() {
		// all queries happen from here
		DBconnection.getConnection(); // example, we will get the db connection this way
	}

}
