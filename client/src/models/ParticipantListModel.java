package models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.swing.DefaultListModel;
import javax.swing.event.ListDataListener;


@SuppressWarnings("serial")
public class ParticipantListModel extends DefaultListModel<Participant> {
	
	
	
	
	
	public ParticipantListModel() {
		super();
	}


    @JsonProperty("getParticipants")
    public Object[] getParticipants() {
        return this.toArray();
    }


	// TRULS: LEGG TIL DISSE!!

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
