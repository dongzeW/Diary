package com.wwd.practise.myprepractise.preglide.api;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import java.util.Collection;
import java.util.List;

/**
 * 创建者: wwd
 * 创建日期:15/11/4
 * 类的功能描述:
 */
public class FlickrQueryResponseListener
    implements Response.Listener<String>, Response.ErrorListener {
  private PhotoJsonStringParser parser;
  private Query query;
  private Collection<Api.QueryListener> listeners;

  public FlickrQueryResponseListener(PhotoJsonStringParser parser, Query query,
      Collection<Api.QueryListener> listeners) {
    this.parser = parser;
    this.query = query;
    this.listeners = listeners;
  }

  @Override public void onErrorResponse(VolleyError error) {

  }

  @Override public void onResponse(String response) {
  }

  private void notifySuccess(List<Photo> results) {
    for (Api.QueryListener listener : listeners) {
      listener.onSearchCompleted(query, results);
    }
  }

  private void notifyFailed(Exception e) {
    for (Api.QueryListener listener : listeners) {
      listener.onSearchFailed(query, e);
    }
  }
}

