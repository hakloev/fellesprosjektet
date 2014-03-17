package models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;
import java.util.Date;

public class Appointment implements DBInterface {

	@JsonProperty("participantList")
	private ParticipantListModel participantList;

	@JsonProperty("appointmentLeader")
	private Employee appointmentLeader;
	@JsonProperty("description")
	private String description;
	@JsonProperty("location")
	private Room location;
	@JsonProperty("locationText")
	private String locationText;

	@JsonProperty("startDateTime")
	private Calendar startDateTime;
	@JsonProperty("endDateTime")
	private Calendar endDateTime;


	/**
	 *  Constructor for new appointment
	 */
	public Appointment() {
		participantList = new ParticipantListModel();
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


	public String getLocation() {
		return locationText;
	}


	@Override
	public String toString() {
		return appointmentLeader.getName().split(" ")[0] + " : " + locationText;
	}


	@Override
	public void initialize() {

	}

	@Override
	public void refresh() {
	}

	@Override
	public void save() {
	}

	@Override
	public void delete() {
	}
}
