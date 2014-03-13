package models;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.swing.DefaultListModel;


@SuppressWarnings("serial")
public class ParticipantListModel extends DefaultListModel<Participant> {
	
	
	
	
	public ParticipantListModel() {
		super();
		
		/* test code */
		this.addElement(new Participant("siri", "Siri Gundersen", null));
		this.addElement(new Participant("arvid", "Arvid Pettersen", ParticipantStatus.participating));
		this.addElement(new Participant("per", "Per Haraldsen", ParticipantStatus.notParticipating));
		/* end test code */
	}


    @JsonProperty("getParticipants")
    public Object[] getParticipants() {
        return this.toArray();


    }


    public ParticipantListModel(ParticipantListModel participantList) {
		super();
		for (Object participant : participantList.toArray()){
			this.addElement((Participant) participant);
		}
	}
	
	
	@Override
	public void addElement(Participant participant){
		if (this.contains(participant)){
			return;
		}
		super.addElement(participant);
	}
}
