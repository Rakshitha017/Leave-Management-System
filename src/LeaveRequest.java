/** Represents one leave request and controls its status transitions. */
public class LeaveRequest {
    private static int nextId = 1;

    private final int requestId;
    private final Employee employee;
    private final int days;
    private final String reason;
    private LeaveStatus status;

    LeaveRequest(Employee employee, int days, String reason) {
        this.requestId = nextId++;
        this.employee = employee;
        this.days = days;
        this.reason = reason;
        this.status = LeaveStatus.PENDING;
    }

    public int getRequestId()      { return requestId; }
    public Employee getEmployee()  { return employee; }
    public int getDays()           { return days; }
    public String getReason()      { return reason; }
    public LeaveStatus getStatus() { return status; }

    void approve() throws InvalidLeaveException { requirePending("approve"); status = LeaveStatus.APPROVED; }
    void reject()  throws InvalidLeaveException { requirePending("reject");  status = LeaveStatus.REJECTED; }

    void cancel() throws InvalidLeaveException {
        if (status == LeaveStatus.CANCELLED || status == LeaveStatus.REJECTED) {
            throw new InvalidLeaveException("Request #" + requestId + " is already " + status + " and cannot be cancelled.");
        }
        status = LeaveStatus.CANCELLED;
    }

    private void requirePending(String action) throws InvalidLeaveException {
        if (status != LeaveStatus.PENDING) {
            throw new InvalidLeaveException("Cannot " + action + " request #" + requestId + " because it is " + status + ".");
        }
    }

    @Override
    public String toString() {
        return "Request #" + requestId + " [" + employee.getName() + ", " + days + " day(s), "
                + reason + ", " + status + "]";
    }
}
