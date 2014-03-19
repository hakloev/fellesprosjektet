package models;

import gui.CalendarView;

import javax.swing.DefaultListModel;
import javax.swing.event.ListDataListener;


@SuppressWarnings("serial")
public class ParticipantListModel extends DefaultListModel<Participant> {

	private Participant appointmentLeader;
	
	/**
	 * Creates a new empty list for a new appointment
	 */
	public ParticipantListModel(Participant appointmentLeader) {
		super();
		this.appointmentLeader = appointmentLeader;
		this.addElement(appointmentLeader);
	}
	
	
	/**
	 * Copy constructor
	 * 
	 * @param participantList
	 */
    public ParticipantListModel(ParticipantListModel participantList) {
		super();
		for (Object participant : participantList.toArray()){
			this.addElement((Participant) participant);
		}
		this.appointmentLeader = participantList.getAppoinmentLeader();
	}

    /**
     * JSON constructor
     */
    public ParticipantListModel() {
    	super();
    }


    public Object[] participants() {
        return this.toArray();
    }


	@Override
	public boolean isEmpty() {
		return this.isEmpty();
	}


	@Override
	public ListDataListener[] getListDataListeners() {
		return this.getListDataListeners();
	}


	@Override
	public void addElement(Participant participant){
		if (this.contains(participant)){
			return;
		}
		super.addElement(participant);
	}


    @Override
    public int getSize(){
        return super.getSize();
    }


	public Participant getAppoinmentLeader() {
		return this.appointmentLeader;
	}
	
	
	public void locateAppointmentLeader(Employee appLeader) {
		this.appointmentLeader = this.get(this.indexOf(new Participant(appLeader)));
	}

}


