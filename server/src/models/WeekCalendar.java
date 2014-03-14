package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class WeekCalendar extends DefaultTableModel implements DBInterface, Iterator<Appointment>{
	

	@JsonProperty("employee")
	private Employee employee;
	@JsonProperty("appointmentList")
	private ArrayList<Appointment> appointmentList;
	@JsonProperty("weekNumber")
	private int weekNumber;
	private int iteratorIndex;
	
	

	
	public WeekCalendar() {
		super();
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
	
}
