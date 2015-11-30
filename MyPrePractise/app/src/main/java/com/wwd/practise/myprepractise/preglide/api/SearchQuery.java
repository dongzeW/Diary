package com.wwd.practise.myprepractise.preglide.api;

import android.os.Parcel;

/**
 * 创建者: wwd
 * 创建日期:15/11/5
 * 类的功能描述:
 */
public class SearchQuery implements Query {
  private String queryString;
  public static final Creator<SearchQuery> CREATOR = new Creator<SearchQuery>() {
    @Override public SearchQuery createFromParcel(Parcel source) {
      return new SearchQuery(source);
    }

    @Override public SearchQuery[] newArray(int size) {
      return new SearchQuery[size];
    }
  };

  public SearchQuery(String queryString) {
    this.queryString = queryString;
  }

  public SearchQuery(Parcel in) {
    this.queryString = in.readString();
  }

  @Override public String getDescription() {
    return queryString;
  }

  @Override public String getUrl() {
    return Api.getSearchUrl(queryString);
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(queryString);
  }
}
