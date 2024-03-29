package models;

import controllers.DBconnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("serial")
public class WeekCalendar implements DBInterface {
	

	private Employee employee;
	private ArrayList<Appointment> appointmentList;
	private int weekNumber;

	public WeekCalendar(Employee employee, int weekNumber) {
		this.employee = employee;
		this.weekNumber = weekNumber;
		this.appointmentList = new ArrayList<>();
	}
	
	@Override
	public void initialize() {
		System.out.println("WeekCalendar.initialize");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.WEEK_OF_YEAR, this.weekNumber);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date startDate = c.getTime();
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Date endDate = c.getTime();
		System.out.println("WeekCalendar.initialize: START OF WEEK: " + sdf.format(startDate));
		System.out.println("WeekCalendar.initialize: END OF WEEK: " + sdf.format(endDate));

		Calendar start = Calendar.getInstance();
		Connection dbCon = DBconnection.getConnection(); // Singelton class
		try {
			String sql = "SELECT a.avtaleid FROM avtale a, deltager d WHERE start >= '" +
					sdf.format(startDate) + "' AND slutt <= '" + sdf.format(endDate) + "' AND d.brukernavn = '" +
					employee.getUsername() + "' AND a.avtaleid = d.avtaleid " +
					"AND d.vises = 1";
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
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Calendar end = Calendar.getInstance();
		System.out.println("WeekCalendar.initialize: DB QUERY TOOK: " + (end.getTimeInMillis() - start.getTimeInMillis()) + " millis");
	}
	
	@Override
	public void refresh() {
		System.out.println("WeekCalendar.refresh");
	}

	@Override
	public void save() {
		System.out.println("WeekCalendar.save");
		// This should probably not be saved as one.
		// Server access should be administered on each Appointment object
	}
	
	@Override
	public void delete() {
		System.out.println("WeekCalendar.delete");
		// This should probably not be deleted as one
		// Server access should be administered on each Appointment object
	}

	public ArrayList<Appointment> getAppointmentList() {
		return appointmentList;
	}

	public void addAppointment(Appointment appointment) {
		appointmentList.add(appointment);
	}
	
}
