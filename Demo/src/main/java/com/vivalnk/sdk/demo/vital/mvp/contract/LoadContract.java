package com.vivalnk.sdk.demo.vital.mvp.contract;

import com.vivalnk.sdk.app.base.mvp.IModel;

/**
 * Created by JakeMo on 18-7-25.
 */
public class LoadContract {

  public interface Model extends IModel {

  }

  public interface View extends TestContract.BaseTestView {
    void showStartDialog(int index, int hr);
    void update(long time, int[] ecg);
    void stopUpdate();
    void showLoadSuccess();
  }

}
