## 1、Spring（2020-11-9）

### 1.1 简介

- Spring：春天------>给软件行业带来了春天！
- 2002，首次推出了Spring框架的雏形：interface21框架！
- Spring框架即以interface21框架为基础，经过重新设计，并不断丰富其内涵，于2004年3月24日发布了1.0正式版。
- **Rod Johnson**，Spring Framework创始人，著名作者。很难想象Rod Johnson的学历，真的让好多人大吃一惊，他是悉尼大学的博士，然而他的专业不是计算机，而是音乐学。
- Spring理念：使现有的技术更加容易使用，本身是一个大杂烩，整合了现有的技术框架！
- SSH：Struct2 + Spring + Hibernate!
- SSM：SpringMVC + Spring + Mybatis!

官网：https://spring.io/projects/spring-framework#overview
 官方下载地址：https://repo.spring.io/release/org/springframework/spring/
 GitHub：https://github.com/spring-projects/spring-framework

```xml
<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.2.0.RELEASE</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.2.0.RELEASE</version>
</dependency>
123456789101112
```

### 1.2 优点

- Spring是一个开源的免费的框架（容器）！
- Spring是一个轻量级的、非入侵式的框架！
- 控制反转（IOC），面向切面编程（AOP）！
- 支持事务的处理，对框架整合的支持！

总结一句话：Spring就是一个轻量级的控制反转（IOC）和面向切面编程（AOP）的框架！

### 1.3 组成

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201109101410173.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpNjQzOTM3NTc5,size_16,color_FFFFFF,t_70#pic_center)

### 1.4 拓展

现代化的Java开发！说白就是基于Spring的开发！

- Spring Boot   
  - 一个快速开发的脚手架。
  - 基于SpringBoot可以快速的开发单个微服务。
  - 约定大于配置。
- Spring Cloud   
  - SpringCloud是基于SpringBoot实现的。

因为现在大多数公司都在使用SpringBoot进行快速开发，学习SpringBoot的前提，需要完全掌握Spring及SpringMVC！**承上启下的作用！**

**弊端：发展了太久之后，违背了原来的理念！配置十分繁琐，人称：“配置地狱！”**

## 2、IOC理论推导

1. UserDao 接口

```java
public interface UserDao {
    void getUser();
}
123
```

1. UserDaoImpl 实现类

```java
public class UserDaoImpl implements UserDao {
    public void getUser() {
        System.out.println("默认获取用户数据");
    }
}
12345
```

1. UserService 业务接口

```java
public interface UserService {
    void getUser();
}
123
```

1. UserServiceImpl 业务实现类

```java
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    public void getUser() {
        userDao.getUser();
    }
}
12345678
```

1. 测试

```java
public class MyTest {
    public static void main(String[] args) {

        //用户实际调用的是业务层，dao层他们不需要接触！
        UserService userService = new UserServiceImpl();
        userService.getUser();
    }
}
12345678
```

在我们之前的业务中，用户的需求可能会影响我们原来的代码，我们需要根据用户的需求去修改原代码！如果程序代码量十分大，修改一次的成本代价十分昂贵！
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201109112133683.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpNjQzOTM3NTc5,size_16,color_FFFFFF,t_70#pic_center)

我们使用一个Set接口实现，已经发生了革命性的变化！

```java
    private UserDao userDao;

    //利用set进行动态实现值的注入！
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
123456
```

- 之前，程序是主动创建对象！控制权在程序猿手上！
- 使用了set注入后，程序不再具有主动性，而是变成了被动的接收对象！

这种思想，从本质上解决了问题，我们程序猿不用再去管理对象的创建了。系统的耦合性大大降低~，可以更加专注的在业务的实现上！这是IOC的原型！
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201109112157443.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpNjQzOTM3NTc5,size_16,color_FFFFFF,t_70#pic_center)

### IOC本质

**控制反转IoC（Inversion of Control），是一种设计思想，DI（依赖注入）是实现IoC的一种方法**，也有人认为DI只是IoC的另一种说法。没有IoC的程序中，我们使用面向对象编程，对象的创建与对象间的依赖关系完全硬编码在程序中，对象的创建由程序自己控制，控制反转后将对象的创建转移给第三方，个人认为所谓控制反转就是：获得依赖对象的方式反转了。

采用XML方式配置Bean的时候，Bean的定义信息是和实现分离的，而采用注解的方式可以把两者合为一体，Bean的定义信息直接以注解的形式定义在实现类中，从而达到了零配置的目的。
 **控制反转是一种通过描述（XML或注解）并通过第三方去生产或获取特定对象的方式。在Spring中实现控制反转的是IoC容器，其实现方法是依赖注入（Dependency Injection，DI）。**

## 3、HelloSpring

1. 新建一个maven项目，编写实体类

```java
public class Hello {
    private String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "Hello{" +
                "str='" + str + '\'' +
                '}';
    }
}
123456789101112131415161718
```

1. 编写xml配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">


    <!--使用Spring来创建对象，在Spring这些都称为Bean
    类型 变量名 = new 类型();
    Hello hello = new Hello();

    id = 变量名
    class = new的对象
    property 相当于给对象中的属性设置一个值！
        -->
    <bean id="hello" class="com.kuang.pojo.Hello">
        <property name="str" value="Spring"/>
    </bean>
