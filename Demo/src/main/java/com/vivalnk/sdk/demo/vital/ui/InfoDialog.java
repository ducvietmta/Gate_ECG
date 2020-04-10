package com.vivalnk.sdk.demo.vital.ui;

import static com.vivalnk.sdk.dataparser.battery.Battery.VL_1;
import static com.vivalnk.sdk.dataparser.battery.Battery.VL_2;
import static com.vivalnk.sdk.dataparser.battery.Battery.VL_3;
import static com.vivalnk.sdk.dataparser.battery.Battery.VL_4;
import static com.vivalnk.sdk.dataparser.battery.Battery.VL_5;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.vivalnk.sdk.demo.vital.R;
import com.vivalnk.sdk.model.BatteryInfo;
import com.vivalnk.sdk.model.BatteryInfo.ChargeStatus;
import com.vivalnk.sdk.model.PatchStatusInfo;

/**
 * 电量信息显示
 *
 * @author Aslan
 * @date 2019/3/26
 */
public class InfoDialog extends DialogFragment {

  public static final String TAG = "InfoDialog";

  private static final String TAG_PATCH = "patchInfo";
  private static final String TAG_BATTERY = "batteryInfo";
  private PatchStatusInfo patchStatusInfo;
  private BatteryInfo batteryInfo;

  //设备信息
  @BindView(R.id.ll1)
  public LinearLayout ll1;

  @BindView(R.id.tvSampling)
  public TextView tvSampling;

  @BindView(R.id.tvLeanOn)
  public TextView tvLeanOn;

  @BindView(R.id.tvSampleFrequency)
  public TextView tvSampleFrequency;

  @BindView(R.id.tvBaseLine)
  public TextView tvBaseLine;

  @BindView(R.id.tvRTS)
  public TextView tvRTS;

  @BindView(R.id.tvMode)
  public TextView tvMode;

  @BindView(R.id.tvFlashSave)
  public TextView tvFlashSave;

  //电池信息
  @BindView(R.id.ll2)
  public LinearLayout ll2;

  @BindView(R.id.tvStatus)
  public TextView tvStatus;

  @BindView(R.id.tvLevel)
  public TextView tvLevel;

  @BindView(R.id.tvCanOta)
  public TextView tvCanOta;

  @BindView(R.id.tvCanSampling)
  public TextView tvCanSampling;

  @BindView(R.id.tvCanBleTransmission)
  public TextView tvCanBleTransmission;

  @BindView(R.id.tvRawVoltage)
  public TextView tvRawVoltage;

  @BindView(R.id.tvVoltage)
  public TextView tvVoltage;

  @BindView(R.id.tvPercent)
  public TextView tvPercent;

  @BindView(R.id.tvTemperature)
  public TextView tvTemperature;

  @BindView(R.id.btOk)
  public Button btOk;

  public static InfoDialog newInstance(PatchStatusInfo patchStatusInfo) {
    Bundle args = new Bundle();
    args.putSerializable(TAG_PATCH, patchStatusInfo);
    InfoDialog fragment = new InfoDialog();
    fragment.setArguments(args);
    return fragment;
  }

  public static InfoDialog newInstance(BatteryInfo batteryInfo) {
    Bundle args = new Bundle();
    args.putSerializable(TAG_BATTERY, batteryInfo);
    InfoDialog fragment = new InfoDialog();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle args = getArguments();
    assert (args != null);

    patchStatusInfo = (PatchStatusInfo) args.getSerializable(TAG_PATCH);
    if (patchStatusInfo == null) {
      batteryInfo = (BatteryInfo) args.getSerializable(TAG_BATTERY);
    } else {
      batteryInfo = patchStatusInfo.batteryInfo;
    }
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.content_battery_info, null);
    ButterKnife.bind(this, view);

