package com.vivalnk.sdk.app.base.app;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import com.vivalnk.sdk.app.base.R;
import com.vivalnk.sdk.app.base.custom.IconfontTextView;
import com.vivalnk.sdk.app.base.mvp.IPresenter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by JakeMo on 18-4-28.
 */
public abstract class BaseToolbarActivity<P extends IPresenter> extends BaseActivity<P> {

  protected Toolbar toolbar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);
    initToolbar();
  }

  private void initToolbar() {
    toolbar = findViewById(R.id.toolbar);
    if (toolbar != null) {
      setSupportActionBar(toolbar);
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
      }
    }
  }

}
