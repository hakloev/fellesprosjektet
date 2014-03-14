package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Notification {
	

	@JsonProperty("notificationID")
	private int notificationID;
	@JsonProperty("userName")
	private String userName;
	@JsonProperty("appointmentID")
	private int appointmentID;
	@JsonProperty("isSeen")
	private boolean isSeen;
	@JsonProperty("type")
	private String type;
	
	@JsonCreator
	public Notification(int notificationID, String userName, int appointmentID, String type) {
		this.notificationID = notificationID;
		this.userName = userName;
		this.appointmentID = appointmentID;
		this.type = type;
		this.isSeen = false;
	}

	public void setNotificationID(int notificationID) {
		this.notificationID = notificationID;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setAppointmentID(int appointmentID) {
		this.appointmentID = appointmentID;
	}

	public void setType(String type) {
		this.type = type;
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
	
	
}
