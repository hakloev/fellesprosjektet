package controllers;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Kristian Volden and Håkon Ødegård Løvdal March 2014
 *
 * Implemented as a singelton class for simplicity
 *
 */
public class DBconnection {
	
	private static String URL = "jdbc:mysql://mysql.stud.ntnu.no/trulsmp_fpgruppe7";
	private static String USERNAME = "trulsmp";
	private static String PASSWORD = "grp07";

	private static Connection dbCon = null;

	public static Connection getConnection() {
		System.out.println("DBConnection.getConnection: REQUESTING DBCONNECTION");
		if (dbCon == null) {
			System.out.println("DBConnection.getConnection: NOT INITIALIZED, INITIALIZING");
			createConnection();
		}
		System.out.println("DBConnection.getConnection: INITIALIZED, RETURNING CONNECTION");
		return dbCon;
	}

	public static Connection createConnection() {
		if (dbCon == null) {
			System.out.println("DBConnection.createConnection: TRYING TO CREATE CONNECTION");
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				dbCon = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		System.out.println("DBConnection.createConnection: CREATED CONNECTION SUCCESSFULLY");
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
