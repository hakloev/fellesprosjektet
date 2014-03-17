package main;

import java.awt.EventQueue;

import gui.CalendarView;

import javax.swing.*;

import controllers.SocketListener;


/**
 * Created by Truls on 13.03.14.
 */
public class Client {
	
	//static CalendarView mainWindow;
	
    public static void main(String[] args) {
    	setupUIManager(); // do first
    	
    	//final CalendarView mainWindow;
    	final SocketListener sl = new SocketListener();
    	
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalendarView mainWindow = new CalendarView(sl);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    	
    	
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


