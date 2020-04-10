package com.vivalnk.sdk.demo.vital.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import com.vivalnk.sdk.Callback;
import com.vivalnk.sdk.CommandRequest;
import com.vivalnk.sdk.VitalClient;
import com.vivalnk.sdk.app.base.app.Layout;
import com.vivalnk.sdk.app.base.i18n.ErrorMessageHandler;
import com.vivalnk.sdk.app.repository.device.ConnectEvent;
import com.vivalnk.sdk.app.repository.device.DeviceManager;
import com.vivalnk.sdk.app.repository.device.ScanEvent;
import com.vivalnk.sdk.command.base.CommandType;
import com.vivalnk.sdk.common.eventbus.Subscribe;
import com.vivalnk.sdk.common.eventbus.ThreadMode;
import com.vivalnk.sdk.demo.vital.R;
import com.vivalnk.sdk.demo.vital.base.BaseDeviceActivity;
import com.vivalnk.sdk.demo.vital.config.Constants;
import com.vivalnk.sdk.demo.vital.ui.adapter.ScanListAdapter;
import com.vivalnk.sdk.demo.vital.ui.adapter.ScanListAdapter.StatusDevice;
import com.vivalnk.sdk.model.Device;
import com.vivalnk.sdk.model.PatchStatusInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 扫描界面
 *
 * @author Aslan
 * @date 2019/3/14
 */
