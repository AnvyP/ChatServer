package utils.errorMessage;

import utils.Message;
import core.server.Constants;

public class ErrorMessageUsernameExists extends Message {

  public static final String ERROR_MESSAGE = "Username already exists!!";

  public ErrorMessageUsernameExists(String username, String message, String toUser, MessageType type) {
    super(username, message, toUser, type);
  }

  public ErrorMessageUsernameExists(String toUser) {
    super(Constants.SERVER_USERNAME, ERROR_MESSAGE, toUser, MessageType.REGISTER_FAIL);
  }

}
