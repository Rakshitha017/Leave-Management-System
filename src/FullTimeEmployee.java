public class FullTimeEmployee extends Employee {
    private static final int ANNUAL_LEAVE = 24;
    private static final int MAX_DAYS_PER_REQUEST = 10;

    public FullTimeEmployee(int id, String name, String department) {
        super(id, name, department, ANNUAL_LEAVE);
    }

    @Override public String getEmployeeType()   { return "Full-Time"; }
    @Override public int getMaxDaysPerRequest() { return MAX_DAYS_PER_REQUEST; }
}
