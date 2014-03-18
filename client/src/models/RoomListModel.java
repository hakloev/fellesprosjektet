package models;

import controllers.OutboundWorker;
import controllers.ResponseWaiter;
import controllers.SocketListener;

import org.json.simple.JSONObject;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class RoomListModel extends DefaultListModel<Room> implements NetInterface {
	
	
	private int minimumCapacity;

    public RoomListModel() {

    }

    public RoomListModel(int capacity) {
		this.minimumCapacity = capacity;
	}
	
	
	public void setCapacity(int capacity) {
		this.minimumCapacity = capacity;
	}
	
	
	@Override
	public void initialize() {
        JSONObject json;
        json = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        json.put("request","roomlistmodel");
        json.put("dbmethod","initialize");
        json.put("capacity", minimumCapacity);
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
	public void refresh() {
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
