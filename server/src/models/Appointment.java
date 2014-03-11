package models;

import java.util.ArrayList;
import java.util.Date;


public class Appointment implements DBinterface{
    private int appointmentID;
    private Employee appointmentManager;
    private Date startTime;
    private Date stopTime;
    private String description;
    private String place;
    private MeetingRoom meetingRoom;
    private ArrayList<Employee> employeeList;

    //Constructor med beskriverlse og place
    public Appointment(int appointmentID, Employee appointmentManager, Date startTime, Date stopTime, String description, String place, ArrayList<Employee> employeeList){
        this.appointmentManager = appointmentManager;
        this.appointmentID = appointmentID;
        this.description = description;
        this.employeeList = employeeList;
        this.place = place;
        this.startTime = startTime;
        this.stopTime = stopTime;

    }
    //Constructor med description og møterom
    public Appointment(int appointmentID, Employee appointmentManager, Date startTime, Date stopTime, String description, MeetingRoom meetingRoom, ArrayList<Employee> employeeList){
        this.appointmentManager = appointmentManager;
        this.appointmentID = appointmentID;
        this.description = description;
        this.employeeList = employeeList;
        this.meetingRoom = meetingRoom;
        this.startTime = startTime;
        this.stopTime = stopTime;
    }
    //Constructor uten description og med place.
    public Appointment(int appointmentID, Employee appointmentManager, Date startTime, Date stopTime, ArrayList<Employee> employeeList, String place) {
        this.appointmentID = appointmentID;
        this.appointmentManager = appointmentManager;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.employeeList = employeeList;
        this.place = place;
    }

    //Constructor uten description og med møterom
    public Appointment(int appointmentID, Employee appointmentManager, Date startTime, Date stopTime, ArrayList<Employee> participantsList, MeetingRoom meetingRoom) {
        this.appointmentID = appointmentID;
        this.appointmentManager = appointmentManager;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.employeeList = participantsList;
        this.meetingRoom = meetingRoom;
    }

    public void delete(){}
    public void initialize(){}

    @Override
    public void save() {

   }

    @Override
    public void refresh() {

    }

    public int getAppointmentID() {
        return appointmentID;
    }
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public Employee getAppointmentManager() {
        return appointmentManager;
    }

    public void setAppointmentManager(Employee appointmentManager) {
        this.appointmentManager = this.appointmentManager;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTid) {
        this.startTime = startTid;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public MeetingRoom getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(MeetingRoom meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public ArrayList<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(ArrayList<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
