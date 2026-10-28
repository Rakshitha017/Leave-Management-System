/** Thrown when a leave operation breaks a business rule. */
public class InvalidLeaveException extends Exception {
    public InvalidLeaveException(String message) {
        super(message);
    }
}
