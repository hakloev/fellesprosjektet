package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Participant {


    private String userName;
    private String name;
    private ParticipantStatus participantStatus;

    @JsonCreator
    public Participant(@JsonProperty("userName" )String userName, @JsonProperty("name") String name,
                       @JsonProperty("participantStatus") ParticipantStatus participantStatus) {
        this.userName = userName;
        this.name = name;
        this.participantStatus = participantStatus;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", participantStatus=" + participantStatus +
                '}';
    }

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



    @Override
    public boolean equals(Object participant){
        if (participant instanceof Participant) {
            return (((Participant)participant).userName.equals(this.userName));
        }
        return false;
    }

}

