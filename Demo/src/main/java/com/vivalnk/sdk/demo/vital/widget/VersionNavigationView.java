package com.vivalnk.sdk.demo.vital.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.design.R.attr;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;
import com.vivalnk.sdk.VitalClient;
import com.vivalnk.sdk.common.utils.DensityUtils;
import com.vivalnk.sdk.demo.vital.BuildConfig;
import com.vivalnk.sdk.demo.vital.R;

public class VersionNavigationView extends NavigationView {

  protected Paint markTextPaint;
  protected int markTextColor;

  private String sdkVersion;
  private String demoVersion;

  private int width;
  private int height;

  public VersionNavigationView(Context context) {
    this(context, (AttributeSet) null);
  }

  public VersionNavigationView(Context context, AttributeSet attrs) {
    this(context, attrs, attr.navigationViewStyle);
  }

  public VersionNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    markTextColor = getContext().getResources().getColor(R.color.black);
    //text paint
    markTextPaint = new Paint();
    markTextPaint.setColor(markTextColor);
    markTextPaint.setTextSize(DensityUtils.dip2px(getContext(), 12F));

    demoVersion = BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ")";
    sdkVersion = VitalClient.getInstance().getVersion() + "(" + VitalClient.getInstance().getVersionCode() + ")";
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    width = w;
    height = h;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    drawMarkText(canvas);
  }

  private void drawMarkText(Canvas canvas) {
    Paint.FontMetrics fm = markTextPaint.getFontMetrics();
    float textHeight = Math.abs(fm.leading + fm.ascent);
    canvas
        .drawText("SDK: v" + sdkVersion, 20, height - textHeight - textHeight - 10, markTextPaint);
    canvas.drawText("Demo: v" + demoVersion, 20, height - textHeight, markTextPaint);
  }

  public void setDemoVersion(String demoVersion) {
    this.demoVersion = demoVersion;
    postInvalidate();
  }

  public void setSDKVersion(String sdkVersion) {
    this.sdkVersion = sdkVersion;
    postInvalidate();
  }
}
