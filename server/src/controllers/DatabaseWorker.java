package controllers;

import helperclasses.JSONHandler;
import helperclasses.Request;
import helperclasses.Response;
import models.*;

/**
 * Created by Håkon Ødegård Løvdal on 11/03/14.
 */
public class DatabaseWorker {


	public static Response handleRequest(Request request) {
		Object obj = JSONHandler.parseJSON(request);
		Response response = null;
		if (obj instanceof Appointment) {
			// TODO
		} else if (obj instanceof Employee) {
			System.out.println("obj instanceof Employee: " + (obj instanceof Employee));
			Employee e = (Employee) obj;
			e.setName("Rudolf Blodstrupmoen");
			response = new Response("employee", "post", e);
			// Do handling based on request type (request.get_REQUESTTYPE())
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
		return response;
	}
}
