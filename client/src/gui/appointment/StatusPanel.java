package gui.appointment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.*;

@SuppressWarnings("serial")
class StatusPanel extends JPanel implements ActionListener {
	
	
	private JComboBox<ParticipantStatus> statusComboBox;
	private Participant currentUser;
	
	
	StatusPanel(Participant currentUser) {
		this.currentUser = currentUser;
		
		JLabel labelDeltagerstatus = new JLabel("Deltagerstatus");
		this.add(labelDeltagerstatus);
		
		statusComboBox = new JComboBox<ParticipantStatus>(ParticipantStatus.getStatusList());
		this.add(statusComboBox);
		
		if (currentUser.getParticipantStatus() == null) {
			statusComboBox.setSelectedItem(null);
		} else {
			statusComboBox.setSelectedItem(currentUser.getParticipantStatus());
		}
		
		statusComboBox.addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		currentUser.setParticipantStatus((ParticipantStatus)statusComboBox.getSelectedItem());
	}
	
	
}

