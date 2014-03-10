package controllers;

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
	private final int _CONNECTIONID;

	public ClientHandler(Socket socket, int connectionID) {
		this._SOCKET = socket;
		this._CONNECTIONID = connectionID;
		System.out.println("ClientHandler.ClientHandler: INITIATED CONNECTIONID " + _CONNECTIONID);
	}

	public void run() {
		boolean _SERVING = true;
		try {
			System.out.println(_CONNECTIONID + ": ClientHandler.run: SERVING");
			BufferedReader readFromClient;
			DataOutputStream writeToClient = new DataOutputStream(_SOCKET.getOutputStream());
			while (_SERVING) {
				readFromClient = new BufferedReader(new InputStreamReader(_SOCKET.getInputStream()));
				String incomingMsg = readFromClient.readLine();
				System.out.println(_CONNECTIONID + ": ClientHandler.run: RECIEVED " + incomingMsg);
				// TODO: HERE WE HANDLE INCOMING JSON AND SPEAK WITH DB
				incomingMsg = incomingMsg.toUpperCase();
				writeToClient.writeBytes(incomingMsg + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
