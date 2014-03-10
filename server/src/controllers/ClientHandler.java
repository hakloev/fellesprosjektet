package controllers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Håkon Ødegård Løvdal on 10/03/14.
 */
public class ClientHandler extends Thread implements Runnable {

	private final Socket _SOCKET;
	private final int _CONNECTIONID;

	public ClientHandler(Socket socket, int connectionID) {
		this._SOCKET = socket;
		this._CONNECTIONID = connectionID;
		System.out.println(_CONNECTIONID + ": ClientHandler.ClientHandler: INITIATED CONNECTIONID " + _CONNECTIONID);
	}

	public void run() {
		boolean _SERVING = true;
		try {
			System.out.println(_CONNECTIONID + ": ClientHandler.run: SERVING " +
					 _SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort());

			BufferedReader readFromClient;
			DataOutputStream writeToClient = new DataOutputStream(_SOCKET.getOutputStream());

			while (_SERVING) {
				readFromClient = new BufferedReader(new InputStreamReader(_SOCKET.getInputStream())); // Added for testing purpose
				String incomingMsg = readFromClient.readLine(); // Added for testing purpose

				// If incoming message is null something has happened, and connection is loosed
				if (incomingMsg == null) {
					System.out.println(_CONNECTIONID + ": ClientHandler.run: " +
							 _SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort() + " CLOSED WHEN RECEIVING NO DATA");
					break;
				}

				System.out.println(_CONNECTIONID + ": ClientHandler.run: RECEIVED " + incomingMsg + " FROM " +
						 _SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort());

				// TODO: HERE WE HANDLE INCOMING JSON AND SPEAK WITH DB
				// TODO: NOT SURE WHAT WE WILL RECEIVE FROM THE CLIENT

				incomingMsg = incomingMsg.toUpperCase(); // Added for testing purpose
				writeToClient.writeBytes(incomingMsg + "\n"); // Added for testing purpose
			}

		} catch (SocketException e) {
			System.out.println(_CONNECTIONID + ": ClientHandler.run: " +
					 _SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort() + "CLOSED UNEXPECTEDLY (SocketException)");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println(_CONNECTIONID + ": ClientHandler.run: " +
					 _SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort() + " FINISHED (finally CLAUSE)");
		}
	}
}
