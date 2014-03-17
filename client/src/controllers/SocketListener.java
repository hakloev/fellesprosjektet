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
    private static boolean connected = false;


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
            Socket socketClient = new Socket(hostname,port);
            System.out.println("Connection Established \n");
            InboundWorker inWorker = new InboundWorker(socketClient);
            inWorker.start();
            outWorker= new OutboundWorker(socketClient);
            System.out.println("Response Thread STARTED");

            
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            
            return false;
            
            /* just return true for testing purposes */
            //return true;
            
        }

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

	public OutboundWorker getOutWorker() {
		return outWorker;
	}

	public void setOutWorker(OutboundWorker outWorker) {
		this.outWorker = outWorker;
	}
}

