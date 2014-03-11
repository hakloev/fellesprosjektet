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
    private ArrayList<String> emailList;

    //Constructor med beskriverlse og place
    public Appointment(Employee appointmentManager, Date startTime, Date stopTime, String description, String place, ArrayList<Employee> employeeList){
        this.appointmentManager = appointmentManager;
        this.description = description;
        this.employeeList = employeeList;
        this.place = place;
        this.startTime = startTime;
        this.stopTime = stopTime;
        emailList = new ArrayList<String>();

    }
    //Constructor med description og møterom
    public Appointment(Employee appointmentManager, Date startTime, Date stopTime, String description, MeetingRoom meetingRoom, ArrayList<Employee> employeeList){
        this.appointmentManager = appointmentManager;
        this.description = description;
        this.employeeList = employeeList;
        this.meetingRoom = meetingRoom;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.employeeList = employeeList;
        emailList = new ArrayList<String>();
    }
    //Constructor uten description og med place.
    public Appointment(Employee appointmentManager, Date startTime, Date stopTime, ArrayList<Employee> employeeList, String place) {
        this.appointmentManager = appointmentManager;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.employeeList = employeeList;
        this.place = place;
        emailList = new ArrayList<String>();
    }

    //Constructor uten description, med meetingroom
    public Appointment(Employee appointmentManager, Date startTime, Date stopTime, ArrayList<Employee> employeeList, MeetingRoom meetingRoom) {
        this.appointmentManager = appointmentManager;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.employeeList = employeeList;
        this.meetingRoom = meetingRoom;
        emailList = new ArrayList<String>();
    }
    //Constructor med kun ID
    public Appointment(int appointmentID){
    	this.appointmentID = appointmentID;
    	employeeList = new ArrayList<Employee>();
        emailList = new ArrayList<String>();
    	
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
        this.appointmentManager = appointmentManager;
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

    public ArrayList<String> getEmailList() {
        return emailList;
    }
    public void addParticipant(Employee employee){
    	if (employeeList.contains(employee)){
    		return;
    	}
    	employeeList.add(employee);
    }
    public void addEmailParticipant(String email){
    	if (emailList.contains(email)){
    		return;
    	}
    	emailList.add(email);
    }
    public void removeParticipant(Employee employee){
    	employeeList.remove(employee);
    }
    public void removeEmailParticipant(String email){
    	emailList.remove(email);
    }
}
