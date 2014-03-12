package models;

public class Employee {
	
	
	private String userName;
	private String name;
	
	
	public Employee(String userName, String name) {
		this.userName = userName;
		this.name = name;
	}


	public String getUserName() {
		return userName;
	}
	
	
	public String toString() {
		return name;
	}

}
