package utils.customExceptions;

public class LogMessage extends RuntimeException {
    public LogMessage(String message) {
      System.out.println(message);
    }
}
