package utils;

public class Message {
  public Message(String username, String message, String toUser, MessageType type) {
    super();
    this.username = username;
    this.message = message;
    this.toUser = toUser;
    this.type = type;
  }

  public Message() {

  }

  private String username;
  private String message;
  private String toUser;
  private MessageType type;

  public enum MessageType {
    REGISTER, LOGIN, LOGOUT, MSG, REGISTER_FAIL
  }

  public String getUsername() {
    return username;
  }

  public String getMessage() {
    return message;
  }

  public String getToUser() {
    return toUser;
  }

  public MessageType getType() {
    return type;
  }
}
