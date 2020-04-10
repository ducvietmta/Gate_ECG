// Generated code from Butter Knife. Do not modify!
package com.vivalnk.sdk.demo.vital.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.vivalnk.sdk.demo.vital.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DeviceMenu200Activity_ViewBinding implements Unbinder {
  private DeviceMenu200Activity target;

  @UiThread
  public DeviceMenu200Activity_ViewBinding(DeviceMenu200Activity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DeviceMenu200Activity_ViewBinding(DeviceMenu200Activity target, View source) {
    this.target = target;

    target.tvPatchMac = Utils.findRequiredViewAsType(source, R.id.tvPatchMac, "field 'tvPatchMac'", TextView.class);
    target.tvPatchSN = Utils.findRequiredViewAsType(source, R.id.tvPatchSN, "field 'tvPatchSN'", TextView.class);
    target.tvPatchFWVersion = Utils.findRequiredViewAsType(source, R.id.tvPatchFWVersion, "field 'tvPatchFWVersion'", TextView.class);
    target.tvPatchHWVersion = Utils.findRequiredViewAsType(source, R.id.tvPatchHWVersion, "field 'tvPatchHWVersion'", TextView.class);
    target.tvChargeFWVersion = Utils.findRequiredViewAsType(source, R.id.tvChargeFWVersion, "field 'tvChargeFWVersion'", TextView.class);
    target.tvChargeHWVersion = Utils.findRequiredViewAsType(source, R.id.tvChargeHWVersion, "field 'tvChargeHWVersion'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DeviceMenu200Activity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvPatchMac = null;
    target.tvPatchSN = null;
    target.tvPatchFWVersion = null;
    target.tvPatchHWVersion = null;
    target.tvChargeFWVersion = null;
    target.tvChargeHWVersion = null;
  }
}
