package models;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Group {

	private String groupName;

	private ArrayList<Employee> employees;

	public Group(String groupName) {
		this.employees = new ArrayList<Employee>();
		this.groupName = groupName;
	}

	public ArrayList<Employee> getEmployees() {
		return employees;
	}

	public String getGroupName() {
		return groupName;
	}

	@Override
	public String toString() {
		return groupName;
	}
	
}
