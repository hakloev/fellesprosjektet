package controllers;

import helperclasses.JSONHandler;
import helperclasses.Login;
import helperclasses.Request;
import helperclasses.Response;
import models.*;

import java.sql.*;
import helperclasses.JSONHandler;

/**
 * Created by Håkon Ødegård Løvdal on 11/03/14.
 */
public class DatabaseWorker {

	// Do handling based on request type (request.get_REQUESTTYPE())
	public static Response handleRequest(Request request) {
		Object obj = JSONHandler.parseJSON(request);
		Response response = null;
		if (obj instanceof Appointment) {
			System.out.println("DatabaseWorker.handleRequest: obj instanceof Appointment: " + (obj instanceof Appointment));
			Appointment a = (Appointment) obj;
			response = new Response("appointment", "post", a);
		} else if (obj instanceof Participant) {
			System.out.println("DatabaseWorker.handleRequest: obj instanceof Participant: " + (obj instanceof Participant));
			Participant g = (Participant) obj;
			response = new Response("participant", "post", g);
		} else if (obj instanceof Employee) {
			System.out.println("DatabaseWorker.handleRequest: obj instance Participant: " + (obj instanceof Employee));
			Employee e = (Employee) obj;
			response = new Response("employee", "post", e);
		} else if (obj instanceof Login) {
			System.out.println("DatabaseWorker.handleRequest: obj instanceof Login: " + (obj instanceof Login));
			Employee e = loginUser((Login)obj);
			if (e != null) {
				response = new Response("login", "null", e);
			} else {
				response = new Response("login", "null", null);
			}
		} else if (obj == null) {
			System.out.println("DatabaseWorker.handleRequest: INIT CLIENT");
			response = initClient();
		} else {
			System.out.println("DatabaseWorker.handleRequest: UNEXPECTED OBJECT");
		}
		// return response object
		return response;
	}

	public static Response initClient() {
		RoomListModel rooms = new RoomListModel();
		rooms.initialize();
		String roomsJSON = JSONHandler.createJSON(rooms);

		GroupListModel groups = new GroupListModel();
		groups.initialize();
		String groupsJSON = JSONHandler.createJSON(groups);

		EmployeeListModel employees = new EmployeeListModel();
		employees.initialize();
		String employeesJSON = JSONHandler.createJSON(employees);

		return new Response("init", "null", roomsJSON + groupsJSON + employeesJSON);
	}

	private static Employee loginUser(Login l) {
		Connection dbCon = DBconnection.getConnection(); // Singelton class
		Employee e = null;
		try {
			String sql = "SELECT * FROM ansatt WHERE brukernavn = '" + l.getUser() + "'";
			Statement stmt = dbCon.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if (rs.getMetaData().getColumnCount() != 0) {
					if (l.checkLogin(rs.getObject(1).toString(), rs.getObject(2).toString())) {
						e = new Employee(rs.getObject(1).toString(), rs.getObject(3).toString());
					}
				}
			}
			stmt.close();
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return e;
	}

}
