package models;

import controllers.LogoutException;
import controllers.OutboundWorker;
import controllers.ResponseWaiter;
import controllers.SocketListener;

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
		
		this.showInCalendar = true;
		this.appointmentLeader = appointmentLeader;
		this.participantList = new ParticipantListModel(new Participant(appointmentLeader));
		this.emailRecipientsList = new EmailListModel();
	}

    public Appointment() {
    	pcs = new PropertyChangeSupport(this);
        startDateTime = Calendar.getInstance();
        endDateTime = Calendar.getInstance();
    }
    
    
    public Appointment(int appointmentID) {
    	pcs = new PropertyChangeSupport(this);
    	this.appointmentID = appointmentID;
    }

    /*
    public Appointment(Employee appointmentLeader, Calendar date) {
		pcs = new PropertyChangeSupport(this);
		this.showInCalendar = true;
		startDateTime = date;
		this.appointmentLeader = appointmentLeader;
		participantList = new ParticipantListModel(new Participant(appointmentLeader));
		emailRecipientsList = new EmailListModel();
	}
	*/
	
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

	@SuppressWarnings("deprecation")
	public void setStart(Date start){
		if (startDateTime == null) startDateTime = Calendar.getInstance();

		startDateTime.set(Calendar.HOUR_OF_DAY, start.getHours());
		startDateTime.set(Calendar.MINUTE, start.getMinutes());
	}
	
    public void setStartDateTime(Calendar startDateTime) {
        this.startDateTime = startDateTime;
    }

	@SuppressWarnings("deprecation")
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
	
	public void setEndDateTime(Calendar endDateTime) {
        this.endDateTime = endDateTime;
    }

	@SuppressWarnings("deprecation")
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
	
	public Calendar getStartDateTime() {
        return startDateTime;
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

	public Calendar getEndDateTime() {
        return endDateTime;
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
	
	public EmailListModel getEmailRecipientsList() {
		return emailRecipientsList;
	}
	
	public void setEmailRecipientsList(EmailListModel emailList) {
		this.emailRecipientsList = emailList;
	}

	public void setAppointmentLeader(Employee appointmentLeader) {
		this.appointmentLeader = appointmentLeader;
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
	
	public String getLocationText() {
        return locationText;
    }

	public void setLocation(Room location) {
		this.location = location;
		this.locationText = location.getRoomCode();
	}

	public Room getLocation() {
		return location;
	}

	public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
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
		return " " + appointmentLeader.getUsername() + " : " + locationText;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize() throws LogoutException {
        JSONObject json = new JSONObject();
        json.put("dbmethod", "initialize");
        json.put("request","appointment");
        json.put("appointmentID",this.appointmentID);
        OutboundWorker.sendRequest(json);
        
        Object[] response = new Object[1];
        new ResponseWaiter(SocketListener.getSL(), response);
        
        if (response[0] != null && response[0] instanceof Appointment) {
        	Appointment app = (Appointment)response[0];
        	this.appointmentLeader = app.appointmentLeader;
        	this.participantList = app.participantList;
        	this.participantList.locateAppointmentLeader(this.appointmentLeader);
        	this.appointmentID = app.appointmentID;
        	this.description = app.description;
        	this.location = app.location;
        	this.locationText = app.locationText;
        	this.startDateTime = app.startDateTime;
        	this.endDateTime = app.endDateTime;
        	this.emailRecipientsList = app.emailRecipientsList;
        	// FIXME showInCalendar. not get from server maybe?
        }

	}

	@Override
	public void refresh() throws LogoutException {
        this.initialize();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void save() throws LogoutException {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject json = new JSONObject();
        json.put("dbmethod", "save");
        json.put("request","appointment");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appointmentLeader",this.appointmentLeader.getUsername());
        jsonObject.put("description",this.description);
        if (this.location != null) {
            jsonObject.put("roomCode",this.location.getRoomCode());
        }
        jsonObject.put("locationText",this.locationText);
        jsonObject.put("startDateTime",sdf.format(startDateTime.getTime()));
        jsonObject.put("endDateTime",sdf.format(endDateTime.getTime()));
        jsonObject.put("appointmentID", appointmentID);
        
        JSONArray emailarray = new JSONArray();
        if (emailRecipientsList != null) for (int i = 0; i < emailRecipientsList.getSize(); i++) {
        	emailarray.add(emailRecipientsList.get(i));
        }
        jsonObject.put("emaillistmodel", emailarray);

        JSONArray array = new JSONArray();
        for (int i = 0; i < participantList.getSize(); i++) {
            JSONObject p = createParticipant(participantList.get(i));
            array.add(p);
        }

        jsonObject.put("participants",array);
        json.put("model",jsonObject);

        OutboundWorker.sendRequest(json);

        Object[] response = new Object[1];
        new ResponseWaiter(SocketListener.getSL(), response);
        if (response[0] != null && response[0] instanceof String) {
            for (Object id : ((RoomListModel)response[0]).toArray() ) {
                this.setAppointmentID(Integer.valueOf(id.toString()));
            }
        }
        
    }

    @SuppressWarnings("unchecked")
	@Override
	public void delete() {
        JSONObject json = new JSONObject();
        json.put("dbmethod", "delete");
        json.put("request","appointment");
        json.put("appointmentID",this.appointmentID);
        OutboundWorker.sendRequest(json);
	}
    
    @SuppressWarnings("unchecked")
	private JSONObject createParticipant(Participant p) {
    	JSONObject participant = new JSONObject();
    	participant.put("name",p.getName());
    	participant.put("username",p.getUserName());
    	if (p.getParticipantStatus() != null) {
    		participant.put("participantstatus",p.getParticipantStatus().toString());
    	} else {
    		participant.put("participantstatus",null);
    	}
    	if (p.getAlarm() != null) {
        	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	participant.put("alarm", sdf.format(p.getAlarm().getTime()));
        } else {
        	participant.put("alarm", null);
        }
    	participant.put("showInCalendar",p.isShowInCalendar());
    	return participant;
    }
    
    @Override
    public boolean equals(Object object) {
    	if (object instanceof Appointment) {
    		return ((Appointment)object).appointmentID == this.appointmentID;
    	}
    	return false;
    }
    
    
}


