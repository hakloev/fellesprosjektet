package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Participant {


    private String userName;
    private String name;
	private boolean showInCalendar;
    private ParticipantStatus participantStatus;

    public Participant(String userName, String name,
                     ParticipantStatus participantStatus, boolean showInCalendar) {
        this.userName = userName;
        this.name = name;
        this.participantStatus = participantStatus;
	    this.showInCalendar = showInCalendar;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", participantStatus=" + participantStatus +
                '}';
    }



	public boolean isShowInCalendar() {
		return showInCalendar;
	}

	public void setShowInCalendar(boolean showInCalendar) {
		this.showInCalendar = showInCalendar;
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



    @Override
    public boolean equals(Object participant){
        if (participant instanceof Participant) {
            return (((Participant)participant).userName.equals(this.userName));
        }
        return false;
    }

}

