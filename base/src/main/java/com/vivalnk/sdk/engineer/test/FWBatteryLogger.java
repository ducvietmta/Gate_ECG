package com.vivalnk.sdk.engineer.test;

import com.vivalnk.sdk.Callback;
import com.vivalnk.sdk.VitalClient;
import com.vivalnk.sdk.app.repository.device.DeviceManager;
import com.vivalnk.sdk.common.eventbus.Subscribe;
import com.vivalnk.sdk.common.utils.FileUtils;
import com.vivalnk.sdk.model.BatteryInfo;
import com.vivalnk.sdk.model.Device;
import com.vivalnk.sdk.utils.DateFormat;
import java.util.Date;
import java.util.Map;
import org.apache.commons.lang3.time.DateFormatUtils;

public class FWBatteryLogger extends AbsLogger {

  @Subscribe
  public void onBatteryEvent(DeviceManager.BatteryData data) {
    if (data == null) {
      return;
    }
    Device device = data.device;
    BatteryInfo batteryInfo = data.batteryInfo;
    readRemoteRSSI(device, batteryInfo);
  }

  private void readRemoteRSSI(Device device, final BatteryInfo info) {

    VitalClient.getInstance().readRemoteRssi(device, new Callback() {
      @Override
      public void onStart() {

      }

      @Override
      public void onComplete(final Map<String, Object> map) {
        if (null != map && map.get("rssi") != null && (map.get("rssi") instanceof Integer)) {

          final Integer rssi = (Integer) map.get("rssi");

          final String date = DateFormatUtils.format(new Date(), DateFormat.sPattern);

          postIO(()->{
            printLog(device, rssi, date, info);
          });

        }
      }

      @Override
      public void onError(int i, String s) {

      }
    }, false);
  }

  private void printLog(Device device, Integer rssi, String date, BatteryInfo info) {
    String log = "date=" + date
        + ", mac=" + device.getId()
        + ", name=" + device.getName()
        + ", rssi=" + rssi
        + ", " + info.toString()
        + "\n";
    FileUtils.writeFile(FileManager.getFWBatteryFilePath(device.getSn()), log, true);
  }

}