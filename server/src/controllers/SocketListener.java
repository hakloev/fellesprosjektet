package controllers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by hakloev on 10/03/14.
 */
public class SocketListener extends Thread implements Runnable {

	private static final int _ENDPORT = 4657; // 4603 to 4657 are unassigned ports
	// Do we want to gather all clienthandlers in an arraylist here?

	@Override
	public void run() {
		boolean _SERVING = true;
		int connectionID = 0;
		try {
			ServerSocket serverSocket = new ServerSocket(_ENDPORT);
			System.out.println("SocketListener.run: SERVERSOCKET LISTENING");
			while (_SERVING) {
				Socket incomingConnection = serverSocket.accept();
				incomingConnection.setKeepAlive(true); // Trying to avoid timeout from socket
				if (incomingConnection != null) {
					System.out.println("SocketListener.run: INCOMING CONNECTION");
					ClientHandler client = new ClientHandler(incomingConnection, connectionID++);
					client.start();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
