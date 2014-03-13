package main;
import controllers.SocketListener;
import gui.CalendarView;

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

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        new CalendarView();



    }
}


