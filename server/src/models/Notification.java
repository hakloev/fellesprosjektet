package models;

import controllers.DBconnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Notification implements DBInterface {

	private int notificationID;
	private String userName;
	private int appointmentID;
	private int isSeen;
	private String type;
	
	public Notification(int notificationID, String userName, int appointmentID, String type, int isSeen) {
		this.notificationID = notificationID;
		this.userName = userName;
		this.appointmentID = appointmentID;
		this.type = type;
		this.isSeen = isSeen;
	}

	public int getNotificationID() {
		return notificationID;
	}

	public void setNotificationID(int notificationID) {
		this.notificationID = notificationID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAppointmentID() {
		return appointmentID;
	}

	public void setAppointmentID(int appointmentID) {
		this.appointmentID = appointmentID;
	}

	public int isSeen() {
		return isSeen;
	}

	public void setSeen(int isSeen) {
		this.isSeen = isSeen;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void initialize() {

	}

	@Override
	public void refresh() {

	}

	@Override
	public void save() {
		Connection dbCon = DBconnection.getConnection();
		try {
			Statement stmt = dbCon.createStatement();
			String sql = "UPDATE varsel SET ersett = '" + isSeen + "' WHERE varselid = " + this.getNotificationID();
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delete() {

	}
}
