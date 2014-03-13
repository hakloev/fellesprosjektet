package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Room {
	
	
	private String roomCode;
	private int capacity;
	
	@JsonCreator
	public Room(@JsonProperty("roomCode") String roomCode, @JsonProperty("capacity") int capacity) {
		this.roomCode = roomCode;
		this.capacity = capacity;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getRoomCode() {
		return roomCode;
	}
	
	
	public int getCapacity() {
		return capacity;
	}
	
	
}
