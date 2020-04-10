package com.vivalnk.sdk.app.base.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.vivalnk.sdk.app.base.R;
import com.vivalnk.sdk.common.excutors.LooperPool;
import java.util.ArrayList;
import java.util.List;

public class LogListDialogView {

  public static final int MAX_SIZE = 10000;

  private AlertDialog mDataLogDialog;
  private List<String> mDataLogList;
  private ListView mDataLogListView;
  private ArrayAdapter<String> mDataLogAdapter;

  private Handler mUiHandler;

  public LogListDialogView() {
    mUiHandler = new Handler(LooperPool.getMainLooper());
  }

  public void create(Activity activity, List<String> list) {
    this.mDataLogList = list;
    mDataLogAdapter = new ArrayAdapter<>(activity,
        android.R.layout.simple_list_item_1, mDataLogList);
    mDataLogListView = new ListView(activity);
    mDataLogListView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    mDataLogListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    mDataLogListView.setAdapter(mDataLogAdapter);
    mDataLogListView.setDivider(new ColorDrawable(Color.MAGENTA));
    mDataLogListView.setDividerHeight(1);

    mDataLogDialog = new AlertDialog.Builder(activity)
        .setView(mDataLogListView)

        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        })
        .setNegativeButton(R.string.clear, new DialogInterface.OnClickListener() {

          @Override
          public void onClick(DialogInterface dialog, int which) {
            mDataLogList.clear();
            mDataLogAdapter.notifyDataSetChanged();
          }
        }).create();
    mDataLogListView.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            mDataLogListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
            break;
          case MotionEvent.ACTION_UP:
            mDataLogListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
            break;
        }
        return false;
      }
    });

    mDataLogDialog.setCanceledOnTouchOutside(false);
  }

  public void create(Activity activity) {
    create(activity, new ArrayList<>());
  }

  public void updateLog(String msg) {
    mUiHandler.post(new Runnable() {
      @Override
      public void run() {
        mDataLogList.add(msg);
        mDataLogAdapter.notifyDataSetChanged();
      }
    });
  }

  public void notifyDataSetChangedDelay(long timeMillis) {
    mUiHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        mDataLogAdapter.notifyDataSetChanged();
      }
    }, timeMillis);
  }

  public void show() {
    mUiHandler.post(new Runnable() {
      @Override
      public void run() {
        if (!mDataLogDialog.isShowing()) {
          mDataLogDialog.show();
        }
      }
    });
  }

  public void dismiss() {
    mUiHandler.post(new Runnable() {
      @Override
      public void run() {
        if (mDataLogDialog.isShowing()) {
          mDataLogDialog.dismiss();
        }
      }
    });
  }

}
