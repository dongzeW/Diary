package com.wwd.practise.myprepractise;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.test.InstrumentationTestCase;

/**
 * 创建者: wwd
 * 创建日期:16/1/4
 * 类的功能描述:
 */
public class MedeData extends InstrumentationTestCase {
  ApplicationInfo applicationInfo;
  ActivityInfo activityInfo;
  Context context;

  @Override protected void setUp() throws Exception {
    super.setUp();
    context = getInstrumentation().getContext();
    //activityInfo = context.getPackageManager().getActivityInfo(new ComponentName(context.getPackageName()), 0);
  }
}
