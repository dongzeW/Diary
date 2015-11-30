package com.wwd.practise.myprepractise.preglide.api;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 创建者: wwd
 * 创建日期:15/11/4
 * 类的功能描述:
 */
public class PhotoJsonStringParser {
  private static final int FLICKR_API_PREFIX_LENGTH = 14;

  List<Photo> parse(String response) throws JSONException {
    JSONObject searchResults =
        new JSONObject(response.substring(FLICKR_API_PREFIX_LENGTH, response.length() - 1));
    JSONArray photos = searchResults.getJSONObject("photos").getJSONArray("photo");
    List<Photo> results = new ArrayList<>(photos.length());
    for (int i = 0; i < photos.length(); i++) {
      results.add(new Photo(photos.getJSONObject(i)));
    }
    return results;
  }
}
