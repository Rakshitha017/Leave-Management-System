public class Intern extends Employee {
    private static final int ANNUAL_LEAVE = 6;
    private static final int MAX_DAYS_PER_REQUEST = 2;

    public Intern(int id, String name, String department) {
        super(id, name, department, ANNUAL_LEAVE);
    }

    @Override public String getEmployeeType()   { return "Intern"; }
    @Override public int getMaxDaysPerRequest() { return MAX_DAYS_PER_REQUEST; }
}
