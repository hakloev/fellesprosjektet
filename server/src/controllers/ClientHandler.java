package controllers;

import helperclasses.Request;
import helperclasses.Response;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
	private DataInputStream inFromClient;
	private DataOutputStream writeToClient;
	private Calendar start;
	private Calendar end;
	protected static Map<ClientHandler, String> loggedInClients = new ConcurrentHashMap<ClientHandler, String>();

	public ClientHandler(Socket socket, int connectionID) {
		this._SOCKET = socket;
		this._CONNECTIONID = connectionID;
		this._SERVING = true;
		DatabaseWorker.addClientHandler(this);
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
				inFromClient = new DataInputStream(_SOCKET.getInputStream());
				Request request = null;
				try {
					request = acceptIncomingRequest(inFromClient.readUTF()); //readFromClient.readLine());
				} catch (EOFException e) {
					System.out.println("ClientHandler.run: EOFException, BUT WHY (PS: KILROY WAS HERE)");
					printKilroy();
				}
				start = Calendar.getInstance();
				if (request == null) {
					System.out.println("ClientHandler.run " + _CONNECTIONID + ": CLIENT FROM " +
							_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort() + " CLOSED WHEN RECEIVING NO DATA");
					if (ClientHandler.loggedInClients.containsKey(this)) {
						ClientHandler.loggedInClients.remove(this);
						System.out.println("********************************************************************************************");
						System.out.println("ClientHandler.run: REMOVED LOGGED IN CLIENT: " + this);
						System.out.println("ClientHandler.run: LOGGED IN: " + ClientHandler.loggedInClients.toString());
						System.out.println("********************************************************************************************");
					}
					break;
				}

				// Handle work on database here
				Response response = DatabaseWorker.handleRequest(request);

				// Return the response to client
				if (response != null) {
					sendOutgoingResponse(response);
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
			printKilroy();
			e.printStackTrace();
		} catch (IOException e) {
			printKilroy();
			e.printStackTrace();
		} finally {
			System.out.println("ClientHandler.run " + _CONNECTIONID + ": CLIENT FROM " +
					_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort() + " FINISHED (finally CLAUSE)");
			if (ClientHandler.loggedInClients.containsKey(this)) {
				System.out.println("********************************************************************************************");
				System.out.println("ClientHandler.run (finally CLAUSE): REMOVED LOGGED IN CLIENT: " + ClientHandler.loggedInClients.get(this));
				ClientHandler.loggedInClients.remove(this);
				System.out.println("ClientHandler.run (finally CLAUSE): LOGGED IN: " + ClientHandler.loggedInClients.toString());
				System.out.println("********************************************************************************************");
			}
		}
	}

	private void sendOutgoingResponse(Response response) {
		try {
			System.out.println("ClientHandler.sendOutgoingResponse " + _CONNECTIONID + ": SENDING " + response.getResponse() + " TO CLIENT FROM " +
					_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort());
			writeToClient.writeUTF(response.getResponse());
			System.out.println("ClientHandler.sendOutgoingResponse " + _CONNECTIONID + ": SENT " + response.getResponse() + " TO CLIENT FROM " +
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

	private void printKilroy() {
		System.out.println(
				"\n..................................................\n" +
				":                    ......                      :\n"+
				":                 .:||||||||:.                   :\n"+
				":                /            \\                  :\n"+
				":               (   o      o   )                 :\n"+
				":-------@@@@----------:  :----------@@@@---------:\n"+
				":                     `--'                       :\n"+
				":                                                :\n"+
				":                                                :\n"+
				":         K I L R O Y   S A Y S   H I !          :\n"+
				":................................................:\n"
		);
	}

	@Override
	public String toString() {
		return "\n\t\tClientHandler[" +
				"_SERVING = " + _SERVING +
				", _CONNECTIONID = " + _CONNECTIONID +
				", _SOCKET = " + _SOCKET +
				", THREAD = " + getId() +
				"] ";
	}
}