package com.vivalnk.sdk.demo.vital.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.vivalnk.sdk.app.base.app.Layout;
import com.vivalnk.sdk.app.base.widget.LiveEcgScreen;
import com.vivalnk.sdk.app.base.widget.RTSEcgView;
import com.vivalnk.sdk.common.eventbus.Subscribe;
import com.vivalnk.sdk.demo.vital.R;
import com.vivalnk.sdk.model.SampleData;
import com.vivalnk.sdk.repository.local.database.VitalData;

public class EcgFragment extends ConnectedFragment {

  @BindView(R.id.ecgView)
  RTSEcgView ecgView;

  @BindView(R.id.btnSwitchGain)
  Button btnSwitchGain;

  @BindView(R.id.btnRevert)
  Button btnRevert;

  @BindView(R.id.btnNoisy)
  Button btnNoisy;

  @BindView(R.id.tvHR)
  TextView tvHR;

  @BindView(R.id.tvRR)
  TextView tvRR;

  LiveEcgScreen mLiveEcgScreen;

  private volatile boolean denoisy;
  private volatile boolean revert;

  @Override
  protected Layout getLayout() {
    return Layout.createLayoutByID(R.layout.fragment_ecg_graphic);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView();
  }

  private void initView() {
    mLiveEcgScreen = new LiveEcgScreen(getContext(), mDevice, ecgView);
    mLiveEcgScreen.setDrawDirection(RTSEcgView.LEFT_IN_RIGHT_OUT);
    mLiveEcgScreen.showMarkPoint(true);

    btnNoisy.setText(denoisy ? R.string.tv_denoising_close : R.string.tv_denoising_open);
    btnRevert.setText(revert ? R.string.tv_de_revert : R.string.tv_revert);
  }

  @Subscribe
  public void onEcgDataEvent(SampleData ecgData) {
    if (!ecgData.getDeviceID().equals(mDevice.getId())) {
      return;
    }
    if (!ecgData.isFlash()) {
      mLiveEcgScreen.update(ecgData);

      if (ecgData.getHR() != null && ecgData.getHR() > 0) {
        tvHR.setText("HR: " + ecgData.getHR());
      }

      if (ecgData.getRR() != null && ecgData.getRR() > 0) {
        tvRR.setText("RR: " + ecgData.getRR());
      }
    }
  }

  @OnClick(R.id.btnSwitchGain)
  protected void clickSwitchGain() {
    mLiveEcgScreen.switchGain();
  }

  @OnClick(R.id.btnRevert)
  protected void clickRevert() {
    revert = !revert;
    mLiveEcgScreen.revert(revert);
    btnRevert.setText(revert ? R.string.tv_de_revert : R.string.tv_revert);
  }

  @OnClick(R.id.btnNoisy)
  protected void clickDenoisy() {
    denoisy = !denoisy;
    mLiveEcgScreen.denoisy(denoisy);
    btnNoisy.setText(denoisy ? R.string.tv_denoising_close : R.string.tv_denoising_open);
  }

  @Override
  public void onDestroyView() {
    mLiveEcgScreen.destroy();
    super.onDestroyView();
  }
}
