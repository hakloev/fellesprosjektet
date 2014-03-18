package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Group {

	@JsonProperty("groupname")
	private String groupName;

	@JsonProperty("employees")
	private ArrayList<Employee> employees;
	
	@JsonCreator
	public Group(String groupName) {
		this.employees = new ArrayList<Employee>();
		this.groupName = groupName;
	}

	public ArrayList<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(ArrayList<Employee> employees) {
		this.employees = employees;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String toString() {
		return groupName;
	}
	
}
