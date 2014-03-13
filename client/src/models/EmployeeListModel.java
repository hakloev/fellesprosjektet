package models;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class EmployeeListModel extends DefaultListModel<Employee> implements NetInterface {
	
	
	
	public EmployeeListModel() {
		super();
		
		/* test code */
		this.addElement(new Employee("siri", "Siri Gundersen"));
		this.addElement(new Employee("arvid", "Arvid Pettersen"));
		this.addElement(new Employee("per", "Per Haraldsen"));
		/* end test code */
	}
	
	
	
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

}
