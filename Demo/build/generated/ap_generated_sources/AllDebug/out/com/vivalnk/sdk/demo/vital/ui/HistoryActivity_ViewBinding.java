// Generated code from Butter Knife. Do not modify!
package com.vivalnk.sdk.demo.vital.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.vivalnk.sdk.app.base.widget.DragEcgView;
import com.vivalnk.sdk.demo.vital.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HistoryActivity_ViewBinding implements Unbinder {
  private HistoryActivity target;

  private View view7f090044;

  private View view7f090043;

  private View view7f09003b;

  @UiThread
  public HistoryActivity_ViewBinding(HistoryActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public HistoryActivity_ViewBinding(final HistoryActivity target, View source) {
    this.target = target;

    View view;
    target.dragEcgView = Utils.findRequiredViewAsType(source, R.id.dragEcgView, "field 'dragEcgView'", DragEcgView.class);
    view = Utils.findRequiredView(source, R.id.btnTimePicker, "field 'btnTimePicker' and method 'clickTimePicker'");
    target.btnTimePicker = Utils.castView(view, R.id.btnTimePicker, "field 'btnTimePicker'", Button.class);
    view7f090044 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickTimePicker();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnSwitchGain, "field 'btnSwitchGain' and method 'clickSwitchGain'");
    target.btnSwitchGain = Utils.castView(view, R.id.btnSwitchGain, "field 'btnSwitchGain'", Button.class);
    view7f090043 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickSwitchGain();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnRevert, "field 'btnRevert' and method 'clickBtnRevert'");
    target.btnRevert = Utils.castView(view, R.id.btnRevert, "field 'btnRevert'", Button.class);
    view7f09003b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickBtnRevert();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    HistoryActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.dragEcgView = null;
    target.btnTimePicker = null;
    target.btnSwitchGain = null;
    target.btnRevert = null;

    view7f090044.setOnClickListener(null);
    view7f090044 = null;
    view7f090043.setOnClickListener(null);
    view7f090043 = null;
    view7f09003b.setOnClickListener(null);
    view7f09003b = null;
  }
}
