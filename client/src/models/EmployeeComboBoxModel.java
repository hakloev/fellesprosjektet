package models;

import javax.swing.DefaultComboBoxModel;

import controllers.LogoutException;

@SuppressWarnings("serial")
public class EmployeeComboBoxModel extends DefaultComboBoxModel<Employee> implements NetInterface {
	
	
	/**
	 * Wrapper class for EmployeeListModel
	 */
	public EmployeeComboBoxModel() {
		super();
		
	}

	
	@Override
	public void initialize() throws LogoutException {
		// Wrapper for employee list. This class should not communicate with the server directly.
		EmployeeListModel tempModel = new EmployeeListModel();
		tempModel.initialize();
		
		for (Object employee : tempModel.toArray()) {
			this.addElement((Employee)employee);
		}
	}

	
	@Override
	public void refresh() throws LogoutException {
		this.removeAllElements();
		this.initialize();
	}

	
	@Override
	public void save() {
		// Should not be saved
	}

	
	@Override
	public void delete() {
		// Can not be deleted
	}
	
	
}
