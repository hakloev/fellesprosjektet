package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.swing.DefaultListModel;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.Objects;

@SuppressWarnings("serial")
public class ParticipantListModel extends DefaultListModel<Participant> {
	
	
	
	public ParticipantListModel() {
		super();
		
		/* test code */
		this.addElement(new Participant("siri", "Siri Gundersen", ParticipantStatus.notParticipating));
		this.addElement(new Participant("arvid", "Arvid Pettersen", ParticipantStatus.participating));
		/* end test code */
	}


    @JsonProperty("getParticipants")
    public Object[] getParticipants() {
        return this.toArray();


    }


    /*

    @Override
    @JsonIgnore
    public int getSize() {
        return super.getSize();
    }

    @Override
    @JsonIgnore
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    @JsonIgnore
    public ListDataListener[] getListDataListeners() {
        return super.getListDataListeners();
    }

    */




}
