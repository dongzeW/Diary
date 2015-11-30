package com.wwd.practise.myprepractise.rx;

import com.wwd.practise.myprepractise.Utils.LogUtils;
import rx.Observer;
import rx.Subscriber;

/**
 * 创建者: wwd
 * 创建日期:15/11/18
 * 类的功能描述:创建观察者
 */
public class RxObserver {

  public void createObserver() {
    Observer<String> observer = new Observer<String>() {
      @Override public void onCompleted() {
        LogUtils.i("success");
      }

      @Override public void onError(Throwable e) {
        LogUtils.i("fail");
      }

      @Override public void onNext(String s) {
        LogUtils.i("next");
      }
    };
  }

  //observer最终被转换为Subscribe
  public void cSubscribe() {
    Subscriber<String> subscriber = new Subscriber<String>() {

      //请求之前的处理方法，可以初始化数据
      @Override public void onStart() {
        //执行在子线程中，不可进行ui刷新操作,不能指定哪个线程操作
        super.onStart();
      }

      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {

      }

      @Override public void onNext(String s) {

      }
    };
    //判断是否持有观察者对象
    if (subscriber.isUnsubscribed()) {
      //取消订阅，避免内存泄漏
      subscriber.unsubscribe();
    }
  }
}
