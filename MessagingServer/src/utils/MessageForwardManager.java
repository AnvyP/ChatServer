package utils;

import java.net.InetSocketAddress;

import core.UserDetails;
import core.exception.UserDetailsException;
import core.exception.UserDoesNotExistException;

import utils.Message.MessageType;
import utils.errorMessage.ErrorMessageUsernameExists;

public class MessageForwardManager {
  public MessageForwardManager() {
    // TODO:
  }

  void forwardMessage(Message msg, InetSocketAddress clientSocketAddress) {
    MessageType type = msg.getType();
    String name = msg.getUsername();
    UserDetails userDetails = UserDetails.getInstance();

    switch (type) {
      case REGISTER:
        userDetails = UserDetails.getInstance();
        try {
          userDetails.addUser(name, clientSocketAddress);
        } catch (UserDetailsException e) {
          ErrorMessageUsernameExists errorMsg = new ErrorMessageUsernameExists(name);

        }
        break;
      case LOGIN:

        break;

      case LOGOUT:

        break;

      case MSG:
        InetSocketAddress address = null;
        try {
          address = userDetails.getUserAddress(name);
        } catch (UserDoesNotExistException e) {
          //TODO:
          e.printStackTrace();
        }
        break;

      default:
        break;
    }
  }

}
