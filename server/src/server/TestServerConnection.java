package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by hakloev on 10/03/14.
 */
public class TestServerConnection {

	// Simple client for testing server

	public static void main(String[] args) {
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		try {
			Socket client = new Socket("localhost", 4657);
			DataOutputStream writeToServer = new DataOutputStream(client.getOutputStream());
			BufferedReader readFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
			while (true) {
				String str = inFromUser.readLine();
				writeToServer.writeBytes(str + "\n");
				String newStr = readFromServer.readLine();
				System.out.println("GOT " + newStr + " FROM SERVER");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
