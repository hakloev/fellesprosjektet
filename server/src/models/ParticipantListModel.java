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
		System.out.println("ParticipantListModel.initialize");
		Connection dbCon = DBconnection.getConnection();
		try {
			String sql = "SELECT d.*, a.navn FROM deltager d, ansatt a WHERE d.brukernavn = a.brukernavn AND d.avtaleid = " + this.appointmentID;
			Statement stmt = dbCon.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ParticipantStatus participating = null;
				if (rs.getString(3) != null) {
					participating = ParticipantStatus.participating;
					if (rs.getString(3).equals("deltar_ikke")) {
						participating = ParticipantStatus.notParticipating;
					} /* else if (rs.getString(3).equals("null")) {
						participating = null;
					}*/
				}
				boolean show = true;
				if (rs.getInt(5) == 0) {
					show = false;
				}
				Participant p = new Participant(rs.getString(1), rs.getString(6), participating, show, this.appointmentID);
				this.addElement(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void refresh() {
		System.out.println("ParticipantListModel.refresh");
		this.initialize();
	}

	@Override
	public void save() {
		System.out.println("ParticipantListModel.save");
		Connection dbCon = DBconnection.getConnection();
		try {
			Statement stmt = dbCon.createStatement();
			String sql = null;
			for (int i = 0; i < this.size(); i++) {
				Participant p = this.get(i);

				sql = "SELECT count(*) FROM deltager WHERE brukernavn = '" + p.getUserName() + "' AND avtaleid = " + this.appointmentID;
				ResultSet rs = stmt.executeQuery(sql);
				int isParticipant = -1;
				if (rs.next()) {
					isParticipant = rs.getInt(1);
					System.out.println("Participating = " + isParticipant);
				}
				rs.close();

				int show = 0;
				if (p.isShowInCalendar()) {
					show = 1;
				}
				String deltar = null;
				if (p.getParticipantStatus() != null) {
					deltar = "deltar";
					if (p.getParticipantStatus().toString().equals("Deltar ikke")) {
						deltar = "deltar_ikke";
					}
				}

				if (isParticipant == 0) {
					if (deltar == null) {
						sql = "INSERT INTO deltager (brukernavn, avtaleid, vises) VALUES ('" + p.getUserName() + "', '" + this.appointmentID + "', '" + show + "')";
					} else {
						sql = "INSERT INTO deltager (brukernavn, avtaleid, deltagerstatus, vises) VALUES ('" + p.getUserName() + "', '" + this.appointmentID + "', '" + deltar + "', '" + show + "')";
					}
				} else {
					if (deltar == null) {
						sql = "UPDATE deltager SET vises = '" + show + "' WHERE brukernavn = '" + p.getUserName() + "' AND avtaleid = '" + this.appointmentID + "'";
					} else {
						sql = "UPDATE deltager SET vises = '" + show + "', deltagerstatus = '" + deltar + "' WHERE brukernavn = '" + p.getUserName() + "' AND avtaleid = '" + this.appointmentID + "'";
					}
				}
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
		System.out.println("ParticipantListModel.delete");
	}
}
