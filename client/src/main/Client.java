package main;

import controllers.OutboundWorker;
import controllers.SocketListener;
import gui.CalendarView;
import helperclasses.Request;
import models.*;

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

		Appoint
		Request request = new Request("appointment", "POST", a);


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