</beans>
12345678910111213141516171819
```

1. 测试

```java
public class MyTest {
    public static void main(String[] args) {
        //获取Spring的上下文对象！
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        //我们的对象现在都在Spring中的管理了，我们需要使用，直接去里面取出来就可以！
        Hello hello = (Hello) context.getBean("hello");
        System.out.println(hello.toString());
    }
}
12345678910
```

**思考问题？**

- Hello对象是谁创建的？
   Hello对象是由Spring创建的。
- Hello对象的属性是怎么设置的？
   Hello对象的属性是由Spring容器设置的。

这个过程就叫控制反转：

**控制**：谁来控制对象的创建，传统应用程序的对象是由程序本身控制创建的，使用Spring后，对象是由Spring来创建的。

**反转**：程序本身不创建对象，而变成被动的接收对象。

**依赖注入**：就是利用set方法来进行注入的。

IOC是一种编程思想，由主动的编程变成被动的接收。

可以通过new ClassPathXmlApplicationContext去浏览一下底层源码。

**OK，到了现在，我们彻底不用在程序中去改动了，要实现不同的操作，只需要在xml配置文件中进行修改，所谓的IOC，一句话搞定：对象由Spring来创建，管理，装配！**

## 4、IOC创建对象的方式（2020-11-10）

1. 使用无参构造创建对象，默认！

2. 假设我们要使用有参构造创建对象。   

   1. 下标赋值

   ```xml
   <!--第一种方式：下标赋值    -->
   <bean id="user" class="com.kuang.pojo.User">
       <constructor-arg index="0" value="狂神说Java"/>
   </bean>
   1234
   ```

   1. 类型

   ```xml
   <!--第二种方式：通过类型的创建，不建议使用    -->
   <bean id="user" class="com.kuang.pojo.User">
       <constructor-arg type="java.lang.String" value="lifa"/>
   </bean>
   1234
   ```

   1. 参数名

   ```xml
   <!--第三种方式：直接通过参数名来设置    -->
   <bean id="user" class="com.kuang.pojo.User">
       <constructor-arg name="name" value="李发"/>
   </bean>
   1234
   ```

总结：在配置文件加载的时候，容器中管理的对象就已经初始化了！

## 5、Spring配置

### 5.1 别名

```xml
    <!--别名，如果添加了别名，我们也可以使用别名获取到这个对象-->
    <alias name="user" alias="userNew"/>
12
```

### 5.2 Bean的配置

```xml
    <!--
    id：bean的唯一标识符，也就是相当于我们学的对象名
    class：bean对象所对应的全限定名：包名+类名
    name：也是别名，而且name可以同时取多个别名
        -->
    <bean id="userT" class="com.kuang.pojo.UserT" name="user2 u2,u3;u4">
        <property name="name" value="黑心白莲"/>
    </bean>
12345678
```

### 5.3 import

这个import。一般用于团队开发使用，它可以将多个配置文件，导入合并为一个。
 假设，现在项目中有多个人开发，这三个人负责不同的类开发，不同的类需要注册在不同的bean中，我们可以利用import将所有人的beans.xml合并为一个总的！

- 张三

- 李四

- 王五

- applicationContext.xml

  ```xml
  <import resource="bean.xml"/>
  <import resource="bean2.xml"/>
  <import resource="bean3.xml"/>
  123
  ```

使用的时候，直接使用总的配置就可以了。

## 6、依赖注入

### 6.1 构造器注入

前面已经介绍过，参考**4、IOC创建对象的方式**

### 6.2 Set方式注入【重点】

- 依赖注入：Set注入   
  - 依赖：bean对象的创建依赖于容器！
  - 注入：bean对象中的所有属性，由容器来注入！

【环境搭建】

1. 复杂类型

```java
public class Address {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
1234567891011
```

1. 真实测试对象

```java
public class Student {

    private String name;
    private Address address;
    private String[] books;
    private List<String> hobbies;
    private Map<String,String> card;
    private Set<String> games;
    private String wife;
    private Properties info;
}
1234567891011
```

1. beans.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="student" class="com.kuang.pojo.Student">
        <!--第一种：普通值注入，value        -->
        <property name="name" value="黑心白莲"/>
    </bean>
</beans>
1234567891011
```

1. 测试类

```java
public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        Student student = (Student) context.getBean("student");
        System.out.println(student.getName());
    }
}
12345678
```

1. 完善注入信息

```xml
    <bean id="address" class="com.kuang.pojo.Address">
        <property name="address" value="西安"/>
    </bean>

    <bean id="student" class="com.kuang.pojo.Student">
        <!--第一种：普通值注入，value        -->
        <property name="name" value="黑心白莲"/>

        <!--第二种：        -->
        <property name="address" ref="address"/>

        <!--数组        -->
        <property name="books">
            <array>
                <value>红楼梦</value>
                <value>西游记</value>
                <value>水浒传</value>
                <value>三国演义</value>
            </array>
        </property>

        <!--List        -->
        <property name="hobbies">
            <list>
                <value>打篮球</value>
                <value>看电影</value>
                <value>敲代码</value>
            </list>
        </property>

        <!--Map        -->
        <property name="card">
            <map>
                <entry key="身份证" value="123456789987456321"/>
                <entry key="银行卡" value="359419496419481649"/>
            </map>
        </property>

        <!--Set        -->
        <property name="games">
            <set>
                <value>LOL</value>
                <value>COC</value>
                <value>BOB</value>
            </set>
        </property>

        <!--NULL        -->
        <property name="wife">
            <null/>
        </property>

        <!--Properties        -->
        <property name="info">
            <props>
                <prop key="driver">20191029</prop>
                <prop key="url">102.0913.524.4585</prop>
                <prop key="user">黑心白莲</prop>
                <prop key="password">123456</prop>
            </props>
        </property>

    </bean>
123456789101112131415161718192021222324252627282930313233343536373839404142434445464748495051525354555657585960616263
```

### 6.3 拓展方式注入

我们可以使用p命名空间和c命名空间进行注入
 官方解释：
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201110193916911.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpNjQzOTM3NTc5,size_16,color_FFFFFF,t_70#pic_center)
 使用：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:c="http://www.springframework.org/schema/c"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--p命名空间注入，可以直接注入属性的值：property-->
    <bean id="user" class="com.kuang.pojo.User" p:name="黑心白莲" p:age="20"/>

