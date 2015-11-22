package core;

import java.io.IOException;
import java.net.ServerSocket;

public class Sockets {
  private static final int STARTING_PORT = 6000;
  private int num = 0;
  private ServerSocket[] sockets = null;

  public Sockets(int num) {
    this.num = num;
  }

  public void init() throws IOException {
    sockets = new ServerSocket[num];
    for (int i = 0; i < num; i++) {
      sockets[i] = new ServerSocket(STARTING_PORT + i);
    }
  }

}
