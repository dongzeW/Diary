package com.wwd.practise.myprepractise.preglide.api;

import android.os.Parcel;

/**
 * 创建者: wwd
 * 创建日期:15/11/5
 * 类的功能描述:
 */
public class RecentQuery implements Query {

  public static final Creator<RecentQuery> CREATOR = new Creator<RecentQuery>() {
    @Override public RecentQuery createFromParcel(Parcel source) {
      return RECENT_QUERY;
    }

    @Override public RecentQuery[] newArray(int size) {
      return new RecentQuery[size];
    }
  };
  private static final RecentQuery RECENT_QUERY = new RecentQuery();

  public static RecentQuery get() {
    return RECENT_QUERY;
  }

  private RecentQuery() {

  }

  @Override public String getDescription() {
    return "Recent";
  }

  @Override public String getUrl() {
    return Api.getRecentUrl();
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {

  }
}
