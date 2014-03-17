package main;

import controllers.OutboundWorker;
import controllers.SocketListener;
import gui.CalendarView;
import helperclasses.Request;
import models.*;

import javax.swing.*;
import java.util.Date;


/**
 * Created by Truls on 13.03.14.
 */
public class Client {

    public static void main(String[] args) {
        //Creating a SocketClient object
        SocketListener client = new SocketListener ("localhost",4657);
        //trying to establish connection to the server
        client.connect();

	    ParticipantListModel plist = new ParticipantListModel();
	    plist.addElement(new Participant("trulsmp","trulsmp", ParticipantStatus.participating));
	    plist.addElement(new Participant("hakloev", "hakloev", ParticipantStatus.notParticipating));

	    Appointment a = new Appointment(new Employee("hakloev", "Håkon Løvdal"));
	    a.setParticipantList(plist);
	    a.setStart(new Date());
	    a.setEnd(new Date());
	    a.setDescription("Lite møte");
	    a.setLocation(new Room("F1", 500));


		Request request = new Request("appointment", "POST", a);

	    client.getOutWorker().sendRequest(request);

	    Request request1 = new Request("login", "null", null);

	    client.getOutWorker().sendRequest(request1);

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


