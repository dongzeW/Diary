package com.wwd.practise.myprepractise.preglide;

import android.support.v4.app.Fragment;
import com.wwd.practise.myprepractise.preglide.api.Photo;
import java.util.List;

/**
 * 创建者: wwd
 * 创建日期:15/11/10
 * 类的功能描述:
 */
public class FlickrPhotoList extends Fragment implements PhotoViewer {
  @Override public void onPhotesUpdated(List<Photo> photos) {

  }

  public static FlickrPhotoList newInstance() {
    return new FlickrPhotoList();
  }
}
