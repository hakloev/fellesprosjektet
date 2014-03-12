package gui.appointment;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.ParticipantStatus;

@SuppressWarnings("serial")
class StatusPanel extends JPanel {
	
	
	StatusPanel() {
		
		JLabel labelDeltagerstatus = new JLabel("Deltagerstatus");
		this.add(labelDeltagerstatus);
		
		JComboBox<ParticipantStatus> statusComboBox = new JComboBox<ParticipantStatus>(ParticipantStatus.getStatusList());
		this.add(statusComboBox);
		
	}
	
}
