/*
Navicat MySQL Data Transfer

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-01-06 15:17:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for properties
-- ----------------------------
DROP TABLE IF EXISTS `properties`;
CREATE TABLE `properties` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `value` varchar(500) COLLATE utf8mb4_general_ci NOT NULL,
  `application` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'sk',
  `profile` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default',
  `label` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'master',
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1正常',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of properties
-- ----------------------------
INSERT INTO `properties` VALUES ('1', 'spring.datasource.url', 'jdbc:mysql://127.0.0.1:3306/fdfs?serverTimezone=UTC&characterEncoding=utf-8', 'sk', 'fdfs', 'master', '连接url', '1', '\0', '2019-12-13 10:07:05', '2019-12-13 10:07:05');
INSERT INTO `properties` VALUES ('2', 'spring.datasource.username', 'root', 'sk', 'fdfs', 'master', '数据库连接用户名', '1', '\0', '2019-12-18 08:29:01', '2019-12-18 08:29:01');
INSERT INTO `properties` VALUES ('3', 'spring.datasource.password', '123456', 'sk', 'fdfs', 'master', '数据库密码', '1', '\0', '2019-12-18 08:29:15', '2019-12-18 08:29:15');
INSERT INTO `properties` VALUES ('4', 'spring.datasource.driver-class-name', 'com.mysql.jdbc.Driver', 'sk', 'default', 'master', 'jdbcdriver', '1', '\0', '2019-12-13 09:58:59', '2019-12-13 09:58:59');
INSERT INTO `properties` VALUES ('5', 'mybatis.mapper-locations', 'classpath:mapper/**/*.xml', 'sk', 'default', 'master', '', '1', '\0', '2019-12-13 10:10:03', '2019-12-13 10:10:03');
INSERT INTO `properties` VALUES ('6', 'spring.thymeleaf.mode', 'LEGACYHTML5', 'sk', 'default', 'master', null, '1', '\0', '2019-12-13 10:10:52', '2019-12-13 10:10:52');
INSERT INTO `properties` VALUES ('7', 'spring.thymeleaf.encoding', 'UTF-8', 'sk', 'default', 'master', null, '1', '\0', '2019-12-13 10:11:04', '2019-12-13 10:11:04');
INSERT INTO `properties` VALUES ('8', 'spring.cloud.consul.discovery.health-check-path', '${server.servlet.context-path}/actuator/health', 'sk', 'default', 'master', null, '1', '\0', '2019-12-13 10:11:32', '2019-12-13 10:11:32');
INSERT INTO `properties` VALUES ('9', 'spring.thymeleaf.cache', 'false', 'sk', 'default', 'master', '页面缓存', '1', '\0', '2019-12-30 02:56:37', '2019-12-30 02:56:37');
INSERT INTO `properties` VALUES ('10', 'fdfs.so-timeout', '1500', 'sk', 'fdfs', 'master', null, '1', '\0', '2019-12-13 10:13:39', '2019-12-13 10:13:39');
INSERT INTO `properties` VALUES ('11', 'fdfs.connect-timeout', '600', 'sk', 'fdfs', 'master', null, '1', '\0', '2019-12-13 10:13:41', '2019-12-13 10:13:41');
INSERT INTO `properties` VALUES ('12', 'fdfs.thumb-image.width', '150', 'sk', 'fdfs', 'master', null, '1', '\0', '2019-12-13 10:13:41', '2019-12-13 10:13:41');
INSERT INTO `properties` VALUES ('13', 'fdfs.thumb-image.height', '150', 'sk', 'fdfs', 'master', null, '1', '\0', '2019-12-13 10:13:42', '2019-12-13 10:13:42');
INSERT INTO `properties` VALUES ('14', 'fdfs.tracker-list[0]', '112.124.3.225:22122', 'sk', 'fdfs', 'master', null, '1', '\0', '2019-12-13 10:13:44', '2019-12-13 10:13:44');
INSERT INTO `properties` VALUES ('15', 'spring.datasource.url', 'jdbc:mysql://127.0.0.1:3306/oauth2?serverTimezone=UTC&characterEncoding=utf-8', 'sk', 'oauth-server', 'master', '', '1', '\0', '2019-12-27 13:08:42', '2019-12-27 13:08:42');
INSERT INTO `properties` VALUES ('16', 'spring.datasource.username', 'root', 'sk', 'oauth-server', 'master', '', '1', '\0', '2019-12-27 13:07:13', '2019-12-27 13:07:13');
INSERT INTO `properties` VALUES ('17', 'spring.datasource.password', '123456', 'sk', 'oauth-server', 'master', '', '1', '\0', '2019-12-27 13:07:54', '2019-12-27 13:07:54');
INSERT INTO `properties` VALUES ('18', 'oauth2.server.url', 'http://localhost/oauth2-server', 'sk', 'default', 'master', '资源认证服务器url', '1', '\0', '2019-12-29 01:00:22', '2019-12-29 01:00:22');
INSERT INTO `properties` VALUES ('19', 'security.oauth2.client.access-token-uri', '${oauth2.server.url}/oauth/token', 'sk', 'default', 'master', '', '1', '\0', '2019-12-27 13:44:32', '2019-12-27 13:44:32');
INSERT INTO `properties` VALUES ('20', 'security.oauth2.client.user-authorization-uri', '${oauth2.server.url}/oauth/authorize', 'sk', 'default', 'master', '', '1', '\0', '2019-12-27 13:44:51', '2019-12-27 13:44:51');
INSERT INTO `properties` VALUES ('21', 'security.oauth2.resource.jwt.key-uri', '${oauth2.server.url}/oauth/token_key', 'sk', 'default', 'master', '', '1', '\0', '2019-12-27 13:45:15', '2019-12-27 13:45:15');
INSERT INTO `properties` VALUES ('22', 'security.oauth2.resource.jwt.key-value', 'sk', 'sk', 'default', 'master', '', '1', '\0', '2019-12-29 10:00:21', '2019-12-29 10:00:21');
INSERT INTO `properties` VALUES ('23', 'app.sso.login.url', '${oauth2.server.url}/login', 'sk', 'default', 'master', '单点登录url', '1', '\0', '2019-12-27 13:45:56', '2019-12-27 13:45:56');
INSERT INTO `properties` VALUES ('24', 'app.sso.logout.url', '${oauth2.server.url}/logout', 'sk', 'default', 'master', '单点退出url', '1', '\0', '2019-12-27 13:46:18', '2019-12-27 13:46:18');
INSERT INTO `properties` VALUES ('25', 'feign.httpclient.connection-timeout', '3000', 'sk', 'default', 'master', '', '1', '\0', '2019-12-27 13:46:41', '2019-12-27 13:46:41');
INSERT INTO `properties` VALUES ('26', 'feign.httpclient.max-connections', '3000', 'sk', 'default', 'master', '', '1', '\0', '2019-12-27 13:46:54', '2019-12-27 13:46:54');
INSERT INTO `properties` VALUES ('27', 'feign.httpclient.time-to-live', '3000', 'sk', 'default', 'master', '', '1', '\0', '2019-12-27 13:47:07', '2019-12-27 13:47:07');
INSERT INTO `properties` VALUES ('28', 'feign.hystrix.enabled', 'true', 'sk', 'default', 'master', '', '1', '\0', '2019-12-27 13:47:21', '2019-12-27 13:47:21');
INSERT INTO `properties` VALUES ('29', 'feign.client.config.default.connectTimeout', '10000', 'sk', 'default', 'master', '', '1', '\0', '2019-12-27 13:47:35', '2019-12-27 13:47:35');
INSERT INTO `properties` VALUES ('30', 'feign.client.config.default.readTimeout', '6000', 'sk', 'default', 'master', '', '1', '\0', '2019-12-27 13:47:50', '2019-12-27 13:47:50');
INSERT INTO `properties` VALUES ('31', 'feign.client.config.default.loggerLevel', 'full', 'sk', 'default', 'master', '', '1', '\0', '2019-12-27 13:48:02', '2019-12-27 13:48:02');
INSERT INTO `properties` VALUES ('32', 'hystrix.command.default.execution.timeout.enabled', 'true', 'sk', 'default', 'master', '', '1', '\0', '2019-12-27 13:48:15', '2019-12-27 13:48:15');
INSERT INTO `properties` VALUES ('33', 'hystrix.command.default.execution.isolation.strategy', 'SEMAPHORE', 'sk', 'default', 'master', '熔断情况下传token（在feign或路由中使用必须要此属性）', '1', '\0', '2019-12-29 08:56:08', '2019-12-29 08:56:08');
INSERT INTO `properties` VALUES ('34', 'hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds', '3000', 'sk', 'default', 'master', '断路器超时时间，默认1000ms', '1', '\0', '2019-12-27 13:52:00', '2019-12-27 13:52:00');
INSERT INTO `properties` VALUES ('35', 'ribbon.OkToRetryOnAllOperations', 'false', 'sk', 'default', 'master', '对所有操作请求都进行重试,默认false', '1', '\0', '2019-12-27 13:53:31', '2019-12-27 13:53:31');
INSERT INTO `properties` VALUES ('36', 'ribbon.ReadTimeout', '30000', 'sk', 'default', 'master', '负载均衡超时时间，默认值5000', '1', '\0', '2019-12-27 13:53:54', '2019-12-27 13:53:54');
INSERT INTO `properties` VALUES ('37', 'ribbon.ConnectTimeout', '1000', 'sk', 'default', 'master', 'ribbon请求连接的超时时间，默认值2000', '1', '\0', '2019-12-27 13:54:19', '2019-12-27 13:54:19');
INSERT INTO `properties` VALUES ('38', 'ribbon.MaxAutoRetries', '0', 'sk', 'default', 'master', '对当前实例的重试次数，默认0', '1', '\0', '2019-12-27 13:54:36', '2019-12-27 13:54:36');
INSERT INTO `properties` VALUES ('39', 'ribbon.MaxAutoRetriesNextServer', '0', 'sk', 'default', 'master', '对切换实例的重试次数，默认1', '1', '\0', '2019-12-27 13:54:52', '2019-12-27 13:54:52');
INSERT INTO `properties` VALUES ('40', 'security.oauth2.client.client-id', 'client-demo', 'sk', 'client-demo', 'master', '', '1', '\0', '2019-12-27 13:55:36', '2019-12-27 13:55:36');
INSERT INTO `properties` VALUES ('41', 'security.oauth2.client.client-secret', '123456', 'sk', 'client-demo', 'master', 'oauth2认证客户端secret', '1', '\0', '2019-12-27 13:56:25', '2019-12-27 13:56:25');
INSERT INTO `properties` VALUES ('42', 'security.oauth2.resource.filter-order', '99', 'sk', 'client-demo', 'master', '配置使普通浏览器请求order大于资源认证的order', '1', '\0', '2019-12-27 13:57:09', '2019-12-27 13:57:09');
INSERT INTO `properties` VALUES ('43', 'spring.datasource.url', 'jdbc:mysql://127.0.0.1:3306/oauth2?serverTimezone=UTC&characterEncoding=utf-8', 'sk', 'client-demo', 'master', '', '1', '\0', '2019-12-27 13:58:25', '2019-12-27 13:58:25');
INSERT INTO `properties` VALUES ('44', 'spring.datasource.username', 'root', 'sk', 'client-demo', 'master', '', '1', '\0', '2019-12-27 13:58:45', '2019-12-27 13:58:45');
INSERT INTO `properties` VALUES ('45', 'spring.datasource.password', '123456', 'sk', 'client-demo', 'master', '', '1', '\0', '2019-12-27 14:01:04', '2019-12-27 14:01:04');
INSERT INTO `properties` VALUES ('46', 'security.oauth2.client.client-id', 'server-demo', 'sk', 'server-demo', 'master', '认证服务端id', '1', '\0', '2019-12-29 06:31:28', '2019-12-29 06:31:28');
INSERT INTO `properties` VALUES ('47', 'security.oauth2.client.client-secret', '123456', 'sk', 'server-demo', 'master', '认证客户端secret', '1', '\0', '2019-12-27 14:03:16', '2019-12-27 14:03:16');
INSERT INTO `properties` VALUES ('48', 'spring.datasource.url', 'jdbc:mysql://127.0.0.1:3306/oauth2?serverTimezone=UTC&characterEncoding=utf-8', 'sk', 'server-demo', 'master', '', '1', '\0', '2019-12-27 14:03:52', '2019-12-27 14:03:52');
INSERT INTO `properties` VALUES ('49', 'spring.datasource.username', 'root', 'sk', 'server-demo', 'master', '', '1', '\0', '2019-12-27 14:04:06', '2019-12-27 14:04:06');
INSERT INTO `properties` VALUES ('50', 'spring.datasource.password', '123456', 'sk', 'server-demo', 'master', '', '1', '\0', '2019-12-27 14:04:23', '2019-12-27 14:04:23');
INSERT INTO `properties` VALUES ('51', 'security.oauth2.client.client-id', 'server-demo', 'sk', 'default', 'server-demo', '', '1', '', '2019-12-29 06:25:35', '2019-12-29 06:25:35');
INSERT INTO `properties` VALUES ('52', 'spring.datasource.initialSize', '5', 'sk', 'default', 'master', '', '1', '\0', '2019-12-29 07:28:54', '2019-12-29 07:28:54');
INSERT INTO `properties` VALUES ('53', 'spring.datasource.minIdle', '5', 'sk', 'default', 'master', '', '1', '\0', '2019-12-29 07:29:10', '2019-12-29 07:29:10');
INSERT INTO `properties` VALUES ('54', 'spring.datasource.maxActive', '20', 'sk', 'default', 'master', '', '1', '\0', '2019-12-29 07:29:23', '2019-12-29 07:29:23');
INSERT INTO `properties` VALUES ('55', 'spring.datasource.maxWait', '60000', 'sk', 'default', 'master', '配置获取连接等待超时的时间', '1', '\0', '2019-12-29 07:29:49', '2019-12-29 07:29:49');
INSERT INTO `properties` VALUES ('56', 'spring.datasource.timeBetweenEvictionRunsMillis', '60000', 'sk', 'default', 'master', '配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒', '1', '\0', '2019-12-29 07:30:19', '2019-12-29 07:30:19');
INSERT INTO `properties` VALUES ('57', 'spring.datasource.minEvictableIdleTimeMillis', '300000', 'sk', 'default', 'master', '配置一个连接在池中最小生存的时间，单位是毫秒\n', '1', '\0', '2019-12-29 07:30:49', '2019-12-29 07:30:49');
INSERT INTO `properties` VALUES ('58', 'spring.datasource.validationQuery', 'SELECT 1 FROM DUAL', 'sk', 'default', 'master', '', '1', '\0', '2019-12-29 07:31:10', '2019-12-29 07:31:10');
INSERT INTO `properties` VALUES ('59', 'spring.datasource.testWhileIdle', 'true', 'sk', 'default', 'master', '', '1', '\0', '2019-12-29 07:31:37', '2019-12-29 07:31:37');
INSERT INTO `properties` VALUES ('60', 'spring.datasource.testOnBorrow', 'false', 'sk', 'default', 'master', '', '1', '\0', '2019-12-29 07:31:56', '2019-12-29 07:31:56');
INSERT INTO `properties` VALUES ('61', 'spring.datasource.testOnReturn', 'false', 'sk', 'default', 'master', '', '1', '\0', '2019-12-29 07:32:08', '2019-12-29 07:32:08');
INSERT INTO `properties` VALUES ('62', 'logging.level.com.sk.common.base.dao', 'debug', 'sk', 'default', 'master', '打印sql到控制台com.sk.common.base.dao为dao层路径', '1', '\0', '2020-01-03 04:14:43', '2020-01-03 04:14:43');
INSERT INTO `properties` VALUES ('63', 'logging.level.com.sk.dao', 'debug', 'sk', 'default', 'master', '打印sql到控制台com.sk.dao为打印dao层路径', '1', '\0', '2020-01-03 04:15:02', '2020-01-03 04:15:02');
