package controllers;

import models.Employee;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Torgeir on 11.03.14.
 */
public class OutboundWorker {
    Socket socketClient;

    public OutboundWorker(Socket socket) {
        this.socketClient = socket;

    }


    public void sendRequest() {
        Employee employee = new Employee("Lars","truls");
        Request request = new Request("employee","update", employee);
        JSONHandler jsonHandler = new JSONHandler();

        try {
            DataOutputStream writeToServer = new DataOutputStream(socketClient.getOutputStream());
            String userDataJSON = jsonHandler.toJson(request);
            writeToServer.writeBytes(userDataJSON + "\n");

        } catch (IOException e) {

            e.printStackTrace();

        }


    }
    
    
    public boolean login(String username, char[] password) {
    	//TODO login logic
    	return true;
    }
    
    
    public boolean logout() {
    	// TODO logout logic
    	return true;
    }
    
}
