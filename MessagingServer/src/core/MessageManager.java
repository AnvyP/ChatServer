package core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import core.server.Constants;

import utils.Message;

public class MessageManager implements IMessageManger {

  private final String LOG_TAG = MessageManager.class.getSimpleName();
  IncomingMessageManager messageReceiver = null;
  IOutgoingMessageManager messageSender = null;
  //Creates sockets
  BlockingQueue<Message> messageQueue = null;
  private static final int MESSAGE_QUEUE_SIZE = Constants.MESSAGE_QUEUE_SIZE;
  @Override
  public void init() {
    messageQueue = new ArrayBlockingQueue<Message>(MESSAGE_QUEUE_SIZE);
    messageReceiver = new IncomingMessageManager(messageQueue);
    messageReceiver.init();
    messageSender = new OutgoingMessageManager(messageQueue);
    messageSender.init();
  }

  @Override
  public void deinit() {
    // TODO Auto-generated method stub
    
  }

}
