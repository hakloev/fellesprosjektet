package models;

import controllers.DBconnection;

import javax.swing.DefaultListModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("serial")
public class EmployeeListModel extends DefaultListModel<Employee> implements DBInterface {

	@Override
	public void initialize() {
		System.out.println("EmployeeListModel.initialize");
		Connection dbCon = DBconnection.getConnection(); // Singelton class
		try {
			String sql = "SELECT * FROM ansatt";
			Statement stmt = dbCon.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				this.addElement(new Employee(rs.getObject("brukernavn").toString(), rs.getObject("navn").toString()));
			}
			stmt.close();
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}


	@Override
	public void refresh() {
		System.out.println("EmployeeListModel.refresh");
		this.initialize();
	}

	@Override
	public void save() {
		System.out.println("EmployeeListModel.save");
		// Do not add code. This model should not be sent to server
	}

	@Override
	public void delete() {
		System.out.println("EmployeeListModel.delete");
		// Do not add code. This model can not be deleted from server
	}
}
