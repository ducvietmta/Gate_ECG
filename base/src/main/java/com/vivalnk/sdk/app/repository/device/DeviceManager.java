package com.vivalnk.sdk.app.repository.device;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import com.vivalnk.sdk.Callback;
import com.vivalnk.sdk.CommandRequest;
import com.vivalnk.sdk.DataReceiveListener;
import com.vivalnk.sdk.SampleDataReceiveListener;
import com.vivalnk.sdk.VitalClient;
import com.vivalnk.sdk.base.connect.ConnectResumeListener;
import com.vivalnk.sdk.ble.BluetoothConnectListener;
import com.vivalnk.sdk.ble.BluetoothScanListener;
import com.vivalnk.sdk.common.ble.connect.BleConnectOptions;
import com.vivalnk.sdk.common.ble.exception.BleCode;
import com.vivalnk.sdk.common.ble.scan.ScanOptions;
import com.vivalnk.sdk.common.ble.utils.BluetoothUtils;
import com.vivalnk.sdk.common.eventbus.EventBus;
import com.vivalnk.sdk.common.excutors.LooperPool;
import com.vivalnk.sdk.common.excutors.LooperType;
import com.vivalnk.sdk.engineer.test.LogerManager;
import com.vivalnk.sdk.model.BatteryInfo;
import com.vivalnk.sdk.model.Device;
import com.vivalnk.sdk.model.SampleData;
import com.vivalnk.sdk.repository.local.database.DatabaseManager;
import com.vivalnk.sdk.repository.local.database.VitalData;
import com.vivalnk.sdk.vital.ete.ETEManager;
import com.vivalnk.sdk.vital.ete.ETEResult;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JakeMo on 18-4-26.
 */
public class DeviceManager {

  public static final String TAG = "DeviceManager";

  private Context mContext;

  private Map<String, DeviceETEManager> eteManagerMap;

  private Handler mDataHandler = LooperPool.createHandler(LooperType.DATA);

  private static class SingletonHolder {

    private static final DeviceManager
        INSTANCE = new DeviceManager();
  }

  private DeviceManager() {
  }

  public static DeviceManager getInstance() {
    return SingletonHolder.INSTANCE;
  }

  public void init(Context context) {
    mContext = context.getApplicationContext();

    //if (BuildConfig.DEBUG) {
    VitalClient.getInstance().openLog();
    VitalClient.getInstance().alloWriteToFile(true);
    //}

    VitalClient.Builder builder = new VitalClient.Builder();
    builder.setConnectResumeListener(myConnectListener);
    VitalClient.getInstance().init(mContext, builder);

    eteManagerMap = new HashMap<>();

    LogerManager.getInstance().init();

  }

  public ETEManager getETEManager(Device device, boolean flash) {
    String key = DeviceETEManager.getKey(device, flash);
    DeviceETEManager deviceETEMannager = eteManagerMap.get(key);
    if (deviceETEMannager == null || deviceETEMannager.eteManager == null) {
      deviceETEMannager = new DeviceETEManager(device, flash);
      eteManagerMap.put(key, deviceETEMannager);
    }
    return deviceETEMannager.eteManager;
  }

  private DataReceiveListener dataReceiveListener = new DataReceiveListener() {
    @Override
    public void onReceiveData(Device device, Map<String, Object> data) {
      if (null != data.get("data")) {
        VitalSampleData liveData = new VitalSampleData();
        liveData.device = device;
        liveData.data = data;

        //raw data
        runOnUiThread(() -> EventBus.getDefault().post(liveData));

        //VitalData
        mDataHandler.post(new Runnable() {
          @Override
          public void run() {
            handleSampleData(device, data);
          }
        });

      }
    }

    @Override
    public void onBatteryChange(Device device, Map<String, Object> data) {
      BatteryInfo batteryInfo = (BatteryInfo) data.get("data");

      BatteryData batteryData = new BatteryData();
      batteryData.device = device;
      batteryData.batteryInfo = batteryInfo;

      runOnUiThread(() -> EventBus.getDefault().post(batteryData));
    }

    @Override
    public void onLeadStatusChange(Device device, boolean isLeadOn) {
      LeadStatusData leadStatusData = new LeadStatusData();
      leadStatusData.device = device;
      leadStatusData.leadOn = isLeadOn;

      runOnUiThread(() -> EventBus.getDefault().post(leadStatusData));
    }
  };

