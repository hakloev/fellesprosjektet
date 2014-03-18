package models;

import controllers.DBconnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Room implements DBInterface {
	
	private String roomCode;
	private int capacity;
	
	public Room(String roomCode, int capacity) {
		this.roomCode = roomCode;
		this.capacity = capacity;
	}

	public String getRoomCode() {
		return roomCode;
	}
	
	
	public int getCapacity() {
		return capacity;
	}
	
	
	public String toString() {
		return roomCode + " : " + capacity + " personer";
	}

	@Override
	public void initialize() {

	}

	@Override
	public void refresh() {
		Connection dbCon = DBconnection.getConnection(); // Singelton class
		try {
			String sql = "SELECT * FROM moterom WHERE romkode = '" + this.roomCode + "'";
			Statement stmt = dbCon.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				this.roomCode = rs.getString(1);
				this.capacity = rs.getInt(2);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void save() {

	}

	@Override
	public void delete() {

	}
}
