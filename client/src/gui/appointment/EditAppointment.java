package gui.appointment;

import gui.CalendarView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import models.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("serial")
public class EditAppointment extends JDialog {
	
	
	private JPanel contentPane;

	
	EditButtonPanel editButtonPanel;
	private DetailsPanel detailsPanel;
	
	
	public EditAppointment(JFrame parent, Appointment appointment) {
		super(parent, true);
		this.setTitle("Rediger avtale");
		this.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		
		ParticipantListModel plModel = appointment.getParticipantList();
		Participant currentUser = plModel.get(plModel.indexOf(new Participant( ((CalendarView)parent).getLoggedInEmployee() )));
		
		GridBagLayout gbl = new GridBagLayout();
		contentPane.setLayout(gbl);
		
		// detailsPanel
		GridBagConstraints gbc_detailsPanel = new GridBagConstraints();
		gbc_detailsPanel.gridx = 0;
		gbc_detailsPanel.gridy = 0;
		
		// TODO Send proper time
		detailsPanel = new DetailsPanel(this, appointment, Calendar.getInstance(), currentUser);   // Temporary use of new date, date can be fetched from the Calendar View
		contentPane.add(detailsPanel, gbc_detailsPanel);

		
		// okButtonPanel
		GridBagConstraints gbc_okButtonPanel = new GridBagConstraints();
		gbc_okButtonPanel.gridx = 0;
		gbc_okButtonPanel.gridy = 1;
		gbc_okButtonPanel.gridwidth = 2;
		contentPane.add(new OKButtonPanel(this, appointment, currentUser), gbc_okButtonPanel);
		
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
	
	
	public void updateUI() {
		detailsPanel.updateUI();
	}
	
	
    public Participant getSelectedParticipant(){
        return this.detailsPanel.getSelectedParticipant();
    }
    
    
}