public class ScanningActivity extends BaseDeviceActivity implements
    NavigationView.OnNavigationItemSelectedListener {

  private Handler mHandler;

  public static Intent newIntent(Context context) {
    Intent intent = new Intent(context, ScanningActivity.class);
    return intent;
  }

  private List<StatusDevice> deviceArrayList;
  private ScanListAdapter recycleAdapter;
  private Boolean mIsScanning = false;

  @BindView(R.id.rvList)
  RecyclerView rvScanList;

  private Comparator comparator = new Comparator<StatusDevice>() {
    @Override
    public int compare(StatusDevice o1, StatusDevice o2) {
      int rssi1 = o1.getRssi();
      int rssi2 = o2.getRssi();
      if (o1.connect) {
        rssi1 = Math.abs(rssi1);
      }

      if (o2.connect) {
        rssi2 = Math.abs(rssi2);
      }

      if (o1.connect && o2.connect) {
        return rssi1 - rssi2;
      } else {
        return rssi2 - rssi1;
      }
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initView();

    VitalClient.getInstance().connectLastDevice();

    mHandler = new Handler(Looper.getMainLooper());

    mHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        if (checkBLE()) {
          startScan();
        }
      }
    }, 1000L);
  }

  /**
   * 开始扫描
   */
  private void startScan() {
    if (!checkBLE()) {
      return;
    }

    if (mIsScanning) {
      return;
    }

    deviceArrayList.clear();
    recycleAdapter.notifyDataSetChanged();

    mIsScanning = true;
    setScanText(R.string.stop_scan);
    DeviceManager.getInstance().startScan();
  }

  /**
   * 关闭扫描
   */
  private void stopScan() {
    if (mIsScanning == false) {
      return;
    }

    mIsScanning = false;
    setScanText(R.string.start_scan);
    DeviceManager.getInstance().stopScan();
  }

  @Override
  protected void onResume() {
    super.onResume();
    recycleAdapter.notifyDataSetChanged();
  }

  @Override
  protected Layout getLayout() {
    return Layout.createLayoutByID(R.layout.activity_main);
  }

  @Override
  protected void onDestroy() {
    DeviceManager.getInstance().stopScan();
    DeviceManager.getInstance().disconnectAll();
    super.onDestroy();
  }

  @Override
  protected void onConnectChanged(ConnectEvent connectEvent) {
    super.onConnectChanged(connectEvent);

    if (ConnectEvent.ON_START.equalsIgnoreCase(connectEvent.event)) {
      stopScan();
    } else if (ConnectEvent.ON_CONNECTED.equalsIgnoreCase(connectEvent.event)) {
    } else if (ConnectEvent.ON_DEVICE_READY.equalsIgnoreCase(connectEvent.event)) {
      startSamplingIfNeed(connectEvent.device);
      updateContent(true, connectEvent.device);
    } else if (ConnectEvent.ON_DISCONNECTED.equalsIgnoreCase(connectEvent.event)) {
      updateContent(false, connectEvent.device);
      showToast(
          ErrorMessageHandler.getInstance().getDisconnectedMeesage(connectEvent.device, connectEvent.isForce));
    } else if (ConnectEvent.ON_ERROR.equalsIgnoreCase(connectEvent.event)) {
      updateContent(false, connectEvent.device);
      showToast(ErrorMessageHandler.getInstance().getConnectErrorMeesage(connectEvent.device, connectEvent.code, connectEvent.msg));
    }
  }

  private void startSamplingIfNeed(Device device) {
    CommandRequest checkPatchStatus = new CommandRequest
        .Builder().setTimeout(3000).setType(CommandType.checkPatchStatus).build();
    DeviceManager.getInstance().execute(device, checkPatchStatus, new Callback() {
      @Override
      public void onStart() {

      }

      @Override
      public void onComplete(Map<String, Object> data) {
        PatchStatusInfo statusInfo = (PatchStatusInfo) data.get("data");
        if (statusInfo != null) {
          if (statusInfo.isSampling()) {
            CommandRequest startSampling = new CommandRequest
                .Builder().setTimeout(3000).setType(CommandType.startSampling).build();
            DeviceManager.getInstance().execute(device, startSampling, null);
          }
          navToDeviceActivity(device);
        }
      }

      @Override
      public void onError(int code, String msg) {

      }
    });
  }

  private void updateContent(boolean connect, Device device) {
    recycleAdapter.updateConnectStatus(connect, device);
    Collections.sort(deviceArrayList, comparator);
    recycleAdapter.notifyDataSetChanged();
  }

  private void initView() {
    // list view
    rvScanList.setLayoutManager(new LinearLayoutManager(this));
    rvScanList.setHasFixedSize(true);
    deviceArrayList = new ArrayList<>();
    recycleAdapter = new ScanListAdapter(deviceArrayList,
        (itemView, position, device) -> {
          if (checkBLE() == false) {
            return;
          }

          if (DeviceManager.getInstance().isConnected(device)) {
            navToDeviceActivity(device);
          } else {
            DeviceManager.getInstance().connect(device);
          }
        });
    rvScanList.setAdapter(recycleAdapter);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
  }

  private void navToDeviceActivity(Device device) {
    Bundle extras = new Bundle();
    extras.putSerializable("device", device);
    navTo(ScanningActivity.this, extras, DeviceMenuActivity.class);
  }

  @UiThread
  private void onDeviceFound(Device device) {
    if (Constants.isSupport310() == false && device.isVV310()) {
      return;
    }

    if (Constants.isSupport330() == false && device.isVV330()) {
      return;
    }

    deviceArrayList.add(new ScanListAdapter.StatusDevice(device));
    Collections.sort(deviceArrayList, comparator);
    recycleAdapter.notifyDataSetChanged();
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_scan) {
      if (mIsScanning) {
        stopScan();
      } else {
        startScan();
      }
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  /**
   *
   * @param resID
   */
  private void setScanText(@StringRes int resID) {
    if (toolbar.getMenu() != null && toolbar.getMenu().size() > 0) {
      MenuItem item = toolbar.getMenu().getItem(0);
      item.setTitle(resID);
    }
  }

  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_scanning) {

    } else if (id == R.id.nav_connected_device) {
      if (!checkBLE()) {
        return true;
      }

      navTo(DeviceConnectedListActivity.class);
    } else if (id == R.id.nav_check) {
      navTo(RuntimeCheckActivity.class);
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override
  protected void onLocationTurnOff() {
    super.onLocationTurnOff();
    stopScan();
  }

  @Override
  protected void onBluetoothTurnOff() {
    super.onBluetoothTurnOff();
    stopScan();
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onScanEvent(ScanEvent event) {
    if (ScanEvent.ON_DEVICEFOUND.equalsIgnoreCase(event.event)) {
      onDeviceFound(event.device);
    }
  }
}