    <!--c命名空间注入，通过构造器注入：constructor-args-->
    <bean id="user2" class="com.kuang.pojo.User" c:name="狂神" c:age="22"/>

</beans>
123456789101112131415
```

测试：

```java
    @Test
    public void test2(){
        ApplicationContext context = new ClassPathXmlApplicationContext("userbeans.xml");

        User user = context.getBean("user",User.class);
        System.out.println(user);

        User user2 = context.getBean("user2",User.class);
        System.out.println(user2);
    }
12345678910
```

注意点：p命名和c命名空间不能直接使用，需要导入xml约束！

```xml
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:c="http://www.springframework.org/schema/c"
12
```

### 6.4 bean的作用域

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201111101635200.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpNjQzOTM3NTc5,size_16,color_FFFFFF,t_70#pic_center)



1. 单例模式（Spring默认机制）

```xml
<bean id="user2" class="com.kuang.pojo.User" c:name="狂神" c:age="22" scope="singleton"/>
1
```

1. 原型模式：每次从容器中get的时候，都会产生一个新对象！

```xml
<bean id="user2" class="com.kuang.pojo.User" c:name="狂神" c:age="22" scope="prototype"/>
1
```

1. 其余的request、session、application、这些只能在web开发中使用到！

## 7、Bean的自动装配（2020-11-11）

- 自动装配是Spring满足bean依赖一种方式！
- Spring会在上下文中自动寻找，并自动给bean装配属性！

在Spring中有三种装配的方式：

1. 在xml中显式的配置；
2. 在java中显式配置；
3. 隐式的自动装配bean【重要】

### 7.1 测试

环境搭建：创建项目，一个人有两个宠物！

```xml
    <bean id="cat" class="com.kuang.pojo.Cat"/>
    <bean id="dog" class="com.kuang.pojo.Dog"/>

    <bean id="people" class="com.kuang.pojo.People">
        <property name="name" value="小白莲"/>
        <property name="cat" ref="cat"/>
        <property name="dog" ref="dog"/>
    </bean>
12345678
```

### 7.2 ByName自动装配

```xml
        <!--
        byName：会自动在容器上下文中查找，和自己对象set方法后面的值对应的bean id！
            -->
        <bean id="people" class="com.kuang.pojo.People" autowire="byName">
            <property name="name" value="小白莲"/>
        </bean>
123456
```

### 7.3 ByType自动装配

```xml
        <!--
        byType：会自动在容器上下文中查找，和自己对象属性类型相同的bean！
            -->
        <bean id="people" class="com.kuang.pojo.People" autowire="byType">
            <property name="name" value="小白莲"/>
        </bean>
123456
```

小结：

- ByName的时候，需要保证所有bean的id唯一，并且这个bean需要和自动注入的属性的set方法的值一致！
- ByType的时候，需要保证所有bean的class唯一，并且这个bean需要和自动注入的属性的类型一致！

### 7.4 使用注解实现自动装配

jdk1.5支持的注解，Spring2.5就支持注解了！

要使用注解须知：

1. 导入约束

2. 配置注解的支持

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
   	        https://www.springframework.org/schema/beans/spring-beans.xsd
   	        http://www.springframework.org/schema/context
   	        https://www.springframework.org/schema/context/spring-context.xsd">
   		
   		<!--开启注解的支持    -->
           <context:annotation-config/>
   </beans>
   123456789101112
   ```

**@Autowired**

直接在属性上使用即可！也可以在set方法上使用！

使用Autowired我们就可以不用编写set方法了，前提是你这个自动配置的属性在IOC（Spring）容器中存在，且符合名字ByName！

**科普：**

```java
@Nullable 字段标记了了这个注解，说明这个字段可以为null;
1
public @interface Autowired {
    boolean required() default true;
}
123
```

测试代码

```java
public class People {
    //如果显式定义了Autowired的required属性为false，说明这个对象可以为null，否则不允许为空
    @Autowired(required = false)
    private Cat cat;
    @Autowired
    private Dog dog;
    private String name;
}
12345678
```

如果@Autowired自动装配的环境比较复杂，自动装配无法通过一个注解【@Autowired】完成的时候，我们可以使用@Qualifier(value = “xxx”)去配置@Autowired的使用，指定一个唯一的bean对象注入！

```java
public class People {
    @Autowired
    @Qualifier(value = "cat111")
    private Cat cat;
    @Autowired
    @Qualifier(value = "dog222")
    private Dog dog;
    private String name;
}
123456789
```

**@Resource**

```java
public class People {

    @Resource
    private Cat cat;

    @Resource
    private Dog dog;
}
12345678
```

小结：

@Resource和@Autowired的区别：

- 都是用来自动装配的，都可以放在属性字段上
- @Autowired通过byType的方式实现，而且必须要求这个对象存在！【常用】
- @Resource默认通过byName的方式实现，如果找不到名字，则通过byType实现！如果两个都找不到的情况下，就报错！【常用】
- 执行顺序不同：@Autowired通过byType的方式实现。

## 8、使用注解开发

在Spring4之后，要使用注解开发，必须要保证aop的包导入了
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201111134932372.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpNjQzOTM3NTc5,size_16,color_FFFFFF,t_70#pic_center)
 使用注解需要导入约束，配置注解的支持！

```xml
	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
	       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	       xmlns:context="http://www.springframework.org/schema/context"
	       xsi:schemaLocation="http://www.springframework.org/schema/beans
		        https://www.springframework.org/schema/beans/spring-beans.xsd
		        http://www.springframework.org/schema/context
		        https://www.springframework.org/schema/context/spring-context.xsd">
			
			<!--开启注解的支持    -->
	        <context:annotation-config/>
	</beans>
123456789101112
```

1. bean

2. 属性如何注入

