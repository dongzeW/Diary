package com.wwd.practise.myprepractise.rx;

import rx.Observable;
import rx.Subscriber;

/**
 * 创建者: wwd
 * 创建日期:15/11/18
 * 类的功能描述:创建被观察者
 */
public class RxObservable {
  public void cObservable() {
    //1,create()创造事件序列的方法
    Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
      @Override public void call(Subscriber<? super String> subscriber) {
        //subscribe是观察者
        subscriber.onNext("hello Next world!");
        subscriber.onNext("this");
        subscriber.onNext("my rx learn");
        subscriber.onCompleted();
      }
    });
    //2,此方法等同于create中的onNext方法
    Observable.just("hello Next world!", "this", "my rx learn");
    //3,类比上面两个方法
    String [] words = {"hello","world","rx"};
    Observable observable1 = Observable.from(words);
  }
}
