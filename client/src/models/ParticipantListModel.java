package models;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class ParticipantListModel extends DefaultListModel<Participant> {
	
	
	
	
	
	public ParticipantListModel() {
		super();
	}
	
	
	/**
	 * Copy constructor to enable use of the cancel button
	 * 
	 * @param participantList
	 */
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