   ```java
   //等价于<bean id="user" class="com.kuang.pojo.User"/>
   //@Component 组件
   
   @Component
   public class User {
   
       //相当于  <property name="name" value="白莲"/>
       @Value("白莲")
       public String name;
   }
   12345678910
   ```

3. 衍生的注解
    @Component有几个衍生注解，我们在web开发中，会按照mvc三层架构分层！

   ```md
   dao 【@Repository】
   service 【@Service】
   controller 【@Controller】
   123
   ```

   这四个注解功能都是一样的，都是代表将某个类注册到Spring中，装配Bean

4. 自动装配

   ```md
   - @Autowired：自动装配通过类型，名字。如果Autowired不能唯一自动装配上属性，则需要通过@Qualifier(value = "xxx")去配置。
   - @Nullable 字段标记了了这个注解，说明这个字段可以为null;
   - @Resource：自动装配通过名字，类型。
   123
   ```

5. 作用域

```java
@Component
@Scope("singleton")
public class User {

    //相当于  <property name="name" value="白莲"/>
    @Value("白莲")
    public String name;
}
12345678
```

1. 小结
    **xml与注解：**
    xml更加万能，适用于任何场合！维护简单方便
    注解不是自己类使用不了，维护相队复杂！
    **xml与注解最佳实践：**
    xml用来管理bean；
    注解只负责完成属性的注入；
    我们在使用的过程中，只需要注意一个问题：必须让注解生效，就需要开启注解的支持

```xml
    <!--指定要扫描的包，这个包下的注解就会生效-->
    <context:component-scan base-package="com.kuang"/>
    <!--开启注解的支持    -->
    <context:annotation-config/>
1234
```

## 9、使用Java的方式配置Spring

我们现在要完全不使用Spring的xml配置了，全权交给Java来做！
 JavaConfig是Spring的一个子项目，在Spring4之后，它成为了一个核心功能！
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201111151604718.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpNjQzOTM3NTc5,size_16,color_FFFFFF,t_70#pic_center)
 实体类

```java
//这里这个注解的意思，就是说明这个类被Spring接管了，注册到了容器中
@Component
public class User {
    private String name;

    public String getName() {
        return name;
    }

    @Value("黑心白莲") //属性注入值
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
123456789101112131415161718192021
```

配置文件

```java
// 这个也会Spring容器托管，注册到容器中，因为它本来就是一个@Component
// @Configuration代表这是一个配置类，就和我们之前看的beans.xml
@Configuration
@ComponentScan("com.kuang.pojo")
@Import(KuangConfig2.class)
public class KuangConfig {

    // 注册一个bean，就相当于我们之前写的一个bean标签
    // 这个方法的名字，就相当于bean标签中id属性
    // 这个方法的返回值，就相当于bean标签中的class属性
    @Bean
    public User user(){
        return new User(); // 就是返回要注入到bean的对象！
    }
}
123456789101112131415
```

测试类

```java
public class MyTest {
    public static void main(String[] args) {

        //如果完全使用了配置类方式去做，我们就只能通过 AnnotationConfig 上下文来获取容器，通过配置类的class对象加载！
        ApplicationContext context = new AnnotationConfigApplicationContext(KuangConfig.class);

        User user = context.getBean("user", User.class);
        System.out.println(user.getName());
    }
}
12345678910
```

这种纯Java的配置方式，在SpringBoot中随处可见！

## 10、代理模式（2020-11-12）（2020-11-20）

### 由于开题答辩耽搁了1个礼拜时间，现在从这回顾继续学习！！！

为什么要学习代理模式？因为这就是SpringAOP的底层！【SpringAOP和SpringMVC】

代理模式的分类：

- 静态代理
- 动态代理
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201112093129742.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpNjQzOTM3NTc5,size_16,color_FFFFFF,t_70#pic_center)

### 10.1 静态代理

角色分析：

- 抽象角色：一般会使用接口或者抽象类来解决
- 真实角色：被代理的角色
- 代理角色：代理真实角色，代理真实角色后，我们一般会做一些附属操作
- 客户：访问代理对象的人！

代码步骤：

1. 接口

```java
//租房
public interface Rent {
    public void rent();
}
1234
```

1. 真实角色

```java
//房东
public class Host implements Rent{
    public void rent() {
        System.out.println("房东出租房子！");
    }
}
123456
```

1. 代理角色

```java
public class Proxy implements Rent{
    private Host host;

    public Proxy() {
    }

    public Proxy(Host host) {
        this.host = host;
    }

    public void rent() {
        host.rent();
        seeHouse();
        sign();
        fee();
    }

    //看房
    public void seeHouse(){
        System.out.println("中介带着看房子！");
    }

    //签合同
    public void sign(){
        System.out.println("和中介签署租赁合同！");
    }

    //收费用
    public void fee(){
        System.out.println("中介收取费用！");
    }
}
1234567891011121314151617181920212223242526272829303132
```

1. 客户端访问代理角色

```java
public class Client {
    public static void main(String[] args) {
        //房东要出租房子
        Host host = new Host();
//        host.rent();

        //代理，中介帮房东出租房子，并且代理角色一般会有一些附属操作！
        Proxy proxy = new Proxy(host);

        //不用面对房东，直接找中介租房即可！
        proxy.rent();
    }
}
12345678910111213
```

代理模式的好处：

- 可以使真实角色的操作更加纯粹！不用去关注一些公共的业务
- 公共角色就交给代理角色！实现了业务的分工！
- 公共业务发生扩展的时候，方便集中管理！

缺点：

- 一个真实角色就会产生一个代理角色，代码量会翻倍，开发效率会变低~

### 10.2 加深理解

代码步骤：

1. 接口

```java
public interface UserService {
    public void add();
    public void delete();
    public void update();
    public void query();
}
123456
```

1. 真实角色

```java
//真实角色
public class UserServiceImpl implements UserService{
    public void add() {
        System.out.println("增加了一个用户！");
    }

    public void delete() {
        System.out.println("删除了一个用户！");
    }

    public void update() {
        System.out.println("修改了一个用户！");
    }

    public void query() {
        System.out.println("查询了一个用户！");
    }
}
123456789101112131415161718
```

1. 代理角色

```java
public class UserServiceProxy implements UserService{
    private UserServiceImpl userService;

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public void add() {
        log("add");
        userService.add();
    }

    public void delete() {
        log("delete");
        userService.delete();
    }

    public void update() {
        log("update");
        userService.update();
    }

    public void query() {
        log("query");
        userService.query();
    }

    public void log(String msg){
        System.out.println("[Debug] 使用了一个"+msg+"方法");
    }
}
12345678910111213141516171819202122232425262728293031
```

1. 客户端访问代理角色

```java
public class Client {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        UserServiceProxy proxy = new UserServiceProxy();
        proxy.setUserService(userService);

        proxy.delete();
    }
}
12345678910
```

聊聊AOP

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201112101554344.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpNjQzOTM3NTc5,size_16,color_FFFFFF,t_70#pic_center)

### 10.3 动态代理

- 动态代理和静态代理角色一样
- 动态代理的代理类是动态生成的，不是我们直接写好的！
- 动态代理分为两大类：基于接口的动态代理，基于类的动态代理   
  - 基于接口 — JDK动态代理【我们在这里使用】
  - 基于类：cglib
  - java字节码实现：javassist

需要了解两个类：Proxy：代理；InvocationHandler：调用处理程序。

代码步骤：

1. 接口

```java
public interface Rent {
    public void rent();
}
123
```

1. 真实角色

```java
public class Host implements Rent{
    public void rent() {
        System.out.println("房东要出租房子！");
    }
}
12345
```

1. ProxyInvocationHandler类

```java
//我们会用这个类，自动生成代理类！
public class ProxyInvocationHandler implements InvocationHandler {
    //被代理的接口
    private Rent rent;

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    //生成得到代理类
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                rent.getClass().getInterfaces(),this);
    }

    //处理代理实例，并返回结果
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //动态代理的本质，就是使用反射机制实现！
        Object result = method.invoke(rent, args);
        seeHose();
        fee();
        return result;
    }

    public void seeHose(){
        System.out.println("中介带着看房子！");
    }

    public void fee(){
        System.out.println("中介收取费用！");
    }
}
1234567891011121314151617181920212223242526272829303132
```

1. 测试

```java
public class Client {
    public static void main(String[] args) {
        //真实角色
        Host host = new Host();

        //代理角色：现在没有
        ProxyInvocationHandler pih = new ProxyInvocationHandler();

        //通过调用程序处理角色来处理我们要调用的接口对象！
        pih.setRent(host);
        Rent proxy = (Rent) pih.getProxy(); //这里的proxy就是动态生成的，我们并没有写
        proxy.rent();

    }
}
123456789101112131415
```

在此，我们可以提炼出ProxyInvocationHandler作为工具类

```java
//用这个类自动生成代理类！
public class ProxyInvocationHandler implements InvocationHandler {

    //被代理的接口
    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    //生成得到代理类
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                target.getClass().getInterfaces(),this);
    }

    //处理代理实例，并返回结果
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log(method.getName());
        Object result = method.invoke(target, args);
        return result;
    }

    public void log(String msg){
        System.out.println("[Debug] 使用了一个"+msg+"方法");
    }
}
123456789101112131415161718192021222324252627
```

动态代理的好处：

- 可以使真实角色的操作更加纯粹！不用去关注一些公共的业务
- 公共角色就交给代理角色！实现了业务的分工！
- 公共业务发生扩展的时候，方便集中管理！
- 一个动态代理类代理的是一个接口，一般就是对应的一类业务
- 一个动态代理类可以代理多个类，只要是实现了同一个接口即可！

## 11、AOP

### 11.1 什么是AOP

AOP（Aspect Oriented  Programming）意为：面向切面编程，通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术。AOP是OOP的延续，是软件开发中的一个热点，也是Spring框架中的一个重要内容，是函数式编程的一种衍生范型。利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201112130947442.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpNjQzOTM3NTc5,size_16,color_FFFFFF,t_70#pic_center)

### 11.2 AOP在Spring中的作用

提供声明式事务；允许用户自定义切面

- 横切关注点：跨越应用程序多个模块的方法或功能。即是，与我们业务逻辑无关的，但是我们需要关注的部分，就是横切关注点。如日志，安全，缓存，事务等等…
- 切面（ASPECT）：横切关注点被模块化的特殊对象。即，它是一个类。
- 通知（Advice）：切面必须要完成的工作。即，它是类中的一个方法。
- 目标（Target）：被通知对象。
- 代理（Proxy）：向目标对象应用通知之后创建的对象。
- 切入点（PointCut）：切面通知执行的“地点”的定义。
- 连接点（JointPoint）：与切入点匹配的执行点。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201112131307360.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpNjQzOTM3NTc5,size_16,color_FFFFFF,t_70#pic_center)
 SpringAOP中，通过Advice定义横切逻辑，Spring中支持5种类型的Advice：
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201120104439637.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpNjQzOTM3NTc5,size_16,color_FFFFFF,t_70#pic_center)
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201120104454887.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpNjQzOTM3NTc5,size_16,color_FFFFFF,t_70#pic_center)
 即AOP在不改变原有代码的情况下，去增加新的功能。

### 11.3 使用Spring实现AOP

【重点】使用AOP织入，需要导入一个依赖包！

```xml
<!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.4</version>
</dependency>
```

**方式一：** 使用Spring的API接口【主要是SpringAPI接口实现】

1. 在service包下，定义UserService业务接口和UserServiceImpl实现类

```java
public interface UserService {
    public void add();
    public void delete();
    public void update();
    public void select();
}
123456
public class UserServiceImpl implements UserService {
    public void add() {
        System.out.println("增加了一个用户！");
    }

    public void delete() {
        System.out.println("删除了一个用户！");
    }

    public void update() {
        System.out.println("更新了一个用户！");
    }

    public void select() {
        System.out.println("查询了一个用户！");
    }
}
1234567891011121314151617
```

1. 在log包下，定义我们的增强类，一个Log前置增强和一个AfterLog后置增强类

```java
public class Log implements MethodBeforeAdvice {

    //method: 要执行的目标对象的方法
    //args：参数
    //target：目标对象
    public void before(Method method, Object[] agrs, Object target) throws Throwable {
        System.out.println(target.getClass().getName()+"的"+method.getName()+"被执行了");
    }
}
123456789
public class AfterLog implements AfterReturningAdvice {

    //returnValue： 返回值
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("执行了"+method.getName()+"方法，返回结果为："+returnValue);
    }
}
1234567
```

1. 最后去spring的文件中注册 , 并实现aop切入实现 , 注意导入约束，配置applicationContext.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--注册bean-->
    <bean id="userService" class="com.kuang.service.UserServiceImpl"/>
    <bean id="log" class="com.kuang.log.Log"/>
    <bean id="afterLog" class="com.kuang.log.AfterLog"/>

