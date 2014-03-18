package models;

import controllers.DBconnection;

import javax.swing.DefaultListModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@SuppressWarnings("serial")
public class EmailListModel extends DefaultListModel<String> implements DBInterface {

	private int appointmentID;
	
	/**
	 * Create a new empty list
	 */
	public EmailListModel() {
		super();
	}
	
	
	/**
	 * Copy constructor
	 * 
	 * @param oldList
	 */
	public EmailListModel(EmailListModel oldList) {
		super();
		for (Object email : oldList.toArray()){
			this.addElement((String) email);
		}
	}
	
	
	@Override
	public void addElement(String email){
		if (! this.contains(email)){
			super.addElement(email);
		}
	}


	@Override
	public void initialize() {
		System.out.println("EmailListModel.initialize");
		Connection dbCon = DBconnection.getConnection(); // Singelton class
		try {
			String sql = "SELECT * FROM email WHERE avtaleid = " + this.appointmentID;
			Statement stmt = dbCon.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				this.addElement(rs.getString(1));
			}
			stmt.close();
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void refresh() {
		System.out.println("EmailListModel.refresh");
	}

	@Override
	public void save() {
		System.out.println("EmailListModel.save");
		Connection dbCon = DBconnection.getConnection();
		try {
			Statement stmt = dbCon.createStatement();
			String sql;
			for (int i = 0; i < this.size(); i++) {
				sql = "INSERT INTO email VALUES ('" + this.get(i).toString() + "', " + this.appointmentID + ")";
				System.out.println(sql);
				stmt.executeUpdate(sql);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete() {
		System.out.println("EmailListModel.delete");
	}

	public int getAppointmentID() {
		return appointmentID;
	}

	public void setAppointmentID(int appointmentID) {
		this.appointmentID = appointmentID;
	}
}
