package models;

import controllers.DBconnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Appointment implements DBInterface {

	private ParticipantListModel participantList;

	private int appointmentID;
	private Employee appointmentLeader;
	private String description;
	private Room location;
	private String locationText;

	private Calendar startDateTime;
	private Calendar endDateTime;


	/**
	 *  Constructor for new appointment
	 */
	public Appointment() {
		participantList = new ParticipantListModel();
		startDateTime = Calendar.getInstance();
		endDateTime = Calendar.getInstance();
	}


	public void setAppointmentLeader(Employee appointmentLeader) {
		this.appointmentLeader = appointmentLeader;
	}

	public String getLocationText() {
		return locationText;
	}

	public void setLocationText(String locationText) {
		this.locationText = locationText;
	}

	public Calendar getStartDateTime() {
		return startDateTime;
	}

	public Calendar getEndDateTime() {
		return endDateTime;
	}

	public void setParticipantList(ParticipantListModel participantList) {
		if (participantList != null){
			this.participantList = participantList;
		}
	}

	public Employee getAppointmentLeader() {
		return appointmentLeader;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setLocation(Room location) {
		this.location = location;
		this.locationText = location.getRoomCode();
	}

	public int getAppointmentID() {
		return appointmentID;
	}

	public void setAppointmentID(int appointmentID) {
		this.appointmentID = appointmentID;
	}

	public Room getRoom() {return this.location;}

	@Override
	public String toString() {
		return "Appointment{" +
				"participantList=" + participantList +
				", appointmentID=" + appointmentID +
				", appointmentLeader=" + appointmentLeader +
				", description='" + description + '\'' +
				", location=" + location +
				", locationText='" + locationText + '\'' +
				", startDateTime=" + startDateTime.getTime() +
				", endDateTime=" + endDateTime.getTime() +
				'}';
	}

	@Override
	public void initialize() {
		Connection dbCon = DBconnection.getConnection(); // Singelton class
		try {
			String sql = "SELECT * FROM avtale WHERE avtaleid = " + this.appointmentID;
			Statement stmt = dbCon.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while (rs.next()) {
				this.setAppointmentLeader(new Employee(rs.getString(2), "navn"));
				this.getAppointmentLeader().refresh();
				this.startDateTime.setTime(sdf.parse(rs.getString(3)));
				this.endDateTime.setTime(sdf.parse(String.valueOf(rs.getString(4))));
				this.description = rs.getString(5);
				this.locationText = rs.getString(6);
				this.setLocation(new Room(rs.getString(7), 0));
				this.getRoom().refresh();
			}
			stmt.close();
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void refresh() {
		this.initialize();
	}

	@Override
	public void save() {
		Connection dbCon = DBconnection.getConnection();
		try {
			Statement stmt = dbCon.createStatement();
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sql;
			if (this.location == null) {
				sql = "insert into avtale (avtaleansvarlig, start, slutt, beskrivelse, sted, romkode) values ('" + this.getAppointmentLeader().getUsername()
						+ "', '"  + sdf.format(this.startDateTime.getTime()) + "', '" + sdf.format(this.endDateTime.getTime()) + "', '" + this.description + "', '" + this.locationText
						+ "', null)";
			} else {
				sql = "insert into avtale (avtaleansvarlig, start, slutt, beskrivelse, sted, romkode) values ('" + this.getAppointmentLeader().getUsername()
						+ "', '"  + sdf.format(this.startDateTime.getTime()) + "', '" + sdf.format(this.endDateTime.getTime()) + "', '" + this.description + "', '" + this.locationText
						+ "', '" + this.location.getRoomCode() + "')";
			}
			System.out.println(sql);
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

			ResultSet rs = stmt.getGeneratedKeys();
			while (rs.next()) {
				this.setAppointmentID(rs.getInt(1));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete() {

	}

}
