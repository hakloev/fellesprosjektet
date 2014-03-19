package gui.appointment;

import gui.CalendarView;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllers.LogoutException;
import models.*;

@SuppressWarnings("serial")
public class ViewAppointment extends JDialog {
	
	
	private JPanel contentPane;
	
	
	public ViewAppointment(JFrame parent, Appointment appointment) throws LogoutException {
		super(parent, true);
		this.setTitle("Avtalevisning");
		this.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		
		ParticipantListModel plModel = appointment.getParticipantList();
		if (plModel == null) {
			appointment.initialize();
		}
		Participant currentUser = plModel.get(plModel.indexOf(new Participant( ((CalendarView)parent).getLoggedInEmployee() )));
		
		GridBagLayout gbl = new GridBagLayout();
		contentPane.setLayout(gbl);
		
		// detailsPanel
		GridBagConstraints gbc_detailsPanel = new GridBagConstraints();
		gbc_detailsPanel.gridx = 0;
		gbc_detailsPanel.gridy = 0;
		// TODO send proper time
		DetailsPanel detailsPanel = new DetailsPanel(this, appointment, currentUser);
		contentPane.add(detailsPanel, gbc_detailsPanel);
		detailsPanel.setEnabled(false);
		
		// statusPanel
		GridBagConstraints gbc_statusPanel = new GridBagConstraints();
		gbc_statusPanel.gridx = 0;
		gbc_statusPanel.gridy = 1;
		gbc_statusPanel.anchor = GridBagConstraints.WEST;
		contentPane.add(new StatusPanel(currentUser), gbc_statusPanel);
		
		// okButtonPanel
		GridBagConstraints gbc_okButtonPanel = new GridBagConstraints();
		gbc_okButtonPanel.gridx = 0;
		gbc_okButtonPanel.gridy = 2;
		contentPane.add(new OKButtonPanel(this, appointment, currentUser), gbc_okButtonPanel);
		
		this.pack();
		this.setLocationRelativeTo(parent);
		this.setVisible(true);
	}
}
