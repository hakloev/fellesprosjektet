package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import controllers.LogoutException;
import controllers.OutboundWorker;
import controllers.ResponseWaiter;
import controllers.SocketListener;

import org.json.simple.JSONObject;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class RoomListModel extends DefaultListModel<Room> implements NetInterface {
	
	
	private int minimumCapacity;
	
	private String startDateTime;
	private String endDateTime;

    public RoomListModel() {

    }

    public RoomListModel(int capacity, Date startDateTime, Date endDateTime) {
		this.minimumCapacity = capacity;
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.startDateTime = sdf.format(startDateTime);
		this.endDateTime = sdf.format(endDateTime);
	}
	
	
	public void setCapacity(int capacity) {
		this.minimumCapacity = capacity;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize() throws LogoutException {
        JSONObject json = new JSONObject();
        json.put("request","roomlistmodel");
        json.put("dbmethod","initialize");
        json.put("capacity", this.minimumCapacity);
        json.put("startdatetime", this.startDateTime);
        json.put("enddatetime", this.endDateTime);
        OutboundWorker.sendRequest(json);
        
        Object[] response = new Object[1];
        new ResponseWaiter(SocketListener.getSL(), response);
        
        if (response[0] != null && response[0] instanceof RoomListModel) {
        	for (Object room : ((RoomListModel)response[0]).toArray() ) {
        		this.addElement((Room)room);
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
		// Do not add code. This model should not be sent to server
	}

	@Override
	public void delete() {
		// Do not add code. This model can not be deleted from server
	}
	
	
	
	
	
}
