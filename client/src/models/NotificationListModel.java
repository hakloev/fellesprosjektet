package models;

import com.sun.org.apache.regexp.internal.recompile;
import controllers.OutboundWorker;
import helperclasses.Request;

import javax.swing.DefaultListModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@SuppressWarnings("serial")
public class NotificationListModel extends DefaultListModel<Notification> implements NetInterface {
	
	
	
	
	
	
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub

        //OutboundWorker.sendRequest(request);
		/* test code
		this.addElement(new Notification(0, "per", 0, "invitation"));
		 end test code */
	}

	@Override
	public void refresh() {
		this.clear();
		this.initialize();
	}

	@Override
	public void save() {
		Request request = new Request("notificationlistmodel","save",this);
        //OutboundWorker.sendRequest(request);
	}

	@Override
	public void delete() {
        Request request = new Request("notificationlistmodel","delete",this);
        //OutboundWorker.sendRequest(request);

	}

}
