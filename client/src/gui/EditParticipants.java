package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import models.*;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

@SuppressWarnings("serial")
public class EditParticipants extends JFrame {
	
	
	private JPanel contentPane;
	private ParticipantListModel appointmentParticipantList;
	
	
	/**
	 * Create the frame.
	 */
	public EditParticipants(ParticipantListModel appointmentParticipantList) {
		this.appointmentParticipantList = appointmentParticipantList;
		
		this.setTitle("Rediger deltagere");
		this.setLocation(100, 100);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		
		GridBagLayout gbl = new GridBagLayout();
		gbl.rowHeights = new int[]{80, 80, 0};
		contentPane.setLayout(gbl);
		
		addListsPanel();
		
		addButtonPanel();
		
		this.pack();
	}
	
	
	private void addListsPanel() {
		/* Grupper */
		JScrollPane groupSscrollPane = new JScrollPane();
		groupSscrollPane.setPreferredSize(new Dimension(120, 0));
		
		JLabel lblGrupper = new JLabel("Grupper");
		groupSscrollPane.setColumnHeaderView(lblGrupper);
		
		GroupListModel groupListModel = new GroupListModel();
		groupListModel.initialize();
		JList<Group> groupList = new JList<Group>(groupListModel);
		groupSscrollPane.setViewportView(groupList);
		GridBagConstraints gbc_groupScrollPane = new GridBagConstraints();
		gbc_groupScrollPane.gridheight = 2;
		gbc_groupScrollPane.fill = GridBagConstraints.BOTH;
		gbc_groupScrollPane.insets = new Insets(5, 5, 5, 5);
		gbc_groupScrollPane.gridx = 0;
		gbc_groupScrollPane.gridy = 0;
		contentPane.add(groupSscrollPane, gbc_groupScrollPane);
		
		JButton addGroupButton = new JButton("->");
		GridBagConstraints gbc_addGroupButton = new GridBagConstraints();
		gbc_addGroupButton.gridheight = 2;
		gbc_addGroupButton.anchor = GridBagConstraints.CENTER;
		gbc_addGroupButton.insets = new Insets(5, 0, 5, 5);
		gbc_addGroupButton.gridx = 1;
		gbc_addGroupButton.gridy = 0;
		contentPane.add(addGroupButton, gbc_addGroupButton);
		
		/* Deltagere */
		JScrollPane participantScrollPane = new JScrollPane();
		participantScrollPane.setPreferredSize(new Dimension(120, 0));
		
		JLabel lblDeltagere = new JLabel("Deltagere");
		participantScrollPane.setColumnHeaderView(lblDeltagere);
		
		JList<Participant> participantList = new JList<Participant>(appointmentParticipantList);
		participantScrollPane.setViewportView(participantList);
		GridBagConstraints gbc_participantScrollPane = new GridBagConstraints();
		gbc_participantScrollPane.gridheight = 2;
		gbc_participantScrollPane.fill = GridBagConstraints.BOTH;
		gbc_participantScrollPane.insets = new Insets(5, 0, 5, 5);
		gbc_participantScrollPane.gridx = 2;
		gbc_participantScrollPane.gridy = 0;
		contentPane.add(participantScrollPane, gbc_participantScrollPane);
		
		JButton addParticipantButton = new JButton("<-");
		GridBagConstraints gbc_addParticipantButton = new GridBagConstraints();
		gbc_addParticipantButton.anchor = GridBagConstraints.SOUTH;
		gbc_addParticipantButton.insets = new Insets(5, 0, 5, 5);
		gbc_addParticipantButton.gridx = 3;
		gbc_addParticipantButton.gridy = 0;
		contentPane.add(addParticipantButton, gbc_addParticipantButton);
		
		JButton removeParticipantButton = new JButton("->");
		GridBagConstraints gbc_removeParticipantButton = new GridBagConstraints();
		gbc_removeParticipantButton.anchor = GridBagConstraints.NORTH;
		gbc_removeParticipantButton.insets = new Insets(0, 0, 5, 5);
		gbc_removeParticipantButton.gridx = 3;
		gbc_removeParticipantButton.gridy = 1;
		contentPane.add(removeParticipantButton, gbc_removeParticipantButton);
		
		/* Ansatte */
		JScrollPane employeeScrollPane = new JScrollPane();
		employeeScrollPane.setPreferredSize(new Dimension(120, 0));
		
		JLabel lblAnsatte = new JLabel("Ansatte");
		employeeScrollPane.setColumnHeaderView(lblAnsatte);
		
		EmployeeListModel employeeListModel = new EmployeeListModel();
		employeeListModel.initialize();
		JList<Employee> employeeList = new JList<Employee>(employeeListModel);
		employeeScrollPane.setViewportView(employeeList);
		GridBagConstraints gbc_employeeScrollPane = new GridBagConstraints();
		gbc_employeeScrollPane.fill = GridBagConstraints.BOTH;
		gbc_employeeScrollPane.insets = new Insets(5, 0, 5, 5);
		gbc_employeeScrollPane.gridheight = 2;
		gbc_employeeScrollPane.gridx = 4;
		gbc_employeeScrollPane.gridy = 0;
		contentPane.add(employeeScrollPane, gbc_employeeScrollPane);
		
	}
	
	
	private void addButtonPanel() {
		JPanel panel = new JPanel();
		
		JButton buttonOK = new JButton("OK");
		panel.add(buttonOK);
		
		JButton buttonAvbryt = new JButton("Avbryt");
		panel.add(buttonAvbryt);
		
		buttonOK.setPreferredSize(buttonAvbryt.getPreferredSize());
		
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.anchor = GridBagConstraints.CENTER;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		gbc_panel.gridwidth = 3;
		contentPane.add(panel, gbc_panel);
	}


	public ParticipantListModel getAppointmentParticipantList() {
		return appointmentParticipantList;
	}
	
	
}
