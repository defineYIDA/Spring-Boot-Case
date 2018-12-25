## JavaWeb之Servlet 

正值期末，项目暂缓，腾出时间，回顾总结

### Servlet简介：

Servlet是Java语言编写的并且在服务器端执行的程序；
Servlet是sun公司提供的一门用于开发动态web资源的技术，是扩展和加强Web服务器端性能的首选技术；
Servlet提供了一种基于组件，平台无关性的构建Web应用的方法，从而取代传统的CGI程序的种种性能限制；
Servlet本质为API的一组接口，我们约定俗成将实现Servlet接口的Java程序，也称之为**Servlet**。

----
### Tomcat和Servlet的关系
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181223170510837.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
<center>图一（来源百度百科）</center>

上图中的容器即为`Tomcat`，
`Tomcat`是Web应用服务器,是`Servlet`和`JSP`的容器，
`Tomcat` 作为`Servlet`容器,负责处理（解析）客户请求,把请求传送给`Servlet`,并将`Servlet`的响应传送回给客户端，
而`Servlet`是一种运行在支持Java语言的服务器上的组件，`Servlet`最常见的用途是扩展Java Web服务器功能,提供非常安全的,可移植的,易于使用的**CGI**替代品！

**从http协议中的请求和响应可以得知，浏览器发出的请求是一个请求文本，而浏览器接收到的也应该是一个响应文本。**

**细观图一：**

当客户端产生一个Request^①^，以文本形式被包含JVM的Web服务器(`Tomcat`)接收到并解析^②^，解析指将请求文本封装为`HttpServletRequest`类型的request对象，request对象中包含请求的所有信息，可以在通过调用其对应方法获取到特定信息；

然后`Tomcat`通过步骤③和步骤④，创建Servlet实例和对其初始化，并且将步骤①解析获得的request对象通过
步骤⑤`service(HttpServletRequest req, HttpServletResponse resp)`，传入Servlet；

Servlet在将对应的请求信息进行处理后，会将处理结果响应信息作为一个`HttpServletResponse`类型的response对象返回给`Tomcat`^⑥^，然后`Tomcat`将response对象转化为响应文本返回给浏览器^⑦^；

当服务器不再需要Servlet对象或者需要重新装入新实例时，Web服务器会调用其`destroy()`方法^⑧^，释放掉内存。

---
### Servlet的生命周期

```mermaid
flowchat
st=>start: 装入  // 加载Servlet类/实例对象
op=>operation: 初始化 // init()，由Web服务器调用
op1=>operation: 调用 // service()，处理请求返回响应结果
e=>end: 销毁 // destroy()
st->op->op1->e
```
Servlet初始化(实例并且调用其` init()`方法)的时机由配置项中`load-on-startup`配置决定：

1)`load-on-startup`元素标记容器是否在启动的时候就加载这个servlet(实例化并调用其init()方法)。

2)它的值必须是一个整数，表示servlet应该被载入的顺序

2)当值为0或者大于0时，表示容器在应用启动时就加载并初始化这个servlet；

3)当值小于0或者没有指定时，则表示容器在该servlet被选择时才会去加载。

4)正数的值越小，该servlet的优先级越高，应用启动时就越先加载。

5)当值相同时，容器就会自己选择顺序来加载


---
### 创建一个Servlet项目
**需求：**
- [x] JDK
- [x] Tomcat
- [x] IDEA/Eclipse(这里以IDEA为例)

1. 左上角点击 File -> New -> Project -> 新建一个 Web Application 项目,设置项目名称：First_Servlet
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181224151544341.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181224152823719.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)

