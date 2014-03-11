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


    public void connect() throws UnknownHostException, IOException{
        System.out.println("Attempting to connect to "+hostname+":"+port);
        socketClient = new Socket(hostname,port);
        System.out.println("Connection Established");
        OutboundWorker worker = new OutboundWorker(socketClient);
        worker.sendRequest();
    }


    public void readResponse() throws IOException{
        String userInput;
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));


        System.out.println("Response from server:");

        //call to json handler to handle the response message

        while ((userInput = stdIn.readLine()) != null) {
            System.out.println(userInput);
        }
    }




    public void closeSocket(){
        try{
            socketClient.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}