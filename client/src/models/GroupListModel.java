package models;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class GroupListModel extends DefaultListModel<Group> implements NetInterface {
	
	
	
	
	
	
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
		/* test code */
		Group group = new Group("Alle ansatte");
		EmployeeListModel tempList = new EmployeeListModel();
		tempList.initialize();
		for (Object employee : tempList.toArray()) {
			group.add((Employee)employee);
		}
		this.addElement(group);
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
