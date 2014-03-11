package controllers;

import helperclasses.Request;
import helperclasses.Response;
import models.*;

/**
 * Created by Håkon Ødegård Løvdal on 11/03/14.
 */
public class DatabaseWorker {


	public static Response handleRequest(Request request) {
		Object obj = request.parseJSON();
		if (obj instanceof Avtale) {
			// TODO
		} else if (obj instanceof Ansatt) {
			// TODO
		} else if (obj instanceof Gruppe) {
			// TODO
		} else if (obj instanceof Moterom) {
			// TODO
		} else if (obj instanceof Varsel) {
			// TODO
		} else {
			System.out.println("DatabaseWorker.handleRequest: UNEXPECTED OBJECT");
		}
		// return response object
		return null;
	}
}
