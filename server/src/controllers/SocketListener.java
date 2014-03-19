package controllers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Håkon Ødegård Løvdal on 10/03/14.
 *
 * This is the socket listener, it listens for incoming connections
 * and creates a ClientHandler for every unique
 *
 */
public class SocketListener extends Thread implements Runnable {

	private static final int _PORT = 4657; // 4603 to 4657 are unassigned ports

	@Override
	public void run() {
		boolean _SERVING = true;
		int connectionID = 1;
		try {
			ServerSocket serverSocket = new ServerSocket(_PORT);
			System.out.println("SocketListener.run: SERVING ON " + serverSocket.getInetAddress().getLocalHost() + " PORT " + serverSocket.getLocalPort());
			while (_SERVING) {
				System.out.println("SocketListener.run: SERVERSOCKET WAITING FOR INCOMING CONNECTION");
				Socket incomingConnection = serverSocket.accept();
				incomingConnection.setKeepAlive(true); // Trying to avoid timeout from socket

				if (incomingConnection != null) {
					System.out.println("\nSocketListener.run: INCOMING CONNECTION");
					ClientHandler client = new ClientHandler(incomingConnection, connectionID++); // Initiate one ClientHandler per incoming connection
					client.start();
				}
			}
			serverSocket.close(); // Will not be reached if things works properly
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
