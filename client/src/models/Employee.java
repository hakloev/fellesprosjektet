package models;

public class Employee {
	
	
	private String username;
	private String name;

    public Employee() {
    }

    public Employee(String username, String name) {
		this.username = username;
		this.name = name;
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
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
