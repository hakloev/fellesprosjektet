package gui.appointment;

import gui.*;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import models.*;

@SuppressWarnings("serial")
class DetailsPanel extends JPanel implements PropertyChangeListener, FocusListener, ChangeListener {


	private JDialog parent;

	private JSpinner dateSpinner;
	private JSpinner startTimeSpinner;
	private JSpinner stopTimeSpinner;
	private JSpinner durationSpinner;
	private JTextField placeTextField;
	private JTextField alarmTextField;
	private JTextArea descriptionTextArea;
	private JButton btnVelgRom;
	private JList<Participant> participantList;

	private boolean enabled = true;

	private Appointment appointment;
	private Calendar[] alarmTime = new Calendar[1];
	private Participant currentUser;


	DetailsPanel(JDialog parent, Appointment appointment, Calendar startDate, Participant currentUser) {
		this.appointment = appointment;
		appointment.addPropertyChangeListener(this);
		this.parent = parent;
		this.currentUser = currentUser;

		Date startDateDate = new Date(startDate.getTimeInMillis());

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

		dateSpinner = new JSpinner(new SpinnerDateModel());
		dateSpinner.addChangeListener(this);
		JSpinner.DateEditor timeEditor01 = new JSpinner.DateEditor(dateSpinner, "dd.MM.yyyy");
		dateSpinner.setEditor(timeEditor01);
		dateSpinner.setValue(startDateDate);
		dateSpinner.addFocusListener(this);
		GridBagConstraints gbc_dateTextField = new GridBagConstraints();
		gbc_dateTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateTextField.insets = new Insets(5, 0, 5, 5);
		gbc_dateTextField.gridx = 1;
		gbc_dateTextField.gridy = 0;
		this.add(dateSpinner, gbc_dateTextField);
		//dateSpinner.setColumns(10);

		/* Start tid */
		JLabel lblStart = new JLabel("Start");
		GridBagConstraints gbc_lblStart = new GridBagConstraints();
		gbc_lblStart.anchor = GridBagConstraints.EAST;
		gbc_lblStart.insets = new Insets(0, 5, 5, 5);
		gbc_lblStart.gridx = 0;
		gbc_lblStart.gridy = 1;
		this.add(lblStart, gbc_lblStart);

		startTimeSpinner = new JSpinner(new SpinnerDateModel());
		startTimeSpinner.addChangeListener(this);
		JSpinner.DateEditor timeEditor02 = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
		startTimeSpinner.setEditor(timeEditor02);
		startTimeSpinner.setValue(startDateDate);
		startTimeSpinner.addFocusListener(this);
		GridBagConstraints gbc_startTimeTextField = new GridBagConstraints();
		gbc_startTimeTextField.insets = new Insets(0, 0, 5, 5);
		gbc_startTimeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_startTimeTextField.gridx = 1;
		gbc_startTimeTextField.gridy = 1;
		this.add(startTimeSpinner, gbc_startTimeTextField);
		//startTimeSpinner.setColumns(10);

		/* Varighet */  // durationSpinner must exist before endTime is set in Appointment!!!
		JLabel lblVarighet = new JLabel("Varighet");
		GridBagConstraints gbc_lblVarighet = new GridBagConstraints();
		gbc_lblVarighet.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblVarighet.insets = new Insets(0, 5, 5, 5);
		gbc_lblVarighet.gridx = 0;
		gbc_lblVarighet.gridy = 3;
		this.add(lblVarighet, gbc_lblVarighet);

		durationSpinner = new JSpinner(new SpinnerDateModel());
		durationSpinner.addChangeListener(this);
		JSpinner.DateEditor timeEditor04 = new JSpinner.DateEditor(durationSpinner, "HH:mm");
		durationSpinner.setEditor(timeEditor04);
		//durationSpinner.setValue(new Date());    This should be set via propertyChange
		durationSpinner.addFocusListener(this);
		GridBagConstraints gbc_durationTextField = new GridBagConstraints();
		gbc_durationTextField.insets = new Insets(0, 0, 5, 5);
		gbc_durationTextField.anchor = GridBagConstraints.NORTH;
		gbc_durationTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_durationTextField.gridx = 1;
		gbc_durationTextField.gridy = 3;
		this.add(durationSpinner, gbc_durationTextField);
		//durationSpinner.setColumns(10);

		/* Slutt tid */
		JLabel lblSlutt = new JLabel("Slutt");
		GridBagConstraints gbc_lblSlutt = new GridBagConstraints();
		gbc_lblSlutt.anchor = GridBagConstraints.EAST;
		gbc_lblSlutt.insets = new Insets(0, 5, 5, 5);
		gbc_lblSlutt.gridx = 0;
		gbc_lblSlutt.gridy = 2;
		this.add(lblSlutt, gbc_lblSlutt);

		stopTimeSpinner = new JSpinner(new SpinnerDateModel());
		stopTimeSpinner.addChangeListener(this);
		JSpinner.DateEditor timeEditor03 = new JSpinner.DateEditor(stopTimeSpinner, "HH:mm");
		stopTimeSpinner.setEditor(timeEditor03);
		Date endDate = new Date(startDateDate.getTime());
		endDate.setHours(startDateDate.getHours()+1);
		this.appointment.setEnd(endDate);
		stopTimeSpinner.setValue(endDate);
		stopTimeSpinner.addFocusListener(this);
		GridBagConstraints gbc_stopTimeTextField = new GridBagConstraints();
		gbc_stopTimeTextField.insets = new Insets(0, 0, 5, 5);
		gbc_stopTimeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_stopTimeTextField.gridx = 1;
		gbc_stopTimeTextField.gridy = 2;
		this.add(stopTimeSpinner, gbc_stopTimeTextField);
		//stopTimeSpinner.setColumns(10);

		/* Beskrivelse */
		JScrollPane descriptionScrollPane = new JScrollPane();
		GridBagConstraints gbc_descriptionScrollPane = new GridBagConstraints();
		gbc_descriptionScrollPane.gridheight = 4;
		gbc_descriptionScrollPane.insets = new Insets(5, 5, 5, 5);
		gbc_descriptionScrollPane.fill = GridBagConstraints.BOTH;
		gbc_descriptionScrollPane.gridx = 2;
		gbc_descriptionScrollPane.gridy = 0;
		this.add(descriptionScrollPane, gbc_descriptionScrollPane);
		descriptionScrollPane.setPreferredSize(new Dimension(120, 120));

		JLabel lblBeskrivelse = new JLabel(" Beskrivelse");
		descriptionScrollPane.setColumnHeaderView(lblBeskrivelse);

		descriptionTextArea = new JTextArea();
		descriptionTextArea.addFocusListener(this);
		descriptionTextArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		descriptionScrollPane.setViewportView(descriptionTextArea);

		/* Deltagere */
		JScrollPane participantScrollPane = new JScrollPane();
		GridBagConstraints gbc_participantScrollPane = new GridBagConstraints();
		gbc_participantScrollPane.gridheight = 4;
		gbc_participantScrollPane.insets = new Insets(5, 5, 5, 5);
		gbc_participantScrollPane.fill = GridBagConstraints.BOTH;
		gbc_participantScrollPane.gridx = 3;
		gbc_participantScrollPane.gridy = 0;
		this.add(participantScrollPane, gbc_participantScrollPane);
		participantScrollPane.setPreferredSize(new Dimension(120, 120));

		JLabel lblDeltagere = new JLabel(" Deltagere");
		participantScrollPane.setColumnHeaderView(lblDeltagere);

		participantList = new JList<Participant>(appointment.getParticipantList());
		participantScrollPane.setViewportView(participantList);
		participantList.addListSelectionListener(pllsl);
		participantList.setCellRenderer(new ParticipantRenderer());

		/* Sted */
		JLabel lblSted = new JLabel("Sted");
		GridBagConstraints gbc_lblSted = new GridBagConstraints();
		gbc_lblSted.anchor = GridBagConstraints.EAST;
		gbc_lblSted.insets = new Insets(5, 5, 5, 5);
		gbc_lblSted.gridx = 0;
		gbc_lblSted.gridy = 4;
		this.add(lblSted, gbc_lblSted);

		placeTextField = new JTextField();
		GridBagConstraints gbc_placeTextField = new GridBagConstraints();
		gbc_placeTextField.gridwidth = 2;
		gbc_placeTextField.insets = new Insets(5, 0, 5, 5);
		gbc_placeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_placeTextField.gridx = 1;
		gbc_placeTextField.gridy = 4;
		this.add(placeTextField, gbc_placeTextField);

		btnVelgRom = new JButton("Velg rom");
		GridBagConstraints gbc_btnVelgRom = new GridBagConstraints();
		gbc_btnVelgRom.anchor = GridBagConstraints.WEST;
		gbc_btnVelgRom.insets = new Insets(5, 0, 5, 5);
		gbc_btnVelgRom.gridx = 3;
		gbc_btnVelgRom.gridy = 4;
		this.add(btnVelgRom, gbc_btnVelgRom);
		btnVelgRom.addActionListener(actionListener);

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
		alarmTextField.setEditable(false);
		alarmTextField.setColumns(11);

		JButton btnVelgTid = new JButton("Velg tid");
		GridBagConstraints gbc_btnVelgTid = new GridBagConstraints();
		gbc_btnVelgTid.insets = new Insets(0, 0, 5, 5);
		gbc_btnVelgTid.anchor = GridBagConstraints.WEST;
		gbc_btnVelgTid.gridx = 2;
		gbc_btnVelgTid.gridy = 5;
		this.add(btnVelgTid, gbc_btnVelgTid);
		btnVelgTid.addActionListener(actionListener);

		if (currentUser.getAlarm() != null) {
			alarmTime[0] = currentUser.getAlarm();
			setAlarmTextField();
		}
	}


