package models;

import controllers.DBconnection;

import javax.swing.DefaultListModel;
import java.sql.*;

@SuppressWarnings("serial")
public class RoomListModel extends DefaultListModel<Room> implements DBInterface {

	private int capacity;

	@Override
	public void initialize() {
		System.out.println("RoomListModel.initialize");
		Connection dbCon = DBconnection.getConnection(); // Singelton class
		try {
			String sql = "SELECT * FROM moterom WHERE maks_personer >= " + capacity + " ORDER BY maks_personer ASC";
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
		System.out.println("RoomListModel.refresh");
		this.initialize();
	}

	@Override
	public void save() {
		System.out.println("RoomListModel.save");
		// Do not add code. This model should not be sent to server
	}

	@Override
	public void delete() {
		System.out.println("RoomListModel.delete");
		// Do not add code. This model can not be deleted from server
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
}
