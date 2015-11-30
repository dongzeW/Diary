package com.wwd.practise.myprepractise.preglide;

import com.wwd.practise.myprepractise.preglide.api.Photo;
import java.util.List;

/**
 * 创建者: wwd
 * 创建日期:15/11/6
 * 类的功能描述:
 */
//dw 删除当前单词
public interface PhotoViewer {
  public void onPhotesUpdated(List<Photo> photos);
}
