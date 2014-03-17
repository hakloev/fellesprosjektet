package controllers;
import gui.LoginScreen;
import helperclasses.JSONHandler;
import helperclasses.Response;

import java.io.*;
import java.net.Socket;


/**
 * Created by Torgeir on 11.03.14.
 */
public class SocketListener extends Thread implements Runnable {

    private String hostname;
    private int port;
    private Socket socketClient;
    private boolean connected;
    private BufferedReader readFromServer;
    
    private Thread registeredWaitingInstance;
    private Object responseObject;


    public SocketListener(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
        connected = false;
    }
    
    
    public SocketListener() {
    	this("localhost", 4657);
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

                responseObject = JSONHandler.parseJSON(response);
                System.out.println("responseObject " + responseObject);
                System.out.println("this Thread: " + (Thread)this);
                
                if (registeredWaitingInstance != null) {
                	System.out.println("k test interrupt");
                	registeredWaitingInstance.join();
                	//((LoginScreen.LoginWaiter)registeredWaitingInstance).jall();
                	registeredWaitingInstance.interrupt();
                	System.out.println("k test interrupt done");
                }
                
                System.out.println("ParsedObjectOutput: " + responseObject);


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


    public synchronized boolean connect(){
        System.out.println("Attempting to connect to "+hostname+":"+port);
        try {
            socketClient = new Socket(hostname, port);
            connected = true;
            System.out.println("Connection Established \n");
            //InboundWorker inWorker = new InboundWorker(socketClient);
            this.start();
            System.out.println("Response Thread STARTED");
            OutboundWorker.setSocket(socketClient);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            
            return false;
            
            /* just return true for testing purposes */
            //return true;
            
        }
    }
    
    
    public synchronized void close() {
    	connected = false;
    	OutboundWorker.setSocket(null);
    	if (socketClient != null) try {
			socketClient.close();
		} catch (IOException e) {
			// Don't care!
			//e.printStackTrace();
		}
    }

    
    public synchronized Socket getSocket() {
    	return socketClient;
    }
    
    
    public synchronized void register(Thread waiting) {
    	registeredWaitingInstance = waiting;
    	System.out.println("Registered: " + registeredWaitingInstance);
    }
    
    public synchronized void unregister(Thread waiting) {
    	if (registeredWaitingInstance == waiting) {
    		System.out.println("Unregistered: " + registeredWaitingInstance);
    		registeredWaitingInstance = null;
    	}
    }
    
    public synchronized Object getResponse() {
    	return responseObject;
    }

    
    
}

