package models;

import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class EmployeeComboBoxModel extends DefaultComboBoxModel<Employee> {
	
	
	/**
	 * Wrapper class for EmployeeListModel
	 */
	public EmployeeComboBoxModel() {
		super();
		EmployeeListModel tempModel = new EmployeeListModel();
		tempModel.initialize();
		
		for (Object employee : tempModel.toArray()) {
			this.addElement((Employee)employee);
		}
	}
	
	
}
