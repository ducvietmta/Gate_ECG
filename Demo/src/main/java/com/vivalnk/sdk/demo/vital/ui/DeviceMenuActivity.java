package com.vivalnk.sdk.demo.vital.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.vivalnk.sdk.Callback;
import com.vivalnk.sdk.CommandRequest;
import com.vivalnk.sdk.app.base.app.ConnectedActivity;
import com.vivalnk.sdk.app.base.app.Layout;
import com.vivalnk.sdk.app.base.i18n.ErrorMessageHandler;
import com.vivalnk.sdk.app.base.utils.NotificationUtils;
import com.vivalnk.sdk.app.base.widget.LogListDialogView;
import com.vivalnk.sdk.app.repository.device.DeviceETEManager;
import com.vivalnk.sdk.app.repository.device.DeviceManager;
import com.vivalnk.sdk.command.base.CommandType;
import com.vivalnk.sdk.common.eventbus.Subscribe;
import com.vivalnk.sdk.demo.vital.R;
import com.vivalnk.sdk.model.BatteryInfo;
import com.vivalnk.sdk.model.PatchStatusInfo;
import com.vivalnk.sdk.model.SampleData;
import com.vivalnk.sdk.repository.local.database.VitalData;
import com.vivalnk.sdk.vital.ete.ETEResult;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 设备菜单界面
 *
 * @author jake
 * @date 2019/3/15
 */
public class DeviceMenuActivity extends ConnectedActivity {

  @BindView(R.id.btnDetail)
  Button mBtnDetail;
  @BindView(R.id.tvStatus)
  TextView mTvStatus;
  //vv310
  @BindView(R.id.btnUploadFlash)
  Button btnUploadFlash;
  @BindView(R.id.btnCancelUpload)
  Button btnCancelUpload;
  @BindView(R.id.btnEngineerModule)
  Button btnEngineerModule;

  private LogListDialogView mDataLogView;
  private LogListDialogView mOperationLogView;

  private NotificationUtils mNotificationUtils;

