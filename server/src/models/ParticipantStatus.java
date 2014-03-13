package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum ParticipantStatus {
	
	
	participating("Deltar"),
	notParticipating("Deltar ikke");
	
	
	private String status;
	
	@JsonCreator
	private ParticipantStatus(@JsonProperty("status") String status) {
		this.status = status;
	}
	
	
	public static ParticipantStatus[] getStatusList() {
		ParticipantStatus[] statusList = {ParticipantStatus.participating, ParticipantStatus.notParticipating};
		return statusList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		return status;
	}
	
}
