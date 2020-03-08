# 在线管理系统
- 端口：8998端口
- 前端启动后访问地址：

## 框架搭建环境
````
- 编译器：IDEA 2019.3
- Maven：3.6.0
- JDK：1.8.0_231
- 系统：Win 10
````
## 数据库
### MySQL
```
- 版本：mysql-8.0.17-winx64.zip
- 账户名:root
- 密码：gisnci
- 端口：3306
- 数据库名：ocean_online
- 数据库表结构：见focean-online项目根目录/dbfile/mysql/
```


## 前端组件
```
- Vue.js
- Node.js
- 
```

## 其他软件
````
- Git客户端：Git-2.21.0 for Windows
- Navicat：Navicat 12 for MySQL Windows版
- Postman
- 
````


## 组件功能支持说明
### 1. 处理http/json 请求
### 2. 静态资源存储访问
前端开发时，静态资源存储需遵循以下规则：
```
Recommended！
1. 静态资源文件、页面文件均放在放在src/main/resources/static目录下；

【注意事项】
开发时，放上述src/main/resources/static目录；
打包后将src/main/resources/static目录下的左右文件放于ocean-online-manage-0.0.1-SNAPSHOT.jar同级目录的online/visual目录下（注意不包含static目录）。
目录结构如：
- ocean-online-manage
    - application.yml
    - ocean-online-manage-0.0.1-SNAPSHOT.jar
    - start-ocean-online-manage.sh
    - online/visual/所有静态资源

其中，所有静态资源即“开发工程中src/main/resources/static目录下的静态文件”。
可动态修改其中的静态文件，不重启服务即可生效。
```

```
Deprecated！
1. 静态资源文件放在src/main/resources/static目录下；
2. 页面文件放在src/main/resources/resources目录下；
```

### 3. 视图模版引擎
一般把页面放在templates中，静态资源放在static中。页面引用这些静态资源时,注意相对路径，因为//*，所以不需要引用到static那级。


### 4. 其他
- libs/springfox-swagger-ui-2.7.0.jar中的swagger-ui.html源码已修改，增加了汉化脚本。
