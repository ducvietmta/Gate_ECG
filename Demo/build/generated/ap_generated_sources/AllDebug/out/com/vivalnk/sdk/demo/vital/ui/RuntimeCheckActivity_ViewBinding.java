// Generated code from Butter Knife. Do not modify!
package com.vivalnk.sdk.demo.vital.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.vivalnk.sdk.demo.vital.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RuntimeCheckActivity_ViewBinding implements Unbinder {
  private RuntimeCheckActivity target;

  @UiThread
  public RuntimeCheckActivity_ViewBinding(RuntimeCheckActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RuntimeCheckActivity_ViewBinding(RuntimeCheckActivity target, View source) {
    this.target = target;

    target.checkboxBLESupport = Utils.findRequiredViewAsType(source, R.id.checkboxBLESupport, "field 'checkboxBLESupport'", AppCompatCheckBox.class);
    target.checkboxBluetoothEnable = Utils.findRequiredViewAsType(source, R.id.checkboxBluetoothEnable, "field 'checkboxBluetoothEnable'", AppCompatCheckBox.class);
    target.checkboxLocationEnable = Utils.findRequiredViewAsType(source, R.id.checkboxLocationEnable, "field 'checkboxLocationEnable'", AppCompatCheckBox.class);
    target.checkboxBluetoothPersmission = Utils.findRequiredViewAsType(source, R.id.checkboxBluetoothPersmission, "field 'checkboxBluetoothPersmission'", AppCompatCheckBox.class);
    target.checkboxLocationPersmission = Utils.findRequiredViewAsType(source, R.id.checkboxLocationPersmission, "field 'checkboxLocationPersmission'", AppCompatCheckBox.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RuntimeCheckActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.checkboxBLESupport = null;
    target.checkboxBluetoothEnable = null;
    target.checkboxLocationEnable = null;
    target.checkboxBluetoothPersmission = null;
    target.checkboxLocationPersmission = null;
  }
}
