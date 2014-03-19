package models;

import java.util.ArrayList;
import java.util.Iterator;

public class CalendarCell implements Iterable<Appointment> {
	
	
	private ArrayList<Appointment> appointments;
	
	public CalendarCell() {
		appointments = new ArrayList<Appointment>();
	}
	
	public void addAppointment(Appointment app){
		if (! appointments.contains(app)) {
			appointments.add(app);
		}
	}
	public void removeAppointment(Appointment app){
		appointments.remove(app);
	}
	public Appointment getfirst(){
		return appointments.get(0);
	}
	
	@Override
	public String toString(){
		String ret = "";
		for (Appointment app: appointments){
			if (ret.equals("")){
				ret += app;
			}
			else{
				ret += "\n" + app;
			}
		}
		return ret;
	}

	@Override
	public Iterator<Appointment> iterator() {
		return appointments.iterator();
	}
}
