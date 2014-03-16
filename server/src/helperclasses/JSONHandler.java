package helperclasses;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.print.DocFlavor;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Håkon Ødegård Løvdal on 12/03/14.
 */
public class JSONHandler {

	public static Object parseJSON(Request request) { // this is supposed to return any given object, must be casted
		System.out.println("JSONHandler.parseJSON: PARSING REQUEST " + request.get_JSONREQUEST());
		ObjectMapper mapper = new ObjectMapper();
		JSONParser parser = new JSONParser();
		Object object = null; // Will cast in DatabaseWorker
		try {
			JsonNode root = mapper.readTree(request.get_JSONREQUEST());
			String type = String.valueOf(root.path("type"));
			request.set_REQUESTTYPE(String.valueOf(root.path("request")));
			System.out.println("JSONHandler.parseJSON: TYPE " + type);

			if (request.get_REQUESTTYPE().equals("\"login\"")) {
				System.out.println("JSONHandler.parseJSON: LOGIN REQUEST");
				Object parsed = parser.parse(String.valueOf(root.path("object")));
				JSONArray array = (JSONArray) parsed;
				String user = array.get(0).toString();
				String pw = array.get(1).toString();
				object = new Login(user, pw);
				return object;
			}

			// THIS CODE IS UGLY
			if (type.equals("\"appointment\"")) {
				object = new Appointment();
				Object parsed = parser.parse(String.valueOf(root.path("object")));
				JSONObject jsonObject = (JSONObject) parsed;
				JSONObject employee = (JSONObject) jsonObject.get("appointmentLeader");
				((Appointment) object).setAppointmentLeader(new Employee(employee.get("name").toString(), employee.get("userName").toString()));
				((Appointment) object).setDescription(jsonObject.get("description").toString());
				((Appointment) object).setLocation(jsonObject.get("location").toString());
				String date = jsonObject.get("date").toString();
				String start = jsonObject.get("start").toString();
				String end = jsonObject.get("end").toString();
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy:HH:mm");
				String startDate = date + ":" + start;
				String endDate = date +":"+ end;
				((Appointment) object).setStart(sdf.parse(startDate));
				((Appointment) object).setEnd(sdf.parse(endDate));

				JSONObject participantList = (JSONObject) jsonObject.get("participantList");
				ParticipantListModel model = new ParticipantListModel();
				JSONArray msg = (JSONArray) participantList.get("participants");
				Iterator<JSONObject> iterator = msg.iterator();
				while (iterator.hasNext()) {
					JSONObject factObj = (JSONObject) iterator.next();
					model.addElement(new Participant(factObj.get("userName").toString(), factObj.get("name").toString(), ParticipantStatus.valueOf(factObj.get("participantStatus").toString())));
					String user = (String) factObj.toJSONString();
				}
				((Appointment)object).setParticipantList(model);
			} else if (type.equals("\"participant\"")) {
				object = mapper.readValue(String.valueOf(root.path("object")), Participant.class);
			} else if (type.equals("\"employee\"")) {
				object = mapper.readValue(String.valueOf(root.path("object")), Employee.class);
			} else {
				System.out.println("Request.parseJSON: ELSE ");
				return object;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
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
