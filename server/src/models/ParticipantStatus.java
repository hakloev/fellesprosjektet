package models;

public enum ParticipantStatus {
	
	participating("Deltar"),
	notParticipating("Deltar ikke");

	private String status;

	
	private ParticipantStatus(String status) {
		this.status = status;
	}
	
	public String toString() {
		return status;
	}
	
	
}
