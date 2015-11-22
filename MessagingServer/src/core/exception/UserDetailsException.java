package core.exception;

public class UserDetailsException extends Exception {

  private static final long serialVersionUID = 2732663621295171808L;

  public UserDetailsException(String msg) {
    super(msg);
  }

  public UserDetailsException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public static final String REASON_USER_ALREADY_EXISTS = "REASON_USER_ALREADY_EXISTS";
}
