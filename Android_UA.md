如何从UA分辨出Android设备类型
=====
Android设备类型：手机or平板（Tablet）  
先有是手机后有的平板，在早期的UA中包含android（不区分大小写）字段。  
在移动设备中包含Mobile字符串，平板中不包含  
例如：Mozilla/5.0（Linux；U；Android 3.0；）此UA中没有mobile字段，可任务此设备为平板。可直接用桌面版式来兼容  
