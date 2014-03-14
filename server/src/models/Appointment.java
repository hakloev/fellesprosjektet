package models;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Appointment implements DBInterface {

	// HUSK DENNE, MÅ HETE DET SAMME
	@JsonProperty("participantList")
	ParticipantListModel participantList;
	
	
	@JsonCreator
	public Appointment() {
		
	}

	public ParticipantListModel getParticipantList() {
		return participantList;
	}

	@JsonProperty("getParticipants")
	public void setParticipantList(ParticipantListModel participantList) {
		this.participantList = participantList;
	}

	@Override
	public String toString() {
		return "Appointment{" +
				"participantList=" + participantList +
				'}';
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
}
