package com.wwd.practise.myprepractise.Http;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.Map;

/**
 * 创建者: wwd
 * 创建日期:15/12/30
 * 类的功能描述:
 */
public class HStringRequest extends StringRequest {

  private Map<String, String> bodyParams;
  private Map<String, String> headersParams;

  public HStringRequest(int method, String url, Response.Listener<String> listener,
      Response.ErrorListener errorListener) {
    super(method, url, listener, errorListener);
  }

  public HStringRequest(String url, Response.Listener<String> listener,
      Response.ErrorListener errorListener) {
    super(url, listener, errorListener);
  }

  /**
   * 封装body数据
   *
   * @throws AuthFailureError
   */
  @Override protected Map<String, String> getParams() throws AuthFailureError {
    return this.bodyParams;
  }

  protected void setParams(Map<String, String> map) {
    this.bodyParams = map;
  }

  /**
   * 封装headers数据
   */
  @Override public Map<String, String> getHeaders() throws AuthFailureError {
    return this.headersParams;
  }

  protected void setHeadersParams(Map<String, String> map) {
    this.headersParams = map;
  }


  class HResponse implements Response.Listener<String> {

    @Override public void onResponse(String response) {

    }
  }
}
