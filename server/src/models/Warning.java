package models;


public class Warning implements DBinterface {
    private int WarningID;
    private boolean isSeen;
    private WarningType warningType;
    private Employee employee;
    private Appointment appointment;

    public Warning(int WarningID, boolean isSeen, WarningType WarningType, Employee employee, Appointment appointment) {
        this.WarningID = WarningID;
        this.isSeen = isSeen;
        this.warningType = WarningType;
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
        return WarningID;
    }

    public void setWarningID(int warningID) {
        this.WarningID = warningID;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        this.isSeen = seen;
    }

    public WarningType getWarningType() {
        return warningType;
    }

    public void setWarningType(WarningType WarningType) {
        this.warningType = WarningType;
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
