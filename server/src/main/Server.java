package main;

import controllers.SocketListener;
import helperclasses.Request;

/**
 * Created by Håkon Ødegård Løvdal on 10/03/14.
 *
 * This is the main class, it's the entry point for the serverprogram.
 * It creates a socket listener that handles all incoming clients.
 *
 */
public class Server {

	public static void main(String[] args) {
		SocketListener socketListener = new SocketListener();
		System.out.println("Server.main: SOCKETLISTENER STARTED AT " + Request.getTime());
		socketListener.start();
	}

}
