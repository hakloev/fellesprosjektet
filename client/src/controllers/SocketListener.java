package controllers;

import models.*;

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
    private InputStream in;
    
    private ResponseWaiter registeredWaitingInstance;
    private Object object;
    
        
    public SocketListener(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
        connected = false;
    }
    
    
    public SocketListener() {
    	this("78.91.29.127", 4657);
    }
    
    
    @Override
    public void run() {
    	System.out.println("Response Thread STARTED");
        try {

            while(connected) {
            	
            	DataInputStream inFromServer = new DataInputStream(socketClient.getInputStream()); 
            	
                //readFromServer = new BufferedReader(new InputStreamReader(socketClient.getInputStream(), "UTF-8"));

                String responseString = inFromServer.readUTF();

                if (responseString == null) {
                    System.out.println("CLOSED WHEN RECEIVING NO DATA");
                    break;
                }

                System.out.print("Received response from Server: ");
                System.out.println(responseString + "\n");
                handleResponse(responseString);

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
    	return object;
    }

    
    public static synchronized void setSL(SocketListener sl) {
    	socketListener = sl;
    }
    
    public static synchronized SocketListener getSL() {
    	return socketListener;
    }


    public void handleResponse(String responseString) {

        object = JSONHandler.parseJSON(responseString);
        if (object instanceof Notification) {
        	// TODO invoke later on EDT
        }
        
        if (registeredWaitingInstance != null) {
            registeredWaitingInstance.setReady(true);
            System.out.println("Other thread set ready");
        }
    }
    
}

