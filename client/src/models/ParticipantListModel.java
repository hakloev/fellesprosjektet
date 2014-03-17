package models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.swing.DefaultListModel;
import javax.swing.event.ListDataListener;


@SuppressWarnings("serial")
public class ParticipantListModel extends DefaultListModel<Participant> {
	
	
	
	private Participant appointmentLeader;
	
	
	/**
	 * Creates a new empty list
	 */
	public ParticipantListModel(Participant appointmentLeader) {
		super();
		this.appointmentLeader = appointmentLeader;
		this.addElement(appointmentLeader);
	}
	
	
	/**
	 * Copy constructor
	 * 
	 * @param participantList
	 */
    public ParticipantListModel(ParticipantListModel participantList) {
		super();
		for (Object participant : participantList.toArray()){
			this.addElement((Participant) participant);
		}
		this.appointmentLeader = participantList.getAppoinmentLeader();
	}


    @JsonProperty("participants")
    public Object[] participants() {
        return this.toArray();
    }


	@Override
	@JsonIgnore
	public boolean isEmpty() {
		return this.isEmpty();
	}


	@Override
	@JsonIgnore
	public ListDataListener[] getListDataListeners() {
		return this.getListDataListeners();
	}


	@Override
	public void addElement(Participant participant){
		if (this.contains(participant)){
			return;
		}
		super.addElement(participant);
	}


    @JsonIgnore
    @Override
    public int getSize(){
        return super.getSize();
    }


	
	public Participant getAppoinmentLeader() {
		return this.appointmentLeader;
	}
	
}


