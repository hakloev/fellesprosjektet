package controllers;

import helperclasses.GMail;
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
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Håkon Ødegård Løvdal on 11/03/14.
 *
 * This class handles all requests from a client, and calls the methods
 * that make queries with the database.
 * It parses all incoming requests and create responses.
 * 
 */
public class DatabaseWorker {

	private static ClientHandler clientHandler;

	// Do handling based on request type (request.get_REQUESTTYPE())
	public static Response handleRequest(Request request) {
		System.out.println("DatabaseWorker.handleRequest: PARSING REQUEST");
		JSONParser parser = new JSONParser();
		Response response = null;
		try {
			Object obj = parser.parse(request.get_JSONREQUEST());
			JSONObject jsonObject = (JSONObject) obj;
			String requestType = (String) jsonObject.get("request");
			if (requestType.equals("login")) {
				System.out.println("DatabaseWorker.handleRequest: LOGIN");
				JSONArray array = (JSONArray) jsonObject.get("array");
				JSONObject responseJSON = new JSONObject();
				responseJSON.put("response", "login");
				responseJSON.put("array", login(array));
				response = new Response(responseJSON.toJSONString());
			} else if (requestType.equals("weekcalendar")) {
				System.out.println("DatabaseWorker.handleRequest: WEEKCALENDAR");
				String dbMethod = (String) jsonObject.get("dbmethod");
				if (dbMethod.equals("initialize")) {
					System.out.println("DatabaseWorker.handleRequest: WEEKCALENDAR INITIALIZE");
					JSONObject model = (JSONObject) jsonObject.get("model");
					int weekNumber = Integer.valueOf(model.get("week").toString());
					Employee e = new Employee(model.get("employee").toString(), "navn");
					e.refresh();
					WeekCalendar weekCalendar = new WeekCalendar(e, weekNumber);
					weekCalendar.initialize();
					response = new Response(weekCalendarAsJSON(weekCalendar));
				}
			} else if (requestType.equals("appointment")) {
				System.out.println("DatabaseWorker.handleRequest: APPOINTMENT");
				String dbMethod = (String) jsonObject.get("dbmethod");
				if (dbMethod.equals("save")) {
					System.out.println("DatabaseWorker.handleRequest: APPOINTMENT SAVE");
					JSONObject model = (JSONObject) jsonObject.get("model");
					response = new Response(createAppointment(model));
				} else if (dbMethod.equals("initialize")) {
					System.out.println("DatabaseWorker.handleRequest: APPOINTMENT INITIALIZE");
					int appointmentID = Integer.valueOf(jsonObject.get("appointmentID").toString());
					Appointment a = new Appointment();
					a.setAppointmentID(appointmentID);
					a.initialize();
					response = new Response(sendAppointment(a));
				} else if (dbMethod.equals("delete")) {
					System.out.println("DatabaseWorker.handleRequest: APPOINTMENT DELETE");
					int appointmentID = Integer.valueOf(jsonObject.get("appointmentID").toString());
					Appointment a = new Appointment();
					a.setAppointmentID(appointmentID);
					a.delete();
					response = new Response(deleteAppointment(a));
				}
			} else if (requestType.equals("roomlistmodel")) {
				System.out.println("DatabaseWorker.handleRequest: ROOMLISTMODEL");
				String dbMethod = (String) jsonObject.get("dbmethod");
				if (dbMethod.equals("initialize")) {
					System.out.println("DatabaseWorker.handleRequest: ROOMLISTMODEL INITIALIZE");
					RoomListModel roomListModel = new RoomListModel();
					roomListModel.setCapacity(Integer.valueOf(jsonObject.get("capacity").toString()));
					roomListModel.setStart((String) jsonObject.get("startdatetime"));
					roomListModel.setSlutt((String) jsonObject.get("enddatetime"));
					roomListModel.initialize();
					response = new Response(roomListModelAsJSON(roomListModel));
				}
			} else if (requestType.equals("grouplistmodel")) {
				System.out.println("DatabaseWorker.handleRequest: GROUPLISTMODEL");
				String dbMethod = (String) jsonObject.get("dbmethod");
				if (dbMethod.equals("initialize")) {
					System.out.println("DatabaseWorker.handleRequest: GROUPLISTMODEL INITIALIZE");
					GroupListModel groupListModel = new GroupListModel();
					groupListModel.initialize();
					response = new Response(groupListModelAsJSON(groupListModel));
				}
			} else if (requestType.equals("employeelistmodel")) {
				System.out.println("DatabaseWorker.handleRequest: EMPLOYEELISTMODEL");
				String dbMethod = (String) jsonObject.get("dbmethod");
				if (dbMethod.equals("initialize")) {
					System.out.println("DatabaseWorker.handleRequest: EMPLOYEELISTMODEL INITALIZE");
					EmployeeListModel employeeListModel = new EmployeeListModel();
					employeeListModel.initialize();
					response = new Response(employeeListModelAsJSON(employeeListModel));
				}
			} else if (requestType.equals("notificationlistmodel")) {
				System.out.println("DatabaseWorker.handleRequest: NOTIFICATIONLISTMODEL");
				String dbMethod = (String) jsonObject.get("dbmethod");
				if (dbMethod.equals("initialize")) {
					System.out.println("DatabaseWorker.handleRequest: NOTIFICATIONLISTMODEL INITIALIZE");
					NotificationListModel notificationListModel = new NotificationListModel();
					notificationListModel.setUsername((String) jsonObject.get("employee"));
					notificationListModel.initialize();
					response = new Response(notificationListModelAsJSON(notificationListModel));
				}
			} else if (requestType.equals("notification")) {
				System.out.println("DatabaseWorker.handleRequest: NOTIFICATION");
				String dbMethod = (String) jsonObject.get("dbmethod");
				if (dbMethod.equals("save")) {
					System.out.println("DatabaseWorker.handleRequest: NOTIFICATION SAVE");
					Notification n = new Notification(Integer.valueOf(jsonObject.get("notificationID").toString()), null, 0, null,  Integer.valueOf(jsonObject.get("isseen").toString()));
					n.save();
					response = new Response(notificationSave(n));
				}
			} else if (requestType.equals("participant")) {
				System.out.println("DatabaseWorker.handleRequest: PARTICIPANT");
				String dbMethod = (String) jsonObject.get("dbmethod");
				if (dbMethod.equals("save")) {
					System.out.println("DatabaseWorker.handleRequest: PARTICIPANT SAVE");
					Participant p = new Participant((String) jsonObject.get("username"), null, ParticipantStatus.valueOf((String) jsonObject.get("participantstatus")),
							Boolean.valueOf((String) jsonObject.get("showInCalendar")), Integer.valueOf((String) jsonObject.get("appointmentID")));
					p.save();
					response = new Response(saveParticipant(p));
				} else if (dbMethod.equals("delete")) {
					System.out.println("DatabaseWorker.handleRequest: PARTICIPANT DELETE");
					Participant p = new Participant((String) jsonObject.get("username"), null, null, false, Integer.valueOf(jsonObject.get("appointmentID").toString()));
					p.delete();
					response = new Response(deleteParticipantFromAppointment(p));
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return response;
	}

	private static String notificationSave(Notification n) {
		JSONObject jsonObject =  new JSONObject();
		jsonObject.put("response", "notification");
		jsonObject.put("dbmethod", "save");
		jsonObject.put("notification", n.getNotificationID());
		return jsonObject.toJSONString();
	}

	private static String saveParticipant(Participant p) {
		JSONObject jsonObject =  new JSONObject();
		jsonObject.put("response", "participant");
		jsonObject.put("dbmethod", "save");
		jsonObject.put("username", p.getUserName());
		return jsonObject.toJSONString();

	}

	private static String deleteParticipantFromAppointment(Participant p) {
		JSONObject jsonObject =  new JSONObject();
		jsonObject.put("response", "participant");
		jsonObject.put("dbmethod", "delete");
		jsonObject.put("username", p.getUserName());
		return jsonObject.toJSONString();
	}

	private static String notificationListModelAsJSON(NotificationListModel notificationListModel) {
		JSONObject jsonObject =  new JSONObject();
		jsonObject.put("response", "notificationlistmodel");
		jsonObject.put("dbmethod", "initialize");
		JSONArray array = new JSONArray();
		for (int i = 0; i < notificationListModel.size(); i++) {
			Notification n = notificationListModel.get(i);
			JSONObject object = new JSONObject();
			jsonObject.put("notificationID", n.getNotificationID());
			jsonObject.put("isSeen", n.isSeen());
			jsonObject.put("type", n.getType());
			jsonObject.put("appointmentID", n.getAppointmentID());
		    array.add(object);
		}
		return jsonObject.toJSONString();

	}

	private static String deleteAppointment(Appointment a) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("response", "appointment");
		jsonObject.put("dbmethod", "delete");
		jsonObject.put("appointmentID", a.getAppointmentID());
		return jsonObject.toJSONString();

	}

	private static String employeeListModelAsJSON(EmployeeListModel employeeListModel) {
		System.out.println("DatabaseWorker.employeeListModelAsJSON: PARSING REQUEST");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("response", "employeelistmodel");
		jsonObject.put("dbmethod", "initialize");
		JSONArray array = new JSONArray();
		for (int i = 0; i < employeeListModel.size(); i++) {
			JSONObject employee = new JSONObject();
			Employee e = employeeListModel.get(i);
			employee.put("username", e.getUsername());
			employee.put("name", e.getName());
			array.add(employee);
		}
		jsonObject.put("model", array);
		return jsonObject.toJSONString();
	}

	private static String groupListModelAsJSON(GroupListModel groupListModel) {
		System.out.println("DatabaseWorker.groupListModelAsJSON: PARSING REQUEST");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("response", "grouplistmodel");
		jsonObject.put("dbmethod", "initialize");
		JSONArray array = new JSONArray();
		for (int i = 0; i < groupListModel.size(); i++) {
			JSONObject group = new JSONObject();
			ArrayList<Employee> participants = groupListModel.get(i).getEmployees();
			JSONArray array1 = new JSONArray();
			for (int j = 0; j < groupListModel.get(i).getEmployees().size(); j++) {
				JSONObject employee = new JSONObject();
				employee.put("name", participants.get(j).getName());
				employee.put("username", participants.get(j).getUsername());
				array1.add(employee);
			}
			group.put("group", groupListModel.get(i).getGroupName());
			group.put("employees", array1);
			array.add(group);
		}
		jsonObject.put("model", array);
		return jsonObject.toJSONString();
	}

	private static String sendAppointment(Appointment a) {
		System.out.println("DatabaseWorker.sendAppointment: PARSING REQUEST");
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject appointment = new JSONObject();
		appointment.put("appointmentID", a.getAppointmentID());
		JSONObject appointmentLeader = new JSONObject();
		appointmentLeader.put("username", a.getAppointmentLeader().getUsername());
		appointmentLeader.put("name", a.getAppointmentLeader().getName());
		appointment.put("appointmentLeader", appointmentLeader);
		appointment.put("description", a.getDescription());
		if (a.getRoom() != null) {
			appointment.put("location", a.getRoom().getRoomCode());
		}
		appointment.put("locationText", a.getLocationText());
		appointment.put("startDateTime", sdf.format(a.getStartDateTime().getTime()));
		appointment.put("endDateTime", sdf.format(a.getEndDateTime().getTime()));
		JSONObject jsonObject = new JSONObject();

		ParticipantListModel model = new ParticipantListModel();
		model.setAppointmentID(a.getAppointmentID());
		model.initialize();
		a.setParticipantList(model); // er egentlig unødvendig
		JSONArray array = new JSONArray();
		for (int i = 0; i < model.size(); i++) {
			JSONObject participant = new JSONObject();
			participant.put("name",model.get(i).getName());
			participant.put("username", model.get(i).getUserName());
			if (model.get(i).getParticipantStatus() != null) {
				participant.put("participantstatus", model.get(i).getParticipantStatus().toString());
			} else {
				participant.put("participantstatus", "null");
			}
			participant.put("showInCalendar", model.get(i).isShowInCalendar());
			array.add(participant);
		}
		appointment.put("participants", array);

		EmailListModel emailListModel = new EmailListModel();
		emailListModel.setAppointmentID(a.getAppointmentID());
		emailListModel.initialize();
		if (!emailListModel.isEmpty()) {
			JSONArray array1 = new JSONArray();
			for (int i = 0; i < emailListModel.size(); i++) {
				array1.add(emailListModel.get(i).toString());
			}
			appointment.put("emaillistmodel", array1);
		}

		jsonObject.put("response", "appointment");
		jsonObject.put("dbmethod", "initialize");
		jsonObject.put("model", appointment);
		return jsonObject.toJSONString();
	}

	private static String createAppointment(JSONObject model) {
		System.out.println("DatabaseWorker.createAppointment: PARSING REQUEST");
		Appointment a = new Appointment();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Employee e = new Employee((String) model.get("appointmentLeader"), "navn");
		e.refresh();
		a.setAppointmentLeader(e);
		a.setDescription((String) model.get("description"));
		if (model.get("roomCode") != null) {
			Room r = new Room((String) model.get("roomCode"), 0);
			r.refresh();
			a.setLocation(r);
		}
		a.setLocationText((String) model.get("locationText"));
		a.setAppointmentID(Integer.valueOf(model.get("appointmentID").toString()));
		try {
			a.getStartDateTime().setTime((sdf.parse((String) model.get("startDateTime"))));
			a.getEndDateTime().setTime((sdf.parse((String) model.get("endDateTime"))));
		} catch (java.text.ParseException e1) {
			e1.printStackTrace();
		}

		JSONArray array = (JSONArray) model.get("participants");
		ParticipantListModel plm = new ParticipantListModel();
		Iterator<JSONObject> iterator = array.iterator();
		while (iterator.hasNext()) {
			JSONObject p = iterator.next();
			ParticipantStatus pStatus = null;
			if (p.get("participantstatus") != null) {
				if (p.get("participantstatus").toString().equals("Deltar")) {
					pStatus = ParticipantStatus.participating;
				} else {
					pStatus = ParticipantStatus.notParticipating;
				}
			}
			Participant participant = new Participant((String) p.get("username"), (String) p.get("name"),
					pStatus, Boolean.valueOf(p.get("showInCalendar").toString()), a.getAppointmentID());
		    plm.addElement(participant);
		}

		a.save();
		plm.setAppointmentID(a.getAppointmentID());
		plm.save();

		if (model.get("emaillistmodel").toString() != null) {
			EmailListModel emailListModel = new EmailListModel();
			JSONArray email = (JSONArray) model.get("emaillistmodel");
			if (!email.isEmpty()) {
				Iterator iterator1 = email.iterator();
				while (iterator1.hasNext()) {
					Object emailaddress = iterator1.next();
					emailListModel.addElement((String) emailaddress);
				}
				emailListModel.setAppointmentID(a.getAppointmentID());
				emailListModel.save();
				for (int i = 0; i < emailListModel.size(); i++) {
					GMail sendMail = new GMail();
					String subject = "Invitert til avtale '" + a.getDescription() + "' hos Firma X";
					String text = "Du er invitert til avtale '" + a.getDescription() + "' av " + a.getAppointmentLeader().getName() + "\n\n" +
							"Avtalen finner sted i rom " + a.getLocationText() + "\nStarttidspunkt: " + a.getStartDateTime().getTime() + " Slutttidspunkt: " + a.getEndDateTime().getTime() +
							"\n\nHilsen Firma X sin superdupre server som håndterer dette for deg!";
					sendMail.sendMail(emailListModel.get(i).toString(), subject, text);
				}
			}
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("response", "appointment");
		jsonObject.put("dbmethod", "save");
		jsonObject.put("appointmentID", a.getAppointmentID());
		return jsonObject.toJSONString();
	}

	private static String roomListModelAsJSON(RoomListModel roomListModel) {
		System.out.println("DatabaseWorker.roomListModelAsJSON: PARSING REQUEST");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("response", "roomlistmodel");
		jsonObject.put("dbmethod", "initialize");
		JSONArray array = new JSONArray();
		for (int i = 0; i < roomListModel.size(); i++) {
			JSONObject room = new JSONObject();
			room.put("roomCode", roomListModel.get(i).getRoomCode());
			room.put("capacity", roomListModel.get(i).getCapacity());
			array.add(room);
		}
		jsonObject.put("rooms", array);
		return jsonObject.toJSONString();
	}

	private static String weekCalendarAsJSON(WeekCalendar weekCalendar) {
		System.out.println("DatabaseWorker.weekCalendarAsJSON: PARSING REQUEST");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("response", "weekcalendar");
		jsonObject.put("dbmethod", "initialize");
		JSONArray array = new JSONArray();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Appointment a : weekCalendar.getAppointmentList()) {
			JSONObject appointment = new JSONObject();
			appointment.put("appointmentID", a.getAppointmentID());
			JSONObject appointmentLeader = new JSONObject();
			appointmentLeader.put("username", a.getAppointmentLeader().getUsername());
			appointmentLeader.put("name", a.getAppointmentLeader().getName());
			appointment.put("appointmentLeader", appointmentLeader);
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

	private static synchronized JSONArray login(JSONArray array) {
		System.out.println("DatabaseWorker.login: PARSING REQUEST");
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
					if (!ClientHandler.loggedInClients.containsKey(clientHandler)) {
						ClientHandler.loggedInClients.put(clientHandler, rs.getString(1));
						System.out.println("********************************************************************************************");
						System.out.println("DatabaseWorker.login: " + rs.getString(1) + " ADDED TO LOGGEDINCLIENTS");
						System.out.println("DatabaseWorker.login: LOGGED IN: " + ClientHandler.loggedInClients.toString());
						System.out.println("********************************************************************************************");
					}
				}
			}
			stmt.close();
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return userArray;
	}

	public static void addClientHandler(ClientHandler handler) {
		clientHandler = handler;
	}
}
