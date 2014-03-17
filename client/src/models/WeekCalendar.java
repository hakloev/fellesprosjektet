package models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class WeekCalendar extends DefaultTableModel implements NetInterface, Iterator<Appointment>{
	
	
	private Employee employee;
	private ArrayList<Appointment> appointmentList;
	private int weekNumber;
	private int iteratorIndex;
	
	
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
	
	
	public WeekCalendar(Employee employee, int weekNumber) {
		this.employee = employee;
		this.weekNumber = weekNumber;
		iteratorIndex = 0;
	}
	
	
	
	@Override
	public void initialize() {
		// TODO Get appointments for this employee for the specific week
		
		
	}
	
	@Override
	public void refresh() {
		// TODO Clear first maybe?
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


	@Override
	public boolean hasNext() {
		return iteratorIndex < appointmentList.size();
	}

	@Override
	public Appointment next() {
		return appointmentList.get(iteratorIndex++);
	}

	@Override
	public void remove() {
		// Not allowed!
	}
	
	
	public void addAppointment(Appointment appointment) {
		appointmentList.add(appointment);
	}
	
	
	/* Don't ask.. */
	public void resetDefaultCalendar() {
		emptyCalendar = new Object[][] {
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
	columnTitles = new String[] {
			"time", "monday", "tuesday", "wednsday", "thursday", "friday", "saturday", "sunday"};
	}
    public void setValueAt(Appointment appointment){
        Calendar newAppointment = appointment.getStartCal();
        int row = newAppointment.get(Calendar.DAY_OF_WEEK);
        int column = newAppointment.get(Calendar.HOUR_OF_DAY);
        if(column < 7 || column > 20){
            System.out.println("InvalidTimeException");
        }
        else{
            emptyCalendar[row][column] = appointment.getDescription();
        }
    }
	
	
}
