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
	
	
}