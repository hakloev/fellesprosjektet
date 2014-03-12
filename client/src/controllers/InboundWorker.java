package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Truls on 12.03.14.
 */
public class InboundWorker extends Thread implements Runnable {
    private boolean connected;
    private String response;
    private BufferedReader readFromServer;
    Socket socketClient;

    public InboundWorker(Socket socketClient) {
        connected = true;
        this.socketClient = socketClient;

    }

    public void run() {
        try {

            while(connected) {

                readFromServer = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
                System.out.println("Response from server:");

                String rec = readFromServer.readLine();

                if (rec == null) {
                    System.out.println("CLOSED WHEN RECEIVING NO DATA");
                    break;
                }

                System.out.println(rec);

                // TODO: call to json handler to handle the response message

            }

        } catch (IOException e) {
            System.err.println("Connection/there is no server");
            e.printStackTrace();
        }

    }
}


