package helperclasses;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.*;

import javax.swing.*;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Håkon Ødegård Løvdal on 12/03/14.
 */
public class JSONHandler {


	public static Object parseJSON(Request request) { // this is supposed to return any given object, must be casted
		System.out.println("JSONHandler.parseJSON: PARSING REQUEST " + request.get_JSONREQUEST());
		ObjectMapper mapper = new ObjectMapper();
		Object object = null; // Will cast in DatabaseWorker
		try {
			JsonNode root = mapper.readTree(request.get_JSONREQUEST());
			String type = String.valueOf(root.path("type"));
			request.set_REQUESTTYPE(String.valueOf(root.path("request")));
			System.out.println("JSONHandler.parseJSON: TYPE " + type);

			switch (type) {
				case "\"appointment\"":
					object = mapper.readValue(String.valueOf(root.path("object")), Appointment.class);
					break;
				case "\"participantlistmodel\"":
					object = new ArrayList<Participant>();
					ArrayList<LinkedHashMap<?, ?>> p = mapper.readValue(String.valueOf(root.path("object").path("getParticipants")), ArrayList.class);
					for (int i = 0; i < p.size(); i++) {
						((ArrayList<Participant>) object).add(new Participant(p.get(i).get("userName").toString(), p.get(i).get("name").toString(), ParticipantStatus.valueOf(p.get(i).get("participantStatus").toString())));
					}
					break;
				case "\"participant\"":
					object = mapper.readValue(String.valueOf(root.path("object")), Participant.class);
					break;
				default:
					System.out.println("Request.parseJSON: ELSE ");
					return null;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return object;
	}

	public static String createJSON(Response response) {
		System.out.println("JSONHandler.createJSON: CREATING JSON");
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
