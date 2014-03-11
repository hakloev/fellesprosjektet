package helperclasses;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


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
		return null;
	}

	public static String getTime() {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return df.format(date);
	}
}
