package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {
	
	
	private String userName;
	private String name;
	
	@JsonCreator
	public Employee(@JsonProperty("userName") String userName, @JsonProperty("name") String name) {
		this.userName = userName;
		this.name = name;
	}


	public String getUserName() {
		return userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String toString() {
		return name;
	}

}
