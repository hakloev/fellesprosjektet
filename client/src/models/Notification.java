package models;

public class Notification {
	
	
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
	
	
}
