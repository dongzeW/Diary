package com.wwd.practise.myprepractise.preglide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.wwd.practise.myprepractise.preglide.api.Photo;
import java.util.List;

/**
 * 创建者: wwd
 * 创建日期:15/11/10
 * 类的功能描述:
 */
public class FlickrPhotoGrid extends Fragment implements PhotoViewer {
  private static final String IMAGE_SIZE_KEY = "image_size";
  private static final String PRELOAD_KEY = "preload";
  private static final String THUMBNAIL_KEY = "thumbnail";

  public static FlickrPhotoGrid newInstance(int size, int preloadCount, boolean thumbnail) {
    FlickrPhotoGrid photoGrid = new FlickrPhotoGrid();
    Bundle args = new Bundle();
    args.putInt(IMAGE_SIZE_KEY, size);
    args.putInt(PRELOAD_KEY, preloadCount);
    args.putBoolean(THUMBNAIL_KEY, thumbnail);
    photoGrid.setArguments(args);
    return photoGrid;
  }

  @Override public void onPhotesUpdated(List<Photo> photos) {

  }
}
