package models;

import org.json.simple.JSONObject;

import controllers.LogoutException;
import controllers.OutboundWorker;

public class Notification implements NetInterface {
	
	
	private int notificationID;
	private String userName;
	private int appointmentID;
	private boolean isSeen;
	private String type;
	
	
	public Notification(int notificationID, String userName, int appointmentID, String type) {
		this.notificationID = notificationID;
		this.userName = userName;
		this.appointmentID = appointmentID;
		this.type = type;
		this.isSeen = false;
	}


	public boolean isSeen() {
		return isSeen;
	}


	public void setSeen(boolean isSeen) {
		this.isSeen = isSeen;
	}


	public int getNotificationID() {
		return notificationID;
	}


	public String getUserName() {
		return userName;
	}


	public int getAppointmentID() {
		return appointmentID;
	}


	public String getType() {
		return type;
	}
	
	
	@Override
	public String toString() {
		return userName + " " + type;
	}


	@Override
	public void initialize() throws LogoutException {
		// Should be initialized as a list or pushed from server
	}

	@Override
	public void refresh() throws LogoutException {
		// Same as initialized
	}

	@SuppressWarnings("unchecked")
	@Override
	public void save() {
		JSONObject json;
        json = new JSONObject();
        json.put("request","notification");
        json.put("dbmethod","save");
        json.put("notificationID", this.notificationID);
        json.put("isseen", this.isSeen);
        OutboundWorker.sendRequest(json);
        
        // Not really interrested in response
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete() {
		JSONObject json;
        json = new JSONObject();
        json.put("request","notification");
        json.put("dbmethod","delete");
        json.put("notificationID", this.notificationID);
        OutboundWorker.sendRequest(json);
        
        // Not really interrested in response
	}
	
	
}

