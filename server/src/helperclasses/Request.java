package helperclasses;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Created by Håkon Ødegård Løvdal on 10/03/14.
 *
 * Request object for holding the incoming json-request and time of request
 *
 */
public class Request {

	private final String _TIMEOFREQUEST;
	private final String _JSONREQUEST;
	private String _REQUESTTYPE;

	public Request(String jsonRequest) {
		_TIMEOFREQUEST = getTime();
		System.out.println("Request.request: TIME OF REQUEST: " + getTime());
		_JSONREQUEST = jsonRequest;
		_REQUESTTYPE = null;
	}

	public String get_REQUESTTYPE() {
		return _REQUESTTYPE;
	}

	public String get_JSONREQUEST() {
		return _JSONREQUEST;
	}

	public String get_TIMEOFREQUEST() {
		return _TIMEOFREQUEST;
	}

	public static String getTime() { // Static so it can be used other places in the program
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return df.format(date);
	}


}
