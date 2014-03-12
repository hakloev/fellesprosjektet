package tests;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Employee;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Håkon Ødegård Løvdal on 12/03/14.
 */
public class TestObjectToFromServer {

	public static void main(String[] args) {
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		Employee e = new Employee("hakloev", "MittPW", "Håkon Løvdal");
		System.out.println(e);
		RequestTest request = new RequestTest("employee","get", e);
		ObjectMapper mapper = new ObjectMapper();
		String userDataJSON = null;

		try {
			Writer strWriter = new StringWriter();
			mapper.writeValue(strWriter, request);
			userDataJSON = strWriter.toString();
			System.out.println(userDataJSON);
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonGenerationException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			Socket client = new Socket("localhost", 4657);
			DataOutputStream writeToServer = new DataOutputStream(client.getOutputStream());
			BufferedReader readFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));

			writeToServer.writeBytes(userDataJSON + "\n");
			String newStr = readFromServer.readLine();
			System.out.println("GOT " + newStr + " FROM SERVER");

		} catch (SocketException e2) {
			System.out.println("CONNECTION LOST");
		} catch (IOException e3) {
			e3.printStackTrace();
		}

	}

}
