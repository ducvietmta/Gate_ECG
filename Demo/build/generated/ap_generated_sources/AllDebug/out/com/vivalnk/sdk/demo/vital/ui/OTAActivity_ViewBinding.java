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

public class OTAActivity_ViewBinding implements Unbinder {
  private OTAActivity target;

  @UiThread
  public OTAActivity_ViewBinding(OTAActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OTAActivity_ViewBinding(OTAActivity target, View source) {
    this.target = target;

    target.tv = Utils.findRequiredViewAsType(source, R.id.tv, "field 'tv'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OTAActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv = null;
  }
}