  private SampleDataReceiveListener sampleDataReceiveListener = new SampleDataReceiveListener() {
    @Override
    public void onReceiveSampleData(Device device, boolean flash, SampleData data) {
      //simplify the usage of onReceiveData, just output sample data
      Log.d(TAG, "onReceiveSampleData: flash = " + flash + ", " + "data = " + data);
    }
  };

  private void handleSampleData(Device device, Map<String, Object> data) {
    SampleData sampleData = (SampleData) data.get("data");

    if (sampleData == null) {
      return;
    }

    //当存在RRI/ACC的时候，输入FB，获取结果
    analyzerData(device, sampleData, sampleData.isFlash());

    runOnUiThread(() -> EventBus.getDefault().post(sampleData));

  }

  public ETEResult getETEResultSync(Device device, boolean flash) {
    ETEManager eteManager = getETEManager(device, flash);
    if (eteManager == null) {
      return null;
    }
    return eteManager.getResultSync(System.currentTimeMillis());
  }

  private MyConnectListener myConnectListener = new MyConnectListener();

  private void analyzerData(Device device, SampleData data, boolean flash) {

    ETEManager eteManager = getETEManager(device, flash);
    if (null != eteManager) {
      eteManager.analyzerData(data);
    }

  }

  Handler mMainHandler = LooperPool.getMainHandler();
  private BluetoothScanListener scanListener = new BluetoothScanListener() {
    @Override
    public void onStart() {
      runOnUiThread(() -> EventBus.getDefault().post(ScanEvent.onStart()));
    }

    @Override
    public void onDeviceFound(Device device) {
      runOnUiThread(() -> EventBus.getDefault().post(ScanEvent.onDeviceFound(device)));
    }

    @Override
    public void onStop() {
      runOnUiThread(() -> EventBus.getDefault().post(ScanEvent.onStop()));
    }

    @Override
    public void onError(int code, String msg) {
      runOnUiThread(() -> EventBus.getDefault().post(ScanEvent.onError(code, msg)));
    }
  };

  public int checkBle() {
    return VitalClient.getInstance().checkBle();
  }

  public void enableBle() {
    VitalClient.getInstance().enableBle();
  }

  public void disableBle() {
    VitalClient.getInstance().disableBle();
  }

  /**
   * start scan device.
   */
  public void startScan() {
    ScanOptions options = new ScanOptions.Builder().setTimeout(10 * 1000).build();
    startScan(options);
  }

  public void startScan(ScanOptions options) {
    VitalClient.getInstance().startScan(options, scanListener);
  }

  public void stopScan() {
    VitalClient.getInstance().stopScan();
  }

  public void connect(final Device device) {
    connect(device, 6);
  }

  /**
   * connect device.
   *
   * @param device your vital device
   */
  public void connect(final Device device, int retry) {
    ConnectEvent connectEvent = new ConnectEvent();
    connectEvent.device = device;
    connectEvent.event = ConnectEvent.ON_ERROR;
    if (null == device || TextUtils.isEmpty(device.getId())) {
      connectEvent.code = BleCode.BLUETOOTH_CONNECT_ERROR;
      connectEvent.msg = "device can not be null";
      runOnUiThread(() -> EventBus.getDefault().post(connectEvent));
      return;
    }

    if (BluetoothUtils.isDeviceConnected(mContext, device.getId())) {
      connectEvent.code = BleCode.BLUETOOTH_CONNECT_ERROR;
      connectEvent.msg = "device is connected";
      runOnUiThread(() -> EventBus.getDefault().post(connectEvent));
      return;
    }

    BleConnectOptions options = new BleConnectOptions.Builder()
        .setConnectRetry(retry)
        .setConnectTimeout(10 * 1000)
        .setAutoConnect(true)
        .build();

    VitalClient.getInstance().connect(device, options, myConnectListener);
  }

