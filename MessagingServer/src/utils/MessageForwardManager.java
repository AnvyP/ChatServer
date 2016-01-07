package utils;

import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;

import core.UserDetails;
import core.exception.UserDetailsException;
import core.exception.UserDoesNotExistException;
import core.server.Constants;

import utils.Message.MessageType;
import utils.errorMessage.ErrorMessageUsernameDoesnotExist;
import utils.errorMessage.ErrorMessageUsernameExists;

public class MessageForwardManager {
  private static final String LOG_TAG = MessageForwardManager.class.getSimpleName();
  private BlockingQueue<Message> messageQueue = null;


  public MessageForwardManager() {
    // TODO:
  }


  public MessageForwardManager(BlockingQueue<Message> messageQueue) {
    this.messageQueue = messageQueue;
  }


  public void forwardMessage(Message msg, InetSocketAddress clientSocketAddress) {
    MessageType type = msg.getType();
    String name = msg.getUsername();
    UserDetails userDetails = UserDetails.getInstance();
    Log.e(LOG_TAG, "Inside forwardMessage");

    switch (type) {
      case REGISTER:
        userDetails = UserDetails.getInstance();
        try {
          userDetails.addUser(name, clientSocketAddress);
          Message registrationSuccessMsg =
              new Message(Constants.SERVER_USERNAME, "RegistationSuccessful", msg.getUsername(),
                  MessageType.REGISTER_SUCCESS);
          messageQueue.add(registrationSuccessMsg);

        } catch (UserDetailsException e) {
          ErrorMessageUsernameExists errorMsg = new ErrorMessageUsernameExists(name);

        }
        break;
      case LOGIN:

        break;

      case LOGOUT:

        break;

      case MSG:
        // TODO: check if the user is registered.
        InetSocketAddress address = null;
        try {
          address = userDetails.getUserAddress(msg.getToUser());
          messageQueue.add(msg);
        } catch (UserDoesNotExistException e) {
          Log.e(LOG_TAG, "Type: MSG, " + e.getMessage());
          ErrorMessageUsernameDoesnotExist userDoesnotExistMsg =
              new ErrorMessageUsernameDoesnotExist(msg.getUsername()); 

          messageQueue.add(userDoesnotExistMsg);
        }
        break;

      default:
        break;
    }
  }

}
