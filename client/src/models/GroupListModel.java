package models;

import controllers.OutboundWorker;
import controllers.ResponseWaiter;
import controllers.SocketListener;

import org.json.simple.JSONObject;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class GroupListModel extends DefaultListModel<Group> implements NetInterface {
	
	
	
	
	
	
	
	@Override
	public void initialize() {
        JSONObject json;
        json = new JSONObject();
        json.put("request","grouplistmodel");
        json.put("dbmethod","initialize");
        OutboundWorker.sendRequest(json);
        
        Object[] response = new Object[1];
        new ResponseWaiter(SocketListener.getSL(), response);
        
        if (response[0] != null && response[0] instanceof GroupListModel) {
        	for (Object group : ((GroupListModel)response[0]).toArray() ) {
        		this.addElement((Group)group);
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
