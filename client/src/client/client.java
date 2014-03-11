package client;

import controllers.SocketListener;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by Truls on 11.03.14.
 */
public class client {

    public static void main(String[] args) {
        //Creating a SocketClient object
        SocketListener client = new SocketListener ("localhost",4657);
        try {
            //trying to establish connection to the server
            client.connect();
            //if successful, read response from server
            client.readResponse();

        } catch (UnknownHostException e) {
            System.err.println("Host unknown. Cannot establish connection");
        } catch (IOException e) {
            System.err.println("Cannot establish connection. Server may not be up."+e.getMessage());
        }
    }
    }
}
