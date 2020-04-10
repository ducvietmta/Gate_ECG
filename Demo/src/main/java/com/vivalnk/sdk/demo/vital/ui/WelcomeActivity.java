package com.vivalnk.sdk.demo.vital.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.vivalnk.sdk.VitalClient;
import com.vivalnk.sdk.app.base.app.BaseToolbarActivity;
import com.vivalnk.sdk.app.base.app.Layout;
import com.vivalnk.sdk.app.repository.device.DeviceManager;
import com.vivalnk.sdk.demo.vital.R;
import com.vivalnk.sdk.demo.vital.base.DemoApplication;
import io.reactivex.disposables.Disposable;

/**
 * 开始界面
 *
 * @author Aslan
 * @date 2019/3/14
 */
public class WelcomeActivity extends BaseToolbarActivity {

  private RxPermissions rxPermissions;
  private Disposable permissionDisposable;

  @Override
  protected Layout getLayout() {
    return Layout.createLayoutByID(R.layout.activity_welcome);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    checkPermission();
  }

  //request location and write permissions at rum time
  private void checkPermission() {
    rxPermissions = new RxPermissions(this);
    permissionDisposable = rxPermissions.request(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION)
        .subscribe(granted -> {
          if (granted) {
            DeviceManager.getInstance().init(this);
            DemoApplication.initBugly(this.getApplicationContext());

            //if (BuildConfig.DEBUG) {
            VitalClient.getInstance().openLog();
            VitalClient.getInstance().alloWriteToFile(true);
            //}

            startActivity(ScanningActivity.newIntent(this));
            finish();
          } else {
            showToast("You must grant the permissions!");
            finish();
          }
        });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (!permissionDisposable.isDisposed()) {
      permissionDisposable.dispose();
    }

  }
}
