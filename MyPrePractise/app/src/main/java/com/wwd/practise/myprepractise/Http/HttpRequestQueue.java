package com.wwd.practise.myprepractise.Http;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

/**
 * 创建者: wwd
 * 创建日期:15/12/30
 * 类的功能描述:
 */
public class HttpRequestQueue {
  private static HttpRequestQueue mHttpRequestQueue;
  private RequestQueue mRequestQueue;

  private HttpRequestQueue() {

  }

  public static HttpRequestQueue getInstance() {
    if (mHttpRequestQueue == null) {
      synchronized (HttpRequestQueue.class) {
        if (mHttpRequestQueue == null) {
          mHttpRequestQueue = new HttpRequestQueue();
        }
      }
    }
    return mHttpRequestQueue;
  }

  public void createHttpQueue(Context context) {
    if (mRequestQueue == null) {
      //使用HttpUrlConnection
      mRequestQueue = Volley.newRequestQueue(context, new HurlStack());
    }
  }

  public RequestQueue getQueue(Context context) {
    createHttpQueue(context.getApplicationContext());
    return mRequestQueue;
  }

  public void cancel(final Object obj) {
    this.mRequestQueue.cancelAll(obj);
  }
}
