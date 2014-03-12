package gui;

import gui.appointment.*;
import models.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class CalendarView extends JFrame implements ActionListener{

	private JTable table;
	private JPanel contentPane;
	private JComboBox<Integer> weekComboBox = new JComboBox<Integer>();


	/**
	 * Create the main view.
	 */
	public CalendarView() {
		this.setTitle("Kalender - Firma X");
		this.setResizable(false);
		this.setBounds(100, 100, 1000, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		
		GridBagLayout gbl = new GridBagLayout();
		//gbl.rowHeights = new int[]{80, 80, 0};
		contentPane.setLayout(gbl);
		
		addTopPanel();
		addRightPanel();
		addCalendar();
		
		this.setVisible(true);
		EditParticipants jall = new EditParticipants(new ParticipantListModel());
		jall.setVisible(true);
	}

	
	private void addTopPanel() {
		JPanel topPanel = new JPanel();
		GridBagConstraints gbc_topPanel = new GridBagConstraints();
		gbc_topPanel.gridx = 0;
		gbc_topPanel.gridy = 0;
		gbc_topPanel.gridwidth = 2;
		gbc_topPanel.anchor = GridBagConstraints.WEST;
		contentPane.add(topPanel, gbc_topPanel);
		
		GridBagLayout gbl_topPanel = new GridBagLayout();
		//gbl_topPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		//gbl_topPanel.rowHeights = new int[]{0};
		gbl_topPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0};
		//gbl_topPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		topPanel.setLayout(gbl_topPanel);

		JButton previousBbutton = new JButton("<-");
		GridBagConstraints gbc_previousBbutton = new GridBagConstraints();
		//gbc_previousBbutton.anchor = GridBagConstraints.WEST;
		gbc_previousBbutton.insets = new Insets(0, 5, 0, 5);
		gbc_previousBbutton.gridx = 0;
		gbc_previousBbutton.gridy = 0;
		topPanel.add(previousBbutton, gbc_previousBbutton);
		previousBbutton.addActionListener(this);

		JButton nextButton = new JButton("->");
		GridBagConstraints gbc_nextButton = new GridBagConstraints();
		//gbc_nextButton.anchor = GridBagConstraints.WEST;
		gbc_nextButton.insets = new Insets(0, 0, 0, 5);
		gbc_nextButton.gridx = 1;
		gbc_nextButton.gridy = 0;
		topPanel.add(nextButton, gbc_nextButton);
		nextButton.addActionListener(this);
		
		JLabel weekLabel = new JLabel("Uke");
		GridBagConstraints gbc_weekLabel = new GridBagConstraints();
		//gbc_weekLabel.anchor = GridBagConstraints.WEST;
		gbc_weekLabel.insets = new Insets(0, 0, 0, 5);
		gbc_weekLabel.gridx = 2;
		gbc_weekLabel.gridy = 0;
		topPanel.add(weekLabel, gbc_weekLabel);

		GridBagConstraints gbc_weekComboBox = new GridBagConstraints();
		//gbc_weekComboBox.anchor = GridBagConstraints.WEST;
		gbc_weekComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_weekComboBox.gridx = 3;
		gbc_weekComboBox.gridy = 0;
		topPanel.add(weekComboBox, gbc_weekComboBox);
		//weekComboBox.setPreferredSize(new Dimension(50, 0));
		for (int week = 1; week <= 52; week++) {
			weekComboBox.addItem(week);
			
		}
		/* test code */
		weekComboBox.setSelectedItem(11);
		
		JSpinner yearSpinner = new JSpinner();
		yearSpinner.setModel(new SpinnerNumberModel(new Integer(2014), new Integer(1970), new Integer(2114), new Integer(1)));
		GridBagConstraints gbc_yearSpinner = new GridBagConstraints();
		//gbc_yearSpinner.anchor = GridBagConstraints.WEST;
		gbc_yearSpinner.insets = new Insets(0, 0, 0, 5);
		gbc_yearSpinner.gridx = 4;
		gbc_yearSpinner.gridy = 0;
		topPanel.add(yearSpinner, gbc_yearSpinner);

		JLabel showingLabel = new JLabel("Viser:");
		GridBagConstraints gbc_showingLabel = new GridBagConstraints();
		//gbc_showingLabel.anchor = GridBagConstraints.WEST;
		gbc_showingLabel.insets = new Insets(0, 0, 0, 5);
		gbc_showingLabel.gridx = 5;
		gbc_showingLabel.gridy = 0;
		topPanel.add(showingLabel, gbc_showingLabel);

		JComboBox<Employee> employeeComboBox = new JComboBox<Employee>();
		GridBagConstraints gbc_employeeComboBox = new GridBagConstraints();
		//gbc_employeeComboBox.anchor = GridBagConstraints.WEST;
		gbc_employeeComboBox.gridx = 6;
		gbc_employeeComboBox.gridy = 0;
		topPanel.add(employeeComboBox, gbc_employeeComboBox);
		employeeComboBox.setPreferredSize(new Dimension(200, 20));
		/* test code */
		employeeComboBox.addItem(new Employee("kristian", "Kristian Volden"));
	}
	
	
	private void addRightPanel() {
		JPanel rightPanel = new JPanel();
		GridBagConstraints gbc_rightPanel = new GridBagConstraints();
		gbc_rightPanel.anchor = GridBagConstraints.EAST;
		gbc_rightPanel.gridx = 1;
		gbc_rightPanel.gridy = 1;
		contentPane.add(rightPanel, gbc_rightPanel);
		
		GridBagLayout gbl_rightPanel = new GridBagLayout();
		//gbl_panel.columnWidths = new int[]{0};
		//gbl_rightPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		//gbl_panel.columnWeights = new double[]{1.0};
		gbl_rightPanel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		rightPanel.setLayout(gbl_rightPanel);

		JButton newAppointmentButton = new JButton("Ny avtale");
		GridBagConstraints gbc_newAppointmentButton = new GridBagConstraints();
		gbc_newAppointmentButton.insets = new Insets(0, 0, 5, 0);
		gbc_newAppointmentButton.gridx = 0;
		gbc_newAppointmentButton.gridy = 1;
		rightPanel.add(newAppointmentButton, gbc_newAppointmentButton);
		newAppointmentButton.addActionListener(this);

		JButton showAppointmentButton = new JButton("Avtalevisning");
		GridBagConstraints gbc_showAppointmentButton = new GridBagConstraints();
		gbc_showAppointmentButton.insets = new Insets(0, 0, 5, 0);
		gbc_showAppointmentButton.gridx = 0;
		gbc_showAppointmentButton.gridy = 2;
		rightPanel.add(showAppointmentButton, gbc_showAppointmentButton);
		showAppointmentButton.addActionListener(this);

		JButton deleteAppointmentButton = new JButton("Slett avtale");
		GridBagConstraints gbc_deleteAppointmentButton = new GridBagConstraints();
		gbc_deleteAppointmentButton.insets = new Insets(0, 0, 5, 0);
		gbc_deleteAppointmentButton.gridx = 0;
		gbc_deleteAppointmentButton.gridy = 3;
		rightPanel.add(deleteAppointmentButton, gbc_deleteAppointmentButton);
		deleteAppointmentButton.addActionListener(this);

		JScrollPane notificationScrollPane = new JScrollPane();
		GridBagConstraints gbc_notificationScrollPane = new GridBagConstraints();
		gbc_notificationScrollPane.insets = new Insets(0, 0, 5, 5);
		//gbc_notificationScrollPane.fill = GridBagConstraints.BOTH;
		gbc_notificationScrollPane.gridx = 0;
		gbc_notificationScrollPane.gridy = 4;
		rightPanel.add(notificationScrollPane, gbc_notificationScrollPane);
		notificationScrollPane.setPreferredSize(new Dimension(180, 200));
		
		JList<Notification> notificationList = new JList<Notification>(new NotificationListModel());
		notificationScrollPane.setViewportView(notificationList);
		
		JLabel notificationLabel = new JLabel("Varsler:");
		notificationScrollPane.setColumnHeaderView(notificationLabel);
	}
	
	
	
	private void addCalendar() {
		
		JPanel calendarPanel = new JPanel();
		GridBagConstraints gbc_calendarPanel = new GridBagConstraints();
		gbc_calendarPanel.gridx = 0;
		gbc_calendarPanel.gridy = 1;
		contentPane.add(calendarPanel, gbc_calendarPanel);

		table = new JTable();
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setPreferredSize(new Dimension(800, 433));
		table.setRowHeight(29);
		table.setGridColor(new Color(192, 192, 192));
		calendarPanel.add(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, "Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag", "Søndag"},
				{"07.00", null, null, null, null, null, null, null},
				{"08.00", null, null, null, null, null, null, null},
				{"09.00", null, null, null, null, null, null, null},
				{"10.00", null, null, null, null, null, null, null},
				{"11.00", null, null, null, null, null, null, null},
				{"12.00", null, null, null, null, null, null, null},
				{"13.00", null, null, null, null, null, null, null},
				{"14.00", null, null, null, null, null, null, null},
				{"15.00", null, null, null, null, null, null, null},
				{"16.00", null, null, null, null, null, null, null},
				{"17.00", null, null, null, null, null, null, null},
				{"18.00", null, null, null, null, null, null, null},
				{"19.00", null, null, null, null, null, null, null},
				{"20.00", null, null, null, null, null, null, null},
			},
			new String[] {
				"New column", "Mandag", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if (actionCommand.equals("Ny avtale")) {
			EditAppointment editApp = new EditAppointment();
			editApp.setVisible(true);
			
		} else if (actionCommand.equals("Avtalevisning")) {
			ViewAppointment viewApp = new ViewAppointment();
			viewApp.setVisible(true);
			
		} else if(actionCommand.equals("Slett avtale")){
			JOptionPane.showConfirmDialog(null,
					"Er du sikker på at du vil slette denne avtalen?", "Bekreft", JOptionPane.YES_NO_OPTION);
			
		} else if(actionCommand.equals("Neste >")){
			weekComboBox.setSelectedItem(weekComboBox.getItemAt(weekComboBox.getSelectedIndex() + 1));
			
		} else if(actionCommand.equals("< Forrige")){
			weekComboBox.setSelectedItem(weekComboBox.getItemAt(weekComboBox.getSelectedIndex() - 1));
			
		}
	}
}
