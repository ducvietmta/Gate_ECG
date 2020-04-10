package com.vivalnk.sdk.demo.vital.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.vivalnk.sdk.VitalClient;
import com.vivalnk.sdk.app.base.app.BaseToolbarActivity;
import com.vivalnk.sdk.app.base.app.Layout;
import com.vivalnk.sdk.app.repository.device.ConnectEvent;
import com.vivalnk.sdk.app.repository.device.DeviceManager;
import com.vivalnk.sdk.common.eventbus.Subscribe;
import com.vivalnk.sdk.demo.vital.R;
import com.vivalnk.sdk.demo.vital.ui.adapter.DeviceConnectedListAdapter;
import com.vivalnk.sdk.model.Device;
import java.util.ArrayList;

/**
 * 已连接列表
 *
 * @author jake
 * @date 2019/3/15
 */
public class DeviceConnectedListActivity extends BaseToolbarActivity {

  private ArrayList<Device> deviceArrayList;
  private RecyclerView.Adapter recycleAdapter;
  @BindView(R.id.deviceList)
  RecyclerView connectedDeviceList;

  @Subscribe
  public void onConnectEvent(ConnectEvent connectEvent) {
    if (connectEvent == null) {
      return;
    }

    if (ConnectEvent.ON_DEVICE_READY.equalsIgnoreCase(connectEvent.event)
        || (ConnectEvent.ON_DISCONNECTED.equalsIgnoreCase(connectEvent.event))
        || (ConnectEvent.ON_ERROR.equalsIgnoreCase(connectEvent.event))) {
      //on connect state change
      updateConnectedList();
    }
  }

  private void updateConnectedList() {
    deviceArrayList.clear();
    deviceArrayList.addAll(VitalClient.getInstance().getConnectedDeviceList());
    recycleAdapter.notifyDataSetChanged();
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initView();
  }

  @Override
  protected void onResume() {
    super.onResume();
    updateConnectedList();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  @Override
  protected Layout getLayout() {
    return Layout.createLayoutByID(R.layout.activity_device_connected_list);
  }

  private void initView() {
    // list view
    connectedDeviceList.setLayoutManager(new LinearLayoutManager(this));
    connectedDeviceList.setHasFixedSize(true);
    deviceArrayList = new ArrayList<>();
    recycleAdapter = new DeviceConnectedListAdapter(this, deviceArrayList,
        (itemView, position, device) -> {
          if (DeviceManager.getInstance().isConnected(device)) {
            navToDeviceActivity(device);
          } else {
            DeviceManager.getInstance().connect(device);
          }
        });
    connectedDeviceList.setAdapter(recycleAdapter);
  }

  private void navToDeviceActivity(Device device) {
    Bundle extras = new Bundle();
    extras.putSerializable("device", device);
    navTo(DeviceConnectedListActivity.this, extras, DeviceMenuActivity.class);
  }
}
