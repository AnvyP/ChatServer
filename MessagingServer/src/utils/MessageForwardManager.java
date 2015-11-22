package utils;

import java.net.InetSocketAddress;

import core.UserDetails;
import core.exception.UserDetailsException;

import utils.Message.MessageType;
import utils.errorMessage.ErrorMessageUsernameExists;

public class MessageForwardManager {
  public MessageForwardManager() {
    // TODO:
  }

  void forwardMessage(Message msg, InetSocketAddress clientSocketAddress) {
    MessageType type = msg.getType();
    String name = msg.getUsername();
    switch (type) {
      case REGISTER:
        UserDetails details = UserDetails.getInstance();
        try {
          details.addUser(name, clientSocketAddress);
        } catch (UserDetailsException e) {
          ErrorMessageUsernameExists errorMsg = new ErrorMessageUsernameExists(name);
          
        }
        break;
      case LOGIN:

        break;

      case LOGOUT:

        break;

      case MSG:

        break;

      default:
        break;
    }
  }

}
