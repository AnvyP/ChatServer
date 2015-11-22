package utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import core.Sockets;


public class ExecutorServiceSocketAcceptors {
  private int poolSize = 0;

  private static final String LOG_TAG = ExecutorServiceSocketAcceptors.class.getSimpleName();
  private ScheduledExecutorService mainThreadPool = null;
  private Sockets sockets = null;

  public ExecutorServiceSocketAcceptors(int poolSize, Sockets sockets) {
    this.poolSize = poolSize;
    this.sockets = sockets;
  }

  public void init() {
    mainThreadPool = Executors.newScheduledThreadPool(poolSize);
    startListening();
  }

  class MessageHandler implements Runnable {
    ServerSocket socket = null;
    MessageParser messageParser = null;
    MessageForwardManager mgr = null;

    public MessageHandler(ServerSocket socket) {
      this.socket = socket;
      messageParser = new MessageParser();
      mgr = new MessageForwardManager();
    }

    @Override
    public void run() {
      Socket clientSocket = null;

      while (true) {
        try {
          clientSocket = socket.accept();
          DataInputStream in = new DataInputStream(clientSocket.getInputStream());
          Message msg = messageParser.toMsg(in.readUTF());
          InetAddress clientAddress = clientSocket.getInetAddress();
          InetSocketAddress clientSocketAddress = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
          mgr.forwardMessage(msg,clientSocketAddress);
        } catch (IOException e) {
          e.printStackTrace();
        }

      }
    }

  }

  private void startListening() {
    for (int i = 0; i < poolSize; i++) {
      // mainThreadPool.execute(command);
    }
  }

  public void deinit() {

  }
}