	@Override
	public void setEnabled(boolean enabled) {
		dateSpinner.setEnabled(enabled);
		startTimeSpinner.setEnabled(enabled);
		stopTimeSpinner.setEnabled(enabled);
		durationSpinner.setEnabled(enabled);
		placeTextField.setEditable(enabled);
		descriptionTextArea.setEditable(enabled);
		//participantList.setEnabled(enabled);
		this.enabled = enabled;
		if (! enabled) this.remove(btnVelgRom);
	}


	@Override
	public void updateUI() {
		super.updateUI();
		if (participantList != null) participantList.updateUI();
	}


	public Participant getSelectedParticipant(){
		return this.participantList.getSelectedValue();
	}


	ListSelectionListener pllsl = new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent lse) {
			if (! enabled) {
				participantList.clearSelection();
				participantList.transferFocus();
				return;
			}

			if (! (parent instanceof EditAppointment)) return;
			EditAppointment editParent = (EditAppointment)parent;

			if (participantList.getSelectedValue() == null) {
				editParent.editButtonPanel.setBothStatusButtonsEnabled(false);
				editParent.editButtonPanel.setButtonSlettEnabled(false);
				return;
			}

			if ( participantList.getSelectedValue().equals( ((ParticipantListModel)participantList.getModel()).getAppoinmentLeader() ) ) {
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

	/*
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Velg tid")) {
            String time = this.appointment.getStart();
            String date = this.appointment.getDate();
            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(0,2)));
            startTime.set(Calendar.MONTH,Integer.parseInt(date.substring(3,5)) + 1);
            startTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0,2)));
            startTime.set(Calendar.MINUTE, Integer.parseInt(time.substring(3,5)));
            new Alarm(parent, startTime, alarmCalendar);
            setAlarmTextField();

        }
    }
    */

	public void setAlarmTextField(){
		if (alarmTime[0] != null) {
			String alarmText = new String();
			alarmText += alarmTime[0].get(Calendar.DAY_OF_MONTH) + ".";
			alarmText += (alarmTime[0].get(Calendar.MONTH) + 1) + ".";  // +1 since Calendar starts months with 0
			alarmText += alarmTime[0].get(Calendar.YEAR) + " ";
			alarmText += alarmTime[0].get(Calendar.HOUR_OF_DAY) + ":";
			alarmText += alarmTime[0].get(Calendar.MINUTE);
			alarmTextField.setText(alarmText);
		}
	}


	ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getActionCommand().equals("Velg tid")) {
				Calendar cal = Calendar.getInstance();
				if (alarmTime[0] == null) {
					cal.setTime((Date)dateSpinner.getValue());
					cal.set(Calendar.HOUR_OF_DAY, ((Date)startTimeSpinner.getValue()).getHours() );
					cal.set(Calendar.MINUTE, ((Date)startTimeSpinner.getValue()).getMinutes() );
				} else {
					cal.setTimeInMillis(alarmTime[0].getTimeInMillis());
				}

				new Alarm(parent, cal, alarmTime);
				if (alarmTime[0] != null) {
					currentUser.setAlarm(alarmTime[0]);
				}
				setAlarmTextField();

			} else if (ae.getActionCommand().equals("Velg rom")) {
				Room[] room = new Room[1];
				new RoomChooser(parent, appointment.getParticipantList().getSize(), room);
				if (room[0] != null) {
					appointment.setLocation(room[0]);
					placeTextField.setText(room[0].toString());
				}
			}
		}
	};


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("participantList")){
			participantList.setModel((ParticipantListModel) evt.getNewValue());

		}
		if (evt.getPropertyName().equals("End")){
			if(stopTimeSpinner != null){
				String end = (String) evt.getNewValue();
				Date date = new Date();
				date.setHours(Integer.parseInt(end.substring(0,2)));
				date.setMinutes(Integer.parseInt(end.substring(3,5)));
				stopTimeSpinner.setValue(date);
			}
		}
		if (evt.getPropertyName().equals("Duration")){
			if(durationSpinner != null){
				String[] duration = ( (String)evt.getNewValue() ).split(":");
				Date date = new Date();
				date.setHours(Integer.parseInt(duration[0]));
				date.setMinutes(Integer.parseInt(duration[1]));
				durationSpinner.setValue(date);
			}
		}
	}


	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Remove focuslistener?

	}


	@Override
	public void focusLost(FocusEvent arg0) {

		if (arg0.getSource() == descriptionTextArea){
			appointment.setDescription(descriptionTextArea.getText());
		}

	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if(arg0.getSource() == dateSpinner){
			appointment.setDate((Date) dateSpinner.getValue());
		}
		else if (arg0.getSource() == startTimeSpinner){
			appointment.setStart((Date) startTimeSpinner.getValue());
		}
		else if (arg0.getSource() == stopTimeSpinner){
			appointment.setEnd((Date) stopTimeSpinner.getValue());
		}
		else if (arg0.getSource() == durationSpinner){
			appointment.setDuration((Date) durationSpinner.getValue());
		}
	}


}
