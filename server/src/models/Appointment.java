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


	public void setDate(Date date) {
		if (startDateTime == null) startDateTime = Calendar.getInstance();

		startDateTime.set(date.getYear(), date.getMonth(), date.getDate());
	}


	public void setStart(Date start){
		if (startDateTime == null) startDateTime = Calendar.getInstance();

		startDateTime.set(Calendar.HOUR_OF_DAY, start.getHours());
		startDateTime.set(Calendar.MINUTE, start.getMinutes());


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

	public void setStartDateTime(Calendar startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Calendar getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Calendar endDateTime) {
		this.endDateTime = endDateTime;
	}

	public void setEnd(Date end){
		if (endDateTime == null) endDateTime = Calendar.getInstance();

		String oldDuration = this.getDuration();

		if (startDateTime != null) {
			endDateTime.set(
					startDateTime.get(Calendar.YEAR),
					startDateTime.get(Calendar.MONTH),
					startDateTime.get(Calendar.DAY_OF_MONTH),
					end.getHours(),
					end.getMinutes());
		} else {
			endDateTime.set(Calendar.HOUR_OF_DAY, end.getHours());
			endDateTime.set(Calendar.MINUTE, end.getMinutes());
		}
	}


	public void setDuration(Date duration){
		if (startDateTime != null) {
			String oldValue = this.getDuration();

			if (endDateTime == null) endDateTime = Calendar.getInstance();

			endDateTime.set(startDateTime.get(Calendar.YEAR),
					startDateTime.get(Calendar.MONTH),
					startDateTime.get(Calendar.DAY_OF_MONTH),
					startDateTime.get(Calendar.HOUR_OF_DAY) + duration.getHours(),
					startDateTime.get(Calendar.MINUTE) + duration.getMinutes());
		}
	}


	public String getDate(){
		if (startDateTime != null) {
			int day = startDateTime.get(Calendar.DAY_OF_MONTH);
			int month = startDateTime.get(Calendar.MONTH);
			int year = startDateTime.get(Calendar.YEAR);
			String ret = "";
			ret += day/10;
			ret += day % 10;
			ret += ".";
			ret += month/10;
			ret += month%10;
			ret += ".";
			ret += year;
			return ret;


		}
		return "01.01.1970";
	}


	public String getStart(){
		if (startDateTime != null) {
			int hour = startDateTime.get(Calendar.HOUR_OF_DAY);
			int min = startDateTime.get(Calendar.MINUTE);
			String ret = "";
			ret += hour /10;
			ret += hour % 10;
			ret += ":";
			ret += min /10;
			ret += min%10;
			return ret;
		}
		return "00:00";
	}


	public String getEnd(){
		if (endDateTime != null) {
			int hour = endDateTime.get(Calendar.HOUR_OF_DAY);
			int min = endDateTime.get(Calendar.MINUTE);
			String ret = "";
			ret += hour /10;
			ret += hour % 10;
			ret += ":";
			ret += min /10;
			ret += min%10;
			return ret;
		}
		return "00:00";
	}


	public String getDuration(){
		if (startDateTime != null && endDateTime != null) {
			long difference = endDateTime.getTimeInMillis() - startDateTime.getTimeInMillis();
			difference /= 60000;
			long hour = difference /60;
			long min = difference % 60;
			String ret = "";
			ret += hour /10;
			ret += hour % 10;
			ret += ":";
			ret += min /10;
			ret += min%10;
			return ret;
		}
		return "00:00";
	}


	public ParticipantListModel getParticipantList() {
		return participantList;
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

	public void setLocation(String location) {
		this.locationText = location;
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

	public String getLocation() {
		return locationText;
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
