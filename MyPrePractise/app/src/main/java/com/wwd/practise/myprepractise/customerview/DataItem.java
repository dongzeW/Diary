package com.wwd.practise.myprepractise.customerview;

import android.graphics.drawable.Drawable;

/**
 * 创建者: wwd
 * 创建日期:15/10/20
 * 类的功能描述:
 */
public class DataItem {
  private String label;//text in show
  private Drawable drawable;//img in show
  private int navigationInfo;//It is can click

  public DataItem(String label, Drawable drawable, int navigationInfo) {
    this.label = label;
    this.drawable = drawable;
    this.navigationInfo = navigationInfo;
  }

  public String getLabel() {
    return label;
  }

  public Drawable getDrawable() {
    return drawable;
  }

  public int getNavigationInfo() {
    return navigationInfo;
  }
}
