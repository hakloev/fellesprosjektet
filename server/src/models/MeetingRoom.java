package models;


public class MeetingRoom implements DBinterface {
    private String roomCode;

    public MeetingRoom(String roomCode) {
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
