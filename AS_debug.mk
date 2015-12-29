
AS下的调试技巧
===
断点模式：  
1、单步执行  
2、条件执行evaluate Expression  
3、日志断点Method Breakpoint Exception Breakpoiont  
4、Field Watchpoint  
   
调试基础：  
打断点的2中方式  
方式一:直接鼠标单击打断点，然后debug模式启动apk  
方式二:attach process方式启动process，此方式如同eclipse勾选device，然后运行到指定断点下调试，不用每次都从新启动程序  

---
1、Evaluate Expression  
可自行切换求值环境，按需求设置表达式。快捷键option + F8  
2、条件断点  
执行循环方式，只想调试某一条件，过滤其他循环，可直接设置循环条件
操作方式，直接右击断点处  
3、日志断点  
在条件断点的基础上，右键弹出的节目设置Suspend属性为false，可以在弹出的节目中，设置Log evaluated expression  
4、方法断点  
只关系方法的输入参数，输出结构，中间执行过程忽略。直接把断点打在方法这一行  
5、异常断点  
当程序有异常抛出时，进入断点模式。操作方式：Run->View BreakPoints（shift+cmd+F8）  
6、Field WathcPoint  
Run->View BreakPoints->+->select Filed WatchPoint
