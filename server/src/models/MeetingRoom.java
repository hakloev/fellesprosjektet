package models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MeetingRoom implements DBinterface {
    private String roomCode;

	@JsonCreator
    public MeetingRoom(@JsonProperty("roomCode") String roomCode) {
        this.roomCode = roomCode;
    }

    public void delete() {

    }

    public void initialize() {

    }

    public void save() {

    }

    @Override
    public void refresh() {

    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }


}
