package controllers;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBconnection {
	
	private static String URL = "jdbc:mysql://mysql.stud.ntnu.no/trulsmp_fpgruppe7";
	private static String USERNAME = "trulsmp";
	private static String PASSWORD = "grp07";

	private static Connection dbCon = null;

	/**
	 * Implemented as singelton-class
	 */
	public DBconnection() {
		// SINGELTON CLASS
	}


	public static Connection getConnection() {
		if (dbCon == null) {
			createConnection();
		}
		return dbCon;
	}

	public static Connection createConnection() {
		if (dbCon == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				dbCon = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return dbCon;
	}
	
	
	public static void closeConnection() {
		try {
			if (dbCon != null) {
				dbCon.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
