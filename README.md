# sk-spring-cloud-master

## 简述
    定位用当下最流行的springcloud技术，搭建了一套易理解、高可用、高扩展的分布式微服务框架，方便实现快速开发。
## 组织结构

sk-spring-cloud-master</br>
├── sk-base -- 公共模块</br>
├───── common-base -- 公共核心模块（mybatis等）
├───── spring-social -- 三方登陆模块
├── spring-cloud-project -- springcloud相关服务
├───── spring-cloud-client-demo --接口调用方demo 
├───── spring-cloud-config-server -- 配置文件服务器
├───── spring-cloud-gateway -- 网关
├───── spring-cloud-oauth2-server -- 基于oauth2协议相关demo
├───── spring-cloud-server-demo -- 接口提供方demo


## 技术选型

### 后端
- JDK：1.8+
- 数据库：Mysql
- springboot：2.2.2.RELEASE
- springcloud：Hoxton.RC1
- 项目构建工具：Maven 3.5.0
- 数据库连接池：Hikari
- 日志管理：SLF4J
- 单点登录：基于oauth2协议
- 三方登陆登录：springsocial
- 网关：springcloud-gateway
- 高可用：redis

## 作者寄语
共同学习，共同进步
