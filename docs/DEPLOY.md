# 打包、部署、启动、使用
## 一、打包
### （一）打包方式
打包方式有两种：
- IntelliJ IDEA工具。
- 命令行执行maven打包命令，需遵循约定的打包顺序。

#### - IntelliJ IDEA
1. Maven工具栏中选择应用模块；
2. 展开模块下的Lifecycle；
3. 点击执行命令操作，如clean、package、intall等。

#### - 命令行
1. 在各个模块源码根目录执行命令；
2. 两命令选一使用
```
# 不执行但编译测试用例类，生成相应的class文件至target/test-classes下
mvn clean package -DskipTests
# 不执行也不编译测试用例类
mvn clean package -Dmaven.test.skip=true（推荐）

注：
-DskipTests、-Dmaven.test.skip=true表示希望不执行测试用例（仅限非正式发布时）
```

### （二）打包顺序
[如果打包时执行测试检测工作，务必遵循以下顺序]

给出了模块打包顺序以及通过maven命令打包时的命令，打包结果在各自模块的target目录下***.jar。

1. 打包ocean-online-manage服务
```
cd ocean-online-manage
mvn clean package -Dmaven.test.skip=true

```


## 二、部署启动
Linux上可以通过nohup或者supervisor（推荐）进行启动。

### 1. 启动ocean-online-manage
**0）** 部署目录组织
```
ocean-online-manage，含以下文件
    ocean-online-manage-0.0.1-SNAPSHOT.jar
    application.yml
    application-mysql.yml
    start-ocean-online-manage.sh
    online/visual/工程所有静态资源

其中，所有静态资源即“开发工程中src/main/resources/static目录下的静态文件”。
可动态修改其中的静态文件，不重启服务即可生效。
```

**1）** 配置查看
 ```
 [application.yml]
    data.catalog: 数据存放的本地目录


 [application-mysql.yml]
    spring.datasource.url
    spring.datasource.username
    spring.datasource.password
使用的mysql8.0.17版本，如果使用较低版本，请检测驱动配置。
 ```

**2）** 启动
 ```
 java -jar ocean-online-manage-0.0.1-SNAPSHOT.jar
 或者
 nohup java -jar ocean-online-manage-0.0.1-SNAPSHOT.jar > console-ocean-online-manage.file 2>&1 &
 ```
 或者直接执行启动脚本
 ```
 start-ocean-online-manage.sh
 ```

**3）** 检测
 - 访问http://IP:PORT/login.html(目前静态页面)
 - 访问http://IP:PORT/index.html(数据浏览页面)
 - 访问http://IP:PORT/swagger-ui.html(服务接口页面)



## 三、其他说明
网关和服务注册中心之间的交互需要一些时间才能生效。如果在启动所有微服务后的第一分钟内使用应用，则可能会从网关获得一些服务器错误（HTTP状态代码500）。

服务注册中心需要更多的时间来完成它的工作。


