package gui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 * Created by Truls on 06.03.14.
 */
public class Main {
	
    public static void main(String[] args) {
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
    	
    	new CalendarView();
    	
    	/*
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalendarView window = new CalendarView();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		*/
    }
}
