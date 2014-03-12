package models;


public class Employee implements DBinterface {
    private String username;
    private String password;
    private String name;

    public Employee(String username, String password, String name) {
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
