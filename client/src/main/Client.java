package main;

import controllers.OutboundWorker;
import controllers.SocketListener;
import gui.CalendarView;
import javax.swing.*;


/**
 * Created by Truls on 13.03.14.
 */
public class Client {

    public static void main(String[] args) {
        //Creating a SocketClient object
        SocketListener client = new SocketListener ();
        //trying to establish connection to the server
        client.connect();

        /*

        ParticipantListModel plist = new ParticipantListModel();
        plist.addElement(new Participant("trulsmp","truls", ParticipantStatus.participating));
        plist.addElement(new Participant("hakloev", "Haakon", ParticipantStatus.notParticipating));

        Appointment appointment = new Appointment();

        appointment.setParticipantList(plist);
        appointment.setDate("14.03.2014");
        appointment.setStart("14:30");
        appointment.setEnd("15:30");
        Request request = new Request("appointment","post",appointment);
        OutboundWorker.sendRequest(request);

        */
        String password = "tr";
        char[] test = password.toCharArray();

        OutboundWorker.login("trulsmp", test);

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


