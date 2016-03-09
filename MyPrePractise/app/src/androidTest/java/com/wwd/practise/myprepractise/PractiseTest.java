package com.wwd.practise.myprepractise;

import android.content.Context;
import android.test.InstrumentationTestCase;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wwd.practise.myprepractise.Http.HStringRequest;
import com.wwd.practise.myprepractise.Http.HttpRequestQueue;
import com.wwd.practise.myprepractise.customerview.ColorGeneratorCustomer;
import com.wwd.practise.myprepractise.preglide.api.Api;

/**
 * 创建者: wwd
 * 创建日期:15/10/19
 * 类的功能描述:测试工具类
 */
public class PractiseTest extends InstrumentationTestCase
    implements Response.Listener, Response.ErrorListener {
  private ColorGeneratorCustomer mColorGeneratorCustoemr;
  private Api api;
  private Context context;

  @Override protected void setUp() throws Exception {
    super.setUp();
    //实例化被测试对象
    mColorGeneratorCustoemr = ColorGeneratorCustomer.DEFAULT;
    //api = new Api();
    context = this.getInstrumentation().getContext();
  }

  public void testGetColor() throws Exception {
    assertEquals(-686759, mColorGeneratorCustoemr.getColor("sf"));
  }

  public void testApiList() throws Exception {
    assertEquals(75, (int) Api.SORTED_SIZE_KEYS.get(0));
  }

  public void testVolley() throws Exception {
    HttpRequestQueue.getInstance()
        .getQueue(context)
        .add(new HStringRequest(Request.Method.GET, "url", this, this));
  }

  @Override public void onErrorResponse(VolleyError error) {

  }

  @Override public void onResponse(Object response) {

  }
}
