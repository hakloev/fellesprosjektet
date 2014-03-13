package controllers;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Created by Torgeir on 11.03.14.
 */
public class SocketListener {

    private String hostname;
    private int port;

    public SocketListener(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
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
            e.printStackTrace();
        }

    }
}