package models;

import controllers.DBconnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Employee implements DBInterface {
	

	private String username;
	private String name;
	
	public Employee(String userName,String name) {
		this.username = userName;
		this.name = name;
	}

    public String getUsername() {
		return username;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"username='" + username + '\'' +
				", name='" + name + '\'' +
				'}';
	}

	public String getName() {
		return name;
	}

	@Override
	public void initialize() {
		System.out.println("Employee.initialize");
	}

	@Override
	public void refresh() {
		System.out.println("Employee.refresh");
		Connection dbCon = DBconnection.getConnection(); // Singelton class
		try {
			String sql = "SELECT * FROM ansatt WHERE brukernavn = '" + this.username + "'";
			Statement stmt = dbCon.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				this.username = rs.getString(1);
				this.name = rs.getString(3);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void save() {
		System.out.println("Employee.save");
	}

	@Override
	public void delete() {
		System.out.println("Employee.delete");
	}
}
