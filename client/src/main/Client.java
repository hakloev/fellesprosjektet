package main;
import gui.CalendarView;

import javax.swing.*;

/**
 * Created by Truls on 13.03.14.
 */
public class Client {

    public static void main(String[] args) {
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


