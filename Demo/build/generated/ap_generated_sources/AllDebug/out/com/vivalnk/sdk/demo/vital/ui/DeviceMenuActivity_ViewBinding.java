// Generated code from Butter Knife. Do not modify!
package com.vivalnk.sdk.demo.vital.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.vivalnk.sdk.demo.vital.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DeviceMenuActivity_ViewBinding implements Unbinder {
  private DeviceMenuActivity target;

  private View view7f09002e;

  private View view7f090045;

  private View view7f09002c;

  private View view7f090030;

  private View view7f09002f;

  private View view7f090038;

  private View view7f090037;

  private View view7f090039;

  private View view7f090036;

  private View view7f09002d;

  private View view7f090031;

  private View view7f090040;

  private View view7f090041;

  private View view7f09003f;

  private View view7f09003c;

  private View view7f09003d;

  private View view7f09003a;

  private View view7f090032;

  private View view7f09003e;

  private View view7f090033;

  private View view7f090035;

  @UiThread
  public DeviceMenuActivity_ViewBinding(DeviceMenuActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DeviceMenuActivity_ViewBinding(final DeviceMenuActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btnDetail, "field 'mBtnDetail' and method 'clickBtnDetail'");
    target.mBtnDetail = Utils.castView(view, R.id.btnDetail, "field 'mBtnDetail'", Button.class);
    view7f09002e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickBtnDetail();
      }
    });
    target.mTvStatus = Utils.findRequiredViewAsType(source, R.id.tvStatus, "field 'mTvStatus'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btnUploadFlash, "field 'btnUploadFlash' and method 'clickUploadFlash'");
    target.btnUploadFlash = Utils.castView(view, R.id.btnUploadFlash, "field 'btnUploadFlash'", Button.class);
    view7f090045 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickUploadFlash(Utils.castParam(p0, "doClick", 0, "clickUploadFlash", 0, Button.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.btnCancelUpload, "field 'btnCancelUpload' and method 'clickCancelUpload'");
    target.btnCancelUpload = Utils.castView(view, R.id.btnCancelUpload, "field 'btnCancelUpload'", Button.class);
    view7f09002c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickCancelUpload(Utils.castParam(p0, "doClick", 0, "clickCancelUpload", 0, Button.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.btnEngineerModule, "field 'btnEngineerModule' and method 'openEngineerModule'");
    target.btnEngineerModule = Utils.castView(view, R.id.btnEngineerModule, "field 'btnEngineerModule'", Button.class);
    view7f090030 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.openEngineerModule();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDisconnect, "method 'clickBtnDisconnect'");
    view7f09002f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickBtnDisconnect();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnReadPatchVersion, "method 'clickReadPatchVersion'");
    view7f090038 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickReadPatchVersion(Utils.castParam(p0, "doClick", 0, "clickReadPatchVersion", 0, Button.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.btnReadDeviceInfo, "method 'clickReadDeviceInfo'");
    view7f090037 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickReadDeviceInfo(Utils.castParam(p0, "doClick", 0, "clickReadDeviceInfo", 0, Button.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.btnReadSn, "method 'clickReadSN'");
    view7f090039 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickReadSN(Utils.castParam(p0, "doClick", 0, "clickReadSN", 0, Button.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.btnQueryFlash, "method 'clickQueryFlashCount'");
    view7f090036 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickQueryFlashCount(Utils.castParam(p0, "doClick", 0, "clickQueryFlashCount", 0, Button.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.btnCheckPatchStatus, "method 'clickCheckPatchStatus'");
    view7f09002d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickCheckPatchStatus(Utils.castParam(p0, "doClick", 0, "clickCheckPatchStatus", 0, Button.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.btnEraseFlash, "method 'clickEraseFlash'");
    view7f090031 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickEraseFlash(Utils.castParam(p0, "doClick", 0, "clickEraseFlash", 0, Button.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.btnStartSampling, "method 'clickStartSampling'");
    view7f090040 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickStartSampling(Utils.castParam(p0, "doClick", 0, "clickStartSampling", 0, Button.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.btnStopSampling, "method 'clickStopSampling'");
    view7f090041 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickStopSampling(Utils.castParam(p0, "doClick", 0, "clickStopSampling", 0, Button.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.btnShutDown, "method 'clickShutdown'");
    view7f09003f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickShutdown(Utils.castParam(p0, "doClick", 0, "clickShutdown", 0, Button.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.btnSelfTest, "method 'clickSelfTest'");
    view7f09003c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickSelfTest(Utils.castParam(p0, "doClick", 0, "clickSelfTest", 0, Button.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.btnSetPatchClock, "method 'clickSetPatchClock'");
    view7f09003d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickSetPatchClock(Utils.castParam(p0, "doClick", 0, "clickSetPatchClock", 0, Button.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.btnReadUserInfo, "method 'clickReadlUserInfo'");
    view7f09003a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickReadlUserInfo(Utils.castParam(p0, "doClick", 0, "clickReadlUserInfo", 0, Button.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.btnEraseUserInfo, "method 'clickEraseUserInfo'");
    view7f090032 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickEraseUserInfo(Utils.castParam(p0, "doClick", 0, "clickEraseUserInfo", 0, Button.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.btnSetUserInfo, "method 'clickSetUserInfo'");
    view7f09003e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickSetUserInfo(Utils.castParam(p0, "doClick", 0, "clickSetUserInfo", 0, Button.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.btnGraphics, "method 'clickBtnGraphics'");
    view7f090033 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickBtnGraphics();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnOTA, "method 'clickOTA'");
    view7f090035 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickOTA();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    DeviceMenuActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mBtnDetail = null;
    target.mTvStatus = null;
    target.btnUploadFlash = null;
    target.btnCancelUpload = null;
    target.btnEngineerModule = null;

    view7f09002e.setOnClickListener(null);
    view7f09002e = null;
    view7f090045.setOnClickListener(null);
    view7f090045 = null;
    view7f09002c.setOnClickListener(null);
    view7f09002c = null;
    view7f090030.setOnClickListener(null);
    view7f090030 = null;
    view7f09002f.setOnClickListener(null);
    view7f09002f = null;
    view7f090038.setOnClickListener(null);
    view7f090038 = null;
    view7f090037.setOnClickListener(null);
    view7f090037 = null;
    view7f090039.setOnClickListener(null);
    view7f090039 = null;
    view7f090036.setOnClickListener(null);
    view7f090036 = null;
    view7f09002d.setOnClickListener(null);
    view7f09002d = null;
    view7f090031.setOnClickListener(null);
    view7f090031 = null;
    view7f090040.setOnClickListener(null);
    view7f090040 = null;
    view7f090041.setOnClickListener(null);
    view7f090041 = null;
    view7f09003f.setOnClickListener(null);
    view7f09003f = null;
    view7f09003c.setOnClickListener(null);
    view7f09003c = null;
    view7f09003d.setOnClickListener(null);
    view7f09003d = null;
    view7f09003a.setOnClickListener(null);
    view7f09003a = null;
    view7f090032.setOnClickListener(null);
    view7f090032 = null;
    view7f09003e.setOnClickListener(null);
    view7f09003e = null;
    view7f090033.setOnClickListener(null);
    view7f090033 = null;
    view7f090035.setOnClickListener(null);
    view7f090035 = null;
  }
}
