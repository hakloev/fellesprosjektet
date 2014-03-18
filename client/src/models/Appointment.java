package models;

import controllers.OutboundWorker;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Appointment implements NetInterface {
    private ParticipantListModel participantList;

    private int appointmentID;
    private Employee appointmentLeader;
    private String description;
    private Room location;
    private String locationText;
    private Calendar startDateTime;
    private Calendar endDateTime;
    private PropertyChangeSupport pcs;
	private EmailListModel emailRecipientsList;
	private boolean showInCalendar;


	/**
	 * Constructor for new appointment
	 * 
	 * @param appointmentLeader
	 */
	public Appointment(Employee appointmentLeader) {
		pcs = new PropertyChangeSupport(this);
		
		this.appointmentLeader = appointmentLeader;
		participantList = new ParticipantListModel(new Participant(appointmentLeader));
		emailRecipientsList = new EmailListModel();
	}

    public Appointment() {
        startDateTime = Calendar.getInstance();
        endDateTime = Calendar.getInstance();
    }
    
    public Appointment(Employee appointmentLeader, Calendar date) {
		pcs = new PropertyChangeSupport(this);
		startDateTime = date;
		this.appointmentLeader = appointmentLeader;
		participantList = new ParticipantListModel(new Participant(appointmentLeader));
		emailRecipientsList = new EmailListModel();
	}
	
	
	/* Work in progress. Use refresh instead if appointment editing is canceled.
	/**
	 * Copy constructor
	 * 
	 * @param oldAppointment
	 *
	public Appointment(Appointment oldAppointment) {
		pcs = new PropertyChangeSupport(this);
		
		participantList
		emailRecipientsList
		appointmentLeader
		description
		location
		locationText
		startDateTime
		endDateTime
		
	}
	*/


	public void setDate(Date date) {
		if (startDateTime == null) startDateTime = Calendar.getInstance();

		//startDateTime.set(date.getYear(), date.getMonth(), date.getDate());
		startDateTime.setTimeInMillis(date.getTime());
	}


	public void setStart(Date start){
		if (startDateTime == null) startDateTime = Calendar.getInstance();

		startDateTime.set(Calendar.HOUR_OF_DAY, start.getHours());
		startDateTime.set(Calendar.MINUTE, start.getMinutes());


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


			pcs.firePropertyChange("Duration", oldDuration, getDuration());


		} else {
			endDateTime.set(Calendar.HOUR_OF_DAY, end.getHours());
			endDateTime.set(Calendar.MINUTE, end.getMinutes());
		}
	}


	public void setDuration(Date duration){
		if (startDateTime != null) {
			String oldValue = this.getEnd();

			if (endDateTime == null) endDateTime = Calendar.getInstance();

			endDateTime.set(startDateTime.get(Calendar.YEAR),
					startDateTime.get(Calendar.MONTH),
					startDateTime.get(Calendar.DAY_OF_MONTH),
					startDateTime.get(Calendar.HOUR_OF_DAY) + duration.getHours(),
					startDateTime.get(Calendar.MINUTE) + duration.getMinutes());
			pcs.firePropertyChange("End", oldValue, this.getEnd());
		}
	}

	public String getDate(){
		if (startDateTime != null) {
			int day = startDateTime.get(Calendar.DAY_OF_MONTH);
			int month = startDateTime.get(Calendar.MONTH) + 1;
			int year = startDateTime.get(Calendar.YEAR);
			String ret = "";
			ret += day/10;
			ret += day % 10;
			ret += ".";
			ret += month/10;
			ret += month%10;
			ret += ".";
			ret += year;
            System.out.println(ret);
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


    public Calendar getEndCal(){
        if(endDateTime != null){
            return endDateTime;
        }
        return Calendar.getInstance();
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
			//pcs.firePropertyChange("participantList", this.participantList, participantList);
			this.participantList = participantList;
		}
	}
	
	
	public EmailListModel getEmailRecipientsList() {
		return emailRecipientsList;
	}
	
	
	public void setEmailRecipientsList(EmailListModel emailList) {
		this.emailRecipientsList = emailList;
	}


	public Employee getAppointmentLeader() {
		return appointmentLeader;
	}


	public void setDescription(String description) {
		this.description = description;
	}

    public void setAppointmentLeader(Employee appointmentLeader) {
        this.appointmentLeader = appointmentLeader;
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


	public Room getLocation() {
		return location;
	}
	
	
	public int getAppointmentID() {
		return appointmentID;
	}


	public boolean isShowInCalendar() {
		return showInCalendar;
	}


	public void setShowInCalendar(boolean showInCalendar) {
		this.showInCalendar = showInCalendar;
	}


	@Override
	public String toString() {
		return appointmentLeader.getName().split(" ")[0] + " : " + locationText;
	}


	@Override
	public void initialize() {
        JSONObject json = new JSONObject();
        json.put("dbmethod", "initialize");
        json.put("request","appointment");
        json.put("appointmentID",this.appointmentID);
        OutboundWorker.sendRequest(json);

	}

	@Override
	public void refresh() {
        this.initialize();

	}

	@Override
	public void save() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject json = new JSONObject();
        json.put("dbmethod", "save");
        json.put("request","appointment");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appointmentLeader",this.appointmentLeader.getUsername());
        jsonObject.put("description",this.description);
        if (this.location.getRoomCode() != null) {
            jsonObject.put("roomCode",this.location.getRoomCode());
        }
        jsonObject.put("locationText",this.locationText);
        jsonObject.put("startDateTime",sdf.format(startDateTime.getTime()));
        jsonObject.put("endDateTime",sdf.format(endDateTime.getTime()));

        JSONArray array = new JSONArray();
        for (int i = 0; i < participantList.getSize(); i++) {
            JSONObject p = createParticipant(participantList.get(i));
            array.add(p);
        }

        jsonObject.put("participants",array);
        json.put("model",jsonObject);

        OutboundWorker.sendRequest(json);


    }

    private JSONObject createParticipant(Participant p) {
        JSONObject participant = new JSONObject();
        participant.put("name",p.getName());
        participant.put("username",p.getUserName());
        participant.put("participantstatus",p.getParticipantStatus().toString());
        participant.put("showInCalendar",p.isShowInCalendar());
        return participant;
    }

    @Override
	public void delete() {
        JSONObject json = new JSONObject();
        json.put("dbmethod", "delete");
        json.put("request","appointment");
        json.put("appointmentid",this.appointmentID);
        OutboundWorker.sendRequest(json);
	}


    public Calendar getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Calendar endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getLocationText() {
        return locationText;
    }

    public Calendar getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Calendar startDateTime) {
        this.startDateTime = startDateTime;
    }
}


