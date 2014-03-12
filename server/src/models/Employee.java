package models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee implements DBinterface {
    private String username;
    private String password;
    private String name;

	@Override
	public String toString() {
		return "Employee{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", name='" + name + '\'' +
				'}';
	}

	@JsonCreator
    public Employee(@JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("name") String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

	@Override
    public void save() {

    }

    @Override
    public void delete() {

    }


    @Override
    public void initialize() {

    }

    @Override
    public void refresh() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
