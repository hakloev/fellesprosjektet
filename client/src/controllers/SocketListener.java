package controllers;
import helperclasses.JSONHandler;
import helperclasses.Response;

import java.io.*;
import java.net.Socket;


/**
 * Created by Torgeir on 11.03.14.
 */
public class SocketListener extends Thread {

    private String hostname;
    private int port;
    private Socket socketClient;
    private boolean connected;
    private BufferedReader readFromServer;
    private static SocketListener socketListener;
    
    private ResponseWaiter registeredWaitingInstance;
    private Object responseObject;
    


    public SocketListener(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
        connected = false;
    }
    
    
    public SocketListener() {
    	this("localhost", 4657);
    }
    
    
    @Override
    public void run() {
    	System.out.println("Response Thread STARTED");
        try {

            while(connected) {

                readFromServer = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));


                String responseString = readFromServer.readLine();

                if (responseString == null) {
                    System.out.println("CLOSED WHEN RECEIVING NO DATA");
                    break;
                }

                Response response = new Response(responseString);

                System.out.print("Received response from Server: ");
                System.out.println(response.get_JSONRESPONSE() + "\n");

                responseObject = JSONHandler.parseJSON(response);
                System.out.println("ParsedObjectOutput: " + responseObject);
                System.out.println("this Thread: " + this);
                
                if (registeredWaitingInstance != null) {
                	System.out.println("k test interrupt");
                	registeredWaitingInstance.setReady(true);
                	System.out.println("k test interrupt done");
                }
                


                // TODO: SENDE VIDERE TIL KLASSE SOM OPPDATER KLIENT
                
            }

        } catch (IOException e) {
            System.err.println("Connection/there is no server: " + e.getMessage());
            //e.printStackTrace();
        }
        System.out.println("Response Thread ENDED");
    }


    public synchronized boolean connect(){
        System.out.println("Attempting to connect to "+hostname+":"+port);
        try {
            socketClient = new Socket(hostname, port);
            connected = true;
            System.out.println("Connection Established \n");
            this.start();
            OutboundWorker.setSocket(socketClient);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public synchronized void close() {
    	connected = false;
    	OutboundWorker.setSocket(null);
    	if (socketClient != null) try {
			socketClient.close();
			System.out.println("Socket closed");
			socketClient = null;
		} catch (IOException e) {
			// Don't care!
			//e.printStackTrace();
		}
    }

    
    public synchronized Socket getSocket() {
    	return socketClient;
    }
    
    
    public synchronized void register(ResponseWaiter waiting) {
    	registeredWaitingInstance = waiting;
    	System.out.println("Registered: " + registeredWaitingInstance);
    }
    
    public synchronized void unregister(ResponseWaiter waiting) {
    	if (registeredWaitingInstance == waiting) {
    		System.out.println("Unregistered: " + registeredWaitingInstance);
    		registeredWaitingInstance = null;
    	}
    }
    
    public synchronized Object getResponse() {
    	return responseObject;
    }

    
    public static synchronized void setSL(SocketListener sl) {
    	socketListener = sl;
    }
    
    
    public static synchronized SocketListener getSL() {
    	return socketListener;
    }
    
    
    
}

