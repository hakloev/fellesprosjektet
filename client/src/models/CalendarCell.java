package models;

import java.util.ArrayList;

public class CalendarCell {
	private ArrayList<Appointment> appointments;
	
	public void addAppointment(Appointment app){
		if (!appointments.contains(app)){
			appointments.add(app);
		}
	}
	public void removeAppointment(Appointment app){
		appointments.remove(app);
	}

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
}
