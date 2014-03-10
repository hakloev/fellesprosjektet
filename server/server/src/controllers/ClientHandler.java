package src.controllers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by hakloev on 10/03/14.
 */
public class ClientHandler extends Thread implements Runnable {

	private final Socket _SOCKET;

	public ClientHandler(Socket socket) {
		this._SOCKET = socket;
	}

	public void run() {
		boolean _SERVING = true;
		try {
			BufferedReader readFromClient;
			DataOutputStream writeToClient = new DataOutputStream(_SOCKET.getOutputStream());
			while (_SERVING) {
				readFromClient = new BufferedReader(new InputStreamReader(_SOCKET.getInputStream()));
				// TODO: HERE WE HANDLE INCOMING JSON AND SPEAK WITH DB
				out

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
