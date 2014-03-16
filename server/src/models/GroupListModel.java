package models;

import controllers.DBconnection;

import javax.swing.DefaultListModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("serial")
public class GroupListModel extends DefaultListModel<Group> implements DBInterface {

	public static void main(String[] args) {
		DBconnection.getConnection();
		GroupListModel g = new GroupListModel();
		g.initialize();
	}
	
	@Override
	public void initialize() {
		Connection dbCon = DBconnection.getConnection(); // Singelton class
		try {
			String sql1 = "SELECT * FROM gruppe";
			Statement stmt1 = dbCon.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			while (rs1.next()) {
				Group group = new Group(rs1.getObject(1).toString());
				String sql2 = "SELECT e.brukernavn, e.navn FROM ansatt e, gruppemedlem g WHERE g.gruppenavn = '" + group.getGroupName() +"' AND e.brukernavn = g.brukernavn";
				ResultSet rs2 = stmt1.executeQuery(sql2);
				while (rs2.next()) {
					Employee e = new Employee(rs2.getObject(1).toString(), rs2.getObject(2).toString());
					group.add(e);
				}
				rs2.close();
			this.addElement(group);
			}
			stmt1.close();
			rs1.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		System.out.println(this.toString());

		/* test code
		Group group = new Group("Alle ansatte");
		EmployeeListModel tempList = new EmployeeListModel();
		tempList.initialize();
		for (Object employee : tempList.toArray()) {
			group.add((Employee)employee);
		}
		this.addElement(group);
		/* end test code */
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
