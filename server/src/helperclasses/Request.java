package helperclasses;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import models.*;


/**
 * Created by Håkon Ødegård Løvdal on 10/03/14.
 */
public class Request {

	private final String _TIMEOFREQUEST;
	private final String _JSONREQUEST;

	public Request(String jsonRequest) {
		_TIMEOFREQUEST = getTime();
		_JSONREQUEST = jsonRequest;
	}

	public Object parseJSON() { // this is supposed to return any given object, must be casted
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(_JSONREQUEST);
		return mapper.readValue(String.valueOf(), Employee.class);

		/*	if (classTypeString.equals("appointment")) {
				// TODO return createAppointmentObject();
			} else if (classTypeString.equals("employee")) {
				return mapper.readValue(root.path("object").traverse(), Employee.class);
			} else if (classTypeString.equals("groupname")) {
				// TODO
			} else if (classTypeString.equals("meetingroom")) {
				// TODO
			} else if (classTypeString.equals("warning")) {
				// TODO
			} else {
				// TODO
			}*/
		} catch (IOException e) {
			e.printStackTrace();
		}


		//User userFromJSON = mapper.readValue(userDataJSON, User.class);
		//System.out.println(userFromJSON);
		return null;
	}

	public static String getTime() {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return df.format(date);
	}
}
