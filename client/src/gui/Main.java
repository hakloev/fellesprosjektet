package gui;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.sun.swing.internal.plaf.synth.resources.synth_zh_TW;

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
    	
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalendarView window = new CalendarView();
					window.frmKalenderFirma.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
}