    <!--方式一：使用原生Spring API接口-->
    <!--配置aop:需要导入aop的约束-->
    <aop:config>
        <!--切入点：expression：表达式，execution(要执行的位置！* * * * *)-->
        <aop:pointcut id="pointcut" expression="execution(* com.kuang.service.UserServiceImpl.*(..))"/>

        <!--执行环绕增加！-->
        <aop:advisor advice-ref="log" pointcut-ref="pointcut"/>
        <aop:advisor advice-ref="afterLog" pointcut-ref="pointcut"/>
    </aop:config>

</beans>
1234567891011121314151617181920212223242526
```

1. 测试

```java
public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        //动态代理代理的是接口：注意点
        UserService userService = (UserService) context.getBean("userService");

        userService.add();
//        userService.select();
    }
}
1234567891011
```

**方式二：** 自定义类来实现AOP【主要是切面定义】

1. 在diy包下定义自己的DiyPointCut切入类

```java
public class DiyPointCut {
    public void before(){
        System.out.println("======方法执行前======");
    }

    public void after(){
        System.out.println("======方法执行后======");
    }
}
123456789
```

1. 去spring中配置文件

```xml
    <!--方式二：自定义类-->
    <bean id="diy" class="com.kuang.diy.DiyPointCut"/>

    <aop:config>
        <!--自定义切面，ref 要引用的类-->
        <aop:aspect ref="diy">
            <!--切入点-->
            <aop:pointcut id="point" expression="execution(* com.kuang.service.UserServiceImpl.*(..))"/>
            <!--通知-->
            <aop:before method="before" pointcut-ref="point"/>
            <aop:after method="after" pointcut-ref="point"/>
        </aop:aspect>
    </aop:config>
