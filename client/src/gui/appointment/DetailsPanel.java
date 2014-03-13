package gui.appointment;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import models.*;

@SuppressWarnings("serial")
class DetailsPanel extends JPanel {
	
	
	JDialog parent;
	
	private JTextField dateTextField;
	private JTextField startTimeTextField;
	private JTextField stopTimeTextField;
	private JTextField durationTextField;
	private JTextField placeTextField;
	private JTextField alarmTextField;
	
	private JTextArea descriptionTextArea;
	
	private JList<Participant> participantList;
	
	private JButton btnVelgRom;
	
	
	DetailsPanel(JDialog parent, ParticipantListModel appointmentParticipantList) {
		this.parent = parent;
		
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		
		/* Dato */
		JLabel lblDato = new JLabel("Dato");
		GridBagConstraints gbc_lblDato = new GridBagConstraints();
		gbc_lblDato.anchor = GridBagConstraints.EAST;
		gbc_lblDato.insets = new Insets(5, 5, 5, 5);
		gbc_lblDato.gridx = 0;
		gbc_lblDato.gridy = 0;
		this.add(lblDato, gbc_lblDato);
		
		dateTextField = new JTextField();
		GridBagConstraints gbc_dateTextField = new GridBagConstraints();
		gbc_dateTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateTextField.insets = new Insets(5, 0, 5, 5);
		gbc_dateTextField.gridx = 1;
		gbc_dateTextField.gridy = 0;
		this.add(dateTextField, gbc_dateTextField);
		dateTextField.setColumns(10);
		
		/* Start tid */
		JLabel lblStart = new JLabel("Start");
		GridBagConstraints gbc_lblStart = new GridBagConstraints();
		gbc_lblStart.anchor = GridBagConstraints.EAST;
		gbc_lblStart.insets = new Insets(0, 5, 5, 5);
		gbc_lblStart.gridx = 0;
		gbc_lblStart.gridy = 1;
		this.add(lblStart, gbc_lblStart);
		
		startTimeTextField = new JTextField();
		GridBagConstraints gbc_startTimeTextField = new GridBagConstraints();
		gbc_startTimeTextField.insets = new Insets(0, 0, 5, 5);
		gbc_startTimeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_startTimeTextField.gridx = 1;
		gbc_startTimeTextField.gridy = 1;
		this.add(startTimeTextField, gbc_startTimeTextField);
		startTimeTextField.setColumns(10);
		
		/* Slutt tid */
		JLabel lblSlutt = new JLabel("Slutt");
		GridBagConstraints gbc_lblSlutt = new GridBagConstraints();
		gbc_lblSlutt.anchor = GridBagConstraints.EAST;
		gbc_lblSlutt.insets = new Insets(0, 5, 5, 5);
		gbc_lblSlutt.gridx = 0;
		gbc_lblSlutt.gridy = 2;
		this.add(lblSlutt, gbc_lblSlutt);
		
		stopTimeTextField = new JTextField();
		GridBagConstraints gbc_stopTimeTextField = new GridBagConstraints();
		gbc_stopTimeTextField.insets = new Insets(0, 0, 5, 5);
		gbc_stopTimeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_stopTimeTextField.gridx = 1;
		gbc_stopTimeTextField.gridy = 2;
		this.add(stopTimeTextField, gbc_stopTimeTextField);
		stopTimeTextField.setColumns(10);
		
		/* Varighet */
		JLabel lblVarighet = new JLabel("Varighet");
		GridBagConstraints gbc_lblVarighet = new GridBagConstraints();
		gbc_lblVarighet.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblVarighet.insets = new Insets(0, 5, 5, 5);
		gbc_lblVarighet.gridx = 0;
		gbc_lblVarighet.gridy = 3;
		this.add(lblVarighet, gbc_lblVarighet);
		
		durationTextField = new JTextField();
		GridBagConstraints gbc_durationTextField = new GridBagConstraints();
		gbc_durationTextField.insets = new Insets(0, 0, 5, 5);
		gbc_durationTextField.anchor = GridBagConstraints.NORTH;
		gbc_durationTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_durationTextField.gridx = 1;
		gbc_durationTextField.gridy = 3;
		this.add(durationTextField, gbc_durationTextField);
		durationTextField.setColumns(10);
		
		/* Beskrivelse */
		JScrollPane descriptionScrollPane = new JScrollPane();
		GridBagConstraints gbc_descriptionScrollPane = new GridBagConstraints();
		gbc_descriptionScrollPane.gridheight = 4;
		gbc_descriptionScrollPane.insets = new Insets(5, 0, 5, 5);
		gbc_descriptionScrollPane.fill = GridBagConstraints.BOTH;
		gbc_descriptionScrollPane.gridx = 2;
		gbc_descriptionScrollPane.gridy = 0;
		this.add(descriptionScrollPane, gbc_descriptionScrollPane);
		descriptionScrollPane.setPreferredSize(new Dimension(120, 120));
		
		JLabel lblBeskrivelse = new JLabel("Beskrivelse");
		descriptionScrollPane.setColumnHeaderView(lblBeskrivelse);
		
		descriptionTextArea = new JTextArea();
		descriptionTextArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		descriptionScrollPane.setViewportView(descriptionTextArea);
		
		/* Deltagere */
		JScrollPane participantScrollPane = new JScrollPane();
		GridBagConstraints gbc_participantScrollPane = new GridBagConstraints();
		gbc_participantScrollPane.gridheight = 4;
		gbc_participantScrollPane.insets = new Insets(5, 0, 5, 5);
		gbc_participantScrollPane.fill = GridBagConstraints.BOTH;
		gbc_participantScrollPane.gridx = 3;
		gbc_participantScrollPane.gridy = 0;
		this.add(participantScrollPane, gbc_participantScrollPane);
		participantScrollPane.setPreferredSize(new Dimension(120, 120));
		
		JLabel lblDeltagere = new JLabel("Deltagere");
		participantScrollPane.setColumnHeaderView(lblDeltagere);
		
		participantList = new JList<Participant>(appointmentParticipantList);
		participantScrollPane.setViewportView(participantList);
		participantList.addListSelectionListener(pllsl);
		
		/* Sted */
		JLabel lblSted = new JLabel("Sted");
		GridBagConstraints gbc_lblSted = new GridBagConstraints();
		gbc_lblSted.anchor = GridBagConstraints.EAST;
		gbc_lblSted.insets = new Insets(0, 5, 5, 5);
		gbc_lblSted.gridx = 0;
		gbc_lblSted.gridy = 4;
		this.add(lblSted, gbc_lblSted);
		
		placeTextField = new JTextField();
		GridBagConstraints gbc_placeTextField = new GridBagConstraints();
		gbc_placeTextField.gridwidth = 2;
		gbc_placeTextField.insets = new Insets(0, 0, 5, 5);
		gbc_placeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_placeTextField.gridx = 1;
		gbc_placeTextField.gridy = 4;
		this.add(placeTextField, gbc_placeTextField);
		
		btnVelgRom = new JButton("Velg rom");
		GridBagConstraints gbc_btnVelgRom = new GridBagConstraints();
		gbc_btnVelgRom.anchor = GridBagConstraints.WEST;
		gbc_btnVelgRom.insets = new Insets(0, 0, 5, 5);
		gbc_btnVelgRom.gridx = 3;
		gbc_btnVelgRom.gridy = 4;
		this.add(btnVelgRom, gbc_btnVelgRom);
		
		/* Alarm */
		JLabel lblAlarm = new JLabel("Alarm");
		GridBagConstraints gbc_lblAlarm = new GridBagConstraints();
		gbc_lblAlarm.anchor = GridBagConstraints.EAST;
		gbc_lblAlarm.insets = new Insets(0, 5, 5, 5);
		gbc_lblAlarm.gridx = 0;
		gbc_lblAlarm.gridy = 5;
		this.add(lblAlarm, gbc_lblAlarm);
		
		alarmTextField = new JTextField();
		GridBagConstraints gbc_alarmTextField = new GridBagConstraints();
		gbc_alarmTextField.insets = new Insets(0, 0, 5, 5);
		gbc_alarmTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_alarmTextField.gridx = 1;
		gbc_alarmTextField.gridy = 5;
		this.add(alarmTextField, gbc_alarmTextField);
		//alarmTextField.setColumns(10);
		
		JButton btnVelgTid = new JButton("Velg tid");
		GridBagConstraints gbc_btnVelgTid = new GridBagConstraints();
		gbc_btnVelgTid.insets = new Insets(0, 0, 5, 5);
		gbc_btnVelgTid.anchor = GridBagConstraints.WEST;
		gbc_btnVelgTid.gridx = 2;
		gbc_btnVelgTid.gridy = 5;
		this.add(btnVelgTid, gbc_btnVelgTid);
		
	}
	
	
	@Override
	public void setEnabled(boolean enabled) {
		dateTextField.setEditable(enabled);
		startTimeTextField.setEditable(enabled);
		stopTimeTextField.setEditable(enabled);
		durationTextField.setEditable(enabled);
		placeTextField.setEditable(enabled);
		descriptionTextArea.setEditable(enabled);
		participantList.setEnabled(enabled);
		this.remove(btnVelgRom);
	}
	
	
	ListSelectionListener pllsl = new ListSelectionListener() {
		
		@Override
		public void valueChanged(ListSelectionEvent lse) {
			if (! (parent instanceof EditAppointment)) return;
			EditAppointment editParent = (EditAppointment)parent;
			
			if (participantList.getSelectedValue() == null) {
				editParent.editButtonPanel.setBothStatusButtonsEnabled(false);
				editParent.editButtonPanel.setButtonSlettEnabled(false);
				return;
			}
			
			ParticipantStatus status = ((Participant)participantList.getSelectedValue()).getParticipantStatus();
			
			if (status == null) {
				editParent.editButtonPanel.setBothStatusButtonsEnabled(true);
				editParent.editButtonPanel.setButtonSlettEnabled(true);
				
			} else if (status == ParticipantStatus.participating) {
				editParent.editButtonPanel.setParticipatingEnabled_notParticipatingDisabled(false);
				editParent.editButtonPanel.setButtonSlettEnabled(true);
				
			} else if (status == ParticipantStatus.notParticipating) {
				editParent.editButtonPanel.setParticipatingEnabled_notParticipatingDisabled(true);
				editParent.editButtonPanel.setButtonSlettEnabled(true);
				
			}
			
			
		}
	};
	
	
}
