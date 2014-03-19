package models;

import java.util.Calendar;


public class Participant {


    private String userName;
    private String name;
    private ParticipantStatus participantStatus;
    private boolean showInCalendar;
    private Calendar alarm;


    public Participant(String userName, String name, ParticipantStatus participantStatus) {
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
        this.userName = employee.getUsername();
        this.name = employee.getName();
        participantStatus = null;
        showInCalendar = true;
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

    
    /**
     * Save alarm and status for this participant in this appointment
     * 
     * @param appointmentID
     */
    public void save(int appointmentID) {
    	// TODO netcode for saving status & alarm to server
    }
    
    
}

