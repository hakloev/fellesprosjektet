package controllers;

import models.Employee;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Torgeir on 11.03.14.
 */
public class OutboundWorker {
    private static Socket socketClient;

    public OutboundWorker(Socket socket) {
        this.socketClient = socket;

    }


    public static void sendRequest(Request request) {
        JSONHandler jsonHandler = new JSONHandler();

        try {
            DataOutputStream writeToServer = new DataOutputStream(socketClient.getOutputStream());
            String userDataJSON = jsonHandler.toJson(request);
            writeToServer.writeBytes(userDataJSON + "\n");

        } catch (IOException e) {

            e.printStackTrace();

        }


    }
}
