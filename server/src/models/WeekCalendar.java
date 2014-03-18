package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import controllers.DBconnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class WeekCalendar implements DBInterface {
	

	private Employee employee;
	private ArrayList<Appointment> appointmentList;
	private int weekNumber;
	private int iteratorIndex;

	public WeekCalendar() {
		super();
	}
	
	public WeekCalendar(Employee employee, int weekNumber) {
		this.employee = employee;
		this.weekNumber = weekNumber;
		this.appointmentList = new ArrayList<>();
		iteratorIndex = 0;
	}
	
	
	
	@Override
	public void initialize() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.WEEK_OF_YEAR, this.weekNumber);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date startDate = c.getTime();
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Date endDate = c.getTime();

		Connection dbCon = DBconnection.getConnection(); // Singelton class
		try {
			String sql = "SELECT avtaleid FROM avtale WHERE start >= '" + sdf.format(startDate) + "' AND slutt <= '" + sdf.format(endDate) + "'";
			Statement stmt = dbCon.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Appointment a = new Appointment();
				a.setAppointmentID(rs.getInt(1));
				a.refresh();
				this.addAppointment(a);
			}
			stmt.close();
			rs.close();
			System.out.println(this.appointmentList.toString());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public void refresh() {

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

	public int getIteratorIndex() {
		return iteratorIndex;
	}

	public void setIteratorIndex(int iteratorIndex) {
		this.iteratorIndex = iteratorIndex;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public ArrayList<Appointment> getAppointmentList() {
		return appointmentList;
	}

	public void setAppointmentList(ArrayList<Appointment> appointmentList) {
		this.appointmentList = appointmentList;
	}

	public int getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}

	public void addAppointment(Appointment appointment) {
		appointmentList.add(appointment);
	}
	
}
