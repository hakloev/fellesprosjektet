package models;

import controllers.OutboundWorker;
import helperclasses.Request;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class WeekCalendar extends DefaultTableModel implements NetInterface, Iterator<Appointment>{
	
	
	private Employee employee;
	private ArrayList<Appointment> appointmentList;
	private int week;
	private int year;
	private int iteratorIndex;
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
        appointmentList = new ArrayList<Appointment>();
	}
	
	
	public WeekCalendar(Employee employee, int weekNumber, int year) {
		super(emptyCalendar, columnTitles);
		this.employee = employee;
		this.week = weekNumber;
		this.year = year;
		iteratorIndex = 0;
	}
	
	
	
	@Override
	public void initialize() {
        json = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("employee",this.getEmployee().getUsername());
        jsonObject.put("week",this.week);
        jsonObject.put("year",this.year);
        json.put("request","weekcalendar");
        json.put("dbmethod","initialize");
        json.put("model",jsonObject);
        OutboundWorker.sendRequest(json);

		
	}
	
	@Override
	public void refresh() {
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
		// TODO code for placing app in table
		appointmentList.add(appointment);
	}
	
	
	public Employee getEmployee() {
		return employee;
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

    public ArrayList<Appointment> getAppointmentList() {
        return appointmentList;
    }
}
