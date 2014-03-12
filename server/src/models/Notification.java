package models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Notification implements DBinterface {
    private int NotificationID;
    private boolean isSeen;
    private NotificationType NotificationType;
    private Employee employee;
    private Appointment appointment;

	@JsonCreator
    public Notification(@JsonProperty("NotificationID") int NotificationID, @JsonProperty("isSeen") boolean isSeen,
	                    @JsonProperty("NotificationType") NotificationType NotificationType, @JsonProperty("employee")Employee employee,
	                    @JsonProperty("appointment") Appointment appointment) {
        this.NotificationID = NotificationID;
        this.isSeen = isSeen;
        this.NotificationType = NotificationType;
        this.employee = employee;
        this.appointment = appointment;
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

    public int getWarningID() {
        return NotificationID;
    }

    public void setWarningID(int warningID) {
        this.NotificationID = warningID;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        this.isSeen = seen;
    }

    public NotificationType getWarningType() {
        return NotificationType;
    }

    public void setWarningType(NotificationType WarningType) {
        this.NotificationType = WarningType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
