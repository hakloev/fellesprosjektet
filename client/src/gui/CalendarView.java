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

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import controllers.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Iterator;

@SuppressWarnings("serial")
public class CalendarView extends JFrame {

	private JPanel contentPane;

	private JTable calendarTable;
	private WeekCalendar calendarTableModel;
	private JComboBox<Integer> weekComboBox;
	private JComboBox<Employee> employeeComboBox;
	private JLabel usernameLabel;
	private JList<Notification> notificationList;
	private JSpinner yearSpinner;
	
	private static NotificationListModel notificationListModel;

	private JFrame thisFrame;

	private Employee loggedInEmployee;


	/**
	 * Create the main view.
	 */
	public CalendarView() {
		thisFrame = this;
		this.setTitle("Kalender - Firma X");
		this.setResizable(false);
		this.addWindowListener(windowListener);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);

		GridBagLayout gbl = new GridBagLayout();
		contentPane.setLayout(gbl);

		addTopPanel();
		addCalendar();
		addRightPanel();

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		/* login */
		initializeLoggedInUser();

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
		gbl_topPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0, 0};
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
		weekComboBox.setActionCommand("week");

		/* Year */
		yearSpinner = new JSpinner();
		yearSpinner.setModel(new SpinnerNumberModel(1970, 1970, 2070, 1));
		GridBagConstraints gbc_yearSpinner = new GridBagConstraints();
		gbc_yearSpinner.anchor = GridBagConstraints.WEST;
		gbc_yearSpinner.insets = new Insets(5, 0, 5, 5);
		gbc_yearSpinner.gridx = 4;
		gbc_yearSpinner.gridy = 0;
		topPanel.add(yearSpinner, gbc_yearSpinner);

		/* Set current week and year */
		Calendar currentCal = Calendar.getInstance();
		weekComboBox.setSelectedIndex(currentCal.get(Calendar.WEEK_OF_YEAR) - 1);
		yearSpinner.setValue(currentCal.get(Calendar.YEAR));
		weekComboBox.addActionListener(actionListener); // Add this here to avoid action event from setting current week

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
		gbc_employeeComboBox.insets = new Insets(5, 0, 5, 5);
		gbc_employeeComboBox.gridx = 6;
		gbc_employeeComboBox.gridy = 0;
		topPanel.add(employeeComboBox, gbc_employeeComboBox);
		employeeComboBox.setPreferredSize(new Dimension(200, 23));
		employeeComboBox.setActionCommand("employeeComboBox");
		employeeComboBox.addActionListener(actionListener);

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

		notificationList = new JList<Notification>();
		notificationList.addMouseListener(mouseListener);
		notificationList.setCellRenderer(new NotificationRenderer());
		notificationScrollPane.setViewportView(notificationList);

		JLabel notificationLabel = new JLabel(" Varsler");
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

		calendarTableModel = new WeekCalendar();
		calendarTable = new JTable(calendarTableModel);
		calendarTable.setModel(calendarTableModel);
		calendarTable.setCellSelectionEnabled(true);
		int rowHeight = 30; // 29
		calendarTable.setPreferredSize(new Dimension(800, rowHeight * 15));
		calendarTable.setRowHeight(rowHeight);
		calendarTable.setGridColor(new Color(200, 200, 200));
		//calendarTable.setDefaultRenderer(CalendarCell.class, new DefaultTableCellRenderer());
		calendarTable.addMouseListener(mouseListener);
		calendarPanel.add(calendarTable);
	}


	ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent ae) {
			String actionCommand = ae.getActionCommand();
			int column = calendarTable.getSelectedColumn();
			int row = calendarTable.getSelectedRow();
			
			if (actionCommand.equals("Ny avtale") && loggedInEmployee.equals(calendarTableModel.getEmployee())) {
				Calendar startDate = Calendar.getInstance();
				Appointment app = new Appointment(loggedInEmployee);
				if (column > 0 && row > 0 && column < 8 && row < 15){
					startDate.set(Calendar.YEAR, (int) yearSpinner.getValue() );
					startDate.set(Calendar.WEEK_OF_YEAR, (int) weekComboBox.getSelectedItem());
					
					int day = 0;
					if (column == 7) {
						day = Calendar.SUNDAY;
					} else {
						day = column + 1;
					}
					
					startDate.set(Calendar.DAY_OF_WEEK, day);
					int hour = row + 6;
					startDate.set(Calendar.HOUR, hour);
					startDate.set(Calendar.MINUTE, 0);
				}
				app.setStartDateTime(startDate);
				try {
					new EditAppointment(thisFrame, app);
				} catch (LogoutException e) {
					logoutUser();
					initializeLoggedInUser();
					return;
				}
			}
			else if (actionCommand.equals("Avtalevisning") && loggedInEmployee.equals(calendarTableModel.getEmployee())) {
				if (column > 0 && row > 0 && column < 8 && row < 15) {
					Appointment app = ((CalendarCell)calendarTableModel.getValueAt(row, column)).getfirst();
					if (app == null) return;
					
					try {
						if (app.getAppointmentLeader().equals(loggedInEmployee)){
							new EditAppointment(thisFrame, app);
						}
						else{
							new ViewAppointment(thisFrame, app);
						}
					} catch (LogoutException e) {
						logoutUser();
						initializeLoggedInUser();
						return;
					}
				}
			}
			else if(actionCommand.equals("Slett avtale") && loggedInEmployee.equals(calendarTableModel.getEmployee())) {
				if (column > 0 && row > 0 && column < 8 && row < 15) {
					Appointment app = ((CalendarCell)calendarTableModel.getValueAt(row, column)).getfirst();
					if (app == null) return;
					
					int choice = JOptionPane.showConfirmDialog(thisFrame,
							"Er du sikker på at du vil slette denne avtalen?", "Bekreft", JOptionPane.YES_NO_OPTION);
					
					if (choice == 0) { // delete
						if (app.getAppointmentLeader().equals(loggedInEmployee)) {
							app.delete();
						} else {
							app.setShowInCalendar(false);
							try {
								app.initialize();
							} catch (LogoutException e) {
								logoutUser();
								initializeLoggedInUser();
								return;
							}
							ParticipantListModel plModel = app.getParticipantList();
							Participant currentUser = plModel.get(plModel.indexOf(new Participant(loggedInEmployee)));
							currentUser.setShowInCalendar(false);
							currentUser.setParticipantStatus(ParticipantStatus.notParticipating);
							currentUser.setAlarm(null);
							currentUser.save(app.getAppointmentID());
						}
						calendarTableModel.setValueAt(null, calendarTable.getSelectedRow(), calendarTable.getSelectedColumn());
					}
				}
			}
			else if(actionCommand.equals("->")) {
				weekComboBox.setSelectedItem((Integer)weekComboBox.getSelectedItem() + 1);
				sendWeekCalendarRequest(calendarTableModel.getEmployee());
				
			} else if(actionCommand.equals("<-")) {
				weekComboBox.setSelectedItem((Integer)weekComboBox.getSelectedItem() - 1);
				sendWeekCalendarRequest(calendarTableModel.getEmployee());

			} else if(actionCommand.equals("week")) {
				sendWeekCalendarRequest(calendarTableModel.getEmployee());
				
			} else if(actionCommand.equals("employeeComboBox")) {
				sendWeekCalendarRequest((Employee)employeeComboBox.getSelectedItem());

			} else if(actionCommand.equals("Logg ut")) {
				logoutUser();
				initializeLoggedInUser();
				
			} else if(actionCommand.split(" ")[0].equals("cellRightClick")) {
				if ( column > 0 && row > 0 && column < 8 && row < 15 && loggedInEmployee.equals(calendarTableModel.getEmployee()) ) {
					int index = Integer.parseInt(actionCommand.split(" ")[1]);
					CalendarCell calCell = (CalendarCell)calendarTable.getValueAt(row, column);
					Appointment app = calCell.get(index);
					
					try {
						if (app.getAppointmentLeader().equals(loggedInEmployee)){
							new EditAppointment(thisFrame, app);
						}
						else{
							new ViewAppointment(thisFrame, app);
						}
					} catch (LogoutException e) {
						logoutUser();
						initializeLoggedInUser();
						return;
					}
					
					
					
				}
			}
		}
	};


	WindowAdapter windowListener = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent we) {
			logoutUser();
			System.exit(0);
		}
		@Override
		public void windowClosed(WindowEvent we) {
		}
	};


	public Employee getLoggedInEmployee() {
		return loggedInEmployee;
	}


	private void logoutUser() {
		OutboundWorker.logout();
		SocketListener.getSL().close();
		
		// clear text
		employeeComboBox.setSelectedItem(null);
		loggedInEmployee = null;
		usernameLabel.setText("");

		// clear calendar table
		calendarTableModel.resetDefaultCalendar();
		calendarTableModel = new WeekCalendar();
		calendarTable.setModel(calendarTableModel);

		// clear notifications
		notificationList.setModel(new NotificationListModel());

		// reset week and year to current
		Calendar currentCal = Calendar.getInstance();
		weekComboBox.setSelectedIndex(currentCal.get(Calendar.WEEK_OF_YEAR) - 1);
		yearSpinner.setValue(currentCal.get(Calendar.YEAR));
	}


	private void initializeLoggedInUser() {
		Employee[] user = new Employee[1]; // point to something mutable so we can get the user back
		new LoginScreen(thisFrame, user);
		usernameLabel.setText(user[0].getUsername());
		loggedInEmployee = user[0];

		/* Do initialization */
		EmployeeComboBoxModel ecbModel = new EmployeeComboBoxModel();
		try {
			ecbModel.initialize();
			employeeComboBox.setModel(ecbModel);
			employeeComboBox.setSelectedItem(loggedInEmployee);
			
			NotificationListModel notiListModel = new NotificationListModel(loggedInEmployee);
			notiListModel.initialize();
			notificationList.setModel(notiListModel);
			notificationListModel = notiListModel;
			
		} catch (LogoutException e) {
			logoutUser();
			initializeLoggedInUser(); // tjuvtriks
			return;
		}

		//sendWeekCalendarRequest(loggedInEmployee); // probably redundant
	}
	
	
	public static void addNotification(Notification notification) {
		notificationListModel.addElement(notification);
	}


	private void sendWeekCalendarRequest(Employee employee) {
		if (employee != null) {
			calendarTableModel.resetDefaultCalendar();
			calendarTableModel = new WeekCalendar(employee, (int)weekComboBox.getSelectedItem(), (int)yearSpinner.getValue());
			try {
				calendarTableModel.initialize();
			} catch (LogoutException e) {
				logoutUser();
				initializeLoggedInUser();
				return;
			}
			calendarTable.setModel(calendarTableModel);
		}
	}


	private MouseAdapter mouseListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent me) {
			if (me.getSource() == notificationList) {
				Notification notification = notificationList.getSelectedValue();
				if (notification == null) return;
				
				if (! notification.isSeen()) {
					notification.setSeen(true);
					notification.save();
				}
				
				if (me.getClickCount() == 2) {
					int appointmentID = notification.getAppointmentID();
					Appointment app = new Appointment(appointmentID);
					try {
						app.initialize();
						
						if (app.getAppointmentLeader().equals(loggedInEmployee)) {
							new EditAppointment(thisFrame, app);
						}
						else{
							new ViewAppointment(thisFrame, app);
						}
					} catch (LogoutException e) {
						logoutUser();
						initializeLoggedInUser();
						return;
					}
				}
			} else if (me.getSource() == calendarTable && me.getButton() == MouseEvent.BUTTON3) {
				int column = calendarTable.getSelectedColumn();
				int row = calendarTable.getSelectedRow();
				if (column > 0 && row > 0 && column < 8 && row < 15) {
					CalendarCell calCell = (CalendarCell)calendarTable.getValueAt(row, column);
					
					JPopupMenu popup = new JPopupMenu();
					JMenuItem menuItem;
					int index = 0;
					if (calCell != null) for (Appointment app : calCell) {
						menuItem = new JMenuItem(app.toString());
						menuItem.setActionCommand("cellRightClick " + index);
						menuItem.addActionListener(actionListener);
						popup.add(menuItem);
						index++;
					}
					popup.show(me.getComponent(), me.getX(), me.getY());
				}
			}
		}
	};
	
	



}



