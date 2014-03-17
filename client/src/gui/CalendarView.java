package gui;

import gui.appointment.*;
import models.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
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

import controllers.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class CalendarView extends JFrame {

	private JTable calendarTable;
	private JPanel contentPane;
	private JComboBox<Integer> weekComboBox;
	private JComboBox<Employee> employeeComboBox;
	private JLabel usernameLabel;
	private WeekCalendar calendarTableModel;
	private JList<Notification> notificationList;
	
	private JFrame thisFrame;
	
	private String[] loggedInUser = new String[1]; // point to something mutable so we can give to login screen


	/**
	 * Create the main view.
	 */
	public CalendarView() {
		thisFrame = this;
		this.setTitle("Kalender - Firma X");
		this.setResizable(false);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(windowListener);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		
		GridBagLayout gbl = new GridBagLayout();
		//gbl.rowHeights = new int[]{0, 0};
		contentPane.setLayout(gbl);
		
		addTopPanel();
		addCalendar();
		addRightPanel();
		
		this.pack();
		this.setLocationRelativeTo(null);
		
		new LoginScreen(this, loggedInUser);
		usernameLabel.setText(loggedInUser[0]);
		this.setVisible(true);
		
	}

	
	private void addTopPanel() {
		JPanel topPanel = new JPanel();
		GridBagConstraints gbc_topPanel = new GridBagConstraints();
		gbc_topPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_topPanel.gridx = 0;
		gbc_topPanel.gridy = 0;
		gbc_topPanel.gridwidth = 2;
		gbc_topPanel.anchor = GridBagConstraints.WEST;
		contentPane.add(topPanel, gbc_topPanel);
		
		GridBagLayout gbl_topPanel = new GridBagLayout();
		//gbl_topPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		//gbl_topPanel.rowHeights = new int[]{0};
		gbl_topPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0, 0};
		//gbl_topPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		topPanel.setLayout(gbl_topPanel);

		/* Previous week */
		JButton previousBbutton = new JButton("<-");
		GridBagConstraints gbc_previousBbutton = new GridBagConstraints();
		gbc_previousBbutton.anchor = GridBagConstraints.WEST;
		gbc_previousBbutton.insets = new Insets(5, 5, 5, 5);
		gbc_previousBbutton.gridx = 0;
		gbc_previousBbutton.gridy = 0;
		topPanel.add(previousBbutton, gbc_previousBbutton);
		previousBbutton.addActionListener(actionListener);

		/* Next week */
		JButton nextButton = new JButton("->");
		GridBagConstraints gbc_nextButton = new GridBagConstraints();
		gbc_nextButton.anchor = GridBagConstraints.WEST;
		gbc_nextButton.insets = new Insets(5, 0, 5, 5);
		gbc_nextButton.gridx = 1;
		gbc_nextButton.gridy = 0;
		topPanel.add(nextButton, gbc_nextButton);
		nextButton.addActionListener(actionListener);
		
		/* Week */
		JLabel weekLabel = new JLabel("Uke");
		GridBagConstraints gbc_weekLabel = new GridBagConstraints();
		gbc_weekLabel.anchor = GridBagConstraints.WEST;
		gbc_weekLabel.insets = new Insets(5, 0, 5, 5);
		gbc_weekLabel.gridx = 2;
		gbc_weekLabel.gridy = 0;
		topPanel.add(weekLabel, gbc_weekLabel);

		weekComboBox = new JComboBox<Integer>();
		GridBagConstraints gbc_weekComboBox = new GridBagConstraints();
		gbc_weekComboBox.anchor = GridBagConstraints.WEST;
		gbc_weekComboBox.insets = new Insets(5, 0, 5, 5);
		gbc_weekComboBox.gridx = 3;
		gbc_weekComboBox.gridy = 0;
		topPanel.add(weekComboBox, gbc_weekComboBox);
		for (int week = 1; week <= 52; week++) {
			weekComboBox.addItem(week);
		}
		/* test code */
		weekComboBox.setSelectedItem(11);
		/* end test code */
		
		/* Year */
		JSpinner yearSpinner = new JSpinner();
		yearSpinner.setModel(new SpinnerNumberModel(new Integer(2014), new Integer(1970), new Integer(2114), new Integer(1)));
		GridBagConstraints gbc_yearSpinner = new GridBagConstraints();
		gbc_yearSpinner.anchor = GridBagConstraints.WEST;
		gbc_yearSpinner.insets = new Insets(5, 0, 5, 5);
		gbc_yearSpinner.gridx = 4;
		gbc_yearSpinner.gridy = 0;
		topPanel.add(yearSpinner, gbc_yearSpinner);
		
		/* Showing calendar of employee */
		JLabel showingLabel = new JLabel("Viser kalender for");
		GridBagConstraints gbc_showingLabel = new GridBagConstraints();
		gbc_showingLabel.anchor = GridBagConstraints.WEST;
		gbc_showingLabel.insets = new Insets(5, 25, 5, 5);
		gbc_showingLabel.gridx = 5;
		gbc_showingLabel.gridy = 0;
		topPanel.add(showingLabel, gbc_showingLabel);

		employeeComboBox = new JComboBox<Employee>();
		GridBagConstraints gbc_employeeComboBox = new GridBagConstraints();
		gbc_employeeComboBox.anchor = GridBagConstraints.WEST;
		gbc_showingLabel.insets = new Insets(5, 0, 5, 5);
		gbc_employeeComboBox.gridx = 6;
		gbc_employeeComboBox.gridy = 0;
		topPanel.add(employeeComboBox, gbc_employeeComboBox);
		employeeComboBox.setPreferredSize(new Dimension(200, 23));
		EmployeeComboBoxModel ecbModel = new EmployeeComboBoxModel();
		employeeComboBox.setModel(ecbModel);
		
		/* Logged in user */
		JLabel loginLabel = new JLabel("Innlogget som:");
		GridBagConstraints gbc_loginLabel = new GridBagConstraints();
		gbc_loginLabel.anchor = GridBagConstraints.EAST;
		gbc_loginLabel.insets = new Insets(5, 0, 5, 5);
		gbc_loginLabel.gridx = 7;
		gbc_loginLabel.gridy = 0;
		topPanel.add(loginLabel, gbc_loginLabel);
		
		usernameLabel = new JLabel();
		GridBagConstraints gbc_usernameLabel = new GridBagConstraints();
		gbc_usernameLabel.anchor = GridBagConstraints.EAST;
		gbc_usernameLabel.insets = new Insets(5, 0, 5, 5);
		gbc_usernameLabel.gridx = 8;
		gbc_usernameLabel.gridy = 0;
		topPanel.add(usernameLabel, gbc_usernameLabel);
		//usernameLabel.setPreferredSize(new Dimension(50, 14));
		usernameLabel.setForeground(new Color(150, 0, 0));
		
		JButton logoutButton = new JButton("Logg ut");
		GridBagConstraints gbc_logoutButton = new GridBagConstraints();
		gbc_logoutButton.anchor = GridBagConstraints.EAST;
		gbc_logoutButton.insets = new Insets(5, 0, 5, 5);
		gbc_logoutButton.gridx = 9;
		gbc_logoutButton.gridy = 0;
		topPanel.add(logoutButton, gbc_logoutButton);
		logoutButton.addActionListener(actionListener);
		
	}
	
	
	private void addRightPanel() {
		JPanel rightPanel = new JPanel();
		GridBagConstraints gbc_rightPanel = new GridBagConstraints();
		gbc_rightPanel.anchor = GridBagConstraints.EAST;
		gbc_rightPanel.fill = GridBagConstraints.VERTICAL;
		gbc_rightPanel.gridx = 1;
		gbc_rightPanel.gridy = 1;
		contentPane.add(rightPanel, gbc_rightPanel);
		
		GridBagLayout gbl_rightPanel = new GridBagLayout();
		gbl_rightPanel.rowHeights = new int[]{0, 0, 0, 0, 30, 0, 0};
		gbl_rightPanel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		rightPanel.setLayout(gbl_rightPanel);

		JButton newAppointmentButton = new JButton("Ny avtale");
		GridBagConstraints gbc_newAppointmentButton = new GridBagConstraints();
		gbc_newAppointmentButton.insets = new Insets(5, 0, 5, 5);
		gbc_newAppointmentButton.anchor = GridBagConstraints.WEST;
		gbc_newAppointmentButton.gridx = 0;
		gbc_newAppointmentButton.gridy = 1;
		rightPanel.add(newAppointmentButton, gbc_newAppointmentButton);
		newAppointmentButton.addActionListener(actionListener);

		JButton showAppointmentButton = new JButton("Avtalevisning");
		GridBagConstraints gbc_showAppointmentButton = new GridBagConstraints();
		gbc_showAppointmentButton.insets = new Insets(0, 0, 5, 5);
		gbc_showAppointmentButton.anchor = GridBagConstraints.WEST;
		gbc_showAppointmentButton.gridx = 0;
		gbc_showAppointmentButton.gridy = 2;
		rightPanel.add(showAppointmentButton, gbc_showAppointmentButton);
		showAppointmentButton.addActionListener(actionListener);

		JButton deleteAppointmentButton = new JButton("Slett avtale");
		GridBagConstraints gbc_deleteAppointmentButton = new GridBagConstraints();
		gbc_deleteAppointmentButton.insets = new Insets(0, 0, 5, 5);
		gbc_deleteAppointmentButton.anchor = GridBagConstraints.WEST;
		gbc_deleteAppointmentButton.gridx = 0;
		gbc_deleteAppointmentButton.gridy = 3;
		rightPanel.add(deleteAppointmentButton, gbc_deleteAppointmentButton);
		deleteAppointmentButton.addActionListener(actionListener);
		
		newAppointmentButton.setPreferredSize(showAppointmentButton.getPreferredSize());
		deleteAppointmentButton.setPreferredSize(showAppointmentButton.getPreferredSize());

		JScrollPane notificationScrollPane = new JScrollPane();
		GridBagConstraints gbc_notificationScrollPane = new GridBagConstraints();
		gbc_notificationScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_notificationScrollPane.gridx = 0;
		gbc_notificationScrollPane.gridy = 5;
		rightPanel.add(notificationScrollPane, gbc_notificationScrollPane);
		notificationScrollPane.setPreferredSize(new Dimension(180, 200));
		
		notificationList = new JList<Notification>(new NotificationListModel());
		notificationScrollPane.setViewportView(notificationList);
		
		JLabel notificationLabel = new JLabel("Varsler:");
		notificationScrollPane.setColumnHeaderView(notificationLabel);
	}
	
	
	
	private void addCalendar() {
		
		/* Kalendervisning */
		JPanel calendarPanel = new JPanel();
		GridBagConstraints gbc_calendarPanel = new GridBagConstraints();
		gbc_calendarPanel.fill = GridBagConstraints.BOTH;
		gbc_calendarPanel.gridx = 0;
		gbc_calendarPanel.gridy = 1;
		contentPane.add(calendarPanel, gbc_calendarPanel);
		
		calendarTable = new JTable();
		//calendarTable.setColumnSelectionAllowed(true);
		calendarTable.setCellSelectionEnabled(true);
		//calendarTable.setFillsViewportHeight(true);
		calendarTable.setPreferredSize(new Dimension(800, 435));
		calendarTable.setRowHeight(29);
		calendarTable.setGridColor(new Color(200, 200, 200));
		calendarPanel.add(calendarTable);
		
		calendarTableModel = new WeekCalendar();
		calendarTable.setModel(calendarTableModel);
	}

	
	ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			if (actionCommand.equals("Ny avtale")) {
				new EditAppointment(thisFrame, new Appointment());
				
			} else if (actionCommand.equals("Avtalevisning")) {
				new ViewAppointment(thisFrame, new Appointment());
				
			} else if(actionCommand.equals("Slett avtale")) {
				int choice = JOptionPane.showConfirmDialog(thisFrame,
						"Er du sikker på at du vil slette denne avtalen?", "Bekreft", JOptionPane.YES_NO_OPTION);
				
				if (choice == 0) {
					// slett avtale
					System.out.println("Avtale slettet");
				}
				
			} else if(actionCommand.equals("->")) {
				weekComboBox.setSelectedItem((Integer)weekComboBox.getSelectedItem() + 1);
				
			} else if(actionCommand.equals("<-")) {
				weekComboBox.setSelectedItem((Integer)weekComboBox.getSelectedItem() - 1);
				
			} else if(actionCommand.equals("Logg ut")) {
				SocketListener sl = SocketListener.getClientSocketListener();
				if (sl != null) {
					//OutboundWorker ow = sl.getOutboundWorker();
					//if (ow != null) ow.logout();
					//sl.closeSocket();
				}
				
				employeeComboBox.setSelectedItem(null);
				loggedInUser[0] = "";
				usernameLabel.setText("");
				
				// TODO
				// clear calendar table
				calendarTableModel.resetDefaultCalendar();
				calendarTable.setModel(new WeekCalendar());
				// clear notifications
				notificationList.setModel(new NotificationListModel());
				// reset week / year to current
				
				new LoginScreen(thisFrame, loggedInUser);
				usernameLabel.setText(loggedInUser[0]);
				
			}
		}
	};
	
	
	WindowAdapter windowListener = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent we) {
			SocketListener listener = SocketListener.getClientSocketListener();
			if (listener != null) listener.closeSocket();
			
			System.exit(0);
			
		}
		
		@Override
		public void windowClosed(WindowEvent we) {
			System.out.println("closed");
		}
	};
	
	
	public String getLoggedInUser() {
		return loggedInUser[0]; // return non-mutable string
	}
	
	
}

