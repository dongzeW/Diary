package com.wwd.practise.myprepractise.preglide.api;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 创建者: wwd
 * 创建日期:15/11/4
 * 类的功能描述:
 */
public class Photo implements Parcelable {
  private String id;
  private String owner;
  private String title;
  private String server;
  private String farm;
  private String secret;
  private String partialUrl = null;

  @Override public int describeContents() {
    return 0;
  }

  public String getId() {
    return id;
  }

  public String getOwner() {
    return owner;
  }

  public String getTitle() {
    return title;
  }

  public String getServer() {
    return server;
  }

  public String getFarm() {
    return farm;
  }

  public String getSecret() {
    return secret;
  }

  public String getPartialUrl() {
    return partialUrl;
  }

  public void setPartialUrl(String partialUrl) {
    this.partialUrl = partialUrl;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeString(this.owner);
    dest.writeString(this.title);
    dest.writeString(this.server);
    dest.writeString(this.farm);
    dest.writeString(this.secret);
    dest.writeString(this.partialUrl);
  }

  public Photo() {
  }

  public Photo(JSONObject jsonPhoto) throws JSONException {
    this.id = jsonPhoto.getString("id");
    this.owner = jsonPhoto.getString("owner");
    this.title = jsonPhoto.getString("title");
    this.server = jsonPhoto.getString("server");
    this.farm = jsonPhoto.getString("farm");
    this.secret = jsonPhoto.getString("secret");
  }

  protected Photo(Parcel in) {
    this.id = in.readString();
    this.owner = in.readString();
    this.title = in.readString();
    this.server = in.readString();
    this.farm = in.readString();
    this.secret = in.readString();
    this.partialUrl = in.readString();
  }

  public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
    public Photo createFromParcel(Parcel source) {
      return new Photo(source);
    }

    public Photo[] newArray(int size) {
      return new Photo[size];
    }
  };
}
