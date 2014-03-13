package helperclasses;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.*;


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

	public void set_REQUESTTYPE(String _REQUESTTYPE) {
		this._REQUESTTYPE = _REQUESTTYPE;
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
