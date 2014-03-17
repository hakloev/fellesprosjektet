package models;

import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class EmployeeComboBoxModel extends DefaultComboBoxModel<Employee> implements NetInterface {
	
	
	/**
	 * Wrapper class for EmployeeListModel
	 */
	public EmployeeComboBoxModel() {
		super();
		
	}

	
	@Override
	public void initialize() {
		
		/* test code */
		EmployeeListModel tempModel = new EmployeeListModel();
		tempModel.initialize();
		
		for (Object employee : tempModel.toArray()) {
			this.addElement((Employee)employee);
		}
		/* end test code */
	}

	
	@Override
	public void refresh() {
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
