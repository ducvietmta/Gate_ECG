// Generated code from Butter Knife. Do not modify!
package com.vivalnk.sdk.demo.vital.ui.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.vivalnk.sdk.demo.vital.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DeviceViewHolder_ViewBinding implements Unbinder {
  private DeviceViewHolder target;

  @UiThread
  public DeviceViewHolder_ViewBinding(DeviceViewHolder target, View source) {
    this.target = target;

    target.tvDeviceName = Utils.findRequiredViewAsType(source, R.id.tvDeviceName, "field 'tvDeviceName'", TextView.class);
    target.tvRSSI = Utils.findRequiredViewAsType(source, R.id.tvRSSI, "field 'tvRSSI'", TextView.class);
    target.tvDeviceMac = Utils.findRequiredViewAsType(source, R.id.tvDeviceMac, "field 'tvDeviceMac'", TextView.class);
    target.cardView = Utils.findRequiredViewAsType(source, R.id.cardView, "field 'cardView'", CardView.class);
    target.tvConnected = Utils.findRequiredViewAsType(source, R.id.tvConnected, "field 'tvConnected'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DeviceViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvDeviceName = null;
    target.tvRSSI = null;
    target.tvDeviceMac = null;
    target.cardView = null;
    target.tvConnected = null;
  }
}
