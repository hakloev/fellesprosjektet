package gui.appointment;

import javax.swing.JFrame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

@SuppressWarnings("serial")
public class EditAppointment extends JFrame {
	
	
	
	public EditAppointment() {
		setLocation(100, 100);
		GridBagLayout gbl = new GridBagLayout();
		this.getContentPane().setLayout(gbl);
		
		// detailsPanel
		GridBagConstraints gbc_detailsPanel = new GridBagConstraints();
		gbc_detailsPanel.gridx = 0;
		gbc_detailsPanel.gridy = 0;
		getContentPane().add(new DetailsPanel(), gbc_detailsPanel);
		
		// okButtonPanel
		GridBagConstraints gbc_okButtonPanel = new GridBagConstraints();
		gbc_okButtonPanel.gridx = 0;
		gbc_okButtonPanel.gridy = 1;
		gbc_okButtonPanel.gridwidth = 2;
		getContentPane().add(new OKButtonPanel(), gbc_okButtonPanel);
		
		// editButtonPanel
		GridBagConstraints gbc_editButtonPanel = new GridBagConstraints();
		gbc_editButtonPanel.gridx = 1;
		gbc_editButtonPanel.gridy = 0;
		gbc_editButtonPanel.gridheight = 2;
		gbc_editButtonPanel.anchor = GridBagConstraints.NORTH;
		getContentPane().add(new EditButtonPanel(), gbc_editButtonPanel);
		
		this.pack();
		//EditParticipants editPart = new EditParticipants();
		//editPart.setVisible(true);
	}
}
