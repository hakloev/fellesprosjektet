package models;

public class Employee {
	
	
	private String userName;
	private String name;
	
	
	public Employee(String userName, String name) {
		this.userName = userName;
		this.name = name;
	}

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
		return userName;
	}
	
	
	public String toString() {
		return name;
	}


	public String getName() {
		return name;
	}

}
