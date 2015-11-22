package core.exception;

public class UserDoesNotExistException extends Exception {

  private static final long serialVersionUID = -782944378508543629L;

  public UserDoesNotExistException(String msg) {
    super(msg);
  }

  public UserDoesNotExistException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public static final String REASON_USER_DOES_NOT_EXISTS = "REASON_USER_DOES_NOT_EXISTS";

}
