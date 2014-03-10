package controllers;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBconnection {
	
	private String URL = "jdbc:mysql://mysql.login.ntnu.no/trulsmp_fpgruppe7";
	private String USERNAME = "trulsmp";
	private String PASSWORD = "grp07";
	private Connection dbCon = null;
	
	
	public DBconnection() {
		Statement st = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			dbCon = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			st = dbCon.createStatement();
			rs = st.executeQuery("SELECT VERSION()");
			
			if (rs.next()) {
				System.out.println(rs.getString(1));
			}
			
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}


	public Connection getConnection() {
		return dbCon;
	}
	
	
	public void closeConnection() {
		try {
			if (dbCon != null) {
				dbCon.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
