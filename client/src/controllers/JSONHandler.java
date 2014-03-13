package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Appointment;
import models.Employee;
import models.Notification;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Created by Torgeir on 11.03.14.
 */
public class JSONHandler {

    public static Object parseJSON(Response response) { // this is supposed to return any given object, must be casted

        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(response.get_JSONRESPONSE());
            String type = String.valueOf(root.path("type"));
            response.set_RESPONSETYPE(String.valueOf(root.path("request")));
            System.out.println("Type " + type);




            // TODO: behandle forskjellige typer response





        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return response;
    }




    public static String toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String userDataJSON = null;


        try {

            Writer strWriter = new StringWriter();
            mapper.writeValue(strWriter, object);
            userDataJSON = strWriter.toString();
            System.out.println("createdJSON: "+ userDataJSON + "\n");

        } catch (IOException e) {

            e.printStackTrace();

        }

        return userDataJSON;
    }





}
