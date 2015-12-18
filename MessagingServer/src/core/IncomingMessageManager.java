package core;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import utils.Log;
import utils.Message;
import utils.MessageForwardManager;
import utils.MessageParser;
import core.server.Constants;

public class IncomingMessageManager implements IIncomingMessageManager {

  private final String LOG_TAG = IncomingMessageManager.class.getSimpleName();
  private static final int NO_OF_RECEIVER_SOCKETS = Constants.NO_OF_RECEIVER_SOCKETS;
  private ExecutorService receiverThreadPool = null;
  private BlockingQueue<Message> messageQueue = null;
  private int STARTING_LISTENING_PORT = Constants.STARTING_LISTENING_PORT;

  public IncomingMessageManager(BlockingQueue<Message> messageQueue) {
    this.messageQueue = messageQueue;
    Log.d(LOG_TAG, "Inside constructor IncomingMessageManager");
  }

  @Override
  public void init() {

    Log.d(LOG_TAG, "Inside init() IncomingMessageManager");
    receiverThreadPool = Executors.newFixedThreadPool(NO_OF_RECEIVER_SOCKETS);
    try {
      startListening();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private ServerSocket[] sockets = null;

  private void startListening() throws IOException {

    Log.d(LOG_TAG, "Inside startListening() IncomingMessageManager");
    sockets = new ServerSocket[NO_OF_RECEIVER_SOCKETS];
    for (int i = 0; i < NO_OF_RECEIVER_SOCKETS; i++) {
      sockets[i] = new ServerSocket(STARTING_LISTENING_PORT + i);
      receiverThreadPool.execute(new MessageHandler(sockets[i], messageQueue));
    }

  }

  private void stopListening() {
    for (int i = 0; i < NO_OF_RECEIVER_SOCKETS; i++) {
      if (sockets[i] != null) {
        try {
          sockets[i].close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public void deinit() {
    receiverThreadPool.shutdown();
    stopListening();
  }


  class MessageHandler implements Runnable {
    ServerSocket socket = null;
    MessageParser messageParser = null;
    MessageForwardManager mgr = null;
    private BlockingQueue<Message> messageQueue;

    public MessageHandler(ServerSocket socket2, BlockingQueue<Message> messageQueue2) {
      this.socket = socket2;
      this.messageQueue = messageQueue2;
      messageParser = new MessageParser();
      mgr = new MessageForwardManager(this.messageQueue);
    }

    @Override
    public void run() {
      Socket clientSocket = null;

      while (true) {
        try {

          Log.d(LOG_TAG, "Inside run() MessageHandler, threadId"+Thread.currentThread().getId());
          
          clientSocket = socket.accept();
          DataInputStream in = new DataInputStream(clientSocket.getInputStream());
          Message msg = messageParser.toMsg(in.readUTF());
          InetAddress clientAddress = clientSocket.getInetAddress();
          InetSocketAddress clientSocketAddress =
              (InetSocketAddress) clientSocket.getRemoteSocketAddress();
          mgr.forwardMessage(msg, clientSocketAddress);
        } catch (IOException e) {
          e.printStackTrace();
        }

      }
    }

  }


}
