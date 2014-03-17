package controllers;

import helperclasses.JSONHandler;
import helperclasses.Request;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

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
    
    
    public static boolean login(String username, char[] password) {

        StringBuilder sb = new StringBuilder(password.length);
        for (Character c : password)
            sb.append(c);
        String result = sb.toString();
        ArrayList<String> list = new ArrayList<String>();
        list.add(username);
        list.add(result);
        Request request = new Request("null","login", list );
        OutboundWorker.sendRequest(request);
    	return true;
    }
    
    
    public static boolean logout() {
        return true;
    }
    
}
