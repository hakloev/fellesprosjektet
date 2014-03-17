package controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Created by Torgeir on 11.03.14.
 */
public class OutboundWorker {
    private static Socket socketClient;


    @Deprecated
    public OutboundWorker(Socket socket) {
        socketClient = socket;

    }


    public static void sendRequest(JSONObject userDataJSON) {
        System.out.println("Sending to server:");
        System.out.println(userDataJSON.toJSONString());
        try {
            DataOutputStream writeToServer = new DataOutputStream(SocketListener.getSocket().getOutputStream());
            writeToServer.writeBytes(userDataJSON.toJSONString() + "\n");

        } catch (IOException e) {

            e.printStackTrace();

        }


    }
    

    public static boolean login(String username, char[] password) {
        JSONObject json;
        StringBuilder sb = new StringBuilder(password.length);
        for (Character c : password)
            sb.append(c);
        String result = sb.toString();
        ArrayList<String> list = new ArrayList<String>();
        list.add(username);
        list.add(result);
        json = new JSONObject();
        json.put("request", "login");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(list.get(0));
        jsonArray.add(list.get(1));
        json.put("array",jsonArray);


        sendRequest(json);

        return true;
    }
    
    
    public static boolean logout() {
        return true;
    }

}
