package models;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class RoomListModel extends DefaultListModel<Room> implements DBInterface {
	
	
	
	
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
		/* test code */
		this.addElement(new Room("B1-183C", 6));
		this.addElement(new Room("Rill", 50));
		this.addElement(new Room("Rall", 50));
		/* end test code */
	}

	@Override
	public void refresh() {
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
