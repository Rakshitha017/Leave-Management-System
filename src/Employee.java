/**
 * Abstract base class for every employee type.
 * Common data and behaviour live here; each subclass supplies its own leave rules.
 */
public abstract class Employee {
    private final int employeeId;
    private final String name;
    private final String department;
    private int leaveBalance;

    protected Employee(int employeeId, String name, String department, int annualLeave) {
        if (employeeId <= 0) {
            throw new IllegalArgumentException("Employee ID must be positive.");
        }
        if (name == null || name.isBlank() || department == null || department.isBlank()) {
            throw new IllegalArgumentException("Name and department are required.");
        }
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.leaveBalance = annualLeave;
    }

    // ---- abstraction: each employee type defines its own rules ----
    public abstract String getEmployeeType();
    public abstract int getMaxDaysPerRequest();

    public int getEmployeeId()   { return employeeId; }
    public String getName()      { return name; }
    public String getDepartment(){ return department; }

    public int checkLeaveBalance() {
        return leaveBalance;
    }

    // ---- method overloading: same name, different parameters ----
    public LeaveRequest applyLeave(int days) throws InvalidLeaveException {
        return applyLeave(days, "Not specified");
    }

    public LeaveRequest applyLeave(int days, String reason) throws InvalidLeaveException {
        validateRequest(days);
        return new LeaveRequest(this, days, reason);
    }

    /** One shared validation routine (refactored out of the subclasses). */
    protected void validateRequest(int days) throws InvalidLeaveException {
        if (days <= 0) {
            throw new InvalidLeaveException("Leave days must be greater than zero.");
        }
        if (days > getMaxDaysPerRequest()) {
            throw new InvalidLeaveException(getEmployeeType() + " can request at most "
                    + getMaxDaysPerRequest() + " day(s) at a time.");
        }
        if (days > leaveBalance) {
            throw new InvalidLeaveException("Insufficient leave balance. Available: " + leaveBalance
                    + ", requested: " + days + ".");
        }
    }

    public void cancelLeave(LeaveRequest request) throws InvalidLeaveException {
        if (request.getEmployee() != this) {
            throw new InvalidLeaveException("You can only cancel your own leave requests.");
        }
        boolean wasApproved = request.getStatus() == LeaveStatus.APPROVED;
        request.cancel();
        if (wasApproved) {
            leaveBalance += request.getDays();   // give the days back
        }
    }

    /** Package-private: only the approval workflow may change the balance. */
    void deductLeave(int days) {
        leaveBalance -= days;
    }

    // ---- method overriding: subclasses may extend this ----
    public void displayDetails() {
        System.out.printf("ID: %d | Name: %s | Dept: %s | Type: %s | Balance: %d day(s)%n",
                employeeId, name, department, getEmployeeType(), leaveBalance);
    }
}