  public void disconnect(Device device) {
    VitalClient.getInstance().disconnect(device);
  }

  public void disconnectAll() {
    VitalClient.getInstance().disconnectAll();
  }

  public boolean isConnected(Device device) {
    if (null == device || TextUtils.isEmpty(device.getId())) {
      throw new NullPointerException("device is null, or has a empty  mac id");
    }
    return VitalClient.getInstance().isConnected(device);
  }

  private void registDataReceiver(Device device) {
    VitalClient.getInstance().registDataReceiver(device, dataReceiveListener);
  }

  public void execute(Device device, CommandRequest command, Callback callback) {
    if (null == device || TextUtils.isEmpty(device.getId())) {
      throw new NullPointerException("device is null, or has a empty  mac id");
    }
    VitalClient.getInstance().execute(device, command, callback);
  }

  public void runOnUiThread(Runnable runnable) {
    mMainHandler.post(runnable);
  }

  public void destroy() {
    VitalClient.getInstance().destroy();
    for (Map.Entry<String, DeviceETEManager> deviceETEManagerEntry : eteManagerMap.entrySet()) {
      DeviceETEManager deviceETEMannager = deviceETEManagerEntry.getValue();
      deviceETEMannager.eteManager.unregisterETEDataReceiveListener();
      eteManagerMap.remove(deviceETEManagerEntry.getKey());
    }
  }

  private class MyConnectListener implements BluetoothConnectListener {

    @Override
    public void onStart(Device device) {
      Log.d(TAG, "Connect onStart " + device.toString());
      runOnUiThread(() -> EventBus.getDefault().post(ConnectEvent.onStart(device)));
    }

    @Override
    public void onConnected(Device device) {
      Log.d(TAG, "Connect onConnected " + device.toString());
      runOnUiThread(() -> EventBus.getDefault().post(ConnectEvent.onConnected(device)));
    }

    @Override
    public void onServiceReady(Device device) {
      Log.d(TAG, "Connect onServiceReady " + device.toString());
      registDataReceiver(device);
      runOnUiThread(() -> EventBus.getDefault().post(ConnectEvent.onServiceReady(device)));
    }

    @Override
    public void onEnableNotify(Device device) {
      Log.d(TAG, "Connect onEnableNotify " + device.toString());
      runOnUiThread(() -> EventBus.getDefault().post(ConnectEvent.onEnableNotify(device)));
    }

    @Override
    public void onDeviceReady(Device device) {
      Log.d(TAG, "Connect onDeviceReady " + device.toString());
      runOnUiThread(() -> EventBus.getDefault().post(ConnectEvent.onDeviceReady(device)));
    }

    @Override
    public void onDisconnected(Device device, boolean isForce) {
      Log.d(TAG, "Connect onDisconnected " + device.toString() + " isForce=" + isForce);
      runOnUiThread(() -> EventBus.getDefault().post(ConnectEvent.onDisconnected(device, isForce)));
    }

    @Override
    public void onError(Device device, int code, String msg) {
      Log.d(TAG, "Connect onError " + device.toString() + " code=" + code + " msg=" + msg);
      runOnUiThread(() -> EventBus.getDefault().post(ConnectEvent.onError(device, code, msg)));
    }

    @Override
    public boolean onResume(Device device) {
      return false;
    }
  }

  public static class VitalSampleData {
    public Device device;
    public Map<String, Object> data;
  }

  public static class BatteryData {
    public Device device;
    public BatteryInfo batteryInfo;
  }

  public static class RssiData {
    public Device device;
    public Integer rssi;
  }

  public static class LeadStatusData {
    public Device device;
    public boolean leadOn;
  }

}
