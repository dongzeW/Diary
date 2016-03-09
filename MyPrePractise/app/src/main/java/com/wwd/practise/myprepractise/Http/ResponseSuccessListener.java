package com.wwd.practise.myprepractise.Http;

/**
 * 创建者: wwd
 * 创建日期:15/12/30
 * 类的功能描述:处理http200后的流程,包含返回的数据成功失败流程
 */
public interface ResponseSuccessListener {
  void success();

  void fail();
}
