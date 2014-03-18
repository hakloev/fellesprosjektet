package controllers;

import helperclasses.JSONHandler;
import helperclasses.Response;
import models.Appointment;
import models.NotificationListModel;
import models.RoomListModel;
import models.WeekCalendar;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sun.org.mozilla.javascript.internal.json.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Truls on 12.03.14.
 */
public class InboundWorker extends Thread implements Runnable {
    private boolean connected;
    private String response;
    private BufferedReader readFromServer;
    private Socket socketClient;
    
    private static Thread registeredWaitingInstance;
    private static Object responseObject;

    public InboundWorker(Socket socketClient) {
        connected = true;
        this.socketClient = socketClient;

    }

    public void run() {
        try {

            while(connected) {

                readFromServer = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));


                String responseString = readFromServer.readLine();

                if (responseString == null) {
                    System.out.println("CLOSED WHEN RECEIVING NO DATA");
                    break;
                }

                System.out.println("Received response from Server: ");
                System.out.println(responseString + "\n");
                Object object = JSONHandler.parseJSON(responseString);
                if (object instanceof WeekCalendar) {
                    System.out.println(((WeekCalendar) object).getAppointmentList().toString());
                }
                else if (object instanceof RoomListModel) {
                    System.out.println(((RoomListModel) object).toString());
                }
                else if (object instanceof Appointment) {
                    System.out.println(((Appointment) object).toString());
                }
                else if (object instanceof NotificationListModel) {

                }
                // TODO: SENDE VIDERE TIL KLASSE SOM OPPDATER KLIENT
                
            }

        } catch (IOException e) {
            System.err.println("Connection/there is no server");
            e.printStackTrace();
        }

    }
    
    
    public static void register(Thread waiting) {
    	registeredWaitingInstance = waiting;
    }
    
    public static void unregister(Thread waiting) {
    	if (registeredWaitingInstance == waiting) {
    		registeredWaitingInstance = null;
    	}
    }
    
    public static Object getResponse() {
    	return responseObject;
    }
}


