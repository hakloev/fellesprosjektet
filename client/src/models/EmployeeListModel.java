package models;

import javax.swing.DefaultListModel;

import org.json.simple.JSONObject;

import controllers.LogoutException;
import controllers.OutboundWorker;
import controllers.ResponseWaiter;
import controllers.SocketListener;

@SuppressWarnings("serial")
public class EmployeeListModel extends DefaultListModel<Employee> implements NetInterface {
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize() throws LogoutException {
		
		JSONObject json;
        json = new JSONObject();
        json.put("request","employeelistmodel");
        json.put("dbmethod","initialize");
        OutboundWorker.sendRequest(json);
        
        Object[] response = new Object[1];
        new ResponseWaiter(SocketListener.getSL(), response);
        
        if (response[0] != null && response[0] instanceof EmployeeListModel) {
        	for (Object employee : ((EmployeeListModel)response[0]).toArray() ) {
        		this.addElement((Employee)employee);
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
