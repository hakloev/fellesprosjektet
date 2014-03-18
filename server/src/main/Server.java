package main;

import controllers.SocketListener;

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
		socketListener.start();
		System.out.println("Server.main: SOCKETLISTENER STARTED");
	}

}
