package helperclasses;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gui.LoginScreen;

import models.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 * Created by Torgeir on 11.03.14.
 */
public class JSONHandler {

    public static Object parseJSON(Response response) { // this is supposed to return any given object, must be casted
        System.out.println("JSONHandler.parseJSON: PARSING REQUEST " + response.get_JSONRESPONSE() + "\n");
        ObjectMapper mapper = new ObjectMapper();
        JSONParser parser = new JSONParser();
        Object object = null;
        try {
            JsonNode root = mapper.readTree(response.get_JSONRESPONSE());
            String type = String.valueOf(root.path("type"));
            response.set_RESPONSETYPE(String.valueOf(root.path("request")));
            System.out.println("Type " + type);

            if (type.equals("\"login\"")) {

                object = mapper.readValue(String.valueOf(root.path("object")),Employee.class);

            }
            else if (type.equals("\"employee\"")) {

                object = mapper.readValue(String.valueOf(root.path("object")), Employee.class);
            }
            else if (type.equals("\"appointment\"")) {
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
            }
            else if (type.equals("\"participant\"")) {
                object = mapper.readValue(String.valueOf(root.path("object")), Participant.class);

            }  else if (type.equals("\"login\"")) {
                object = mapper.readValue(String.valueOf(root.path("object")), Participant.class);
                LoginScreen.loggedIn = true;
            } else {
                System.out.println("Request.parseJSON: ELSE ");
                return null;

            }



            // TODO: behandle forskjellige typer response





        } catch (IOException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
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
