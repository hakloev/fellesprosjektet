package models;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class NotificationListModel extends DefaultListModel<Notification> implements NetInterface {
	
	
	
	
	
	
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
		/* test code */
		this.addElement(new Notification(0, "per", 0, "invitation"));
		/* end test code */
	}

	@Override
	public void refresh() {
		this.clear();
		this.initialize();
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
