package models;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Appointment implements DBInterface {
	
	
	ParticipantListModel participantList;
	
	
	@JsonCreator
	public Appointment() {
		
	}

	public ParticipantListModel getParticipantList() {
		return participantList;
	}

	public void setParticipantList(ParticipantListModel participantList) {
		this.participantList = participantList;
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
