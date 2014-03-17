package models;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class EmployeeListModel extends DefaultListModel<Employee> implements NetInterface {
	
	
	
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
		/* test code */
		this.addElement(new Employee("siri", "Siri Gundersen"));
		this.addElement(new Employee("arvid", "Arvid Pettersen"));
		this.addElement(new Employee("per", "Per Haraldsen"));
		/* end test code */
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
