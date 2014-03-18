package tests;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Employee;
import models.Participant;
import models.ParticipantStatus;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by Håkon Ødegård Løvdal on 12/03/14.
 */
public class TestObjectToFromServer {

	public static void main(String[] args) {
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		RequestTest request1 = new RequestTest("groupname", "get", new Participant("uname", "derp", ParticipantStatus.participating, true));
		ObjectMapper mapper = new ObjectMapper();
		ObjectMapper mapper1 = new ObjectMapper();
		String userDataJSON = null;
		String groupJSON = null;

		try {
			Writer strWriter = new StringWriter();
			Writer strWriter1 = new StringWriter();
			mapper1.writeValue(strWriter1, request1);
			userDataJSON = strWriter.toString();
			groupJSON = strWriter1.toString();
			System.out.println(userDataJSON);
			System.out.println(groupJSON);
		} catch (JsonMappingException e12) {
			e12.printStackTrace();
		} catch (JsonGenerationException e21) {
			e21.printStackTrace();
		} catch (IOException e221) {
			e221.printStackTrace();
		}

		try {
			Socket client = new Socket("localhost", 4657);
			DataOutputStream writeToServer = new DataOutputStream(client.getOutputStream());
			BufferedReader readFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));

			//writeToServer.writeBytes(userDataJSON + "\n");
			writeToServer.writeBytes(groupJSON + "\n");
			String newStr = readFromServer.readLine();
			System.out.println("GOT " + newStr + " FROM SERVER");

		} catch (SocketException e52) {
			System.out.println("CONNECTION LOST");
		} catch (IOException e3) {
			e3.printStackTrace();
		}

	}

}
