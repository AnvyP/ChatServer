package utils.errorMessage;

import utils.Message;
import core.server.Constants;

public class ErrorMessageUsernameDoesnotExist extends Message {

  public static final String ERROR_MESSAGE = "Username Doesn't exist!!";

  public ErrorMessageUsernameDoesnotExist(String username, String message, String toUser,
      MessageType type) {
    super(username, message, toUser, type);
  }

  public ErrorMessageUsernameDoesnotExist(String toUser) {
    super(Constants.SERVER_USERNAME, ERROR_MESSAGE, toUser, MessageType.MSG_SENDING_FAILED);
  }

}
