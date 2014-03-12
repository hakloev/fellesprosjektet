package controllers;

import helperclasses.JSONHandler;
import helperclasses.Request;
import helperclasses.Response;
import models.*;

/**
 * Created by Håkon Ødegård Løvdal on 11/03/14.
 */
public class DatabaseWorker {

	// Do handling based on request type (request.get_REQUESTTYPE())
	public static Response handleRequest(Request request) {
		Object obj = JSONHandler.parseJSON(request);
		Response response = null;
		if (obj instanceof Appointment) {
			System.out.println("DatabaseWorker.handleRequest: obj instanceof Appointment: " + (obj instanceof Appointment));
			Appointment a = (Appointment) obj;
			response = new Response("appointment", "post", a);
		} else if (obj instanceof Employee) {
			System.out.println("DatabaseWorker.handleRequest: obj instanceof Employee: " + (obj instanceof Employee));
			Employee e = (Employee) obj;
			e.setName("Rudolf Blodstrupmoen");
			response = new Response("employee", "post", e);
		} else if (obj instanceof Groupname) {
			System.out.println("DatabaseWorker.handleRequest: obj instanceof Groupname: " + (obj instanceof Groupname));
			Groupname g = (Groupname) obj;
			response = new Response("groupname", "post", g);
		} else if (obj instanceof MeetingRoom) {
			System.out.println("DatabaseWorker.handleRequest: obj instanceof MeetingRoom: " + (obj instanceof MeetingRoom));
			MeetingRoom m = (MeetingRoom) obj;
			response = new Response("meetingroom", "post", m);
		} else if (obj instanceof Notification) {
			System.out.println("DatabaseWorker.handleRequest: obj instanceof Notification: " + (obj instanceof Notification));
			Notification n = (Notification) obj;
			response = new Response("notification", "post", n);
		} else {
			System.out.println("DatabaseWorker.handleRequest: UNEXPECTED OBJECT");
		}
		// return response object
		return response;
	}
}
