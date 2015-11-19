====
#Rxjava的观察者模式  
基本概念解读  ***注，制表于上一行要有空格，否则实现不了

基本概念|含义|备注 
---------- | ---|-------------
Observable| 被观察者|动作的发起者
Observer| 观察者|动作的执行者
subscribe| 订阅者|
event| 事件

Ob---->s<------Os

------
__beatutiful__

#Rx包含的方法

方法名|作用|类比
-----|---|----
onNext()|回调方法|onClick()/onEvent()
onCompleted()|事件队列完结|success()
onError()|事件队列异常|fail(),队列自动终止不再继续执行
#Rx创建过程
1.`创建Observer`  
2.创建Observable  
3.Subscribe(订阅)  

=======  
#异步线程控制，Scheduler机制  
在不指定线程的情况下，Rxjava遵循的是线程不变原则，即：在哪个线程调用subscribe（），就在哪个线程生产事件，在哪个线程生产事件，就在哪个线程消费事件，如果需要切换线程，就需要用到`Scheduler（调度器）`

调取器方式|含义  
-------|-----
Schedulers.immediate()|直接在当前线程运行，默认方式
Schedulers.newThread()|总是启动新线程，并在新线程执行操作
Schedulers.io()|I/O操作
Schedulers.computation()|计算所使用的Scheduler
AndroidSchedulers.mainThread()|主线程

控制线程的方法有两个，`subscribeOn()`,`observerOn()`

方法|作用
----|---
subscribeOn()|指定事件发生的线程
observerOn()|指定事件结束后回调的线程
		nb的大招在于变换，Rx提供了对事件序列进行变换的支持，这是它的核心功能之一,变换就
		是将事件序列中的对象或整个序列进行加工处理，转换成不同的事件或事件序列。

类|作用
---|---
Func|带参数，带返回类型的接口
Action|带参数，无返回类型的接口

变换的方法|作用
-------|-----
1,map()|处理单个对象变换，int-->str
2,FlatMap()|处理集合对象变换List<String>-->str
3,compose()|处理整体变换,整合flatmap中的lift方法
	大招之二，线程自由控制

onStart()和doOnSubscribe()用于出来请求前的操作，不同点在于，doOnSubscribe()可以指定执行的线程，而onStart()不行。
