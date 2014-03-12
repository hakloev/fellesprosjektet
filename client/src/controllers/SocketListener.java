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
    Socket socketClient;

    public SocketListener(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }


    public void connect(){
        System.out.println("Attempting to connect to "+hostname+":"+port);
        try {
            socketClient = new Socket(hostname,port);
            System.out.println("Connection Established \n");
            InboundWorker inWorker = new InboundWorker(socketClient);
            inWorker.start();
            System.out.println("Response Thread STARTED");
            OutboundWorker outWorker = new OutboundWorker(socketClient);
            outWorker.sendRequest();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}