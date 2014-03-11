package models;

public enum ParticipantStatus {
	
	
	participating("Deltar"),
	notParticipating("Deltar ikke");
	
	
	private String status;
	
	
	private ParticipantStatus(String status) {
		this.status = status;
	}
	
	
	public static ParticipantStatus[] getStatusList() {
		ParticipantStatus[] statusList = {ParticipantStatus.participating, ParticipantStatus.notParticipating};
		return statusList;
	}
	
	
	public String toString() {
		return status;
	}
	
}
