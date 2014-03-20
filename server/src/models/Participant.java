package models;

import controllers.DBconnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Participant implements DBInterface {

    private String userName;
    private String name;
	private boolean showInCalendar;
    private ParticipantStatus participantStatus;
	private int appointmentID;

    public Participant(String userName, String name,
                     ParticipantStatus participantStatus, boolean showInCalendar, int appointmentID) {
        this.userName = userName;
        this.name = name;
        this.participantStatus = participantStatus;
	    this.showInCalendar = showInCalendar;
	    this.appointmentID = appointmentID;
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

	@Override
	public void initialize() {

	}

	@Override
	public void refresh() {

	}

	@Override
	public void save() {
		System.out.println("ParticipantListModel.save");
		Connection dbCon = DBconnection.getConnection();
		try {
			Statement stmt = dbCon.createStatement();
			String sql = "SELECT count(*) FROM deltager WHERE brukernavn = '" + this.getUserName() + "' AND avtaleid = " + this.appointmentID;
			ResultSet rs = stmt.executeQuery(sql);
			int isParticipant = -1;
			if (rs.next()) {
				isParticipant = rs.getInt(1);
				System.out.println("Participating = " + isParticipant);
			}
			rs.close();

			int show = 0;
			if (this.isShowInCalendar()) {
				show = 1;
			}
			String deltar = null;
			if (this.getParticipantStatus() != null) {
				deltar = "deltar";
				if (this.getParticipantStatus().toString().equals("Deltar ikke")) {
					deltar = "deltar_ikke";
				}
			}

			if (isParticipant == 0) {
				if (deltar == null) {
					sql = "INSERT INTO deltager (brukernavn, avtaleid, vises) VALUES ('" + this.getUserName() + "', '" + this.appointmentID + "', '" + show + "')";
				} else {
					sql = "INSERT INTO deltager (brukernavn, avtaleid, deltagerstatus, vises) VALUES ('" + this.getUserName() + "', '" + this.appointmentID + "', '" + deltar + "', '" + show + "')";
				}
			} else {
				if (deltar == null) {
					sql = "UPDATE deltager SET vises = '" + show + "' WHERE brukernavn = '" + this.getUserName() + "' AND avtaleid = '" + this.appointmentID + "'";
				} else {
					sql = "UPDATE deltager SET vises = '" + show + "', deltagerstatus = '" + deltar + "' WHERE brukernavn = '" + this.getUserName() + "' AND avtaleid = '" + this.appointmentID + "'";
				}
			}
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void delete() {
		System.out.println("Participant.delete");
		Connection dbCon = DBconnection.getConnection();
		try {
			Statement stmt = dbCon.createStatement();
			String sql = "DELETE FROM deltager WHERE avtaleid = " + this.appointmentID + " AND brukernavn = '" + this.getUserName() + "'";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

