package com.wwd.practise.myprepractise.comment;

import android.app.Application;
import android.content.Context;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * 创建者: wwd
 * 创建日期:15/10/20
 * 类的功能描述:
 */
public class MyApplication extends Application {
  //移动到底部L
  //移动屏幕中间M
  //移动光标到屏幕顶端H
  //移动到行尾$
  //移动行首0（数字）

  private RefWatcher mRefWatcher;

  @Override public void onCreate() {
    super.onCreate();
    //init
    mRefWatcher = LeakCanary.install(this);
    Stetho.initializeWithDefaults(this);
  }

  public static RefWatcher getRefWatcher(Context context) {
    MyApplication application = (MyApplication) context.getApplicationContext();
    return application.mRefWatcher;
  }
}
