package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.swing.DefaultListModel;
import java.util.Collection;

@SuppressWarnings("serial")
public class ParticipantListModel extends DefaultListModel<Participant> {

	public ParticipantListModel() {
		super();
	}

	@JsonProperty("getParticipants")
	public Object[] getParticipants() {
		return this.toArray();
	}










	
}
