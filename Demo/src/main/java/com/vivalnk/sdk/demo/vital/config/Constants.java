package com.vivalnk.sdk.demo.vital.config;

import android.os.Environment;
import com.vivalnk.sdk.demo.vital.BuildConfig;

/**
 * Created by JakeMo on 18-8-1.
 */
public class Constants {

  public static final String ROOT_DIR =
      Environment.getExternalStorageDirectory().getPath() + "/PL_DATA/finaltest";
  public static final String OTAPath = ROOT_DIR + "/OTA";
  public static final String fwPath = OTAPath + "/FW";
  public static final String blPath = OTAPath + "/BL";
  public static final String PREF_NAME = "vivalnk_finaltest_sp";

  public static final String FG_Operation_Name = "VS_PL_FG_Operation.txt";
  public static final String FG_Operation = ROOT_DIR + "/" + FG_Operation_Name;
  public static final String FG_ECG_Name = "VS_PL_FG_ECG.txt";
  public static final String FG_ECG = ROOT_DIR + "/" + FG_ECG_Name;
  public static final String FG_ACC_Name = "VS_PL_FG_ACC.txt";
  public static final String FG_ACC = ROOT_DIR + "/" + FG_ACC_Name;

  public static final String BACKUP_FOLDER = ROOT_DIR + "/Backup";

  public static final String GoldenECGFolder = ROOT_DIR + "/" + "GoldenECG";
  public static final String ECGFolder = ROOT_DIR + "/" + "TestECG";

  public static final int startSamplingStableTime = 6 * 1000;


  /**
   * 需要从心率精度测试缓存的数据键值： KEY_bHR_(value).txt
   */
  public static final String KEY_bHR = "KEY_bHR_";

  public static final String KEY_suffix_DeviceInfo = "_DeviceInfo";

  private static final int APP_TYPE_VV200 = 1;//200平台
  private static final int APP_TYPE_VV310 = 2;//310平台
  private static final int APP_TYPE_VV330 = 3;//330平台

  public static boolean isSupport200() {
    return BuildConfig.APPTYPES.contains(APP_TYPE_VV200);
  }

  public static boolean isSupport310() {
    return BuildConfig.APPTYPES.contains(APP_TYPE_VV310);
  }

  public static boolean isSupport330() {
    return BuildConfig.APPTYPES.contains(APP_TYPE_VV330);
  }

  private static final String TAG_LOG_PATCH = "patch";
}