package com.vivalnk.sdk.app.base.i18n;

import com.vivalnk.sdk.command.base.CommandAllType;
import com.vivalnk.sdk.model.Device;
import java.util.Map;

public class ErrorMessageHandler_EN implements IRequestMessageHandler {
  @Override
  public String getRequestTypeName(int type) {
    return CommandAllType.getTypeName(type);
  }

  @Override
  public String getErrorMesage(int code, String msg) {
    return ErrorMessageData.getErrorMessage(ErrorMessageData.Locale.en, code, msg);
  }

  @Override
  public String getOnErrorMesage(int type, int code, String msg) {
    return "request onError: " + getRequestTypeName(type) + ", msg: " + getErrorMesage(code, msg);
  }

  @Override
  public String getOnStartMessage(int type) {
    return "request onStart: " + getRequestTypeName(type);
  }

  @Override
  public String getOnCompleteMessage(int type, Map<String, Object> data) {
    return "request onComplete: " + getRequestTypeName(type) +
        (data == null ? "" : ", data = " + data.toString());
  }

  @Override
  public String getConnectErrorMeesage(Device device, int code, String msg) {
    return "on connect error: code = " + code + ", msg = " + msg + "\ndevice = " + device;
  }

  @Override
  public String getDisconnectedMeesage(Device device, boolean isForce) {
    return "on disconnect: isForce = " + isForce + "\ndevice = " + device;
  }

  @Override
  public String getConnectedMeesage(Device device) {
    return "on connected: " + "\ndevice = " +device;
  }
}
