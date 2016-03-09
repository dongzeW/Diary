package com.wwd.practise.myprepractise.Http.ResultResponse;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wwd.practise.myprepractise.Http.ResponseSuccessListener;

/**
 * 创建者: wwd
 * 创建日期:15/12/31
 * 类的功能描述:
 */
public class BaseResult
    implements Response.Listener<ResponseSuccessListener>, Response.ErrorListener {

  @Override public void onResponse(ResponseSuccessListener response) {
    response.success();
    response.fail();
  }

  @Override public void onErrorResponse(VolleyError error) {

  }
}
