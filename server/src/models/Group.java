package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Group extends ArrayList<Employee> {
	
	
	private String groupName;
	
	@JsonCreator
	public Group(@JsonProperty("groupName") String groupName) {
		super();
		this.groupName = groupName;
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
