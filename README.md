# XTTDR
学通天地人在线学习平台😁

一个Spring Boot  + Vue的入门项目，虽然还有一些不完善的地方，但是由于时间关系以及本人十分随意就暂且写到这个程度了。🙉

虽说是一个入门项目，但是完完整整写下来确实得花一番功夫，本项目是一个软件课程设计项目，意在运用一点MVC架构知识搭建一个稍微完整一点的项目。本项目具有完整的设计文档，是我们小组~~呕心沥血之作~~，若对各位有一点点帮助的话，还恳请各位多多Star❤

最后，祝各位心明眼亮，一帆风顺🎉

PS. 禁止任何形式商用，搞诈骗的每天死一个野爹。

联系作者：

QQ：37179423

e-mail：dailing0124@gmail.com

## 环境i

> java 1.8
>
> mysql 8.0
>
> node 14.17.5
>
> npm 6.14.14
>
> vue/cli 4.5.13
>

## 后端第三方库

> Mybatis-plus
>
> druid

## 前端第三方库

> elementui
>
> wangeditor
>
> echart
>
> vuex
>
> axios
>
> router

## 项目本地部署调试

### 前端

`npm install`

`npm run serve`

## 后端

直接运行springboot即可

## 项目部署到服务器

以Ubuntu20.04为例：

打包前端：

`npm run build`

打包后端：

用maven执行clean，compile，package获得jar

服务器需要安装：

> java 1.8
>
> nginx
>
> mysql 8.0
>
> node
>
> npm

### 启动后端

java -jar xxx.jar --spring.profiles.active=prod

### 启动前端

由于是使用nginx代理，配置好nginx之后重启nginx即可

本次项目我的nginx配置如下：

在/etc/nginx中：

nginx.config:

    client_max_body_size 100M;
    client_body_buffer_size 100M;
    include /etc/nginx/sites-enabled/xttdr;

sites-available：

xttdr：

```
server{
        listen 80;
        server_name localhost;
        charset utf-8;
        location / {
        	root /home/dl/Desktop/dist;
        	index index.html index.htm;
        	try_files $uri $uri/ /index.html;
		}
        location /api {
        	proxy_pass http://localhost:9090/;
		}	
}

```

然后将xttdr硬链接到sites-enabled

启动nginx：`service nginx restart`

平滑重启：`/usr/sbin/nginx -s reload`

### 数据库配置

Ubuntu20.04的Mysql 8.0有不少坑，比如用户密码，ip绑定，端口等都需有进行相应的配置。

/etc/mysql/mysql.conf.d/mysqld.cnf：

```
user            = root
port            = 3306
bind-address            = 127.0.0.1
mysqlx-bind-address     = 127.0.0.1
```

