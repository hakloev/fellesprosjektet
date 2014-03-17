package controllers;

import com.sun.corba.se.spi.activation._ServerStub;
import helperclasses.Request;
import helperclasses.Response;
import models.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Håkon Ødegård Løvdal on 11/03/14.
 */
public class DatabaseWorker {

	// Do handling based on request type (request.get_REQUESTTYPE())
	public static Response handleRequest(Request request) {
		JSONParser parser = new JSONParser();
		Response response = null;
		try {
			Object obj = parser.parse(request.get_JSONREQUEST());
			JSONObject jsonObject = (JSONObject) obj;
			String requestType = (String) jsonObject.get("request");
			System.out.println(requestType);
			if (requestType.equals("login")) {
				JSONArray array = (JSONArray) jsonObject.get("array");
				JSONObject responseJSON = new JSONObject();
				responseJSON.put("response", "login");
				responseJSON.put("array", login(array));
				response = new Response(responseJSON.toJSONString());
			} else if (requestType.equals("weekcalendar")) {
				String dbMethod = (String) jsonObject.get("dbmethod");
				if (dbMethod.equals("initialize")) {
					JSONObject model = (JSONObject) jsonObject.get("model");
					int weekNumber = Integer.valueOf(model.get("week").toString());
					Employee e = new Employee(model.get("employee").toString(), "navn");
					e.refresh();
					WeekCalendar weekCalendar = new WeekCalendar(e, weekNumber);
					weekCalendar.initialize();
					response = new Response(weekCalendarAsJSON(weekCalendar));
				}
			} else if (requestType.equals("appointment")) {
				String dbMethod = (String) jsonObject.get("dbmethod");
				if (dbMethod.equals("save")) {
					JSONObject model = (JSONObject) jsonObject.get("model");

				}
			} else if (requestType.equals("roomlistmodel")) {
				String dbMethod = (String) jsonObject.get("dbmethod");
				if (dbMethod.equals("initialize")) {
					RoomListModel roomListModel = new RoomListModel();
					roomListModel.initialize();
					response = new Response(roomListModelAsJSON(roomListModel));
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return response;
	}

	private static String roomListModelAsJSON(RoomListModel roomListModel) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("response", "roomlistmodel");
		jsonObject.put("dbmethod", "initialize");
		JSONArray array = new JSONArray();
		for (int i = 0; i < roomListModel.size(); i++) {
			JSONObject room = new JSONObject();
			room.put("romkode", roomListModel.get(i).getRoomCode());
			room.put("kapasitet", roomListModel.get(i).getCapacity());
			array.add(room);
		}
		jsonObject.put("rooms", array);
		return jsonObject.toJSONString();
	}

	private static String weekCalendarAsJSON(WeekCalendar weekCalendar) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("response", "weekcalendar");
		jsonObject.put("dbmethod", "initialize");
		JSONArray array = new JSONArray();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Appointment a : weekCalendar.getAppointmentList()) {
			JSONObject appointment = new JSONObject();
			appointment.put("appointmentID", a.getAppointmentID());
			appointment.put("description", a.getDescription());
			if (a.getRoom() != null) {
				appointment.put("location", a.getRoom().getRoomCode());
			}
			appointment.put("locationText", a.getLocationText());
			appointment.put("startDateTime", sdf.format(a.getStartDateTime().getTime()));
			appointment.put("endDateTime", sdf.format(a.getEndDateTime().getTime()));
			array.add(appointment);
		}
		jsonObject.put("appointments", array);
		return jsonObject.toJSONString();
	}

	private static JSONArray login(JSONArray array) {
		Connection dbCon = DBconnection.getConnection(); // Singelton class
		JSONArray userArray = new JSONArray();
		try {
			String sql = "SELECT * FROM ansatt WHERE brukernavn = '" + array.get(0) + "'";
			Statement stmt = dbCon.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if (rs.getString(2).equals(array.get(1)) && rs.getString(1).equals(array.get(0))) {
					userArray.add(rs.getString(1));
					userArray.add(rs.getString(3));
				}
			}
			stmt.close();
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return userArray;
	}
}
