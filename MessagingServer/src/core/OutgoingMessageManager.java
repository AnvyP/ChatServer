package core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import utils.Log;
import utils.Message;
import utils.MessageParser;
import core.server.Constants;

public class OutgoingMessageManager implements IOutgoingMessageManager {

  private final Object QUEUE_FETCH_LOCK = new Object();
  private final int NO_OF_SENDER_SOCKETS = Constants.NO_OF_SENDER_SOCKETS;
  private final int STARTING_SENDING_PORT = Constants.STARTING_SENDING_PORT;

  private final String LOG_TAG = OutgoingMessageManager.class.getSimpleName();
  private BlockingQueue<Message> messageQueue;

  private ExecutorService senderThreadPool = null;

  public OutgoingMessageManager(BlockingQueue<Message> messageQueue) {
    Log.d(LOG_TAG, "Inside OutgoingMessageManager(BlockingQueue<Message> messageQueue)");
    this.messageQueue = messageQueue;
  }

  @Override
  public void init() {
    Log.d(LOG_TAG, "Inside init()");
    senderThreadPool = Executors.newFixedThreadPool(NO_OF_SENDER_SOCKETS);
    startSending();
  }

  private void startSending() {
    MessageSender[] sender = new MessageSender[NO_OF_SENDER_SOCKETS];
    Log.d(LOG_TAG, "Inside startSending()");

    for (int i = 0; i < NO_OF_SENDER_SOCKETS; i++) {
      sender[i] = new MessageSender(STARTING_SENDING_PORT, messageQueue);
      senderThreadPool.execute(sender[i]);
    }
  }

  class MessageSender implements Runnable {
    private int port;
    private BlockingQueue<Message> messageQueue;

    MessageSender(int port2, BlockingQueue<Message> messageQueue2) {
      this.port = port2;
      this.messageQueue = messageQueue2;
    }

    @Override
    public void run() {
      Message msg = null;

      while (true) {

        Log.e(LOG_TAG, "in run()");
        try {
          synchronized (QUEUE_FETCH_LOCK) {
            msg = messageQueue.take(); 
          }
        } catch (InterruptedException e) {
          Log.e(LOG_TAG, "Error in run()");
        }
        sendMessage(msg.getToUser(), port, msg);
      }
    }

    void sendMessage(String clientName, int port, Message msg) {
      try {
        Log.d(LOG_TAG, ("Connecting to " + clientName + " on port " + port));

        Socket server = new Socket(clientName, port);
        Log.d(LOG_TAG, "Just connected to " + server.getRemoteSocketAddress());

        OutputStream outToClient = server.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToClient);

        MessageParser parser = new MessageParser();
        out.writeUTF(parser.toJson(msg));
        InputStream inFromServer = server.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);
        System.out.println("Server says " + parser.toMsg(in.readUTF()));
        server.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void deinit() {
    Log.d(LOG_TAG, "Inside deinit()");
  }



}
