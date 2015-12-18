package core.server;

import core.MessageManager;

public class Startup {
  public static void main(String[] args) {
    MessageManager mgr = new MessageManager();
    mgr.init();
  }
}
