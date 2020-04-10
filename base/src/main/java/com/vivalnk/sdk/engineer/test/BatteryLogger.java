package com.vivalnk.sdk.engineer.test;

import android.os.Message;
import com.vivalnk.sdk.Callback;
import com.vivalnk.sdk.CommandRequest;
import com.vivalnk.sdk.VitalClient;
import com.vivalnk.sdk.command.base.CommandType;
import com.vivalnk.sdk.common.utils.FileUtils;
import com.vivalnk.sdk.model.Device;
import com.vivalnk.sdk.model.PatchStatusInfo;
import com.vivalnk.sdk.utils.DateFormat;
import java.util.Date;
import java.util.Map;
import org.apache.commons.lang3.time.DateFormatUtils;

public class BatteryLogger extends AbsLogger {

  private volatile boolean started;

  public BatteryLogger(Device device) {
    super(device);
  }

  Runnable runnable = new Runnable() {
    @Override
    public void run() {
      requestStatus();
      mLogHandler.postDelayed(this, 10 * 1000);
    }
  };

  CommandRequest checkPatchStatusReq = new CommandRequest.Builder()
      .setType(CommandType.checkPatchStatus)
      .setLoggable(false)
      .build();

  private void requestStatus() {

    VitalClient.getInstance().execute(mDevice, checkPatchStatusReq, new Callback() {
      @Override
      public void onStart() {

      }

      @Override
      public void onComplete(Map<String, Object> map) {
        if (map != null && map.get("data") != null && (map
            .get("data") instanceof PatchStatusInfo)) {
          PatchStatusInfo info = (PatchStatusInfo) map.get("data");
          readRemoteRSSI(info);
        }
      }

      @Override
      public void onError(int i, String s) {

      }
    });

  }

  private void readRemoteRSSI(final PatchStatusInfo info) {

    VitalClient.getInstance().readRemoteRssi(mDevice, new Callback() {
      @Override
      public void onStart() {

      }

      @Override
      public void onComplete(Map<String, Object> map) {
        if (null != map && map.get("rssi") != null && (map.get("rssi") instanceof Integer)) {

          final Integer rssi = (Integer) map.get("rssi");

          final String date = DateFormatUtils.format(new Date(), DateFormat.sPattern);

          printLog(mDevice, rssi, date, info);

        }
      }

      @Override
      public void onError(int i, String s) {

      }
    }, false);
  }

  private void printLog(Device mConnectedDevice, Integer rssi, String date, PatchStatusInfo info) {

    String log = "date=" + date
        + ", mac=" + mConnectedDevice.getId()
        + ", name=" + mConnectedDevice.getName()
        + ", rssi=" + rssi
        + ", " + info.toString()
        + "\n";
    FileUtils.writeFile(FileManager.getBatteryFilePath(mDevice.getSn()), log, true);
  }

  @Override
  public void start() {
    if (started) {
      return;
    }
    started = true;
    mLogHandler.sendMessageDelayed(Message.obtain(mLogHandler, runnable), 1000);
  }

  @Override
  public void stop() {
    if (!started) {
      return;
    }
    mLogHandler.removeCallbacksAndMessages(null);
    started = false;
  }

  @Override
  public boolean isStarted() {
    return started;
  }
}
