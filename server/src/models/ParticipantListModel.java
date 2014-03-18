package models;

import controllers.DBconnection;

import javax.swing.DefaultListModel;
import java.sql.Connection;
import java.sql.ResultSet;
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
		Connection dbCon = DBconnection.getConnection();
		try {
			String sql = "SELECT d.*, a.navn FROM deltager d, ansatt a WHERE d.brukernavn = a.brukernavn AND d.avtaleid = " + this.appointmentID;
			Statement stmt = dbCon.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ParticipantStatus participating = ParticipantStatus.participating;
				if (rs.getString(3).equals("deltar_ikke")) {
					participating = ParticipantStatus.notParticipating;
				}
				boolean show = true;
				if (rs.getInt(5) == 0) {
					show = false;
				}
				Participant p = new Participant(rs.getString(1), rs.getString(6), participating, show);
				this.addElement(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

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
				String deltar = "deltar";
				if (p.getParticipantStatus().toString().equals("deltar_ikke")) {
					deltar = "deltar_ikke";
				}
				sql = "insert into deltager values ('" + p.getUserName() + "', '" + this.appointmentID + "', '" + deltar + "', null, '" + show + "')";
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
