package controllers;

import models.*;

/**
 * Created by Truls on 12.03.14.
 */
public class InboundWorker extends Thread implements Runnable {

    public static void handleResponse(String responseString) {

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
                // TODO: SENDE VIDERE TIL KLASSE SOM OPPDATER KLIENT

    }
}


