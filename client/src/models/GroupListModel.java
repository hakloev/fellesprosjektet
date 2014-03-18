package models;

import controllers.OutboundWorker;
import helperclasses.Request;
import org.json.simple.JSONObject;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class GroupListModel extends DefaultListModel<Group> implements NetInterface {
	
	
	
	
	
	
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
        JSONObject json;
        json = new JSONObject();
        json.put("request","grouplistmodel");
        json.put("dbmethod","initialize");
        OutboundWorker.sendRequest(json);

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
