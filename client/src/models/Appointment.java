package models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import controllers.OutboundWorker;
import helperclasses.Request;





import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Appointment implements NetInterface {



	@JsonProperty("participantList")
	private ParticipantListModel participantList;

	private PropertyChangeSupport pcs;
	
	private Employee appointmentLeader;
	private String description;
	private Room location;
	private String locationText;
	
	private Calendar startDateTime;
	private Calendar endDateTime;


	/**
	 *  Constructor for new appointment
	 */
	public Appointment(Employee appointmentLeader) {
		this.appointmentLeader = appointmentLeader;
		pcs = new PropertyChangeSupport(this);
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
		
		/* TODO
		 * Possibly fix so that duration or end is calculated again if one of those fields are set before start
		if(endset){
		
			pcs.firePropertyChange("duration", duration, newduration);
			
		}
		else if(durationset){
			
			pcs.firePropertyChange("End", oldEnd, getEnd());
		}
		*/

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
			
			if (startDateTime != null) {
				pcs.firePropertyChange("Duration", oldDuration, getDuration());
			}
			
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
			
			pcs.firePropertyChange("End", oldValue, this.getDuration());
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

	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}
	
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		pcs.removePropertyChangeListener(listener);
	}

	
	public ParticipantListModel getParticipantList() {
		return participantList;
	}


	public void setParticipantList(ParticipantListModel participantList) {
		if (participantList != null){
			pcs.firePropertyChange("participantList", this.participantList, participantList);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		this.initialize();

	}

	@Override
	public void save() {
        Request request = new Request("appointment","post",this);
        OutboundWorker.sendRequest(request);
	}

	@Override
	public void delete() {
      Request request = new Request("appointment","delete",this);
      OutboundWorker.sendRequest(request);

	}
}
