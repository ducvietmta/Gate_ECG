package com.vivalnk.sdk.app.repository.device;

import com.vivalnk.sdk.model.Device;
import java.util.Map;

/**
 * Created by JakeMo on 18-4-30.
 */
public class ConnectEvent {
  public static final String ON_START = "ON_START";
  public static final String ON_CONNECTED = "ON_CONNECTED";
  public static final String ON_SERVICE_READY = "ON_SERVICE_READY";
  public static final String ON_ENABLE_NOTIFY = "ON_ENABLE_NOTIFY";
  public static final String ON_DEVICE_READY = "ON_DEVICE_READY";
  public static final String ON_DISCONNECTED = "ON_DISCONNECTED";
  public static final String ON_ERROR = "ON_ERROR";

  //just one device
  public static String connectStatus = ON_DISCONNECTED;

  public String event;
  public Device device;
  Map<String, Object> ret;
  public int code;
  public String msg;
  public boolean isForce;

  public long time;

  public ConnectEvent() {
    time = System.currentTimeMillis();
  }

  public static ConnectEvent onStart(Device device) {
    ConnectEvent
        event = new ConnectEvent();
    event.device = device;
    event.event = ON_START;
    return event;
  }

  public static ConnectEvent onConnected(Device device) {
    ConnectEvent
        event = new ConnectEvent();
    event.device = device;
    event.event = ON_CONNECTED;
    connectStatus = ON_CONNECTED;
    return event;
  }
  public static ConnectEvent onServiceReady(Device device) {
    ConnectEvent
        event = new ConnectEvent();
    event.device = device;
    event.event = ON_SERVICE_READY;
    connectStatus = ON_CONNECTED;
    return event;
  }

  public static ConnectEvent onEnableNotify(Device device) {
    ConnectEvent
        event = new ConnectEvent();
    event.device = device;
    event.event = ON_ENABLE_NOTIFY;
    return event;
  }

  public static ConnectEvent onDeviceReady(Device device) {
    ConnectEvent
        event = new ConnectEvent();
    event.device = device;
    event.event = ON_DEVICE_READY;
    return event;
  }

  public static ConnectEvent onDisconnected(Device device, boolean isForce) {
    ConnectEvent
        event = new ConnectEvent();
    event.device = device;
    event.isForce = isForce;
    event.event = ON_DISCONNECTED;
    connectStatus = ON_DISCONNECTED;
    return event;
  }
  public static ConnectEvent onError(Device device, int code, String msg) {
    ConnectEvent
        event = new ConnectEvent();
    event.device = device;
    event.code = code;
    event.msg = msg;
    event.event = ON_ERROR;
    return event;
  }

}
