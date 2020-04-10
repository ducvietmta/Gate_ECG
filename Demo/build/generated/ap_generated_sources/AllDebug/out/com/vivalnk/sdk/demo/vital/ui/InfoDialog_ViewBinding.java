// Generated code from Butter Knife. Do not modify!
package com.vivalnk.sdk.demo.vital.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.vivalnk.sdk.demo.vital.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InfoDialog_ViewBinding implements Unbinder {
  private InfoDialog target;

  private View view7f09002a;

  @UiThread
  public InfoDialog_ViewBinding(final InfoDialog target, View source) {
    this.target = target;

    View view;
    target.ll1 = Utils.findRequiredViewAsType(source, R.id.ll1, "field 'll1'", LinearLayout.class);
    target.tvSampling = Utils.findRequiredViewAsType(source, R.id.tvSampling, "field 'tvSampling'", TextView.class);
    target.tvLeanOn = Utils.findRequiredViewAsType(source, R.id.tvLeanOn, "field 'tvLeanOn'", TextView.class);
    target.tvSampleFrequency = Utils.findRequiredViewAsType(source, R.id.tvSampleFrequency, "field 'tvSampleFrequency'", TextView.class);
    target.tvBaseLine = Utils.findRequiredViewAsType(source, R.id.tvBaseLine, "field 'tvBaseLine'", TextView.class);
    target.tvRTS = Utils.findRequiredViewAsType(source, R.id.tvRTS, "field 'tvRTS'", TextView.class);
    target.tvMode = Utils.findRequiredViewAsType(source, R.id.tvMode, "field 'tvMode'", TextView.class);
    target.tvFlashSave = Utils.findRequiredViewAsType(source, R.id.tvFlashSave, "field 'tvFlashSave'", TextView.class);
    target.ll2 = Utils.findRequiredViewAsType(source, R.id.ll2, "field 'll2'", LinearLayout.class);
    target.tvStatus = Utils.findRequiredViewAsType(source, R.id.tvStatus, "field 'tvStatus'", TextView.class);
    target.tvLevel = Utils.findRequiredViewAsType(source, R.id.tvLevel, "field 'tvLevel'", TextView.class);
    target.tvCanOta = Utils.findRequiredViewAsType(source, R.id.tvCanOta, "field 'tvCanOta'", TextView.class);
    target.tvCanSampling = Utils.findRequiredViewAsType(source, R.id.tvCanSampling, "field 'tvCanSampling'", TextView.class);
    target.tvCanBleTransmission = Utils.findRequiredViewAsType(source, R.id.tvCanBleTransmission, "field 'tvCanBleTransmission'", TextView.class);
    target.tvRawVoltage = Utils.findRequiredViewAsType(source, R.id.tvRawVoltage, "field 'tvRawVoltage'", TextView.class);
    target.tvVoltage = Utils.findRequiredViewAsType(source, R.id.tvVoltage, "field 'tvVoltage'", TextView.class);
    target.tvPercent = Utils.findRequiredViewAsType(source, R.id.tvPercent, "field 'tvPercent'", TextView.class);
    target.tvTemperature = Utils.findRequiredViewAsType(source, R.id.tvTemperature, "field 'tvTemperature'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btOk, "field 'btOk' and method 'onOKClick'");
    target.btOk = Utils.castView(view, R.id.btOk, "field 'btOk'", Button.class);
    view7f09002a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onOKClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    InfoDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ll1 = null;
    target.tvSampling = null;
    target.tvLeanOn = null;
    target.tvSampleFrequency = null;
    target.tvBaseLine = null;
    target.tvRTS = null;
    target.tvMode = null;
    target.tvFlashSave = null;
    target.ll2 = null;
    target.tvStatus = null;
    target.tvLevel = null;
    target.tvCanOta = null;
    target.tvCanSampling = null;
    target.tvCanBleTransmission = null;
    target.tvRawVoltage = null;
    target.tvVoltage = null;
    target.tvPercent = null;
    target.tvTemperature = null;
    target.btOk = null;

    view7f09002a.setOnClickListener(null);
    view7f09002a = null;
  }
}
