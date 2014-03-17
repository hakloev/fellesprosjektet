package helperclasses;



import com.sun.java.swing.plaf.windows.TMSchema;
import gui.LoginScreen;

import models.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Torgeir on 11.03.14.
 */
public class JSONHandler {


    public static Object parseJSON(String responseString) { // this is supposed to return any given object, must be casted
        Object object = null;
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            Object jobject = parser.parse(responseString);
            jsonObject = (JSONObject) jobject;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (jsonObject != null) {
            if (jsonObject.get("response").equals("login")) {
                JSONArray array = (JSONArray) jsonObject.get("array");
                if (array.isEmpty()) {
                    // TODO: invalid login, send response to GUI
                    System.out.println("Invalid Login");
                }
                else {
                    Employee employee = new Employee(array.get(0).toString(),array.get(1).toString());
                    System.out.println(employee.getName() + employee.getUsername());
                    return employee;
                }
            }
            else if (jsonObject.get("response").equals("weekcalendar")) {
                if (jsonObject.get("dbmethod").equals("initialize")) {

                    WeekCalendar weekCalendar = new WeekCalendar();
                    JSONArray jsonArray = (JSONArray) jsonObject.get("appointments");
                    Iterator iterator = jsonArray.iterator();
                    while (iterator.hasNext()) {
                        JSONObject appointment = (JSONObject) iterator.next();
                        weekCalendar.addAppointment(createAppointment(appointment));

                    }
                    return weekCalendar;
                }
            }
            else if (jsonObject.get("response").equals("appointment")) {
                if (jsonObject.get("dbmethod").equals("initialize")) {
                    Appointment appointment = createAppointment((JSONObject) jsonObject.get("model"));
                    return appointment;
                }
                else if (jsonObject.get("dbmethod").equals("save")) {

                }
                else if (jsonObject.get("dbmethod").equals("delete")) {

                }
            }
            else if (jsonObject.get("response").equals("roomlistmodel")) {
                RoomListModel roomListModel = new RoomListModel();
                JSONArray jsonArray = (JSONArray) jsonObject.get("rooms");
                Iterator iterator = jsonArray.iterator();
                while (iterator.hasNext()) {
                    JSONObject jroom = (JSONObject) iterator.next();
                    Room room = new Room((String) jroom.get("roomCode"),Integer.valueOf(jroom.get("capacity").toString()));
                    roomListModel.addElement(room);
                }
                return roomListModel;
            }


        }


        return object;
    }

    private static Appointment createAppointment(JSONObject jsonObject) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentID(Integer.valueOf(jsonObject.get("appointmentID").toString()));
        appointment.setDescription(String.valueOf(jsonObject.get("description").toString()));
        JSONObject appEmployee = (JSONObject) jsonObject.get("appointmentLeader");
        Employee employee = new Employee((String) appEmployee.get("username"), (String) appEmployee.get("name"));
        appointment.setAppointmentLeader(employee);

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            appointment.getStartDateTime().setTime(sdf.parse((String) jsonObject.get("startDateTime")));
            appointment.getEndDateTime().setTime(sdf.parse((String) jsonObject.get("endDateTime")));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        if (jsonObject.get("location") != null) {
            //TODO: legge til room
        }
        appointment.setLocation((String) jsonObject.get("locationText"));
        if (jsonObject.get("participants") != null) {

            JSONArray array = (JSONArray) jsonObject.get("participants");
            Iterator iterator = array.iterator();
            while (iterator.hasNext()) {
                JSONObject jp = new JSONObject();
                jp =  (JSONObject) iterator.next();
                ParticipantStatus status;
                if (jp.get("participantStatus").toString().equals("Deltar")) {
                    status = ParticipantStatus.participating;
                }
                else {
                    status = ParticipantStatus.notParticipating;
                }
                ParticipantStatus status = new ParticipantStatus(jp.get("participantStatus"));
                Participant participant = new Participant((String) jp.get("username").toString(),(String) jp.get("name"),status);
                appointment.getParticipantList().addElement(participant);
            }
        }

        return appointment;

    }

}
