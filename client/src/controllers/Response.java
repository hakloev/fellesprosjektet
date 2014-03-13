package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Truls on 13.03.14.
 */
public class Response {
    private final String _TIMEOFRESPONSE;
    private final String _JSONRESPONSE;
    private String _RESPONSETYPE;

    public Response(String jsonResponse) {
        _TIMEOFRESPONSE = getTime();
        _JSONRESPONSE = jsonResponse;
        _RESPONSETYPE = null;
    }

    public void set_RESPONSETYPE(String _RESPONSETYPE) {
        this._RESPONSETYPE = _RESPONSETYPE;
    }

    public String get_RESPONSETYPE() {
        return _RESPONSETYPE;
    }

    public String get_JSONRESPONSE() {
        return _JSONRESPONSE;
    }

    public String get_TIMEOFRESPONSE() {
        return _TIMEOFRESPONSE;
    }

    public static String getTime() { // Static so it can be used other places in the program
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return df.format(date);
    }

}
