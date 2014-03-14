package controllers;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;


/**
 * Created by Torgeir on 11.03.14.
 */
public class SocketListener {

    private String hostname;
    private int port;
    private Socket socketClient;
    
    private static SocketListener clientSocketListener;
    private static boolean connected = false;


    public SocketListener(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }
    
    
    public SocketListener() {
    	this.hostname = "localhost";
        this.port = 4657;
    }


    public void connect(){
        System.out.println("Attempting to connect to "+hostname+":"+port);
        try {
            Socket socketClient = new Socket(hostname,port);
            System.out.println("Connection Established \n");
            InboundWorker inWorker = new InboundWorker(socketClient);
            inWorker.start();
            OutboundWorker outbound = new OutboundWorker(socketClient);
            System.out.println("Response Thread STARTED");

        } catch (IOException e) {
        	JOptionPane.showMessageDialog(null, "Kunne ikke koble til serveren!", "Feil", JOptionPane.ERROR_MESSAGE);
        	connected = false;
            e.printStackTrace();
            
        }

    }
    
    
    public static void setClientSocketListener(SocketListener clientSocket) {
    	clientSocketListener = clientSocket;
    }
    
    
    public static SocketListener getClientSocketListener() {
    	return clientSocketListener;
    }
    
    
    public static boolean isConnected() {
    	return connected;
    }
    
    
    public void closeSocket() {
    	if (socketClient != null) try {
			socketClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}

