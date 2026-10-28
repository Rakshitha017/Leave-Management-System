/** Change request: a new employee type with extra benefits and approval rights. */
public class Manager extends Employee {
    private static final int ANNUAL_LEAVE = 30;
    private static final int MAX_DAYS_PER_REQUEST = 15;

    public Manager(int id, String name, String department) {
        super(id, name, department, ANNUAL_LEAVE);
    }

    @Override public String getEmployeeType()   { return "Manager"; }
    @Override public int getMaxDaysPerRequest() { return MAX_DAYS_PER_REQUEST; }

    public void approveLeave(LeaveRequest request) throws InvalidLeaveException {
        checkApprovalRights(request);
        Employee applicant = request.getEmployee();
        if (request.getDays() > applicant.checkLeaveBalance()) {
            throw new InvalidLeaveException("Cannot approve: " + applicant.getName()
                    + " only has " + applicant.checkLeaveBalance() + " day(s) left.");
        }
        request.approve();
        applicant.deductLeave(request.getDays());
    }

    public void rejectLeave(LeaveRequest request) throws InvalidLeaveException {
        checkApprovalRights(request);
        request.reject();
    }

    private void checkApprovalRights(LeaveRequest request) throws InvalidLeaveException {
        if (request.getEmployee() == this) {
            throw new InvalidLeaveException("A manager cannot approve or reject their own request.");
        }
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("      Extra: can approve/reject leave requests, max "
                + MAX_DAYS_PER_REQUEST + " days per request");
    }
}
