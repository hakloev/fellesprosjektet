package models;

public class Participant {

    private String userName;
    private String name;
	private boolean showInCalendar;
    private ParticipantStatus participantStatus;

    public Participant(String userName, String name,
                     ParticipantStatus participantStatus, boolean showInCalendar) {
        this.userName = userName;
        this.name = name;
        this.participantStatus = participantStatus;
	    this.showInCalendar = showInCalendar;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", participantStatus=" + participantStatus +
                '}';
    }



	public boolean isShowInCalendar() {
		return showInCalendar;
	}

	public String getName() {
		return name;
	}

	public String getUserName() {
		return userName;
	}

    public ParticipantStatus getParticipantStatus() {
        return participantStatus;
    }

    @Override
    public boolean equals(Object participant){
        if (participant instanceof Participant) {
            return (((Participant)participant).userName.equals(this.userName));
        }
        return false;
    }

}

