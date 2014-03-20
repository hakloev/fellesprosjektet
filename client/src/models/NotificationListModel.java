package models;

import javax.swing.DefaultListModel;

import org.json.simple.JSONObject;

import controllers.LogoutException;
import controllers.OutboundWorker;
import controllers.ResponseWaiter;
import controllers.SocketListener;

@SuppressWarnings("serial")
public class NotificationListModel extends DefaultListModel<Notification> implements NetInterface {
	
	
	private Employee employee;
	
	
	/**
	 * Creates an empty notification list
	 * Should not be initialized
	 */
	public NotificationListModel() {
		
	}
	
	
	public NotificationListModel(Employee employee) {
		super();
		this.employee = employee;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize() throws LogoutException {
		JSONObject json;
        json = new JSONObject();
        json.put("request","notificationlistmodel");
        json.put("dbmethod","initialize");
        json.put("employee", employee.getUsername());
        OutboundWorker.sendRequest(json);
        
        Object[] response = new Object[1];
        new ResponseWaiter(SocketListener.getSL(), response);
        
        if (response[0] != null && response[0] instanceof NotificationListModel) {
        	NotificationListModel nlm = (NotificationListModel)response[0];
        	for (Object notif : nlm.toArray()) {
        		this.addElement((Notification)notif);
        	}
        }
	}

	@Override
	public void refresh() throws LogoutException {
		this.clear();
		this.initialize();
	}

	@Override
	public void save() {
		// Not in need of being saved
	}

	@Override
	public void delete() {
		// The whole list should not be deleted
	}

}