12345678910111213
```

1. 测试

**方式三：** 使用注解实现！

1. 在diy包下定义注解实现的AnnotationPointCut增强类

```java
//声明式事务！
@Aspect //标注这个类是一个切面
public class AnnotationPointCut {

    @Before("execution(* com.kuang.service.UserServiceImpl.*(..))")
    public void before(){
        System.out.println("====方法执行前====");
    }

    @After("execution(* com.kuang.service.UserServiceImpl.*(..))")
    public void after(){
        System.out.println("====方法执行后====");
    }

    //在环绕增强中，我们可以给定一个参数，代表我们要获取处理切入的点；
    @Around("execution(* com.kuang.service.UserServiceImpl.*(..))")
    public void around(ProceedingJoinPoint jp) throws Throwable{
        System.out.println("环绕前");

        Signature signature = jp.getSignature();// 获得签名
        System.out.println("signature:"+signature);

        Object proceed = jp.proceed(); //执行方法

        System.out.println("环绕后");

        System.out.println(proceed);
    }

}
123456789101112131415161718192021222324252627282930
```

1. 在Spring配置文件中，注册bean，并增加支持注解的配置。

```xml
    <!--方式三：使用注解-->
    <bean id="annotationPointCut" class="com.kuang.diy.AnnotationPointCut"/>
    <!--开启注解支持！ JDK(默认是 proxy-target-class="false")  cglib（proxy-target-class="true"）-->
    <aop:aspectj-autoproxy/>
1234
```

1. 测试

## 12、整合Mybatis（2020-11-21）

步骤：

1. 导入相关jar包
   - junit
   - mybatis
   - mysql数据库
   - spring相关
   - aop织入器
   - mybatis-spring整合包【重点】在此还导入了lombok包。
   - 配置Maven静态资源过滤问题！

```xml
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.6</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.2.0.RELEASE</version>
        </dependency>
        <!--Spring操作数据库的话，还需要一个spring-jdbc
               -->
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.2.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.8.13</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.2</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.10</version>
        </dependency>
    </dependencies>

    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
    </build>
