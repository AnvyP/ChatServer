package utils;

import com.google.gson.Gson;


public class MessageParser {
  Gson gson = null;

  public MessageParser() {
    gson = new Gson();
  }

  public Message toMsg(String jsonString) {
    return gson.fromJson(jsonString, Message.class);
  }

  public String toJson(Message msg) {
    return gson.toJson(msg);
  }
}
