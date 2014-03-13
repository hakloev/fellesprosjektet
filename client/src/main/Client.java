package main;
import controllers.OutboundWorker;
import helperclasses.Request;
import controllers.SocketListener;
import models.Appointment;
import models.Participant;
import models.ParticipantListModel;
import models.ParticipantStatus;

/**
 * Created by Truls on 13.03.14.
 */
public class Client {

    public static void main(String[] args) {
        //Creating a SocketClient object
        SocketListener client = new SocketListener ("78.91.29.166",4657);
        //trying to establish connection to the server
        client.connect();

        ParticipantListModel plist = new ParticipantListModel();
        plist.addElement(new Participant("trulsmp","truls", ParticipantStatus.participating));
        plist.addElement(new Participant("hakloev","Haakon", ParticipantStatus.notParticipating));

        Appointment appointment = new Appointment();

        Request request = new Request("participantlistmodel","POST", plist);

        OutboundWorker.sendRequest(request);

        /*

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

       */

        //new CalendarView();



    }
}


