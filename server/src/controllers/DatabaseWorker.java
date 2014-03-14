package controllers;

import helperclasses.JSONHandler;
import helperclasses.Login;
import helperclasses.Request;
import helperclasses.Response;
import models.*;

import java.sql.*;
import java.util.ArrayList;

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
			g.setName("TRRRRULLLSS");
			System.out.println(g.toString());
			response = new Response("participant", "post", g);
		} else if (obj instanceof Employee) {
			System.out.println("DatabaseWorker.handleRequest: obj instance Participant: " + (obj instanceof Employee));
			Employee e = (Employee) obj;
			response = new Response("employee", "post", e);
		} else if (obj instanceof ArrayList) { // DETTE ER STYGT, IKKE BRA KODE
			for (int i = 0; i < ((ArrayList) obj).size(); i++) {
				insertParticipant((Participant)((ArrayList) obj).get(i));
			}
			response = new Response("participantlistmodel", "post", (ArrayList<Participant>) obj);
		} else if (obj instanceof Login) {
			System.out.println("DatabaseWorker.handleRequest: obj instanceof Login: " + (obj instanceof Login));
			Employee e = loginUser((Login)obj);
			if (e != null) {
				response = new Response("login", "null", e);
			} else {
				response = new Response("login", "null", null);
			}

		} else {
			System.out.println("DatabaseWorker.handleRequest: UNEXPECTED OBJECT");
		}
		// return response object
		return response;
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

	public static void insertParticipant(Participant p) {
		Connection dbCon = DBconnection.getConnection(); // Singelton class
		try {
			String sql = "INSERT INTO deltager VALUES ('" + p.getUserName() + "', 1, '" +
					p.getParticipantStatus() + "', null, 1)";
			PreparedStatement stmt = dbCon.prepareStatement(sql);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void updateParticipant(Participant p) {

	}

	public static void deleteParticipant(Participant p) {

	}

	public static  void getAllParticipants() {
		Connection dbCon = DBconnection.getConnection();
		String sql = "SELECT * FROM deltager";
	}

	public static void getAllAppointments() {

	}
}
