package com.wwd.practise.myprepractise.Utils;

import com.orhanobut.logger.Logger;
import com.wwd.practise.myprepractise.BuildConfig;

/**
 * 创建者: wwd
 * 创建日期:15/11/5
 * 类的功能描述:
 */
public class LogUtils {

  public static void i(String msg) {
    if (BuildConfig.LOG_DEBUG) {
      Logger.i(msg);
    }
  }

  public static void w(String msg) {
    if (BuildConfig.LOG_DEBUG) {
      Logger.w(msg);
    }
  }

  public static void e(String msg) {
    if (BuildConfig.LOG_DEBUG) {
      Logger.e(msg);
    }
  }
}
