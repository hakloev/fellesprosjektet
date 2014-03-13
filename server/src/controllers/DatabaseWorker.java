package controllers;

import helperclasses.JSONHandler;
import helperclasses.Request;
import helperclasses.Response;
import models.*;

import java.util.ArrayList;

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
		} else if (obj instanceof ParticipantListModel) {
			System.out.println("DatabaseWorker.handleRequest: obj instanceof ParticipantListModel: " + (obj instanceof ParticipantListModel));
			ParticipantListModel e = (ParticipantListModel) obj;
			response = new Response("participantlistmodel", "post", e);
		} else if (obj instanceof Participant) {
			System.out.println("DatabaseWorker.handleRequest: obj instanceof Participant: " + (obj instanceof Participant));
			Participant g = (Participant) obj;
			response = new Response("participant", "post", g);
		} else if (obj instanceof ParticipantStatus) {
			System.out.println("DatabaseWorker.handleRequest: obj instanceof ParticipantStatus: " + (obj instanceof ParticipantStatus));
			ParticipantStatus m = (ParticipantStatus) obj;
			response = new Response("participantstatus", "post", m);
		} else if (obj instanceof ArrayList) {
			response = new Response("participantlistmodel", "post", (ArrayList<Participant>) obj);
		} else {
			System.out.println("DatabaseWorker.handleRequest: UNEXPECTED OBJECT");
		}
		// return response object
		return response;
	}
}
