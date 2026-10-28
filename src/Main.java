public class Main {

    public static void main(String[] args) {
        System.out.println("===== Leave Management System =====\n");

        Employee[] staff = {
            new FullTimeEmployee(101, "Meera", "Engineering"),
            new PartTimeEmployee(102, "Arjun", "Design"),
            new Intern(103, "Divya", "Engineering"),
            new Manager(201, "Suresh", "Engineering")
        };
        Manager manager = (Manager) staff[3];

        System.out.println("--- Employee details (polymorphism) ---");
        for (Employee e : staff) {
            e.displayDetails();
        }

        System.out.println("\n--- Scenario 1: valid request approved ---");
        try {
            LeaveRequest r1 = staff[0].applyLeave(5, "Family function");
            System.out.println(r1);
            manager.approveLeave(r1);
            System.out.println(r1);
            System.out.println("Meera's balance: " + staff[0].checkLeaveBalance());
        } catch (InvalidLeaveException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        System.out.println("\n--- Scenario 2: request rejected by manager ---");
        try {
            LeaveRequest r2 = staff[2].applyLeave(2, "Exam preparation");
            manager.rejectLeave(r2);
            System.out.println(r2);
            System.out.println("Divya's balance: " + staff[2].checkLeaveBalance());
        } catch (InvalidLeaveException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        System.out.println("\n--- Scenario 3: approved leave cancelled (days restored) ---");
        try {
            LeaveRequest r3 = staff[1].applyLeave(4);          // overloaded method
            manager.approveLeave(r3);
            System.out.println("After approval, Arjun's balance: " + staff[1].checkLeaveBalance());
            staff[1].cancelLeave(r3);
            System.out.println(r3);
            System.out.println("After cancellation, Arjun's balance: " + staff[1].checkLeaveBalance());
        } catch (InvalidLeaveException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        System.out.println("\n--- Scenario 4: invalid requests ---");
        tryApply(staff[2], 3);     // intern limit is 2
        tryApply(staff[1], 6);     // part-time limit is 5
        tryApply(staff[0], 0);     // zero days
        tryApply(staff[0], -2);    // negative days

        System.out.println("\n--- Scenario 5: manager rules ---");
        try {
            LeaveRequest own = manager.applyLeave(12, "Vacation");
            manager.approveLeave(own);
        } catch (InvalidLeaveException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        tryApply(manager, 15);     // allowed for managers

        System.out.println("\n--- Scenario 6: processing a request twice ---");
        try {
            LeaveRequest r4 = staff[0].applyLeave(2, "Medical appointment");
            manager.approveLeave(r4);
            manager.approveLeave(r4);
        } catch (InvalidLeaveException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        System.out.println("\n--- Final balances ---");
        for (Employee e : staff) {
            e.displayDetails();
        }
    }

    private static void tryApply(Employee e, int days) {
        try {
            LeaveRequest r = e.applyLeave(days, "Test");
            System.out.println("Accepted: " + r);
        } catch (InvalidLeaveException ex) {
            System.out.println(e.getName() + " (" + e.getEmployeeType() + ", " + days + " days) -> " + ex.getMessage());
        }
    }
}
