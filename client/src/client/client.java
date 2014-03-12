package client;

import controllers.SocketListener;

/**
 * Created by Truls on 12.03.14.
 */
public class Client {
    public static void main(String[] args) {
        //Creating a SocketClient object
        SocketListener client = new SocketListener ("localhost",4658);
        //trying to establish connection to the server
        client.connect();
    }
}
