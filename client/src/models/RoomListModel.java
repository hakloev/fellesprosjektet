package models;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class RoomListModel extends DefaultListModel<Room> implements NetInterface {
	
	
	private int minimumCapacity;
	
	
	public RoomListModel(int capacity) {
		this.minimumCapacity = capacity;
	}
	
	
	public void setCapacity(int capacity) {
		this.minimumCapacity = capacity;
	}
	
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
		/* test code */
		Room[] rooms = new Room[] {
			new Room("B1-183C", 6),
			new Room("WhiteCube", 8),
			new Room("Rill", 50),
			new Room("Rall", 50)
		};
		
		for (Room room : rooms) {
			if (room.getCapacity() >= minimumCapacity) {
				this.addElement(room);
			}
		}
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
