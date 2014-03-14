package models;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Group extends ArrayList<Employee> {
	
	
	private String groupName;
	
	
	public Group(String groupName) {
		super();
		this.groupName = groupName;
	}


	@Override
	public String toString() {
		return groupName;
	}
	
}
