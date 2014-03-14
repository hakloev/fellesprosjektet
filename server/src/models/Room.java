package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Room {
	
	@JsonProperty("roomCode")
	private String roomCode;
	@JsonProperty("capacity")
	private int capacity;
	
	@JsonCreator
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
