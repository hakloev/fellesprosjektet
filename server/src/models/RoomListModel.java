package models;

import controllers.DBconnection;

import javax.swing.DefaultListModel;
import java.sql.*;

@SuppressWarnings("serial")
public class RoomListModel extends DefaultListModel<Room> implements DBInterface {
	
	@Override
	public void initialize() {
		Connection dbCon = DBconnection.getConnection(); // Singelton class
		try {
			String sql = "SELECT * FROM moterom";
			Statement stmt = dbCon.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				this.addElement(new Room(rs.getObject(1).toString(), Integer.valueOf(rs.getObject(2).toString())));
			}
			stmt.close();
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void refresh() {
		this.initialize();
	}

	@Override
	public void save() {
		// Do not add code. This model should not be sent to server
	}

	@Override
	public void delete() {
		// Do not add code. This model can not be deleted from server
	}

}
