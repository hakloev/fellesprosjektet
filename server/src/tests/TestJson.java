package tests;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.DatabaseWorker;
import helperclasses.Request;
import models.Employee;
import models.Participant;
import models.ParticipantStatus;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Håkon Ødegård Løvdal on 11/03/14.
 */
public class TestJson {

	public static void main(String[] args) {
		RequestTest request = new RequestTest("employee","get",  new Participant("uname", "derp", ParticipantStatus.participating, true));
		ObjectMapper mapper = new ObjectMapper();

		try {
			Writer strWriter = new StringWriter();
			mapper.writeValue(strWriter, request);
			String userDataJSON = strWriter.toString();
			System.out.println(userDataJSON);

			// Loevdal
			JsonNode root = mapper.readTree(userDataJSON);
			JsonNode fields = root.get("type");
			//System.out.println(fields);

			Request r = new Request(userDataJSON);
			DatabaseWorker.handleRequest(r);

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
