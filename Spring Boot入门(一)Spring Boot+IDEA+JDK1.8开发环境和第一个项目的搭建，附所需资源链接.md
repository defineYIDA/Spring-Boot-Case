![在这里插入图片描述](https://img-blog.csdnimg.cn/20181108145831237.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
### 前言
&nbsp;&nbsp;&nbsp;博主第一次了解Spring Boot 这个框架，之前的时候就一直对这个框架心驰神往，如今刚开始学习这个框架，把学习历程和过程中遇到的问题和解决方法记录下来，与大家一起分享！

----
 ### 关于Spring Boot
&nbsp;&nbsp;&nbsp;Spring Boot 是由 Pivotal 团队提供的全新框架，其设计目的是用来简化新 Spring 应用的初始搭建以及开发过程。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。采用 Spring Boot 可以大大的简化你的开发模式，所有你想集成的常用框架，它都有对应的组件支持。

**Spring Boot特性**

 - 使用 Spring 项目引导页面可以在几秒构建一个项目
 - 方便对外输出各种形式的服务，比如：REST API, WebSocket, Web, Streaming, Tasks
 - 非常简洁的安全策略集成
 - 支持关系数据库和非关系数据库
 - 支持运行期内嵌容器，如：Tomcat, Jetty
 - 强大的开发包，支持热启动
 - 自动管理依赖
 - 自带应用监控支持各种IED，如：IntelliJ IDEA 、NetBeans
 
**学习网站**

[Spring Boot 中文索引](http://springboot.fun/#resources)

 ---
 
### 开发环境的部署
>操作系统：Windows 10

为部署`Spring Boot`项目，我们需要的工具有：**IDE:IntelliJ IDEA安装包**，**JDK安装包**；
如果你需要部署的项目需要连接数据库的话还需要：**MySQL安装包**，**Navicat For MySQL**。
因为IDEA不用额外配置Maven,所以推荐大家使用！

**安装IntelliJ IDEA:**

可以使用学生注册获得免费版，也可以通过下面给出的破解码进行破解。
IntelliJ IDEA：https://www.jetbrains.com/idea/download/#section=windows
破解码：        http://idea.lanyus.com/

**安装配置JDK:**

JDK版本要求7以上，推荐使用8；

JDK:https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
配置的具体过程也就是一路next就行了；

**安装MySQL：**

**&nbsp;&nbsp;&nbsp;如果需要进行数据库相关操作，配置MySQL的时候就得注意了，这里分两种情况，如果你电脑上已经安装过，类似与XAMPP这种集成了MySQL的程序，初始的3306端口会被占用（如果你有XAMPP这种集成了MySQL的程序也可以直接用不用额外的安装了）。**
**如果在安装过程中端口被占用，你更换了端口，记得在项目的配置中更改对应的账号密码和端口号。**

MySQL8.0下载地址：https://dev.mysql.com/downloads/windows/installer/

---
### 获得第一个Spring Boot项目

 1. **通过Speing Initializr 获取第一个基础框架：**
 1.1 这里：https://start.spring.io/
 1.2 选择构建工具`Maven Project`、Spring Boot版本1.5.18以及一些工程基本信息，注意打开最下方的` Switch to the full version`，选择Web模块，可参考下图所示：
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20181108153756739.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)

**如果不选择web模块，则项目不会自动引入`spring-web`和`spring-webmvc`这两个jar包，`pom.xml`中也不会自动生成web模块，导致很多不必要麻烦。**

1.3 点击`Generate Project`下载项目压缩包
  
 2. **解压项目包，并用IDE以`Maven`项目导入**
 1.1 菜单中选择File–>New–>Project from Existing Sources...
1.2 选择解压后的项目文件夹，点击OK
1.3 点击Import project from external model并选择Maven，点击Next到底为止。
1.4 若你的环境有多个版本的JDK，注意到选择Java SDK的时候请选择Java 7以上的版本

**项目结构解析：**

通过上面步骤完成了基础项目的创建，如图所示，Spring Boot的基础结构共三个文件（具体路径根据用户生成项目时填写的Group所有差异）：

 1. src/main/java下的程序入口：			HelloApplication
 2. src/main/resources下的配置文件：application.properties
 3. src/test/下的测试入口：HelloApplicationTests
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20181108155659417.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)

**编写Hello服务：**
在`com.example.hello`文件处右键生成如上图所示的`Controller`文件，并在文件中生成`helloController`类；
在类中编写如下代码：
```
package com.example.hello.Controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class helloController {
    @RequestMapping
    public String hello() {
        return "Hello Spring-Boot";
    }

    @RequestMapping("/info")
    public Map<String, String> getInfo(@RequestParam String name) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        return map;
    }

    @RequestMapping("/list")
    public List<Map<String, String>> getList() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = null;
        for (int i = 1; i <= 5; i++) {
            map = new HashMap<>();
            map.put("name", "YIDA-" + i);
            list.add(map);
        }
        return list;
    }
}

```
**右键`HelloApplication`选择`Run(HelloApplication)`运行程序，控制台显示 `Started HelloApplication in 2.301 seconds (JVM running for 3.41)`即成功启动。**
在浏览器中输入url：
http://localhost:8080/hello 
输出：Hello Spring-Boot 
http://localhost:8080/hello/info?name=YIDA 
输出：{“name”:”YIDA”} 
http://localhost:8080/hello/list 
输出：[{“name”:”YIDA-1”},{“name”:”YIDA-2”},{“name”:”YIDA-3”},{“name”:”YIDA-4”},{“name”:”YIDA-5”}]

--
### 最后
如果出现默认8080端口被占用的问题，可能由于被其他程序占用或者被你以前生成的`spring-boot`项目占用了端口，可以通过在`application.properties`中设置将端口更改：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181108163024842.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
[Hello小Demo的代码附件](https://github.com/defineYIDA/Spring-Boot-Case/tree/master/hello)

