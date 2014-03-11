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
		if (obj instanceof Appointment) {
			// TODO
		} else if (obj instanceof Employee) {
			// TODO
		} else if (obj instanceof Groupname) {
			// TODO
		} else if (obj instanceof MeetingRoom) {
			// TODO
		} else if (obj instanceof Notification) {
			// TODO
		} else {
			System.out.println("DatabaseWorker.handleRequest: UNEXPECTED OBJECT");
		}
		// return response object
		return null;
	}
}