12345678910111213141516171819202122232425262728293031323334353637383940414243444546474849505152535455565758
```

1. 编写配置文件
2. 测试

### 12.1 回忆mybatis

1. 编写pojo实体类

```java
@Data
public class User {
    private int id;
    private String name;
    private String pwd;
}
123456
```

1. 编写实现mybatis的配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
       PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
       "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

   <typeAliases>
       <package name="com.kuang.pojo"/>
   </typeAliases>

   <environments default="development">
       <environment id="development">
           <transactionManager type="JDBC"/>
           <dataSource type="POOLED">
               <property name="driver" value="com.mysql.jdbc.Driver"/>
               <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=false&amp;useUnicode=true&amp;characterEncoding=utf8"/>
               <property name="username" value="root"/>
               <property name="password" value="123456"/>
           </dataSource>
       </environment>
   </environments>

   <mappers>
       <package name="com.kuang.dao"/>
   </mappers>
</configuration>
1234567891011121314151617181920212223242526
```

1. 编写UserMapper接口

```java
public interface UserMapper {
    public List<User> selectUser();
}
123
```

1. 编写UserMapper.xml文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.kuang.mapper.UserMapper">

    <!--sql-->
    <select id="selectUser" resultType="user">
        select * from mybatis.user
    </select>
</mapper>
123456789101112
```

1. 测试

```java
@Test
public void selectUser() throws IOException {

   String resource = "mybatis-config.xml";
   InputStream inputStream = Resources.getResourceAsStream(resource);
   SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
   SqlSession sqlSession = sqlSessionFactory.openSession();

   UserMapper mapper = sqlSession.getMapper(UserMapper.class);

   List<User> userList = mapper.selectUser();
   for (User user: userList){
       System.out.println(user);
  }

   sqlSession.close();
}
1234567891011121314151617
```

### 12.2 Mybatis-Spring

**什么是MyBatis-Spring？**

MyBatis-Spring 会帮助你将 MyBatis 代码无缝地整合到 Spring 中。

文档链接：http://mybatis.org/spring/zh/index.html

如果使用 Maven 作为构建工具，仅需要在 pom.xml 中加入以下代码即可：

```xml
        <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.2</version>
        </dependency>
123456
```

**整合实现一：**

1. 引入Spring配置文件spring-dao.xml

```xml
<?xml version="1.0" encoding="GBK"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd

</beans>
1234567
```

1. 配置数据源替换mybaits的数据源

```xml
    <!--DataSource:使用Spring的数据源替换Mybatis的配置 c3p0 dbcp druid
    我们这里使用Spring提供的JDBC：-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=false&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
        <property name="username" value="root"/>
        <property name="password" value="123456"/>
    </bean>
12345678
```

1. 配置SqlSessionFactory，关联MyBatis

```xml
    <!--sqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource" />
        <!--关联mybatis配置文件-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <property name="mapperLocations" value="classpath:com/kuang/mapper/*.xml"/>
    </bean>
1234567
```

1. 注册sqlSessionTemplate，关联sqlSessionFactory

```xml
    <!--SqlSessionTemplate:就是我们使用的sqlSession-->
    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <!--只能使用构造器注入sqlSessionFactory，因为它没有set方法-->
        <constructor-arg index="0" ref="sqlSessionFactory" />
    </bean>
12345
```

1. 需要UserMapper接口的UserMapperImpl 实现类，私有化sqlSessionTemplate

```java
public class UserMapperImpl implements UserMapper {

    //我们的所有操作，都使用sqlSession来执行，在原来，现在都使用SqlsessionTemplate
    private SqlSessionTemplate sqlSession;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<User> selectUser() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.selectUser();
    }
}
1234567891011121314
```

1. 将自己写的实现类，注入到Spring配置文件中。

```xml
    <bean id="userMapper" class="com.kuang.mapper.UserMapperImpl">
        <property name="sqlSession" ref="sqlSession"/>
    </bean>
123
```

1. 测试使用即可！

```java
    @Test
    public void test () throws IOException {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        for (User user : userMapper.selectUser()) {
            System.out.println(user);
        }
    }
12345678910
```

结果成功输出！现在我们的Mybatis配置文件的状态！发现都可以被Spring整合！

```xml
<?xml version="1.0" encoding="GBK" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!--configuration core file-->
<configuration>
    
    <typeAliases>
        <package name="com.kuang.pojo"/>
    </typeAliases>

</configuration>
123456789101112
```

**整合实现二：**

mybatis-spring1.2.3版以上的才有这个，官方文档截图：

dao继承Support类 , 直接利用 getSqlSession() 获得 , 然后直接注入SqlSessionFactory . 比起整合方式一 , 不需要管理SqlSessionTemplate , 而且对事务的支持更加友好 . 可跟踪源码查看。
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201122213331963.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpNjQzOTM3NTc5,size_16,color_FFFFFF,t_70#pic_center)
 测试：

1. 将我们上面写的UserMapperImpl修改一下

```java
public class UserMapperImpl extends SqlSessionDaoSupport implements UserMapper {

    public List<User> selectUser() {
        
        return getSqlSession().getMapper(UserMapper.class).selectUser();
    }
}

12345678
```

1. 注入到Spring配置文件中。

```xml
    <bean id="userMapper" class="com.kuang.mapper.UserMapperImpl">
        <property name="sqlSessionFactory" ref="sqlSessionFactory" />
    </bean>
123
```

1. 测试

```java
    @Test
    public void test () throws IOException {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        for (User user : userMapper.selectUser()) {
            System.out.println(user);
        }
    }
