package com.wwd.practise.myprepractise.preglide.api;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * 创建者: wwd
 * 创建日期:15/11/6
 * 类的功能描述:
 */
public class MyGlideModule implements GlideModule {
  @Override public void applyOptions(Context context, GlideBuilder builder) {
    builder.setDiskCache(new InternalCacheDiskCacheFactory(context,"123",1024));
  }

  @Override public void registerComponents(Context context, Glide glide) {

  }
}
