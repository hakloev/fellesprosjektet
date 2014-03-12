package controllers;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
		System.out.println(_CONNECTIONID + ": ClientHandler.ClientHandler: INITIATED CONNECTIONID " + _CONNECTIONID);
	}

	public void run() {
		try {
			System.out.println(_CONNECTIONID + ": ClientHandler.run: SERVING " +
					 _SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort());

			writeToClient = new DataOutputStream(_SOCKET.getOutputStream());

			while (_SERVING) {
				readFromClient = new BufferedReader(new InputStreamReader(_SOCKET.getInputStream()));

				Request request = acceptIncomingRequest(readFromClient.readLine());
				if (request == null) {
					System.out.println(_CONNECTIONID + ": ClientHandler.run: " +
							_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort() + " CLOSED WHEN RECEIVING NO DATA");
					break;
				}

				// Handle work on database here
				Response response = DatabaseWorker.handleRequest(request);

				// Return the response to client
				if (response != null) {
					sendOutgoingResponse(createJSON(response));
				} else {
					System.out.println(_CONNECTIONID + ": ClientHandler.run: " +
							_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort() + " GOT NULL RESPONSE, NOTHING TO SEND");
				}
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

	private void sendOutgoingResponse(String response) {
		try {
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
		System.out.println(_CONNECTIONID + ": ClientHandler.run: RECEIVED " + incomingJSON + " FROM " +
				_SOCKET.getInetAddress() + " ON PORT " + _SOCKET.getPort());
		return new Request(incomingJSON);
	}

	private String createJSON(Response response) {
		ObjectMapper mapper = new ObjectMapper();
		String dataJSON = null;

		try {

			Writer strWriter = new StringWriter();
			mapper.writeValue(strWriter, response);
			dataJSON = strWriter.toString();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dataJSON;
	}

}