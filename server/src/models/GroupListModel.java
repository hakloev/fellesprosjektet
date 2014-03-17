package models;

import controllers.DBconnection;

import javax.swing.DefaultListModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GroupListModel extends DefaultListModel<Group> implements DBInterface {

	@Override
	public void initialize() {
		Connection dbCon = DBconnection.getConnection(); // Singelton class
		ArrayList<Group> groups = new ArrayList<Group>();
		try {
			String sql1 = "SELECT * FROM gruppe";
			Statement stmt1 = dbCon.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			while (rs1.next()) {
				groups.add(new Group(rs1.getObject(1).toString()));
			}
			stmt1.close();
			rs1.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}


		Statement stm2 = null;
		try {
			stm2 = dbCon.createStatement();
			for (Group g : groups) {
				String sql2 = "SELECT e.brukernavn, e.navn FROM ansatt e, gruppemedlem g WHERE g.gruppenavn = '" + g.getGroupName() +"' AND e.brukernavn = g.brukernavn";
				ResultSet rs2 = stm2.executeQuery(sql2);
				while (rs2.next()) {
					Employee e = new Employee(rs2.getObject("e.navn").toString(), rs2.getObject("e.brukernavn").toString());
					g.getEmployees().add(e);
				}
				rs2.close();
				this.addElement(g);
			}
			stm2.close();
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
		// Do not add code. This model should not be sent to server
	}

	@Override
	public void delete() {
		// Do not add code. This model can not be deleted from server
	}

}