  String NRF_CONNECT_CLASS = "com.vivalnk.sdk.engineer.ui.EngineerAcitivity";
  String clientId = MqttClient.generateClientId();
  MqttAndroidClient client;
  MqttConnectOptions options;
  String macAddr;
  @Subscribe
  public void onDataUpdate(SampleData data) {
    if (!data.getDeviceID().equals(mDevice.getId())) {
      return;
    }
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        mDataLogView.updateLog(data.toSimpleString());
        long currentTimeM = System.currentTimeMillis();
        int[] ecg = data.getECG();
        String deviceId = data.getDeviceID();
        int heartRate = data.getHR();
        float f=data.getRR();
        String restRate = String.valueOf(f);

        StringBuilder ecgData = new StringBuilder("[");
        for(int i =0; i<data.getECG().length; i++) {
          if( i == (data.getECG().length - 1))ecgData.append(ecg[i]);
          else{
            ecgData.append(ecg[i]);
            ecgData.append(",");
          }
        }
        ecgData.append("]");
        Log.e("TAG",ecgData.toString());
        publish(String.format(" {\"timestamp\":%d, \"id\":\"%s\",\"HR\": %d,\"RR\": %s,\"ECG\":%s}",currentTimeM,deviceId,heartRate,restRate, ecgData.toString()));
      }
    });
  }

  @Subscribe
  public void onEteResult(DeviceETEManager.DeviceETEResult deviceETEResult) {
    if (!deviceETEResult.device.equals(mDevice)) {
      return;
    }
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        mDataLogView.updateLog("flash = " + deviceETEResult.flash + "\n"
            + deviceETEResult.result.toString());
      }
    });
  }

  @Subscribe
  public void onBatteryEvent(DeviceManager.BatteryData batteryData) {
    if (batteryData.device.equals(mDevice)) {
      if (batteryData.batteryInfo.needWarming()) {
        mNotificationUtils.sendNotification(mDevice.getName(), getString(R.string.low_battery_warning));
      }
      mTvStatus.setText(batteryData.batteryInfo.getNotifyStr());
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mNotificationUtils = new NotificationUtils(this.getApplicationContext());
    initView();
    initMQTT();
    macAddr = getMacAddr();
  }

  private void initView() {
    if (mDevice.isVV310()) {
      btnUploadFlash.setVisibility(View.VISIBLE);
      btnCancelUpload.setVisibility(View.VISIBLE);
    } else {
      btnUploadFlash.setVisibility(View.GONE);
      btnCancelUpload.setVisibility(View.GONE);
    }

    mDataLogView = new LogListDialogView();
    mOperationLogView = new LogListDialogView();

    mDataLogView.create(this);
    mOperationLogView.create(this);

    initEngineerModule();
  }

  private void initEngineerModule() {
    try {
      Class.forName(NRF_CONNECT_CLASS);
      btnEngineerModule.setVisibility(View.VISIBLE);
    } catch (ClassNotFoundException e) {
      btnEngineerModule.setVisibility(View.GONE);
    }
  }

  @Override
  protected Layout getLayout() {
    return Layout.createLayoutByID(R.layout.activity_device_detail);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mNotificationUtils = null;
  }

  @OnClick(R.id.btnDetail)
  void clickBtnDetail() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(R.string.title_todo)
        .setItems(R.array.log_details, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
            //DataLog
            if (which == 0) {
              mDataLogView.show();
              //Operation Log
            } else if (which == 1) {
              mOperationLogView.show();
            }
          }
        });
    AlertDialog dialog = builder.create();
    dialog.show();
  }

  @OnClick(R.id.btnDisconnect)
  void clickBtnDisconnect() {
    showProgressDialog("Disconnecting...");
    DeviceManager.getInstance().disconnect(mDevice);
  }

  @OnClick(R.id.btnReadPatchVersion)
  public void clickReadPatchVersion(Button view) {
    execute(CommandType.readPatchVersion, new Callback() {
      @Override
      public void onComplete(Map<String, Object> data) {
        String hwVersion = (String) data.get("hwVersion");
        String fwVersion = (String) data.get("fwVersion");
        showToast(getString(R.string.device_read_patch_version, hwVersion, fwVersion));
      }
    });
  }

  @OnClick(R.id.btnReadDeviceInfo)
  public void clickReadDeviceInfo(Button view) {
    execute(CommandType.readDeviceInfo, new Callback() {
      @Override
      public void onComplete(Map<String, Object> data) {
        String magnification = (String) data.get("magnification");
        String samplingFrequency = (String) data.get("ecgSamplingFrequency");
        String model = (String) data.get("model");
        String encryption = (String) data.get("encryption");
        String manufacturer = (String) data.get("manufacturer");
        String info = (String) data.get("info");
        String TroyHR = (String) data.get("hasHR");
        showToast(
            getString(R.string.device_read_device_info, magnification, samplingFrequency, model,
                encryption, manufacturer, info));
      }
    });
  }

  @OnClick(R.id.btnReadSn)
  public void clickReadSN(Button view) {
    execute(CommandType.readSnFromPatch, new Callback() {
      @Override
      public void onComplete(Map<String, Object> data) {
        String sn = (String) data.get("sn");
        showToast(sn);
      }
    });
  }

  @OnClick(R.id.btnQueryFlash)
  public void clickQueryFlashCount(Button view) {
    execute(CommandType.checkFlashDataStatus, new Callback() {
      @Override
      public void onComplete(Map<String, Object> data) {
        long number = (long) data.get("number"); //bytes
        if (data.containsKey("totalNumber") && data.containsKey("seconds")) {
          long totalNumber = (long) data.get("totalNumber"); //bytes
          //unit seconds
          long seconds = (long) data.get("seconds");
          showToast(getString(R.string.flash_info_new, String.valueOf(totalNumber), String.valueOf(number), String.valueOf(seconds)));
        } else {
          showToast(getString(R.string.flash_info_old, String.valueOf(number)));
          showToast(String.valueOf(number));
        }
      }
    });
  }

  @OnClick(R.id.btnCheckPatchStatus)
  public void clickCheckPatchStatus(Button view) {
    execute(CommandType.checkPatchStatus, new Callback() {
      @Override
      public void onComplete(Map<String, Object> data) {
        PatchStatusInfo patchStatusInfo = (PatchStatusInfo) data.get("data");
        try {
          InfoDialog.newInstance(patchStatusInfo).show(getSupportFragmentManager(), InfoDialog.TAG);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  @OnClick(R.id.btnUploadFlash)
  public void clickUploadFlash(Button view) {
    CommandRequest uploadFlashRequest = getCommandRequest(CommandType.uploadFlash, 10 * 1000);
    execute(uploadFlashRequest);
  }

  @OnClick(R.id.btnCancelUpload)
  public void clickCancelUpload(Button view) {
    execute(CommandType.cancelUploadFlash);
  }

  @OnClick(R.id.btnEraseFlash)
  public void clickEraseFlash(Button view) {
    execute(CommandType.eraseFlash);
  }

  @OnClick(R.id.btnStartSampling)
  public void clickStartSampling(Button view) {
    execute(CommandType.startSampling);
  }

  @OnClick(R.id.btnStopSampling)
  public void clickStopSampling(Button view) {
    execute(CommandType.stopSampling);
  }

  @OnClick(R.id.btnShutDown)
  public void clickShutdown(Button view) {
    execute(CommandType.shutdown, new Callback() {
      @Override
      public void onComplete(Map<String, Object> data) {
        showProgressDialog("Shutdown...");
      }
    });
  }

  @OnClick(R.id.btnSelfTest)
  public void clickSelfTest(Button view) {
    CommandRequest selfTestRequest = getCommandRequest(CommandType.selfTest, 10000);
    execute(selfTestRequest, new Callback() {
      @Override
      public void onComplete(Map<String, Object> data) {
        BatteryInfo batteryInfo = (BatteryInfo) data.get("batteryInfo");
        InfoDialog.newInstance(batteryInfo)
            .show(getSupportFragmentManager(), InfoDialog.TAG);
      }
    });
  }

  @OnClick(R.id.btnSetPatchClock)
  public void clickSetPatchClock(Button view) {
    execute(CommandType.setPatchClock);
  }

  @OnClick(R.id.btnReadUserInfo)
  public void clickReadlUserInfo(Button view) {
    execute(CommandType.readUserInfoFromFlash, new Callback() {
      @Override
      public void onComplete(Map<String, Object> data) {
        String userInfo = (String) data.get("userInfo");
        showToast(userInfo);
      }
    });
  }

  @OnClick(R.id.btnEraseUserInfo)
  public void clickEraseUserInfo(Button view) {
    execute(CommandType.eraseUserInfoFromFlash);
  }

  @OnClick(R.id.btnSetUserInfo)
  public void clickSetUserInfo(Button view) {
    final EditText et = new EditText(this);
    et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
    AlertDialog mUserInfoDialog = new AlertDialog.Builder(this)
        .setTitle(R.string.input_text_hint)
        .setIcon(android.R.drawable.ic_dialog_info)
        .setView(et)
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
            String input = et.getText().toString();
            if (TextUtils.isEmpty(input)) {
              showToast(R.string.input_text_empty);
            } else {
              CommandRequest setUserInfoRequest = getCommandRequest(CommandType.setUserInfoToFlash,
                  3000, "info", input);
              execute(setUserInfoRequest);
            }
          }
        })
        .setNegativeButton(R.string.cancel, null)
        .show();
    mUserInfoDialog.setCanceledOnTouchOutside(false);
  }

  @OnClick(R.id.btnGraphics)
  void clickBtnGraphics() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(R.string.title_todo)
        .setItems(R.array.data_graphics, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
            //RTS
            if (which == 0) {
              navToConnectedActivity(mDevice, MotionGraphicActivity.class);
              //History
            } else if (which == 1) {
              navToConnectedActivity(mDevice, HistoryActivity.class);
            }
          }
        });
    AlertDialog dialog = builder.create();
    dialog.show();
  }

  private static final int OTA_RET_CODE = 2019;
  private static final int ACTIVITY_CHOOSE_FILE = 3;
  @OnClick(R.id.btnOTA)
  public void clickOTA() {
    openFileSelecter();
  }

  private void openFileSelecter() {
    Intent intent = new Intent(this, FilePickerActivity.class);
    intent.putExtra(FilePickerActivity.ARG_FILTER, Pattern.compile("(VV|BLACK_GOLD).*(_FW_|_BL_)+.*\\.zip"));
    startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == ACTIVITY_CHOOSE_FILE) {
      if (resultCode != RESULT_OK || data == null) {
        isOTAing = false;
      } else {
        String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
        startActivityForResult(OTAActivity.newIntent(this, mDevice, filePath), OTA_RET_CODE);
        isOTAing = true;
      }
    } else if (requestCode == OTA_RET_CODE) {
      isOTAing = false;
    } else {
      finish();
    }
  }

  @OnClick(R.id.btnEngineerModule)
  public void openEngineerModule() {
    try {
      // look for engineer Activity
      Intent engineerActivity = new Intent(this, Class.forName(NRF_CONNECT_CLASS));
      engineerActivity.putExtra("device", mDevice);
      startActivity(engineerActivity);
    } catch (final Exception e) {
      showToast(R.string.error_no_support_engineer_module);
    }
  }

  public void execute(final CommandRequest request, final Callback callback) {
    DeviceManager.getInstance().execute(mDevice, request, new Callback() {
      @Override
      public void onStart() {
        String log = request.getTypeName() + ": onStart";
        showProgressDialog(ErrorMessageHandler.getInstance().getOnStartMessage(request.getType()));
        mOperationLogView.updateLog(log);
        if (null != callback) {
          callback.onStart();
        }
      }

      @Override
      public void onComplete(Map<String, Object> data) {
        dismissProgressDialog();
        showToast(ErrorMessageHandler.getInstance().getOnCompleteMessage(request.getType(), data));

        String log = request.getTypeName() + ": " + (data != null ? "onComplete: data = " + data : "onComplete");
        mOperationLogView.updateLog(log);
        if (null != callback) {
          callback.onComplete(data);
        }
      }

      @Override
      public void onError(int code, String msg) {
        dismissProgressDialog();
        showToast(ErrorMessageHandler.getInstance().getOnErrorMesage(request.getType(), code, msg));

        String log = request.getTypeName() + ": " + "onError: code = " + code + ", msg = " + msg;
        mOperationLogView.updateLog(log);
        if (null != callback) {
          callback.onError(code, msg);
        }
      }
    });
  }
  public void initMQTT(){
    options = new MqttConnectOptions();
    options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
    options.setUserName("viettd");
    options.setPassword("ubuntu".toCharArray());
    client = new MqttAndroidClient(this.getApplicationContext(), "tcp://103.21.151.182:1883", clientId);
    client.setCallback(new MqttCallback() {
      @Override
      public void connectionLost(Throwable cause) {
      }
      @Override
      public void messageArrived(String topic, MqttMessage message) throws Exception {
//                convertJSONtoInt cv = new convertJSONtoInt();
//                cv.s = message.toString();
//                cv.convert(cv);
//                addEntry(cv.timestamp, cv.heart_rate);
      }

      @Override
      public void deliveryComplete(IMqttDeliveryToken token) {

      }
    });
    try {
      IMqttToken token = client.connect(options);
      token.setActionCallback(new IMqttActionListener() {
        @Override
        public void onSuccess(IMqttToken asyncActionToken) {
          Log.d("MQTT", "onSuccess");
          String topic = "Heart_Rate";
          int qos = 1;
          try {
            IMqttToken subToken = client.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {
              @Override
              public void onSuccess(IMqttToken asyncActionToken) {
              }

              @Override
              public void onFailure(IMqttToken asyncActionToken,
                                    Throwable exception) {
              }
            });
          } catch (MqttException e) {
            e.printStackTrace();
          }
        }
        @Override
        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
          // Something went wrong e.g. connection timeout or firewall problems
          Log.d("MQTT", "onFailure");
        }
      });
    } catch (MqttException e) {
      e.printStackTrace();
    }
  }
  void publish(String payload){
    String topic = "GW2SV/"+macAddr+"/data";
    Log.e(TAG,topic);
    byte[] encodedPayload = new byte[0];
    try {
      encodedPayload = payload.getBytes("UTF-8");
      MqttMessage message = new MqttMessage(encodedPayload);
      client.publish(topic, message);
    } catch (UnsupportedEncodingException | MqttException e) {
      e.printStackTrace();
    }
  }
//  void publishDeviceInfo(String payload){
//    String topic = "GW2SV/"+macAddr+"/info";
//    Log.e(TAG,topic);
//    byte[] encodedPayload = new byte[0];
//    try {
//      encodedPayload = payload.getBytes("UTF-8");
//      MqttMessage message = new MqttMessage(encodedPayload);
//      client.publish(topic, message);
//    } catch (UnsupportedEncodingException | MqttException e) {
//      e.printStackTrace();
//    }
//  }
  public static String getMacAddr() {
    try {
      List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
      for (NetworkInterface nif : all) {
        if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

        byte[] macBytes = nif.getHardwareAddress();
        if (macBytes == null) {
          return "";
        }

        StringBuilder res1 = new StringBuilder();
        for (byte b : macBytes) {
          res1.append(String.format("%02X",b));
        }

        if (res1.length() > 0) {
          res1.deleteCharAt(res1.length() - 1);
        }
        return res1.toString();
      }
    } catch (Exception ex) {
    }
    return "02:00:00:00:00:00";
  }
}
