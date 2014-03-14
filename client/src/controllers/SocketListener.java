package controllers;
import java.io.*;
import java.net.Socket;


/**
 * Created by Torgeir on 11.03.14.
 */
public class SocketListener{

    private String hostname;
    private int port;
    private Socket socketClient;
    private OutboundWorker outWorker;
    
    private static SocketListener clientSocketListener;
    

    public SocketListener(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }
    
    
    public SocketListener() {
    	this.hostname = "localhost";
        this.port = 4657;
    }


    public boolean connect(){
        System.out.println("Attempting to connect to "+hostname+":"+port);
        try {
            socketClient = new Socket(hostname,port);
            System.out.println("Connection Established \n");
            InboundWorker inWorker = new InboundWorker(socketClient);
            inWorker.start();
            System.out.println("Response Thread STARTED");
            outWorker = new OutboundWorker(socketClient);
            outWorker.sendRequest();
            
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            
            return false;
            
            /* just return true for testing purposes */
            //return true;
            
        }

    }
    
    
    public OutboundWorker getOutboundWorker() {
    	return outWorker;
    }
    
    
    public void closeSocket() {
    	if (socketClient != null) try {
    		socketClient.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    
    public static void setClientSocketListener(SocketListener clientSocket) {
    	clientSocketListener = clientSocket;
    }
    
    
    public static SocketListener getClientSocketListener() {
    	return clientSocketListener;
    }
    
    
    
    
}

