package helperclasses;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import helperclasses.Response;
import models.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Torgeir on 11.03.14.
 */
public class JSONHandler {

    public static Object parseJSON(Response response) { // this is supposed to return any given object, must be casted
        System.out.println("JSONHandler.parseJSON: PARSING REQUEST " + response.get_JSONRESPONSE() + "\n");
        ObjectMapper mapper = new ObjectMapper();
        Object object = null;
        try {
            JsonNode root = mapper.readTree(response.get_JSONRESPONSE());
            String type = String.valueOf(root.path("type"));
            response.set_RESPONSETYPE(String.valueOf(root.path("request")));
            System.out.println("Type " + type);

            switch (type) {
                case "\"appointment\"":
                    object = mapper.readValue(String.valueOf(root.path("object")), Appointment.class);
                    break;
                case "\"participantlistmodel\"":
                    object = new ArrayList<Participant>();
                    ArrayList<LinkedHashMap<?, ?>> p = mapper.readValue(String.valueOf(root.path("object")), ArrayList.class);
                    for (LinkedHashMap<?, ?> aP : p) {
                        ((ArrayList<Participant>) object).add(new Participant(aP.get("userName").toString(), aP.get("name").toString(), ParticipantStatus.valueOf(aP.get("participantStatus").toString())));
                    }
                    ParticipantListModel model = new ParticipantListModel();
                    for (Participant participant : (ArrayList<Participant>) object) {
                        model.addElement(participant);
                    }
                    return model;
                case "\"participant\"":
                    object = mapper.readValue(String.valueOf(root.path("object")), Participant.class);
                    break;
                case "\"employee\"":
    				object = mapper.readValue(String.valueOf(root.path("object")), Employee.class);
    				break;
                default:
                    System.out.println("Request.parseJSON: ELSE ");
                    return null;
            }



            // TODO: behandle forskjellige typer response





        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return object;
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
