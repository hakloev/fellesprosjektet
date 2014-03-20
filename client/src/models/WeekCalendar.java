package models;

import controllers.LogoutException;
import controllers.OutboundWorker;
import controllers.ResponseWaiter;
import controllers.SocketListener;

import org.json.simple.JSONObject;

import java.util.Calendar;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class WeekCalendar extends DefaultTableModel implements NetInterface {


	private Employee employee;
	private int week;
	private int year;

	private JSONObject json;


	private static Object[][] emptyCalendar = new Object[][] {
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
	};
	private static Object[] columnTitles = new String[] {
		"time", "monday", "tuesday", "wednsday", "thursday", "friday", "saturday", "sunday"};

	
	public WeekCalendar() {
		super(emptyCalendar, columnTitles);
	}


	public WeekCalendar(Employee employee, int weekNumber, int year) {
		super(emptyCalendar, columnTitles);
		this.employee = employee;
		this.week = weekNumber;
		this.year = year;
	}



	@SuppressWarnings("unchecked")
	@Override
	public void initialize() throws LogoutException {

		json = new JSONObject();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("employee",this.getEmployee().getUsername());
		jsonObject.put("week",this.week);
		jsonObject.put("year",this.year);
		json.put("request","weekcalendar");
		json.put("dbmethod","initialize");
		json.put("model",jsonObject);
		OutboundWorker.sendRequest(json);

		Object[] response = new Object[1];
        new ResponseWaiter(SocketListener.getSL(), response);
        if (response[0] != null && response[0] instanceof WeekCalendar) {
        	WeekCalendar weekCal = (WeekCalendar)response[0];
        	Vector dataVector = weekCal.getDataVector();
        	for (Object vector : dataVector) {
        		for (Object cell : (Vector)vector) {
        			if (cell != null && cell instanceof CalendarCell) {
        				for (Appointment app : (CalendarCell)cell) {
        					this.addAppointment((Appointment)app);
        				}
        			}
        		}
        	}
        }
	}

	@Override
	public void refresh() throws LogoutException {
		this.initialize();
	}

	@Override
	public void save() {
		// This should probably not be saved as one.
		// Server access should be administered on each Appointment object
	}

	@Override
	public void delete() {
		// This should probably not be deleted as one
		// Server access should be administered on each Appointment object
	}


	public Employee getEmployee() {
		return employee;
	}

	/* Don't ask.. */
	public void resetDefaultCalendar() {
		emptyCalendar = new Object[][] {
				{null, " Mandag", " Tirsdag", " Onsdag", " Torsdag", " Fredag", " Lørdag", " Søndag"},
				{" 07.00", null, null, null, null, null, null, null},
				{" 08.00", null, null, null, null, null, null, null},
				{" 09.00", null, null, null, null, null, null, null},
				{" 10.00", null, null, null, null, null, null, null},
				{" 11.00", null, null, null, null, null, null, null},
				{" 12.00", null, null, null, null, null, null, null},
				{" 13.00", null, null, null, null, null, null, null},
				{" 14.00", null, null, null, null, null, null, null},
				{" 15.00", null, null, null, null, null, null, null},
				{" 16.00", null, null, null, null, null, null, null},
				{" 17.00", null, null, null, null, null, null, null},
				{" 18.00", null, null, null, null, null, null, null},
				{" 19.00", null, null, null, null, null, null, null},
				{" 20.00", null, null, null, null, null, null, null},
		};
		columnTitles = new String[] {
				"time", "monday", "tuesday", "wednsday", "thursday", "friday", "saturday", "sunday"};
	}

	
	public void addAppointment(Appointment appointment) {
		Calendar dateTime = appointment.getStartDateTime();
		//if (dateTime.getWeekYear() == week){
			int column = dateTime.get(Calendar.DAY_OF_WEEK);
			if (column == Calendar.SUNDAY) {
				column = 7;
			} else {
				column -= 1;
			}
			int row = dateTime.get(Calendar.HOUR_OF_DAY);
			row -= 6;
			System.out.println("Column: " + column);
			System.out.println("Row: " + row);
			if (column > 0 && column < 8 && row > 0 && row < 15) {
				CalendarCell cell = (CalendarCell)this.getValueAt(row, column);
				if (cell == null){
					System.out.println("Found null");
					cell = new CalendarCell();
					this.setValueAt(cell, row, column);
					
				} else if (! (cell instanceof CalendarCell)) {
					System.out.println("Not a calendar cell!");
					return;
				}
				cell.addAppointment(appointment);
				System.out.println("Appointment added; " + appointment);
				System.out.println(this.getValueAt(row, column));
			}
		//}
	}
	
	
	public void removeAppointment(Appointment appointment) {
		int hour = appointment.getStartDateTime().get(Calendar.HOUR_OF_DAY);
		hour-= 6;
		int day = appointment.getStartDateTime().get(Calendar.DAY_OF_WEEK);
		if (day == 1){
			day = 7;
		} else {
			day -= 1;
		}
		CalendarCell cell = (CalendarCell) this.getValueAt(day, hour);
		if (cell != null){
			cell.removeAppointment(appointment);
		}
	}


}


