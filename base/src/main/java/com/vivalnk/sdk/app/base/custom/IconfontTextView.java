package com.vivalnk.sdk.app.base.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.vivalnk.sdk.app.base.R;

public class IconfontTextView extends AppCompatTextView {


  private Typeface sIconfont;

  private String customTtf;

  public IconfontTextView(Context context) {
    this(context, null, 0);

  }

  public IconfontTextView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public IconfontTextView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    if (attrs != null) {
      TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.iconfont);
      customTtf = a.getString(R.styleable.iconfont_ttf);
      if (TextUtils.isEmpty(customTtf)) {
        customTtf = "iconfont.ttf";
      }
      a.recycle();
    } else {
      customTtf = "iconfont.ttf";
    }
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    try {
      sIconfont = Typeface.createFromAsset(getContext().getAssets(), customTtf);
    } catch (Exception e) {
      e.printStackTrace();
    }

    this.setTypeface(sIconfont);
    this.setIncludeFontPadding(false);
  }

  @Override
  protected void onDetachedFromWindow() {
    this.setTypeface(null);
    sIconfont = null;
    super.onDetachedFromWindow();
  }

  @Override
  public Typeface getTypeface() {
    if (null == sIconfont) {
      try {
        sIconfont = Typeface.createFromAsset(getContext().getAssets(), customTtf);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return sIconfont;
  }

}


