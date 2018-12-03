![在这里插入图片描述](https://img-blog.csdnimg.cn/2018111320063592.jpg)
### 错误提示：
2018-11-12 21:25:58.422 ERROR 15916 --- [  restartedMain] o.s.b.d.LoggingFailureAnalysisReporter   : 

***************************
APPLICATION FAILED TO START
***************************

Description:

The Tomcat connector configured to listen on port 8080 failed to start. The port may already be in use or the connector may be misconfigured.

Action:

Verify the connector's configuration, identify and stop any process that's listening on port 8080, or configure this application to listen on another port.

---
### 可能的原因：

 1. **电脑中其他进程占用8080端口；**
 2. **其他Spring Boot项目占用8080端口；**
 3. **自己要运行的项目重复生成占用了端口。**
 
### 解决方法：
 **对于造成端口占用的原因1和2解决方法有两种：**
 ***方法一：更改项目运行的端口号***
 如果我们没有在配置文件(application.properties)中配置端口号，Spring Boot项目则会采用默认的8080端口号，我们通过在配置文件中添加`server.port=8004`将端口号改为不为8080的端口；
 如下图：
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20181113201904623.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
  ***方法二：使用cmd结束占用8080端口的进程***
  1 打开cmd输入：`netstat   -ano|findstr  8080`，显示占用8080的进程；
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20181113202255208.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
  2 可以查看8080下的各个进程的具体信息，例如通过`netstat   -ano|findstr  4252`查看PID为3160的进程具体的信息，然后使用`taskkill  /pid  3160/f`将进程关闭，我们可以将8080下的进程全给关掉，然后在运行Spring Boot项目，就不会出现端口占用问题了！
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20181113202946648.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)



----
 **对于造成端口占用的原因3的解决方法：**
 
 对于原因3，是由于在IDEA中开启了Spring Boot项目的多端口部署的原因，也就是你每一次运行(**Run**)都会生成一个程序，如下图：
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20181113205451313.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
 注解1处显示已经运行了两个Application,如同注解2显示的俩个，因为如果打开了Spring Boot项目的多端口部署，每一次点击注解1的运行图标都是**Run**,而不是如同注解三处的的重新生成(**Rerun**)这个程序;
 
**1.点击图中Edit Configurations，如图**![在这里插入图片描述](https://img-blog.csdnimg.cn/20181113205949251.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
 
 2.选中的Single instance only点击ok。
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20181113210132771.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
 
 ---

**彩蛋：**
 心态崩了！惊了！**Σ(っ °Д °;)っ**
 显示的占用8080的pid为13748的进程是qq浏览器的，我也给结束掉了，
 CSDN™连个自动保存都没有！**(╬▔皿▔)凸**
写的东西全没了！含泪写了第二次 ！**(;´༎ຶД༎ຶ`)**
