package main;

import controllers.OutboundWorker;
import controllers.SocketListener;
import gui.CalendarView;
import helperclasses.Request;
import models.Appointment;

import javax.swing.*;


/**
 * Created by Truls on 13.03.14.
 */
public class Client {

    public static void main(String[] args) {
        //Creating a SocketClient object
        SocketListener client = new SocketListener ("localhost",4657);
        //trying to establish connection to the server
        client.connect();

	/*
        ParticipantListModel plist = new ParticipantListModel();
        plist.addElement(new Participant("trulsmp","truls", ParticipantStatus.participating));
        plist.addElement(new Participant("hakloev", "Haakon", ParticipantStatus.notParticipating));
		Request request = new Request("participantlistmodel", "POST", plist);
	*/
	    Appointment appointment = new Appointment();
		appointment.setDate("10:02:2000");
        Request request = new Request("appointment","POST", appointment);


        OutboundWorker.sendRequest(request);
    	setupUIManager(); // do first
        new CalendarView();

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


