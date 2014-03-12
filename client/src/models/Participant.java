package models;

public class Participant {
	
	
	private String userName;
	private String name;
	private ParticipantStatus participantStatus;
	
	
	public Participant(String userName, String name, ParticipantStatus participantStatus) {
		this.userName = userName;
		this.name = name;
		this.participantStatus = participantStatus;
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
	
	
	public String toString() {
		return name;
	}
	
}
