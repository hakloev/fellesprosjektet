package gui.appointment;

import gui.EditParticipants;
import gui.EditEmailRecipients;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controllers.LogoutException;
import models.*;

@SuppressWarnings("serial")
class EditButtonPanel extends JPanel implements ActionListener {


	private Appointment appointment;
	private JDialog parent;

	private JButton btnDeltar;
	private JButton btnDeltarIkke;
	private JButton btnSlett;


	EditButtonPanel(JDialog parent, Appointment appointment) {
		this.appointment = appointment;
		this.parent = parent;

		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);

		JButton btnRedigerListe = new JButton("Rediger liste");
		GridBagConstraints gbc_btnRedigerListe = new GridBagConstraints();
		gbc_btnRedigerListe.insets = new Insets(5, 0, 5, 5);
		gbc_btnRedigerListe.gridx = 0;
		gbc_btnRedigerListe.gridy = 0;
		this.add(btnRedigerListe, gbc_btnRedigerListe);
		btnRedigerListe.addActionListener(this);

		btnDeltar = new JButton("Deltar");
		GridBagConstraints gbc_btnDeltar = new GridBagConstraints();
		gbc_btnDeltar.insets = new Insets(0, 0, 5, 5);
		gbc_btnDeltar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDeltar.gridx = 0;
		gbc_btnDeltar.gridy = 1;
		this.add(btnDeltar, gbc_btnDeltar);
		btnDeltar.addActionListener(this);

		btnDeltarIkke = new JButton("Deltar ikke");
		GridBagConstraints gbc_btnDeltarIkke = new GridBagConstraints();
		gbc_btnDeltarIkke.insets = new Insets(0, 0, 5, 5);
		gbc_btnDeltarIkke.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDeltarIkke.gridx = 0;
		gbc_btnDeltarIkke.gridy = 2;
		this.add(btnDeltarIkke, gbc_btnDeltarIkke);
		btnDeltarIkke.addActionListener(this);

		btnSlett = new JButton("Slett");
		GridBagConstraints gbc_btnSlett = new GridBagConstraints();
		gbc_btnSlett.insets = new Insets(0, 0, 5, 5);
		gbc_btnSlett.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSlett.gridx = 0;
		gbc_btnSlett.gridy = 3;
		this.add(btnSlett, gbc_btnSlett);
		btnSlett.addActionListener(this);
		
		JButton btnEmail = new JButton("e-post liste");
		btnEmail.setActionCommand("email");
		GridBagConstraints gbc_btnEmail = new GridBagConstraints();
		gbc_btnEmail.insets = new Insets(0, 0, 5, 5);
		gbc_btnEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEmail.gridx = 0;
		gbc_btnEmail.gridy = 4;
		this.add(btnEmail, gbc_btnEmail);
		btnEmail.addActionListener(this);

	}


	void setBothStatusButtonsEnabled(boolean enabled) {
		btnDeltar.setEnabled(enabled);
		btnDeltarIkke.setEnabled(enabled);
	}


	void setParticipatingEnabled_notParticipatingDisabled(boolean enabled) {
		btnDeltar.setEnabled(enabled);
		btnDeltarIkke.setEnabled(! enabled);
	}


	void setButtonSlettEnabled(boolean enabled) {
		btnSlett.setEnabled(enabled);
	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("Rediger liste")) {
			try {
				new EditParticipants(parent, appointment);
			} catch (LogoutException e) {
				// TODO notify calendarView of logout
				//e.printStackTrace();
				System.out.println(e.getMessage());
			}
			
		}
        else if(ae.getActionCommand().equals(("Deltar"))){

            ((EditAppointment)parent).getSelectedParticipant().setParticipantStatus(ParticipantStatus.participating);
            btnDeltar.setEnabled(false);
            btnDeltarIkke.setEnabled(true);
            ((EditAppointment)parent).updateUI();
            
        }
        else if( ae.getActionCommand().equals("Deltar ikke")){
            ((EditAppointment)parent).getSelectedParticipant().setParticipantStatus(ParticipantStatus.notParticipating);
            btnDeltarIkke.setEnabled(false);
            btnDeltar.setEnabled(true);
            ((EditAppointment)parent).updateUI();
            
        }
        else if(ae.getActionCommand().equals("Slett")){
             int choice = JOptionPane.showConfirmDialog(null,
                "Er du sikker på at du vil slette " + ((EditAppointment)parent).getSelectedParticipant().getName() + " fra avtalen?", "Bekreft", JOptionPane.YES_NO_OPTION);

            if (choice == 0) {
            	Participant part = ((EditAppointment) parent).getSelectedParticipant();
            	appointment.getParticipantList().removeElement(part);
            	part.delete(appointment.getAppointmentID());
            }
        }
        else if (ae.getActionCommand().equals("email")) {
        	new EditEmailRecipients(parent, appointment);
        }

	}

}


