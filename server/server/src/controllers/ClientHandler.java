package src.controllers;

import java.net.Socket;

/**
 * Created by hakloev on 10/03/14.
 */
public class ClientHandler extends Thread {

	private Socket socket;

	public ClientHandler(Socket socket) {
		this.socket = socket;
	}
}
