package models;

public class Room {
	

	private String roomCode;
	private int capacity;

	public Room(String roomCode, int capacity) {
		this.roomCode = roomCode;
		this.capacity = capacity;
	}
	
	
	public String getRoomCode() {
		return roomCode;
	}
	
	
	public int getCapacity() {
		return capacity;
	}
	
	
	public String toString() {
		return roomCode + " : " + capacity + " personer";
	}
	
}
