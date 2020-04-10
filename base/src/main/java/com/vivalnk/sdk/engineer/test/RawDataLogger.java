package com.vivalnk.sdk.engineer.test;

import android.util.Log;

import com.vivalnk.sdk.common.eventbus.Subscribe;
import com.vivalnk.sdk.common.utils.FileUtils;
import com.vivalnk.sdk.common.utils.log.LogLevel;
import com.vivalnk.sdk.model.Device;
import com.vivalnk.sdk.open.RawData;
import com.vivalnk.sdk.utils.DateFormat;
import org.apache.commons.lang3.time.DateFormatUtils;

public class RawDataLogger extends AbsLogger {

  @Subscribe
  public void onDataEvent(final RawData rawData) {
    if (rawData == null) {
      return;
    }

    postIO(new Runnable() {
      @Override
      public void run() {
        Device device = rawData.device;

        StringBuilder sb = new StringBuilder();
        long timeStamp = System.currentTimeMillis();
        String tag = "DATA";
        LogLevel priority = LogLevel.INFO;
        String threadInfo = Thread.currentThread().toString();
        String message = rawData.data.toFileString();
        sb.append("\n")
            .append(DateFormatUtils.format((timeStamp), DateFormat.sPattern))
            .append(" -- ").append("Log[").append(tag).append(",").append(priority.toString()).append("]")
            .append(" -- ").append(threadInfo)
            .append(" :")
            .append(message);

        FileUtils.writeFile(FileManager.getRawDataFilePath(device.getSn()), sb.toString());
      }
    });


  }

}
