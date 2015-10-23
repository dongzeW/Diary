package com.wwd.practise.myprepractise;

import android.test.InstrumentationTestCase;
import com.wwd.practise.myprepractise.customerview.ColorGeneratorCustomer;

/**
 * 创建者: wwd
 * 创建日期:15/10/19
 * 类的功能描述:测试工具类
 */
public class PractiseTest extends InstrumentationTestCase {
  private ColorGeneratorCustomer mColorGeneratorCustoemr;

  @Override protected void setUp() throws Exception {
    super.setUp();
    //实例化被测试对象
    mColorGeneratorCustoemr = ColorGeneratorCustomer.DEFAULT;
  }

  public void testGetColor() throws Exception{
    assertEquals(-686759, mColorGeneratorCustoemr.getColor("sf"));
  }
}
