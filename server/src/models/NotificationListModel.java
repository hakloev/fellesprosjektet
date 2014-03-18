package models;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class NotificationListModel extends DefaultListModel<Notification> implements DBInterface {

	
	@Override
	public void initialize() {
		System.out.println("NotificationListModel.initialize");
	}

	@Override
	public void refresh() {
		System.out.println("NotificationListModel.refresh");
		this.initialize();
	}

	@Override
	public void save() {
		System.out.println("NotificationListModel.save");
	}

	@Override
	public void delete() {
		System.out.println("NotificationListModel.delete");
	}

}