    iniData();
    AlertDialog.Builder builder = new Builder(getContext())
        .setTitle(R.string.info)
        .setView(view);
    return builder.create();
  }

  private String formatBatteryStatus(Context context, ChargeStatus status) {
    if (status == ChargeStatus.INCHARGING_NOT_COMPLETE) {
      return context.getString(R.string.battery_info_status_incharging_not_complete);
    } else if (status == ChargeStatus.INCHARGING_COMPLETE) {
      return context.getString(R.string.battery_info_status_incharging_complete);
    } else if (status == ChargeStatus.NOT_INCHARGING) {
      return context.getString(R.string.battery_info_status_not_incharging);
    } else {
      return context.getString(R.string.battery_info_status_unknow);
    }
  }

  private String forLevel(int level) {
    String ret = "";
    switch (level) {
      case VL_1:
        ret = "V3≤V";
        break;
      case VL_2:
        ret = "V2≤V<V3";
        break;
      case VL_3:
        ret = "V1≤V<V2";
        break;
      case VL_4:
        ret = "V0≤V<V1";
        break;
      case VL_5:
        ret = "V≤V0";
        break;
      default:
        break;
    }
    return ret;
  }

  private void iniData() {
    if (patchStatusInfo != null) {
      ll1.setVisibility(View.VISIBLE);
      tvSampling.setText(getString(R.string.patch_info_sampling,
          patchStatusInfo.sampling ? getString(R.string.yes) : getString(R.string.no)));
      tvLeanOn.setText(getString(R.string.patch_info_leadOn,
          patchStatusInfo.leadOn ? getString(R.string.yes) : getString(R.string.no)));

      //vv310: sample mode
      if (patchStatusInfo.baseLineAlgoOpen != null) {
        tvBaseLine.setVisibility(View.VISIBLE);
        tvBaseLine.setText(getString(R.string.patch_info_baselinealgoopen, patchStatusInfo.baseLineAlgoOpen == null ? null
            : (patchStatusInfo.baseLineAlgoOpen ? getString(R.string.yes) : getString(R.string.no))));
      } else {
        tvBaseLine.setVisibility(View.GONE);
      }

      //vv330: sample frequency
      if (patchStatusInfo.sampleFrequency != null) {
        tvSampleFrequency.setVisibility(View.VISIBLE);
        tvSampleFrequency.setText(getString(R.string.patch_info_sampleFrequency, patchStatusInfo.sampleFrequency));
      } else {
        tvSampleFrequency.setVisibility(View.GONE);
      }

      //vv330: RTS data switcher
      if (patchStatusInfo.RTSDataOpen != null) {
        tvRTS.setVisibility(View.VISIBLE);
        tvRTS.setText(getString(R.string.patch_info_rtsdataopen, (patchStatusInfo.RTSDataOpen ? getString(R.string.yes) : getString(R.string.no))));
      } else {
        tvRTS.setVisibility(View.GONE);
      }

      //vv310: sample mode
      if (patchStatusInfo.mode != null) {
        tvMode.setVisibility(View.VISIBLE);
        tvMode.setText(getString(R.string.patch_info_mode, patchStatusInfo.mode));
      } else {
        tvMode.setVisibility(View.GONE);
      }

      //temperature
      if (patchStatusInfo.RTSFlashSave != null) {
        tvFlashSave.setVisibility(View.VISIBLE);
        tvFlashSave.setText(getString(R.string.patch_info_flash_save, (patchStatusInfo.RTSFlashSave ? getString(R.string.yes) : getString(R.string.no))));
      } else {
        tvFlashSave.setVisibility(View.GONE);
      }

    } else {
      ll1.setVisibility(View.GONE);
    }

    tvStatus.setText(getString(R.string.battery_info_status,
        formatBatteryStatus(getContext(), batteryInfo.getStatus())));
    tvLevel.setText(getString(R.string.battery_info_level, forLevel(batteryInfo.getLevel())));
    tvCanOta
        .setText(getString(R.string.battery_info_can_ota,
            batteryInfo.canOTA() ? getString(R.string.yes) : getString(R.string.no)));
    tvCanSampling.setText(
        getString(R.string.battery_info_can_sampling,
            batteryInfo.canSampling() ? getString(R.string.yes) : getString(R.string.no)));
    tvCanBleTransmission.setText(
        getString(R.string.battery_info_can_ble_transmission,
            batteryInfo.canBleTransmission() ? getString(R.string.yes) : getString(R.string.no)));
    tvRawVoltage.setText(
        getString(R.string.battery_info_raw_voltage, String.valueOf(batteryInfo.getVoltage())));
    tvVoltage.setText(
        getString(R.string.battery_info_voltage, String.valueOf(batteryInfo.getVoltage())));
    tvPercent.setText(
        getString(R.string.battery_info_percent, String.valueOf(batteryInfo.getPercent())));
    if (batteryInfo.getTemperature() == null) {
      tvTemperature.setVisibility(View.GONE);
    } else {
      tvTemperature.setText(
          getString(R.string.battery_info_temperature, String.valueOf(batteryInfo.getTemperature())));
    }
  }

  private void setPatchInfoText(TextView tv, Object obj, String text) {
    if (obj != null) {
      tv.setVisibility(View.VISIBLE);
      tv.setText(text);
    } else {
      tv.setVisibility(View.GONE);
    }
  }

  @OnClick(R.id.btOk)
  public void onOKClick() {
    this.dismiss();
  }
}
