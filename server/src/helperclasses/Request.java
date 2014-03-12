package helperclasses;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import models.*;
import sun.jvm.hotspot.ci.ciObjArrayKlass;


/**
 * Created by Håkon Ødegård Løvdal on 10/03/14.
 */
public class Request {

	private final String _TIMEOFREQUEST;
	private final String _JSONREQUEST;
	private String _REQUESTTYPE;

	public Request(String jsonRequest) {
		_TIMEOFREQUEST = getTime();
		_JSONREQUEST = jsonRequest;
		_REQUESTTYPE = null;
	}

	public Object parseJSON() { // this is supposed to return any given object, must be casted
		ObjectMapper mapper = new ObjectMapper();
		Object object = null; // Will cast in DatabaseWorker
		try {
			JsonNode root = mapper.readTree(_JSONREQUEST);
			String type = String.valueOf(root.path("type"));
			_REQUESTTYPE = String.valueOf(root.path("request"));
			System.out.println("Type " + type);

			if (type.equals("\"appointment\"")) {
				object = mapper.readValue(String.valueOf(root.path("object")), Appointment.class);
			} else if (type.equals("\"employee\"")) {
				object = mapper.readValue(String.valueOf(root.path("object")), Employee.class);
			} else if (type.equals("\"groupname\"")) {
				object = mapper.readValue(String.valueOf(root.path("object")), Groupname.class);
			} else if (type.equals("\"meetingroom\"")) {
				object = mapper.readValue(String.valueOf(root.path("object")), MeetingRoom.class);
			} else if (type.equals("\"notification\"")) {
				object = mapper.readValue(String.valueOf(root.path("object")), Notification.class);
			} else {
				System.out.println("ELSE IN Request.parseJSON");
				return null;
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return object;
	}

	public static String getTime() {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return df.format(date);
	}
}
