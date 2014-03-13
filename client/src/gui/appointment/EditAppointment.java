package gui.appointment;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

@SuppressWarnings("serial")
public class EditAppointment extends JDialog {
	
	
	private JPanel contentPane;
	
	EditButtonPanel editButtonPanel;
	
	
	public EditAppointment(JFrame parent, Appointment appointment) {
		super(parent, true);
		this.setTitle("Rediger avtale");
		this.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		
		GridBagLayout gbl = new GridBagLayout();
		contentPane.setLayout(gbl);
		
		// detailsPanel
		GridBagConstraints gbc_detailsPanel = new GridBagConstraints();
		gbc_detailsPanel.gridx = 0;
		gbc_detailsPanel.gridy = 0;
		contentPane.add(new DetailsPanel(this, appointment.getParticipantList()), gbc_detailsPanel);
		
		// okButtonPanel
		GridBagConstraints gbc_okButtonPanel = new GridBagConstraints();
		gbc_okButtonPanel.gridx = 0;
		gbc_okButtonPanel.gridy = 1;
		gbc_okButtonPanel.gridwidth = 2;
		contentPane.add(new OKButtonPanel(this), gbc_okButtonPanel);
		
		// editButtonPanel
		GridBagConstraints gbc_editButtonPanel = new GridBagConstraints();
		gbc_editButtonPanel.gridx = 1;
		gbc_editButtonPanel.gridy = 0;
		gbc_editButtonPanel.gridheight = 2;
		gbc_editButtonPanel.anchor = GridBagConstraints.NORTH;
		editButtonPanel = new EditButtonPanel(this, appointment);
		contentPane.add(editButtonPanel, gbc_editButtonPanel);
		editButtonPanel.setButtonSlettEnabled(false);
		editButtonPanel.setBothStatusButtonsEnabled(false);
		
		this.pack();
		this.setLocationRelativeTo(parent);
		this.setVisible(true);
		
	}
}
