package controllers;

import helperclasses.JSONHandler;
import helperclasses.Request;
import helperclasses.Response;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Håkon Ødegård Løvdal on 10/03/14.
 */
public class ClientHandler extends Thread implements Runnable {

	private final Socket _SOCKET;
	private final int _CONNECTIONID;
	private boolean _SERVING;
	private BufferedReader readFromClient;
	private DataOutputStream writeToClient;

	public ClientHandler(Socket socket, int connectionID) {
		this._SOCKET = socket;
		this._CONNECTIONID = connectionID;
		this._SERVING = true;
		System.out.println(": ClientHandler.ClientHandler " + _CONNECTIONID + ": INITIATED CONNECTIONID " + _CONNECTIONID);
	}

	@Override
	public void run() {
		try {
			System.out.println("ClientHandler.run " + _CONNECTIONID + ": " +
					 _SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort());

			writeToClient = new DataOutputStream(_SOCKET.getOutputStream());

			while (_SERVING) {
				readFromClient = new BufferedReader(new InputStreamReader(_SOCKET.getInputStream()));

				Request request = acceptIncomingRequest(readFromClient.readLine());
				if (request == null) {
					System.out.println("ClientHandler.run " + _CONNECTIONID + ": " +
							_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort() + " CLOSED WHEN RECEIVING NO DATA");
					break;
				}

				// Handle work on database here
				Response response = DatabaseWorker.handleRequest(request);

				// Return the response to client
				if (response != null) {
					sendOutgoingResponse(JSONHandler.createJSON(response));
				} else {
					System.out.println("ClientHandler.run " + _CONNECTIONID + ": " +
							_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort() + " GOT NULL RESPONSE, NOTHING TO SEND");
				}
			}

		} catch (SocketException e) {
			System.out.println("ClientHandler.run " + _CONNECTIONID + ": " +
					_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort() + "CLOSED UNEXPECTEDLY (SocketException)");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("ClientHandler.run " + _CONNECTIONID + ": " +
					_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort() + " FINISHED (finally CLAUSE)");
		}
	}

	private void sendOutgoingResponse(String response) {
		try {
			System.out.println("ClientHandler.sendOutgoingResponse " + _CONNECTIONID + ": SENDING " + response + " TO " +
					_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort());
			writeToClient.writeBytes(response + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Request acceptIncomingRequest(String incomingJSON) {
		// If incoming message is null something has happened, and connection is loosed
		if (incomingJSON == null) {
			return null;
		}
		System.out.println("ClientHandler.acceptIncomingRequest " + _CONNECTIONID + ": RECEIVED " + incomingJSON + " FROM " +
				_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort());
		return new Request(incomingJSON);
	}
}