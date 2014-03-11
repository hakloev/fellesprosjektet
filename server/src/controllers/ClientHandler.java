package controllers;

import helperclasses.Request;
import helperclasses.Response;

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
	private boolean _SERVING;
	private BufferedReader readFromClient;

	public ClientHandler(Socket socket, int connectionID) {
		this._SOCKET = socket;
		this._CONNECTIONID = connectionID;
		this._SERVING = true;
		System.out.println(_CONNECTIONID + ": ClientHandler.ClientHandler: INITIATED CONNECTIONID " + _CONNECTIONID);
	}

	public void run() {
		try {
			System.out.println(_CONNECTIONID + ": ClientHandler.run: SERVING " +
					 _SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort());

			DataOutputStream writeToClient = new DataOutputStream(_SOCKET.getOutputStream());

			while (_SERVING) {
				readFromClient = new BufferedReader(new InputStreamReader(_SOCKET.getInputStream()));

				Request request = acceptIncomingRequest(readFromClient.readLine());
				if (request == null) {
					System.out.println(_CONNECTIONID + ": ClientHandler.run: " +
							_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort() + " CLOSED WHEN RECEIVING NO DATA");
					break;
				}

				// behandle i database her
				Response response = DatabaseWorker.handleRequest(request);

				// return response
				writeToClient.writeBytes(response.getJSONString()); // Maybe we need "/n"
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

	private Request acceptIncomingRequest(String incomingJSON) {
		// If incoming message is null something has happened, and connection is loosed
		if (incomingJSON == null) {
			return null;
		}
		System.out.println(_CONNECTIONID + ": ClientHandler.run: RECEIVED " + incomingJSON + " FROM " +
				_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort());
		return new Request(incomingJSON);
	}
}
