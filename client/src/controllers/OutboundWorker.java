package controllers;

import helperclasses.JSONHandler;
import helperclasses.Request;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * Created by Torgeir on 11.03.14.
 */
public class OutboundWorker {
    private static Socket socketClient;


    @Deprecated
    public OutboundWorker(Socket socket) {
        socketClient = socket;

    }


    public static void sendRequest(Request request) {

        try {
            DataOutputStream writeToServer = new DataOutputStream(SocketListener.getSocket().getOutputStream());
            String userDataJSON = JSONHandler.toJson(request);
            writeToServer.writeBytes(userDataJSON + "\n");

        } catch (IOException e) {

            e.printStackTrace();

        }


    }
    
    
}