2. 左上角点击 File -> Project Structure ->Project,选择JDK(这里ide可能会默认配置好)
3. 左上角点击 File -> Project Structure ->Modules，右击项目名add 添加Web模块
![在这里插入图片描述](https://img-blog.csdnimg.cn/2018122416014757.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)

4. 右键项目文件中的web文件夹，添加`classes`文件夹用来存放编译生成的类文件和`lib`文件夹用来存放依赖包
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181224200645779.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
5. 左上角点击 File -> Project Structure ->Modules ->paths,修改output path和test output path 为我们生成的`classes`文件夹
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181224200853486.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
6. 左上角点击 File -> Project Structure ->Modules ->Dependencies ->点击 + 号 ->JARs or directories,选择之前生成的lib文件
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181224201400909.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
结果为生成下图选中项：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181224202159858.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
7. 左上角点击 File -> Project Structure ->Modules ->Dependencies ->选中上一步生成的lib ->点击 + 号 ->library->Tomcat,最后点击ok
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181224202407724.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
8. 右键src，新建一个Servlet文件，命名为myServlet，并且在生成的类中重写doGet和doPost方法，为如下代码：

```
@WebServlet(name = "myServlet")
public class myServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");    //设置响应的字符集格式为UTF-8
        response.setContentType("text/html");  //设置响应正文的MIME类型
        PrintWriter out = response.getWriter();    //返回一个PrintWriter对象，Servlet使用它来输出字符串形式的正文数据
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println("  <HEAD><TITLE>过年好</TITLE></HEAD>");
        out.println("  <BODY>");
        out.println("    <table border='0' align='center'>");
        out.println("            <tr><td bgcolor='skyblue'colspan=2>过年好</td></tr>");
        out.println("     </table>");
        out.println("  </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }
}
```

9. 配置web.xml，添加`servlet-mapping`也就是访问的URL

```
<servlet>
        <servlet-name>myServlet</servlet-name>
        <servlet-class>myServlet</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>myServlet</servlet-name>
        <url-pattern>/testServlet</url-pattern>
    </servlet-mapping>
```
10. 配置Tomcat，Edit Config ->右上+号 -> Tomcat Server -> local -> Server -> Config ,选中App server 为tomcat
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181224203914670.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181224204206707.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181224204003437.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
也可以在Deployment中配置项目根目录
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181224204515902.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
11. 点击运行项目浏览器访问[http://localhost:8081/First_Servlet/testServlet](http://localhost:8081/First_Servlet/testServlet?)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181224204813310.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)

**大功告成！忙活这么久，目的大家也看出来了，就是想给大家拜个早年！好了就到这里了！**
。
。
。
。
。
。
。
。
。
。
。
。
。
。

额，开玩笑，我们继续

---
### Servlet运行过程
经过第二部分---**Tomcat和Servlet的关系**了解到，Servlet始于`init()`，调用于`service()`,消亡于`destroy()`，当我们为什么只需重写其`doGet`和`doPost`就能实现一个简单的请求？
观下图：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20181224212158349.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
为了更加直观，清楚的观察调用顺序，我再次整理了Servlet类中的方法，如下：
关键方法在调用时，在控制台进行打印处理：
```
@WebServlet(name = "myServlet")
public class myServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("***********************************************************************");
        System.out.println("do get");
        System.out.println("***********************************************************************");
       response.setCharacterEncoding("UTF-8");    //设置响应的字符集格式为UTF-8
        response.setContentType("text/html");  //设置响应正文的MIME类型
        PrintWriter out = response.getWriter();    //返回一个PrintWriter对象，Servlet使用它来输出字符串形式的正文数据
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println("  <HEAD><TITLE>过年好</TITLE></HEAD>");
        out.println("  <BODY>");
        out.println("    <table border='0' align='center'>");
        out.println("            <tr><td bgcolor='skyblue'colspan=2>过年好</td></tr>");
        out.println("     </table>");
        out.println("  </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("***********************************************************************");
        System.out.println("do post");
        System.out.println("***********************************************************************");

        response.setCharacterEncoding("UTF-8");    //设置响应的字符集格式为UTF-8
        response.setContentType("text/html");  //设置响应正文的MIME类型
        PrintWriter out = response.getWriter();    //返回一个PrintWriter对象，Servlet使用它来输出字符串形式的正文数据
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println("  <HEAD><TITLE>过年好</TITLE></HEAD>");
        out.println("  <BODY>");
        out.println("    <table border='0' align='center'>");
        out.println("            <tr><td bgcolor='skyblue'colspan=2>过年好</td></tr>");
        out.println("     </table>");
        out.println("  </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("***********************************************************************");
        System.out.println("service");
        System.out.println("***********************************************************************");
        super.service(req, resp);
    }

    @Override
    public void init() throws ServletException {
        System.out.println("***********************************************************************");
        System.out.println("Init");
        System.out.println("***********************************************************************");
        super.init();
    }

    @Override
    public void destroy() {
        System.out.println("***********************************************************************");
        System.out.println("destroy");
        System.out.println("***********************************************************************");
        super.destroy();
    }
}
```
点击运行，然后tomcat服务启动，Output如下，可见，我们自定义的Servlet并未启动(调用`init()`方法)，原因是这里我们并未配置其`load-on-startup`属性，采用默认的被调用时(访问URL)初始化
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181225101654144.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)

我们在`web.xml`中添加`load-on-startup`配置，设置为1，在容器启动时加载：
```
    <servlet>
        <servlet-name>myServlet</servlet-name>
        <servlet-class>myServlet</servlet-class>
        <load-on-startup>1</load-on-startup>
    </servlet>
```
Output 如下：可见在Tomcat容器初始化时，调用了Servlet的初始化方法

![在这里插入图片描述](https://img-blog.csdnimg.cn/20181225101806155.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
当我们访问URL时，Output如下(`load-on-startup`配置为1)：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181225101929646.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
通过上面的处理可以清楚的看出，**Servlet**的运行过程，在初始化后当有URL访问**Tomcat**容器，会调用Servlet的`service()`方法，然后由进行判断,获取具体的请求类型，再调用具体的处理请求的方法，`doGet()`/`doPost()`/`doPut()`等系列方法，这里我们对`doGet()`/`doPost()`的重写就是我们自定义的具体处理方法！

---
### Servlet创建原理
在IDEA中将鼠标放在我们自定义Servlet类上，然后快捷键Ctrl+H,查看类的继承关系
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181225103743710.png)
myServlet(自定义Servlet)直接继承自`httpServlet`，
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181225105746254.png)
而`httpServlet`继承自`GenericServlet`，
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181225105808293.png)
`GenericServlet`实现了两个核心接口`Servlet, ServletConfig`
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181225105850439.png)
其实，我们自定义Servlet根本上是，实现两个核心接口`Servlet, ServletConfig`，而`GenericServlet`(通用Servlet)和`httpServlet`的出现只是为了简化编写Servlet的过程，所以当我们简单的自定义一个Servlet	时只需重写其具体对请求的处理方法。

 >**`Servlet`接口：**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181225111500907.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
