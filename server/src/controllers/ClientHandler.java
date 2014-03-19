package controllers;

import helperclasses.Request;
import helperclasses.Response;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Calendar;

/**
 * Created by Håkon Ødegård Løvdal on 10/03/14.
 *
 * ClientHandler handles write to and from server.
 * It accepts the request and creates a response to send back.
 *
 */
public class ClientHandler extends Thread implements Runnable {

	private final Socket _SOCKET;
	private final int _CONNECTIONID;
	private boolean _SERVING;
	//private BufferedReader readFromClient;
	private DataInputStream inFromClient;
	private DataOutputStream writeToClient;
	private Calendar start;
	private Calendar end;

	public ClientHandler(Socket socket, int connectionID) {
		this._SOCKET = socket;
		this._CONNECTIONID = connectionID;
		this._SERVING = true;
		System.out.println("ClientHandler.ClientHandler " + _CONNECTIONID + ": INITIATED CONNECTIONID " + _CONNECTIONID);
		System.out.println("ClientHandler.ClientHandler " + _CONNECTIONID + ": CREATED AT " + Request.getTime());

	}

	@Override
	public void run() {
		try {
			System.out.println("ClientHandler.run " + _CONNECTIONID + ": CLIENT CONNECTED FROM " +
					 _SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort());

			writeToClient = new DataOutputStream(_SOCKET.getOutputStream());

			while (_SERVING) {
				InputStream in = _SOCKET.getInputStream();
				inFromClient = new DataInputStream(in);
				//readFromClient = new BufferedReader(new InputStreamReader(_SOCKET.getInputStream(), "UTF-8"));
				Request request = acceptIncomingRequest(inFromClient.readUTF()); //readFromClient.readLine());
				start = Calendar.getInstance();
				if (request == null) {
					System.out.println("ClientHandler.run " + _CONNECTIONID + ": CLIENT FROM " +
							_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort() + " CLOSED WHEN RECEIVING NO DATA");
					break;
				}

				// Handle work on database here
				Response response = DatabaseWorker.handleRequest(request);

				// Return the response to client
				if (response != null) {
					sendOutgoingResponse(response.getResponse());
				} else {
					System.out.println("ClientHandler.run " + _CONNECTIONID + ": CLIENT FROM " +
							_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort() + " GOT NULL RESPONSE, NOTHING TO SEND");
				}
				end = Calendar.getInstance();
				System.out.println("ClientHandler.run: REQUEST HANDLED AND RESPONSE SENT IN: " + (end.getTimeInMillis() - start.getTimeInMillis()) + " millis");
			}

		} catch (SocketException e) {
			System.out.println("ClientHandler.run " + _CONNECTIONID + ": CLIENT FROM " +
					_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort() + "CLOSED UNEXPECTEDLY (SocketException)");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("ClientHandler.run " + _CONNECTIONID + ": CLIENT FROM " +
					_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort() + " FINISHED (finally CLAUSE)");
		}
	}

	private void sendOutgoingResponse(String response) {
		try {
			System.out.println("ClientHandler.sendOutgoingResponse " + _CONNECTIONID + ": SENDING " + response + " TO CLIENT FROM " +
					_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort());
			writeToClient.writeUTF(response);
			System.out.println("ClientHandler.sendOutgoingResponse " + _CONNECTIONID + ": SENT " + response + " TO CLIENT FROM " +
					_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort() + " AT " + Request.getTime());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Request acceptIncomingRequest(String incomingJSON) {
		// If incoming message is null something has happened, and connection is loosed
		if (incomingJSON == null) {
			return null;
		}
		System.out.println("\nClientHandler.acceptIncomingRequest " + _CONNECTIONID + ": RECEIVED " + incomingJSON + " FROM CLIENT " +
				_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort());
		return new Request(incomingJSON);
	}
}