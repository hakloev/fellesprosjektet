package models;

public class Employee {
	
	
	private String username;
	private String name;
	
	
	public Employee(String username, String name) {
		this.username = username;
		this.name = name;
	}


    public String getUserName() {
		return username;
	}
	
	
	public String toString() {
		return name;
	}


	public String getName() {
		return name;
	}

	
	@Override
    public boolean equals(Object employee){
        if (employee instanceof Employee) {
            return (((Employee)employee).username.equals(this.username));
        }
        return false;
    }
	
	
	
}
