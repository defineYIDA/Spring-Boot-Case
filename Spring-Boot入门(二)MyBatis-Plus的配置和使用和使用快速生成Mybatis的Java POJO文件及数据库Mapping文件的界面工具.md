![在这里插入图片描述](https://img-blog.csdnimg.cn/20181202181854686.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RlZmluZV9MSU4=,size_16,color_FFFFFF,t_70)
### 前言

已经两个多星期没写博客，不知不觉博客已经有了不少的访问量，也是对自己坚持的一种认可，最近忙于项目，也积累了对项目整合的ORM框架---MyBatis-Plus的一些使用技巧和心得，特与大家一起分享交流！
### MyBatis-Plus简介
[MyBatis官网](http://www.mybatis.org/mybatis-3/zh/index.html)
[MyBatis-Plus](https://mp.baomidou.com/guide/#%E7%89%B9%E6%80%A7)是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以使用简单的 XML 或注解来配置和映射原生信息，将接口和 Java 的 **POJOs**(Plain Old Java Objects,普通的 Java对象)映射成数据库中的记录。

[MyBatis-Plus](https://mp.baomidou.com/guide/quick-start.html#%E5%88%9D%E5%A7%8B%E5%8C%96%E5%B7%A5%E7%A8%8B) （简称 MP）是一个 MyBatis 的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。

这是官网上的介绍，我们关心的是mybatis可以通过XML文件或者基于注解两种方法来进行配置，而生成的实体类(entiy)文件也就是MVC中的model是POJOs文件，并且MyBatis-Plus能够自动生成POJOs和mapper文件，其中mapper文件即是我们常见的dao层文件。

接下来从MyBatis-Plus的两种配置方法开始到使用快速生成POJOs和mapper文件的图形工具进行代码生成，最后再介绍简单的使用！

---
### 创建Spring-Boot空的工程
参考：
[Spring Boot入门(一)Spring Boot+IDEA+JDK1.8开发环境和第一个项目的搭建，附所需资源链接](https://blog.csdn.net/define_LIN/article/details/83855710)
通过这种方法生成的工程，已经添加好了`Spring Boot Starter` 父工程和`spring-boot-starter`、`spring-boot-starter-test`、`mybatis-plus-boot-starter`等依赖。

---
### 创建一个测试用MySql数据库
**DbName：spring_boot_test**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181203110149797.jpg)
然后插入俩条数据：

|id|name|sex|college|
|---|---|---|---|
|1|Tom|男|NewEast|
|2|Jack|男|BlueSky|

---
### pom.xml中添加Spring-Boot+MySQL+Mybatis-Plus等依赖项
在`pom.xml` 配置文件中添加配置：

```
<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
				<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<optional>true</optional>
		</dependency>
		<!--mysql8.0的依赖项-->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>8.0.11</version>
		</dependency>
		<!--MyBatis-Plus3.0.6的依赖项-->
		<dependency>
			<groupId>com.baomidou</groupId>
			<artifactId>mybatis-plus-boot-starter</artifactId>
			<version>3.0.6</version>
		</dependency>
</dependencies>
```
如果出现红色的波浪线，原因是我们添加的配置没有现成的依赖包(一般IDE会自动下载)，通过在Maven Projects下载依赖包可以解决。

---
### 配置MyBatis-Plus
**两种配置方式：**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这两种方法上面已经说到一种基于XML配置文件，另一种基于注解，推荐使用注解这种方式来进行配置来避免使用易错，臃肿xml配置文件！而且如果你阅读过[MyBatis-Plus](https://mp.baomidou.com/guide/#%E7%89%B9%E6%80%A7)的官网，也会发现官网上的快速入门也采用的是基于注解的配置方式，这种方式的优点也是Spring-Boot的优点-----化繁为简,所以我们这里介绍通过注解的方式来进行配置。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;不禁感概，Spring-Boot总会和各种组件之间都会迸发出不一样的 ji 情火花！甚至连`mybatis-spring-boot-starter`的配置都不用引入到**pom**文件。

**通过注解进行配置：**

`application.properties` 添加相关配置：
```
mybatis-plus.type-aliases-package=com.example.hello.Model
spring.datasource.driverClassName = com.mysql.cj.jdbc.Driver
spring.datasource.url = jdbc:mysql://localhost:3307/spring_boot_test?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&serverTimezone=CTT
spring.datasource.username = root
spring.datasource.password = root
```
第一行设置实体类位置，也就是常见的Model层，这里对应Model的package就行了(model现在还没有生成)；
第二行配置连接池；
第三行配置JDBC有关Mysql的连接URL，里面除了IP地址，MySQL的端口号(我这里为3307，一般来说为3306)，和数据库名(spring_boot_test）外还有一些连接参数，例如`autoReconnect`自动重连/`characterEncoding`连接的字符编码等；
剩下的就是MySQL的账号密码。

---

### 添加实体类(Model)和服务类(Mapper.java+Mapper.xml)
生成`User.java`继承`Model`基类，对应数据库中User表生成对应的字段：

```
package com.example.hello.Model;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
public class User extends Model<User> {
    private int id;
    private String name;
    private String sex;
    private  String college;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getCollege() {
        return college;
    }
    public void setCollege(String college) {
        this.college = college;
    }
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", college='" + college + '\'' +
                '}';
    }
}
```
生成`UserMapper.java`接口继承`BaseMapper`
```
package com.example.hello.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hello.Model.User;
public interface UserMapper extends BaseMapper<User> {
}
```
生成`UserMapper.xml`
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.hello.Mapper.UserMapper">
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="cn.yrcti.sup.modular.system.model.CourseTime">
        <id column="id" property="id" />
        <result column="name" property="name" />
        <result column="sex" property="sex" />
        <result column="college" property="college"/>
    </resultMap>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
        id, name,sex,college
    </sql>
</mapper>
```
到这里可能会问：
`UserMapper.xml`在这里充当的是什么角色？
答：充当的是`UserMapper.java`的实现类，`UserMapper.java`的自定义查询操作，在`UserMapper.xml`实现。
增删改查怎么没看到？
答：MyBatis-Plus将CRUD操作封装在`BaseMapper`接口，已经默认实现，我们直接调用即可，所以我们只有当`BaseMapper`接口的方法不满足我们需求时才会在`UserMapper.java`中定义方法，然后在`UserMapper.xml`实现。

---

### 在启动类中添加对mapper包扫描@MapperScan

```
@SpringBootApplication
@MapperScan("com.example.hello.Mapper")
public class HelloApplication {
	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}
}
```
---

### 添加测试方法
这里我沿用之前的[Spring-Boot入门(一)](https://blog.csdn.net/define_LIN/article/details/83855710)的控制器
在控制器中载入`UserMapper`的实例，然后调用
```
@RequestMapping("/hello")
public class helloController {
    @Autowired
    UserMapper userMapper;
    @RequestMapping
    public String hello() {
        List<User> list=userMapper.selectList(null);
        return "Hello Spring-Boot"+list.toString();
    }
}
```
在浏览器输入：
http://localhost:8004/hello
输出：
```
Hello Spring-Boot[User{id=1, name='Tom', sex='男', college='NewEast'}, User{id=2, name='Jack', sex='男', college='BlueSky'}]
```
### [mybatis-generator-gui](https://github.com/zouzg/mybatis-generator-gui)图形界面工具的使用
在上面生成实体类和Mapper.java/.xml文件，可能会想到当数据库的表和字段过多时，这个步骤将会变得十分枯燥，所以在这里我推荐一个比较好用的界面工具批量生成！
[mybatis-generator-gui](https://github.com/zouzg/mybatis-generator-gui)
使用十分简单，大家可以试一试！

[示例代码的Git地址](https://github.com/defineYIDA/Spring-Boot-Case)



