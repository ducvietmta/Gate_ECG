// Generated code from Butter Knife. Do not modify!
package com.vivalnk.sdk.demo.vital.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.vivalnk.sdk.demo.vital.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DeviceConnectedListActivity_ViewBinding implements Unbinder {
  private DeviceConnectedListActivity target;

  @UiThread
  public DeviceConnectedListActivity_ViewBinding(DeviceConnectedListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DeviceConnectedListActivity_ViewBinding(DeviceConnectedListActivity target, View source) {
    this.target = target;

    target.connectedDeviceList = Utils.findRequiredViewAsType(source, R.id.deviceList, "field 'connectedDeviceList'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DeviceConnectedListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.connectedDeviceList = null;
  }
}
