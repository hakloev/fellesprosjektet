package models;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Participant {


    private String userName;
    private String name;
    private ParticipantStatus participantStatus;
    private boolean showInCalendar;
    private Calendar alarm;

    @JsonCreator
    public Participant(@JsonProperty("userName" )String userName, @JsonProperty("name") String name,
                       @JsonProperty("participantStatus") ParticipantStatus participantStatus) {
        this.userName = userName;
        this.name = name;
        this.participantStatus = participantStatus;
    }

    
    /**
     * Converts an employee into a participant
     * 
     * @param employee
     */
    public Participant(Employee employee) {
        this.userName = employee.getUserName();
        this.name = employee.getName();
        participantStatus = null;
    }
    

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

    public ParticipantStatus getParticipantStatus() {
        return participantStatus;
    }


    public void setParticipantStatus(ParticipantStatus participantStatus) {
        this.participantStatus = participantStatus;
    }


    public boolean isShowInCalendar() {
		return showInCalendar;
	}


	public void setShowInCalendar(boolean showInCalendar) {
		this.showInCalendar = showInCalendar;
	}


	public Calendar getAlarm() {
		return alarm;
	}


	public void setAlarm(Calendar alarm) {
		this.alarm = alarm;
	}


	@Override
    public String toString() {
        return name;
    }


    @Override
    public boolean equals(Object participant){
        if (participant instanceof Participant) {
            return (((Participant)participant).userName.equals(this.userName));
        }
        return false;
    }

}