可以看出`Servlet`接口定义的是，我们前面说到的Servlet运行过去的核心方法，包括初始化，调用和销毁，还有就是`getServletConfig()`获得配置对象`ServletConfig`，这个对象在我们在对Servlet初始化时，作为`init()`的形参传入,`init(ServletConfig config)`;
还有一个`getServletInfo()`方法，这个需要我们在自定义类里重写，返回由我们自己定义的Servlet的信息。

> **`ServletConfig`接口：**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181225111711841.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
**ServletContext对象：**
Tomcat为每个web项目都创建一个ServletContext实例，tomcat在启动时创建，服务器关闭时销毁，在一个web项目中共享数据，管理web项目资源，为整个web配置公共信息等，通俗点讲，就是一个web项目，就存在一个ServletContext实例，每个Servlet读可以访问到它。 
`getServletName()`;  //获取servlet的名称，也就是我们在web.xml中配置的servlet-name
`getServletContext()`; //获取ServletContext对象
`getInitParameter(String)`; //获取在servlet中初始化参数的值。这里注意与全局初始化参数的区分。这个获取的只是在该servlet下的初始化参数
`getInitParameterNames()`; //获取在Servlet中所有初始化参数的名字，也就是key值，可以通过key值，来找到各个初始化参数的value值。注意返回的是枚举类型

---
### 收尾
最后，既然我们了解到了，我们的请求经过浏览器到了Tomcat经过了处理，封装成了`HttpServletRequest`和`HttpServletResponse`，被**Servlet**的`service()`方法接收，我们再来仔细理解下面这两个熟悉而又陌生的概念。
**请求转发：**

    request.getRequestDispatcher(String path).forward(request,response); //方法一

特点：
浏览器发起一次请求，就是将用户的请求，连同请求信息等内容，一起转发到服务器的另外一个**servlet**去处理，它不会丢失request对象的信息；
例如在前面的处理请求的方法(`doGet/doPost`)中我们进行请求转发调用方法一，**path**对应的**servlet**将直接活动我们传入的`forward`的参数；
这一过程是服务器内部完成的，所有只能在同一web项目；
浏览器中url不会改变，也就是浏览器不知道服务器做了什么，是服务器帮我们跳转页面的，并且在转发后的页面，能够继续使用原先的request，因为是原先的request，所以request域中的属性都可以继续获取到。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181225153552442.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)

**重定向：**

    response.sendRedirect((String path);//方法二

特点：

实际是两次HTTP请求，服务器端在响应第一次请求的时候，让浏览器再向另外一个URL发出请求，从而达到转发的目的；
redirect() 会丢失request的所有信息  它属于页面级的重定向；
服务器告诉浏览器要跳转的页面，是浏览器主动去跳转的页面，浏览器知道，也浏览器的地址栏中url会变，是浏览器重新发起一个请求到另外一个页面，所以request是重新发起的，跟请求转发不一样。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20181225153728714.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)

有了前面Servlet的知识，趁热打铁，对请求转发和重定向的概念也是很随意的理解了！

**最后**

[myServlet源码](https://github.com/defineYIDA/Spring-Boot-Case/tree/master/First_Servlet)

