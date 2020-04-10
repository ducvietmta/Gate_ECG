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

public class ScanningActivity_ViewBinding implements Unbinder {
  private ScanningActivity target;

  @UiThread
  public ScanningActivity_ViewBinding(ScanningActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ScanningActivity_ViewBinding(ScanningActivity target, View source) {
    this.target = target;

    target.rvScanList = Utils.findRequiredViewAsType(source, R.id.rvList, "field 'rvScanList'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ScanningActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvScanList = null;
  }
}
