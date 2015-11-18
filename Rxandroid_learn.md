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
1.创建Observer  
2.创建Observable  
3.Subscribe(订阅)  
