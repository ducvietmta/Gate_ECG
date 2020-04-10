// Generated code from Butter Knife. Do not modify!
package com.vivalnk.sdk.demo.vital.ui.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.vivalnk.sdk.demo.vital.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DeviceConnectedViewHolder_ViewBinding implements Unbinder {
  private DeviceConnectedViewHolder target;

  @UiThread
  public DeviceConnectedViewHolder_ViewBinding(DeviceConnectedViewHolder target, View source) {
    this.target = target;

    target.tvDeviceName = Utils.findRequiredViewAsType(source, R.id.tvDeviceName, "field 'tvDeviceName'", TextView.class);
    target.tvRSSI = Utils.findRequiredViewAsType(source, R.id.tvRSSI, "field 'tvRSSI'", TextView.class);
    target.tvDeviceMac = Utils.findRequiredViewAsType(source, R.id.tvDeviceMac, "field 'tvDeviceMac'", TextView.class);
    target.tvFWVersion = Utils.findRequiredViewAsType(source, R.id.tvFWVersion, "field 'tvFWVersion'", TextView.class);
    target.tvHWVersion = Utils.findRequiredViewAsType(source, R.id.tvHWVersion, "field 'tvHWVersion'", TextView.class);
    target.tvDeviceInfo = Utils.findRequiredViewAsType(source, R.id.tvDeviceInfo, "field 'tvDeviceInfo'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DeviceConnectedViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvDeviceName = null;
    target.tvRSSI = null;
    target.tvDeviceMac = null;
    target.tvFWVersion = null;
    target.tvHWVersion = null;
    target.tvDeviceInfo = null;
  }
}
