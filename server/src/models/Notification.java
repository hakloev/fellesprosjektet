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
}
