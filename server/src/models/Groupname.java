package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


public class Groupname implements DBinterface{

	private String gruppeNavn;
    private ArrayList<Employee> employeeList;

	@JsonCreator
	public Groupname(@JsonProperty("gruppeNavn") String gruppeNavn) {
		this.gruppeNavn = gruppeNavn;
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

    // Get-ers and set-ers
    public ArrayList<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(ArrayList<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public String getGruppeNavn() {
        return gruppeNavn;
    }

    public void setGruppeNavn(String gruppeNavn) {
        this.gruppeNavn = gruppeNavn;
    }
}
