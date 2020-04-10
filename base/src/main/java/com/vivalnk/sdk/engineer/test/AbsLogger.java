package com.vivalnk.sdk.engineer.test;

import android.os.Handler;
import com.vivalnk.sdk.common.eventbus.EventBus;
import com.vivalnk.sdk.common.excutors.LooperPool;
import com.vivalnk.sdk.common.excutors.LooperType;
import com.vivalnk.sdk.model.Device;

public abstract class AbsLogger {

  protected Device mDevice;
  protected Handler mLogHandler = LooperPool.createHandler(LooperType.IO);

  protected AbsLogger() {
  }

  protected AbsLogger(Device device) {
    this.mDevice = device;
  }

  public void start() {
    register(this);
  }
  public void stop() {
    unregister(this);
  }
  public boolean isStarted() {
    return EventBus.getDefault().isRegistered(this);
  }

  protected void register(Object object) {
    if (EventBus.getDefault().isRegistered(this) == false) {
      EventBus.getDefault().register(this);
    }
  }

  protected void unregister(Object object) {
    if (EventBus.getDefault().isRegistered(this) == true) {
      EventBus.getDefault().unregister(this);
    }
  }

  protected void postIO(Runnable runnable) {
    mLogHandler.post(runnable);
  }

}
