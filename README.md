[![Maven Central](https://img.shields.io/maven-central/v/com.github.adrninistrator/java-behavior-control.svg)](https://search.maven.org/artifact/com.github.adrninistrator/java-behavior-control/)

[![Apache License 2.0](https://img.shields.io/badge/license-Apache%20License%202.0-green.svg)](https://github.com/Adrninistrator/java-behavior-control/blob/master/LICENSE)


可参考文档：
[https://www.freebuf.com/sectool/256099.html](https://www.freebuf.com/sectool/256099.html)

# 1. 前言

Java程序所执行的业务层面的操作，可以通过查看应用日志了解，但其系统行为缺乏相应的日志，难以根据特定日志快速判定Java程序是否出现了非预期的行为。

以下提供对Java程序行为进行监控的轻量级可快速上线的组件，可以用于辅助判定是否出现了入侵行为。

- 提供组件的目的

Java程序可能存在系统漏洞，当漏洞被利用时，可能会产生未知的行为，针对关注的Java程序的未知行为进行监控并告警，可以及时发现系统被入侵的情况。

- 行为监控组件源代码

行为监控组件源代码可从 [https://github.com/Adrninistrator/java-behavior-control](https://github.com/Adrninistrator/java-behavior-control) 下载。

- 可以达到什么效果

对Java程序的创建进程、网络行为进行监控，当出现不在白名单内的未知的行为时，可以由默认处理类，或自定义处理类进行处理。

默认处理类会进行告警，告警的内容包括对应的行为，以及线程调用堆栈，便于分析系统是否被攻击，或漏洞触发的执行过程。

自定义处理类中可以实现自己需要的功能，例如调用监控系统接口、按照自定义格式记录日志、阻断行为等。对未知行为进行阻断存在风险，可能会影响正常功能，需要慎重处理。

- 建议引入行为监控组件的系统

建议对接入互联网的系统引入行为监控组件，原因如下：

a. 外层系统相对更容易被入侵；

b. 减少需要配置及观察的机器数量。

- 行为监控原理

Java提供了安全管理器机制，创建进程、网络行为等相关的API在执行前会调用安全管理器进行权限检查。

可实现自定义的安全管理器并进行设置，当相关的行为发生时，会触发自定义安全管理器的检查方法，可以起到监控或阻断的作用。

- 是否会对正常功能造成影响

JDK 1.8中有约410处对安全管理器的调用，行为监控组件只是实现了安全管理器并增加了判断与打印日志，不影响原有功能。

若存在多个安全管理器，也会调用原有安全管理器的检测方法，避免覆盖已存在的安全管理器。

行为监控组件支持在配置文件发生变化时动态读取，经测试反复加载时（包含1万行的配置文件，读取8万次以上），GC情况正常。

- 是否会影响性能

不会对系统性能造成影响。经测试，白名单文件中包含1万行数据，使用100并发执行会被监控的行为，当不需要记录告警日志时，行为监控组件处理耗时0 ms；当需要记录告警日志时，行为监控组件处理耗时约30 ms。每种未知行为的最大告警次数可配置。

- 是否会带来其他安全问题

行为监控组件带来其他安全问题的可能性极小，未使用序列化与反序列化，未提供前端可访问的接口，未执行系统命令，未执行网络操作。

行为监控组件的编译依赖只有org.slf4j\:jcl-over-slf4j\:1.7.30与commons-io\:commons-io\:2.7，不存在已知漏洞。

- 是否会频繁告警

理论上不会出现频繁告警。Java程序产生的需要关注的行为中，正常行为是可以穷举的，范围并不大，将已知的行为配置到白名单后，可以尽可能避免误报。

例如Java程序通常很少需要创建进程（除非由于业务功能需要主动调用）；需要监听的端口、需要允许访问的客户端IP、需要访问的远程服务IP与端口，都是固定的，很少发生改变；设置安全管理器的操作极少被调用（除非由于业务功能需要主动调用）。

- Java程序如何使用行为监控组件

a. 在工程中添加行为监控组件的Maven依赖；

b. 在初始化时调用行为监控组件的方法；

c. 在日志配置中增加行为监控组件相关的配置。

- 白名单如何配置

行为监控组件针对不同类型的行为使用单独的白名单配置文件。

配置文件可在Java程序启动前配置，或在Java程序执行时配置，当配置文件发生变化时，行为监控组件会重新读取配置文件，检测配置文件变化的周期可以配置。

- 监控如何配置

依赖实际使用的监控系统。

若监控系统支持对日志文件内容进行监控，可监控行为监控组件输出的日志文件；

若监控系统只支持其他的方式进行监控，则可在自定义处理类中进行处理。例如监控系统只支持通过HTTP接口进行上报时，可在自定义处理类中调用其接口。

- 已知的正常行为

与实际的应用有关，例如当用户通过公网访问HTTP服务时，需要访问Tomcat提供的HTTP端口；当应用访问数据库服务时，需要访问数据库提供的端口；当应用访问其他系统的服务时，需要访问其他系统提供的端口。

# 2. 行为监控组件上线步骤

a. 修改Java程序代码，引入行为监控组件；

b. 测试环境测试，验证对正常功能是否有影响，观察正常的行为；

c. 生产环境灰度发布，验证对正常功能是否有影响；

d. 生产环境全量发布，观察正常的行为；

e. 将正常行为导入白名单配置。

# 3. 行为监控组件引入方法

## 3.1. maven依赖

```
com.github.adrninistrator:java-behavior-control:0.0.1
```

## 3.2. 启用行为监控组件

可在Java Web应用原有的初始化操作中，如ServletContainerInitializer、SmartLifecycle等实现类中调用行为监控组件的启用方法。

行为监控组件提供的启用方法为BehaviorControl.register()，如下所示：

```java
public static void register(String confPath)
```

```java
public static void register(String confPath, BehaviorHandlerInterface handler, long monitorInterval, String dftAlertFlag, int maxAlertTimes)
```

参数说明如下：

|参数|说明|
|---|---|
|confPath|保存配置文件的目录，结尾可包含或不包含目录分隔符“/"”\\"。配置文件某行的内容若前后包含空格，在读取时会被去除|
|handler|自定义处理类实例|
|monitorInterval|监控配置文件发生变化的时间间隔，单位为毫秒，默认：30000L，代表30秒|
|dftAlertFlag|默认的告警关键字，用于在出现异常时告警，默认：OnError|
|maxAlertTimes|某个未知行为的告警最大次数，可为0，默认：3|

前一个方法对于参数2至参数5分别使用默认值：30秒、new DefaultAlertBehaviorHandler()，“OnError”、3次，后一个方法可对参数2至参数4进行设置。可根据需要调用。

### 3.2.1. 自定义处理类

自定义处理类需要实现BehaviorHandlerInterface接口，需要实现的方法及说明如下：

|方法|说明|
|---|---|
|handleExec|监控到创建进程行为时进行处理|
|handleListen|监控到监听端口行为时进行处理|
|handleAccept|监控到接受Socket连接行为时进行处理|
|handleConnect|监控到建立Socket连接行为时进行处理|
|handleSetSecurityManager|监控到设置安全管理器行为时进行处理|

以上方法的具体参数可参考代码中的注释，在方法中若抛出SecurityException异常，可以阻断对应行为。

## 3.3. 参数设置

## 3.4. 日志配置

若使用log4j2，可参考以下格式对行为监控组件的日志进行配置：

```xml
在<Appenders>添加：

<RollingFile name="behaviorControlAppender" fileName="${sys:index.log.home}/behaviorControl.log" filePattern="${sys:index.log.home}/behaviorControl.log.%d{yyyy-MM-dd}.%i.log.gz">
    <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5p %c{1}.%M(%F:%L - %m%n" />
    <Policies>
    <TimeBasedTriggeringPolicy />
    <SizeBasedTriggeringPolicy size="1024 MB" />
    </Policies>
    <DefaultRolloverStrategy max="512" />
</RollingFile>
```

```xml
在<Loggers>添加：

<AsyncLogger name="behaviorControl" level="info" additivity="false" includeLocation="true">
    <AppenderRef ref="behaviorControlAppender" />
</AsyncLogger>
```

## 3.5. 配置文件目录建议

建议将白名单配置文件保存在不包含其他文件的目录中，因为需要对配置文件目录中的文件变化进行监控。

行为监控组件在初始化时会创建配置文件目录，不需要手工创建。

若应用部署在docker中，则配置文件目录需要使用docker对应的母机会挂载的目录，防止进程重启后文件丢失。

因此建议配置文件目录使用应用日志目录的“config”子目录，示例如下：

```java
System.getProperty("index.log.home") + File.separator + "config"
```

# 4. 未知行为默认告警文件与关键字

行为监控组件记录的默认日志文件名为“behaviorControl.log”。

不同类型的未知行为对应的默认告警关键字如下：

|行为类型|默认告警关键字|
|---|---|
|创建进程|behv_alert_exec|
|监听端口|behv_alert_listen|
|接受Socket连接|behv_alert_accept|
|建立Socket连接|behv_alert_connect|
|设置安全管理器|behv_alert_setsecman|

未知行为进行告警日志中，在对未知行为的操作内容前后均记录了“###”，便于提取。

# 5. 未知行为告警说明

当行为监控组件发现以下不在白名单中的未知行为出现，且对应操作内容的告警次数未超过允许的最大值时，会在日志中输出告警信息。

## 5.1. 创建进程

告警示例如下所示：

```
exec ###calc.exe### behv_alert_exec
```

对于未知的创建进程行为，若不在白名单内，判断对应行为的告警次数是否超过最大值，用于判断的操作内容为被执行程序的相对路径或绝对路径，如上示例中的“calc.exe”。

即对于每个被执行的程序，最多产生指定次数的告警。

## 5.2. 监听端口

告警示例如下所示：

```
listen ###8888### behv_alert_listen
```

对于监听端口的未知行为，若不在白名单内，判断对应行为的告警次数是否超过最大值，用于判断的操作内容为监听的端口，如上示例中的“8888”。

即对于每个监听的端口，最多产生指定次数的告警。

## 5.3. 接受Socket连接

告警示例如下所示：

```
accept ###10.0.0.1### behv_alert_accept
```

对于未知的接受Socket连接行为，若不在白名单内，判断对应行为的告警次数是否超过最大值，用于判断的操作内容为对应的客户端IP，如上示例中的“10.0.0.1”（只能获取到客户端使用的端口，无法获取到客户端连接的本机端口，因此不处理端口）。

即对于每个客户端IP，最多产生指定次数的告警。

## 5.4. 建立Socket连接

告警示例如下所示：

```
connect ###10.0.0.1:8888### behv_alert_connect
```

对于未知的建立Socket连接行为，若不在白名单内，判断对应行为的告警次数是否超过最大值，用于判断的操作内容为对应的服务器IP与端口，如上示例中的“10.0.0.1:8888”。

即对于每个服务器IP及端口，最多产生指定次数的告警。

## 5.5. 设置安全管理器

告警示例如下所示：

```
setSecurityManager behv_alert_setsecman
```

对于设置安全管理器行为，最多产生指定次数的告警（对于设置安全管理器行为，没有对应的被操作对象，需要根据调用堆栈分析）。

# 6. 白名单文件

行为监控组件在初始化时，若白名单文件不存在，会创建白名单文件，不需要手动创建。

白名单文件中每条记录占一行。

## 6.1. 白名单文件-创建进程

创建进程行为的白名单文件名为“exec.conf”。

格式为：\[cmd\]，即被执行程序的相对路径或绝对路径。

## 6.2. 白名单文件-监听端口

监听端口行为的白名单文件名为“listen.conf”。

格式为：\[port\]，即监听的端口。

## 6.3. 白名单文件-接受Socket连接

接受Socket连接行为的白名单文件名为“accept.conf”。

格式为：\[ip\]，即对应的客户端IP。

## 6.4. 白名单文件-建立Socket连接

建立Socket连接行为的白名单文件名为“connect.conf”。

格式为：\[ip\]:\[port\]，即对应的服务器IP与端口。

# 7. 常用端口

- Tomcat的shutdown端口

在Tomcat的Server.xml文件中配置，如“\<Server port="8005"”，各应用配置的端口不同。

- Tomcat的JMX端口

若Tomcat应用启动脚本中有配置启动JMX，则会监听两个随机端口，监听JMX端口比启用行为监控组件要早，不会被监控到。

# 8. 白名单配置

## 8.1. 白名单配置-创建进程

在创建进程行为的白名单文件中，需要配置Java程序会启动的进程的相对路径或绝对路径，若不会启动进程则不需要配置。

## 8.2. 白名单配置-监听端口

在监听端口行为的白名单文件中，需要配置Tomcat的shutdown端口，可能会被行为监控组件监控到，与行为监控组件的启用时机有关。

## 8.3. 白名单配置-接受Socket连接

对于接受Socket连接的行为，若对应的客户端IP为"127.0.0.1"或“localhost”，不会告警，也不需要添加至白名单内。

当Tomcat使用APR Connector时，访问Tomcat的HTTP端口不会使Tomcat发生Socket的accept行为，此类情况不需要在白名单中对对应的客户端IP进行配置。包括用户通过正常业务功能访问Tomcat的HTTP接口等情况。

可参考附录“判断Tomcat是否使用APR Connector”。

## 8.4. 白名单配置-建立Socket连接

对于建立Socket连接的行为，若对应的服务器IP为"127.0.0.1"或“localhost”，不会告警，也不需要添加至白名单内。

# 9. 从日志中提取行为到白名单的脚本

可从日志中提取行为到白名单，提取脚本如下所示，可在日志目录中执行。

## 9.1. 从日志提取到白名单-创建进程

```shell
mv config/exec.conf config/exec.conf.bak
grep behv_alert_exec behaviorControl.log | awk -F '###' '{print $2}' | sort | uniq | sort >> config/exec.conf.bak
cat config/exec.conf.bak | sort | uniq | sort > config/exec.conf
```

## 9.2. 从日志提取到白名单-监听端口

```shell
mv config/listen.conf config/listen.conf.bak
grep behv_alert_listen behaviorControl.log | awk -F '###' '{print $2}' | sort | uniq | sort >> config/listen.conf.bak
cat config/listen.conf.bak | sort | uniq | sort > config/listen.conf
```

## 9.3. 从日志提取到白名单-接受Socket连接

```shell
mv config/accept.conf config/accept.conf.bak
grep behv_alert_accept behaviorControl.log | awk -F '###' '{print $2}' | sort | uniq | sort >> config/accept.conf.bak
cat config/accept.conf.bak | sort | uniq | sort > config/accept.conf
```

## 9.4. 从日志提取到白名单-建立Socket连接

```shell
mv config/connect.conf config/connect.conf.bak
grep behv_alert_connect behaviorControl.log | awk -F '###' '{print $2}' | sort | uniq | sort >> config/connect.conf.bak
cat config/connect.conf.bak | sort | uniq | sort > config/connect.conf
```

# 10. 附录

## 10.1. 判断Tomcat是否使用APR Connector

使用jmap命令查看Tomcat加载的AbstractProtocol实现类，可以确定Tomcat使用的Connector类型。

Linux系统可使用以下命令查看：

```shell
jmap -histo [PID]|grep Http|grep Protocol
```

Windows系统可使用以下命令查看：

```shell
jmap -histo [PID]|findstr Http|findstr Protocol
```

当Tomcat使用Java HTTP Connector时，以上命令执行结果会出现“org.apache.coyote.http11.Http11Protocol、org.apache.coyote.http11.Http11Protocol$Http11ConnectionHandler”。

当Tomcat使用APR Connector时，以上命令执行结果会出现“org.apache.coyote.http11.Http11AprProtocol、org.apache.coyote.http11.Http11AprProtocol$Http11ConnectionHandler”。
