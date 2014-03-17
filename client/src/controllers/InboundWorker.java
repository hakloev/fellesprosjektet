package controllers;

import gui.LoginScreen;
import helperclasses.JSONHandler;
import helperclasses.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Truls on 12.03.14.
 */

@Deprecated
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
                
                if (registeredWaitingInstance != null) {
                	System.out.println("k test interrupt");
                	((LoginScreen.LoginWaiter)registeredWaitingInstance).jall();
                	registeredWaitingInstance.join();
                	System.out.println("k test interrupt done");
                }
                
                System.out.println("ParsedObjectOutput: " + object);


                // TODO: SENDE VIDERE TIL KLASSE SOM OPPDATER KLIENT
                
            }

        } catch (IOException e) {
            System.err.println("Connection/there is no server");
            e.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    
    public static synchronized void register(Thread waiting) {
    	registeredWaitingInstance = waiting;
    	System.out.println("registered: " + registeredWaitingInstance);
    }
    
    public static synchronized void unregister(Thread waiting) {
    	if (registeredWaitingInstance == waiting) {
    		registeredWaitingInstance = null;
    	}
    }
    
    public static synchronized Object getResponse() {
    	return responseObject;
    }
}


