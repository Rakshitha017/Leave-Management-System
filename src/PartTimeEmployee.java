public class PartTimeEmployee extends Employee {
    private static final int ANNUAL_LEAVE = 12;
    private static final int MAX_DAYS_PER_REQUEST = 5;

    public PartTimeEmployee(int id, String name, String department) {
        super(id, name, department, ANNUAL_LEAVE);
    }

    @Override public String getEmployeeType()   { return "Part-Time"; }
    @Override public int getMaxDaysPerRequest() { return MAX_DAYS_PER_REQUEST; }
}
