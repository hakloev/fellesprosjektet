package main;

import controllers.SocketListener;
import gui.CalendarView;
import models.*;
import java.awt.EventQueue;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Truls on 13.03.14.
 */
public class Client {
	
	
    public static void main(String[] args) {
    	setupUIManager(); // do first
        
    	//trulsTestCode();
    	
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new CalendarView();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
    
    
    private static void setupUIManager() {
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
    	
    	UIManager.put("OptionPane.yesButtonText", "Ja");
    	UIManager.put("OptionPane.noButtonText", "Nei");
    }
    
    
    private static void trulsTestCode() {

        Employee employee = new Employee("trulsmp","trulsmp");

        ParticipantListModel plist = new ParticipantListModel();
        plist.addElement(new Participant("trulsmp","truls", ParticipantStatus.participating));
        plist.addElement(new Participant("hakloev", "Haakon", ParticipantStatus.notParticipating));

        Appointment appointment = new Appointment();
        appointment.setAppointmentID(2);
        appointment.setParticipantList(plist);
        Date date = new Date();
        appointment.setDate(date);
        appointment.setStartDateTime(Calendar.getInstance());
        appointment.setEndDateTime(Calendar.getInstance());
        appointment.setAppointmentLeader(employee);
        Room room = new Room("R1",478);
        appointment.setLocation(room);
        appointment.setDescription("Kodekveld i fellesprosjekt, yayyyy");
        //appointment.save();

        Appointment a = new Appointment();
        a.setAppointmentID(16);
        //a.initialize();

        String password = "truls";
        char[] test = password.toCharArray();
        //OutboundWorker.login("trulsmp", test);


        WeekCalendar weekCalendar = new WeekCalendar(new Employee("trulsmp","Truls Mork Pettersen"),12,2014);
        //weekCalendar.initialize();

        RoomListModel roomListModel = new RoomListModel();
        //roomListModel.initialize();

        GroupListModel groupListModel = new GroupListModel();
        groupListModel.initialize();


    }
}


