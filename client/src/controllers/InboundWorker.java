package controllers;

import models.*;

/**
 * Created by Truls on 12.03.14.
 */
public class InboundWorker{

    private ResponseWaiter registeredWaitingInstance;
    private Object responseObject;

    public void handleResponse(String responseString) {

                Object object = JSONHandler.parseJSON(responseString);

                if (object instanceof WeekCalendar) {
                    System.out.println(((WeekCalendar) object).getAppointmentList().toString());
                }
                else if (object instanceof RoomListModel) {
                    System.out.println(((RoomListModel) object).toString());
                }
                else if (object instanceof Appointment) {
                    System.out.println(((Appointment) object).toString());
                }
                else if (object instanceof NotificationListModel) {
                    System.out.println("");
                }
                else if (object instanceof GroupListModel) {
                    System.out.println(((GroupListModel) object).toString());
                }


        System.out.println("ParsedObjectOutput: " + responseObject);
        System.out.println("this Thread: " + this);
            if (registeredWaitingInstance != null) {
            System.out.println("k test interrupt");
            registeredWaitingInstance.setReady(true);
           System.out.println("k test interrupt done");
           }
    }
}


