package com.vivalnk.sdk.app.base.i18n;

import com.vivalnk.sdk.model.Device;
import java.util.Map;

public interface IRequestMessageHandler {
  String getRequestTypeName(int type);
  String getErrorMesage(int type, String msg);
  String getOnErrorMesage(int type, int code, String msg);
  String getOnStartMessage(int type);
  String getOnCompleteMessage(int type, Map<String, Object> data);

  String getConnectErrorMeesage(Device device, int code, String msg);
  String getDisconnectedMeesage(Device device, boolean isForce);
  String getConnectedMeesage(Device device);
}
