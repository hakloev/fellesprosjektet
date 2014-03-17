package main;

import controllers.OutboundWorker;
import controllers.SocketListener;
import gui.CalendarView;
import helperclasses.Request;
import models.*;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Truls on 13.03.14.
 */
public class Client {

    public static void main(String[] args) {
        //Creating a SocketClient object
        SocketListener client = new SocketListener ();
        //trying to establish connection to the server
        client.connect();


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
        a.initialize();

        String password = "truls";
        char[] test = password.toCharArray();
        //OutboundWorker.login("trulsmp", test);


        WeekCalendar weekCalendar = new WeekCalendar(new Employee("trulsmp","Truls Mork Pettersen"),12,2014);
        //weekCalendar.initialize();

        RoomListModel roomListModel = new RoomListModel();
        //roomListModel.initialize();





        //setupUIManager(); // do first
    	
        //new CalendarView();

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
    
    
}


