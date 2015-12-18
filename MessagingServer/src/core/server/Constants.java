package core.server;

public class Constants {

  public static final String SERVER_USERNAME = "APATLOL";

  public static final int NO_OF_RECEIVER_SOCKETS = 4; // Number of sockets on which we would be
                                                      // listening for messages.
  public static final int NO_OF_SENDER_SOCKETS = 4;
  public static final int MESSAGE_QUEUE_SIZE = 10000;
  
  public static final int STARTING_LISTENING_PORT = 6000;

  public static final int STARTING_SENDING_PORT = 10000;
}
