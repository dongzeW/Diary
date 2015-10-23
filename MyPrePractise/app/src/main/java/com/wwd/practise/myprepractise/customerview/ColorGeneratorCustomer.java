package com.wwd.practise.myprepractise.customerview;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 创建者: wwd
 * 创建日期:15/10/20
 * 类的功能描述:
 */
public class ColorGeneratorCustomer {
  public static ColorGeneratorCustomer DEFAULT;
  public static ColorGeneratorCustomer MATERIAL;
  private final List<Integer> mColors;
  private final Random mRandom;

  private ColorGeneratorCustomer(List<Integer> colorList) {
    mRandom = new Random(System.currentTimeMillis());
    mColors = colorList;
  }

  public static ColorGeneratorCustomer create(List<Integer> colorList) {
    return new ColorGeneratorCustomer(colorList);
  }

  static {
    DEFAULT = create(Arrays.asList(0xfff16364, 0xfff58559));
    MATERIAL = create(Arrays.asList(0xffe57373, 0xffba68c8));
  }

  public int getRandomColor() {
    return mColors.get(mRandom.nextInt(mColors.size()));
  }

  public int getColor(Object key) {
    return mColors.get(Math.abs(key.hashCode()) % mColors.size());
  }
}
