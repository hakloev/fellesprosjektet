package controllers;

/**
 * Created by Truls on 13.03.14.
 */
public class Response {

    private final String type;
    private final String response;
    private Object object;

    public Response(String type, String responsetype, Object object) {
        this.type = type;
        this.response = responsetype;
        this.object = object;
    }

    public String getType() {
        return type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getResponse() {
        return response;
    }

}
