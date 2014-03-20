package models;

import controllers.DBconnection;

import javax.swing.DefaultListModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@SuppressWarnings("serial")
public class NotificationListModel extends DefaultListModel<Notification> implements DBInterface {

	private String username;
	
	@Override
	public void initialize() {
		System.out.println("NotificationListModel.initialize");
		Connection dbCon = DBconnection.getConnection(); // Singelton class
		try {
			String sql = "SELECT * FROM varsel WHERE brukernavn = '" + this.username + "'";
			Statement stmt = dbCon.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Notification n = new Notification(rs.getInt(1), this.username, rs.getInt(4), rs.getString(6), rs.getInt(2));
				this.addElement(n);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void refresh() {
		System.out.println("NotificationListModel.refresh");
		this.initialize();
	}

	@Override
	public void save() {
		System.out.println("NotificationListModel.save");
	}

	@Override
	public void delete() {
		System.out.println("NotificationListModel.delete");
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
