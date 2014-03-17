package models;

import controllers.DBconnection;

import javax.swing.DefaultListModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


@SuppressWarnings("serial")
public class ParticipantListModel extends DefaultListModel<Participant> implements DBInterface {

	private int appointmentID;

	public ParticipantListModel() {
		super();
	}

    public Object[] getParticipants() {
        return this.toArray();
    }

    public ParticipantListModel(ParticipantListModel participantList) {
		super();
		for (Object participant : participantList.toArray()){
			this.addElement((Participant) participant);
		}
	}

	public int getAppointmentID() {
		return appointmentID;
	}

	public void setAppointmentID(int appointmentID) {
		this.appointmentID = appointmentID;
	}

	@Override
	public void addElement(Participant participant){
		if (this.contains(participant)){
			return;
		}
		super.addElement(participant);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void refresh() {
		this.initialize();
	}

	@Override
	public void save() {
		Connection dbCon = DBconnection.getConnection();
		try {
			Statement stmt = dbCon.createStatement();
			String sql = null;
			for (int i = 0; i < this.size(); i++) {
				Participant p = this.get(i);
				int show = 0;
				if (p.isShowInCalendar()) {
					show = 1;
				}
				sql = "insert into deltager values ('" + p.getUserName() + "', '" + this.appointmentID + "', '" + p.getParticipantStatus().toString() + "', null, '" + show + ")";
				System.out.println(sql);
				stmt.executeUpdate(sql);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete() {

	}
}