12345678910
```

## 13、声明式事务

### 13.1 回顾事务

- 把一组业务当成一个业务来做；要么都成功，要么都失败！
- 事务在项目开发中，十分的重要，涉及到数据的一致性问题，不能马虎！
- 确保完整性和一致性。

**事务ACID原则：**

- 原子性（atomicity）   
  - 事务是原子性操作，由一系列动作组成，事务的原子性确保动作要么全部完成，要么完全不起作用。
- 一致性（consistency）   
  - 一旦所有事务动作完成，事务就要被提交。数据和资源处于一种满足业务规则的一致性状态中。
- 隔离性（isolation）   
  - 可能多个事务会同时处理相同的数据，因此每个事务都应该与其他事务隔离开来，防止数据损坏。
- 持久性（durability）   
  - 事务一旦完成，无论系统发生什么错误，结果都不会受到影响。通常情况下，事务的结果被写到持久化存储器中。

**测试：**

将上面的代码拷贝到一个新项目中
 在之前的案例中，我们给userMapper接口新增两个方法，删除和增加用户；

```java
//添加一个用户
int addUser(User user);

//根据id删除用户
int deleteUser(int id);
12345
```

UserMapper文件，我们故意把 deletes 写错，测试！

```xml
<insert id="addUser" parameterType="com.kuang.pojo.User">
insert into user (id,name,pwd) values (#{id},#{name},#{pwd})
</insert>

<delete id="deleteUser" parameterType="int">
deletes from user where id = #{id}
</delete>
1234567
```

编写接口的UserMapperImpl实现类，在实现类中，我们去操作一波

```java
public class UserMapperImpl extends SqlSessionDaoSupport implements UserMapper {


    //增加一些操作
    public List<User> selectUser() {
        User user = new User(5, "小王", "185161");
        UserMapper mapper = getSqlSession().getMapper(UserMapper.class);
        mapper.addUser(user);
        mapper.deleteUser(5);

        return mapper.selectUser();
    }
    
    //新增
    public int addUser(User user) {
        return getSqlSession().getMapper(UserMapper.class).addUser(user);
    }

    //删除
    public int deleteUser(int id) {
        return getSqlSession().getMapper(UserMapper.class).deleteUser(id);
    }
}
1234567891011121314151617181920212223
```

测试

```java
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);

        for (User user : userMapper.selectUser()) {
            System.out.println(user);
        }
    }
12345678910
```

报错：sql异常，delete写错了

结果 ：数据库结果显示插入成功！

没有进行事务的管理；我们想让他们都成功才成功，有一个失败，就都失败，我们就应该需要事务！

以前我们都需要自己手动管理事务，十分麻烦！

但是Spring给我们提供了事务管理，我们只需要配置即可；

### 13.2 Spring中的事务管理

Spring在不同的事务管理API之上定义了一个抽象层，使得开发人员不必了解底层的事务管理API就可以使用Spring的事务管理机制。Spring支持编程式事务管理和声明式的事务管理。

**编程式事务管理**

- 将事务管理代码嵌到业务方法中来控制事务的提交和回滚
- 缺点：必须在每个事务操作业务逻辑中包含额外的事务管理代码

**声明式事务管理**

- 一般情况下比编程式事务好用。
- 将事务管理代码从业务方法中分离出来，以声明的方式来实现事务管理。
- 将事务管理作为横切关注点，通过aop方法模块化。Spring中通过Spring AOP框架支持声明式事务管理。

**1. 使用Spring管理事务，注意头文件的约束导入 : tx**

```xml
xmlns:tx="http://www.springframework.org/schema/tx"

http://www.springframework.org/schema/tx
http://www.springframework.org/schema/tx/spring-tx.xsd">
1234
```

**2. JDBC事务**

```xml
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
123
```

**3. 配置好事务管理器后我们需要去配置事务的通知**

```xml
    <!--结合AOP实现事务的织入-->
    <!--配置事务通知-->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <!--给那些方法配置事务-->
        <!--配置事务的传播特性： new -->
        <tx:attributes>
            <tx:method name="add" propagation="REQUIRED"/>
            <tx:method name="delete" propagation="REQUIRED"/>
            <tx:method name="update" propagation="REQUIRED"/>
            <tx:method name="query" read-only="true"/>
            <tx:method name="*" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>
12345678910111213
```

**spring事务传播特性：**
 事务传播行为就是多个事务方法相互调用时，事务如何在这些方法间传播。spring支持7种事务传播行为：

- propagation_requierd：如果当前没有事务，就新建一个事务，如果已存在一个事务中，加入到这个事务中，这是最常见的选择。
- propagation_supports：支持当前事务，如果没有当前事务，就以非事务方法执行。
- propagation_mandatory：使用当前事务，如果没有当前事务，就抛出异常。
- propagation_required_new：新建事务，如果当前存在事务，把当前事务挂起。
- propagation_not_supported：以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
- propagation_never：以非事务方式执行操作，如果当前事务存在则抛出异常。
- propagation_nested：如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与propagation_required类似的操作。

Spring 默认的事务传播行为是 PROPAGATION_REQUIRED，它适合于绝大多数的情况。

就好比，我们刚才的几个方法存在调用，所以会被放在一组事务当中！

**4. 配置AOP，导入aop的头文件**

```xml
    <!--配置事务切入-->
    <aop:config>
        <aop:pointcut id="txPointCut" expression="execution(* com.kuang.mapper.*.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>
    </aop:config>
12345
```

**5. 删掉刚才插入的数据，再次测试！**

```java
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);

        for (User user : userMapper.selectUser()) {
            System.out.println(user);
        }
    }
12345678910
```

**思考：**

为什么需要事务？

- 如果不配置事务，可能存在数据提交不一致的情况；
- 如果我们不在Spring中去配置声明式事务，我们就需要在代码中手动配置事务！
- 事务在项目的开发中十分重要，涉及到数据的一致性和完整性问题，不容马虎！

## 写给自己的话

一方面，跟着视频学习，并记录下学习笔记，方便以后复习回顾。另一方面，标注时间只为了解自己的学习进度，宁保质量不比速度，学习路程还很遥远，继续加油坚持！！！