package controllers;

import helperclasses.JSONHandler;
import helperclasses.Response;

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

                Response response = new Response(responseString);

                System.out.println("Received response from Server: ");
                System.out.println(response.get_JSONRESPONSE() + "\n");

                Object object = JSONHandler.parseJSON(response);
                
                responseObject = object;

                if (responseObject == null) {
                    // TODO: ikke gyldig login
                }
                else {
                    // TODO: gyldig login
                }


                if (registeredWaitingInstance != null) {
                	registeredWaitingInstance.interrupt();
                }
                
                System.out.println("ParsedObjectOutput: " + object);


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


