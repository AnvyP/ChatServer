package core;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import core.exception.UserDetailsException;

public class UserDetails {
  private ConcurrentHashMap<String, InetSocketAddress> userIPMap = null;

  private UserDetails() {
    userIPMap = new ConcurrentHashMap<String, InetSocketAddress>();
  }

  static UserDetails sInstance = null;

  public static synchronized UserDetails getInstance() {
    if (sInstance == null) {
      sInstance = new UserDetails();
    }
    return sInstance;
  }

  public synchronized void addUser(String name, InetSocketAddress IP) throws UserDetailsException {
    if (!userIPMap.contains(name)) {
      userIPMap.put(name, IP);
    } else {
      throw new UserDetailsException(UserDetailsException.REASON_USER_ALREADY_EXISTS);
    }
  }

  public void overRideUserDetails(String name, InetSocketAddress IP) {
    userIPMap.put(name, IP);
  }

  public boolean doesUserExist(String Name) {
    return userIPMap.get(Name) == null ? false : true;
  }

}
