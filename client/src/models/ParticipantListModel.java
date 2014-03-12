package models;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class ParticipantListModel extends DefaultListModel<Participant> {
	
	
	
	public ParticipantListModel() {
		super();
		
		/* test code */
		this.addElement(new Participant("siri", "Siri Gundersen", null));
		this.addElement(new Participant("arvid", "Arvid Pettersen", null));
		/* end test code */
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
