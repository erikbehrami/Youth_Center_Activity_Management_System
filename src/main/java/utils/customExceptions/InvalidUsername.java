package utils.customExceptions;

public class InvalidUsername extends RuntimeException {
    public InvalidUsername(String message) {
        super(message);
    }
}
