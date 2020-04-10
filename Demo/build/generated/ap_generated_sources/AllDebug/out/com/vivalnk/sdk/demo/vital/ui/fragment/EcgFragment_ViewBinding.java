// Generated code from Butter Knife. Do not modify!
package com.vivalnk.sdk.demo.vital.ui.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.vivalnk.sdk.app.base.widget.RTSEcgView;
import com.vivalnk.sdk.demo.vital.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EcgFragment_ViewBinding implements Unbinder {
  private EcgFragment target;

  private View view7f090043;

  private View view7f09003b;

  private View view7f090034;

  @UiThread
  public EcgFragment_ViewBinding(final EcgFragment target, View source) {
    this.target = target;

    View view;
    target.ecgView = Utils.findRequiredViewAsType(source, R.id.ecgView, "field 'ecgView'", RTSEcgView.class);
    view = Utils.findRequiredView(source, R.id.btnSwitchGain, "field 'btnSwitchGain' and method 'clickSwitchGain'");
    target.btnSwitchGain = Utils.castView(view, R.id.btnSwitchGain, "field 'btnSwitchGain'", Button.class);
    view7f090043 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickSwitchGain();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnRevert, "field 'btnRevert' and method 'clickRevert'");
    target.btnRevert = Utils.castView(view, R.id.btnRevert, "field 'btnRevert'", Button.class);
    view7f09003b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickRevert();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnNoisy, "field 'btnNoisy' and method 'clickDenoisy'");
    target.btnNoisy = Utils.castView(view, R.id.btnNoisy, "field 'btnNoisy'", Button.class);
    view7f090034 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickDenoisy();
      }
    });
    target.tvHR = Utils.findRequiredViewAsType(source, R.id.tvHR, "field 'tvHR'", TextView.class);
    target.tvRR = Utils.findRequiredViewAsType(source, R.id.tvRR, "field 'tvRR'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EcgFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ecgView = null;
    target.btnSwitchGain = null;
    target.btnRevert = null;
    target.btnNoisy = null;
    target.tvHR = null;
    target.tvRR = null;

    view7f090043.setOnClickListener(null);
    view7f090043 = null;
    view7f09003b.setOnClickListener(null);
    view7f09003b = null;
    view7f090034.setOnClickListener(null);
    view7f090034 = null;
  }
}
