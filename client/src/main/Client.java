package main;

import java.awt.EventQueue;

import javax.swing.*;

import gui.CalendarView;


/**
 * Created by Truls on 13.03.14.
 */
public class Client {
	
	
    public static void main(String[] args) {
    	setupUIManager(); // do first
    	
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
    
    
}


