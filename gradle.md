Build.gradle中配置引用properties里面的配置文件（如用户名密码）  
========
配置代码  
<pre>Properties properties = new Properties()
properties.load(project.rootProject.file('local.properties').newDataInputStream())文件位置local.properties  
bintray {  
    user = properties.getProperty("bintray.user")获取用户名  
    key = properties.getProperty("bintray.apikey")获取密码  
}</pre>
