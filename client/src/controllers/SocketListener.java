package controllers;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;




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
        sendRequest();
    }


    public void readResponse() throws IOException{
        String userInput;
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));


        System.out.println("Response from server:");

        //call to json handler

        while ((userInput = stdIn.readLine()) != null) {
            System.out.println(userInput);
        }
    }


    public void sendRequest() {
        Request request = new Request("APPOINTMENT","GET");
        ObjectMapper mapper = new ObjectMapper();

        try {

            Writer strWriter = new StringWriter();
            mapper.writeValue(strWriter, request);
            String userDataJSON = strWriter.toString();
            System.out.println(userDataJSON);

        } catch (IOException e) {

            e.printStackTrace();

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