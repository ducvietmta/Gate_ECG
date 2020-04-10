// Generated code from Butter Knife. Do not modify!
package com.vivalnk.sdk.demo.vital.ui.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.vivalnk.sdk.app.base.widget.AccView;
import com.vivalnk.sdk.demo.vital.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AccFragment_ViewBinding implements Unbinder {
  private AccFragment target;

  @UiThread
  public AccFragment_ViewBinding(AccFragment target, View source) {
    this.target = target;

    target.accView = Utils.findRequiredViewAsType(source, R.id.accView, "field 'accView'", AccView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AccFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.accView = null;
  }
}
