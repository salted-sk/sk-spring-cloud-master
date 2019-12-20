/*
Navicat MySQL Data Transfer

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2019-12-20 14:30:18
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of properties
-- ----------------------------
INSERT INTO `properties` VALUES ('1', 'spring.datasource.url', 'jdbc:mysql://112.124.3.225:3333/fdfs?serverTimezone=UTC&characterEncoding=utf-8', 'sk', 'fdfs', 'master', '连接url', '1', '\0', '2019-12-13 10:07:05', '2019-12-13 10:07:05');
INSERT INTO `properties` VALUES ('2', 'spring.datasource.username', 'root', 'sk', 'fdfs', 'master', '数据库连接用户名', '1', '\0', '2019-12-18 08:29:01', '2019-12-18 08:29:01');
INSERT INTO `properties` VALUES ('3', 'spring.datasource.password', 'frzxbkw', 'sk', 'fdfs', 'master', '数据库密码', '1', '\0', '2019-12-18 08:29:15', '2019-12-18 08:29:15');
INSERT INTO `properties` VALUES ('4', 'spring.datasource.driver-class-name', 'com.mysql.jdbc.Driver', 'sk', 'default', 'master', 'jdbcdriver', '1', '\0', '2019-12-13 09:58:59', '2019-12-13 09:58:59');
INSERT INTO `properties` VALUES ('5', 'mybatis.mapper-locations', 'classpath:mapper/**/*.xml', 'sk', 'default', 'master', '', '1', '\0', '2019-12-13 10:10:03', '2019-12-13 10:10:03');
INSERT INTO `properties` VALUES ('6', 'spring.thymeleaf.mode', 'LEGACYHTML5', 'sk', 'default', 'master', null, '1', '\0', '2019-12-13 10:10:52', '2019-12-13 10:10:52');
INSERT INTO `properties` VALUES ('7', 'spring.thymeleaf.encoding', 'UTF-8', 'sk', 'default', 'master', null, '1', '\0', '2019-12-13 10:11:04', '2019-12-13 10:11:04');
INSERT INTO `properties` VALUES ('8', 'spring.cloud.consul.discovery.health-check-path', '${server.servlet.context-path}/actuator/health', 'sk', 'default', 'master', null, '1', '\0', '2019-12-13 10:11:32', '2019-12-13 10:11:32');
INSERT INTO `properties` VALUES ('9', 'spring.thymeleaf.cache', 'true', 'sk', 'default', 'master', '页面缓存', '1', '\0', '2019-12-18 07:48:51', '2019-12-18 07:48:49');
INSERT INTO `properties` VALUES ('10', 'fdfs.so-timeout', '1500', 'sk', 'fdfs', 'master', null, '1', '\0', '2019-12-13 10:13:39', '2019-12-13 10:13:39');
INSERT INTO `properties` VALUES ('11', 'fdfs.connect-timeout', '600', 'sk', 'fdfs', 'master', null, '1', '\0', '2019-12-13 10:13:41', '2019-12-13 10:13:41');
INSERT INTO `properties` VALUES ('12', 'fdfs.thumb-image.width', '150', 'sk', 'fdfs', 'master', null, '1', '\0', '2019-12-13 10:13:41', '2019-12-13 10:13:41');
INSERT INTO `properties` VALUES ('13', 'fdfs.thumb-image.height', '150', 'sk', 'fdfs', 'master', null, '1', '\0', '2019-12-13 10:13:42', '2019-12-13 10:13:42');
INSERT INTO `properties` VALUES ('14', 'fdfs.tracker-list[0]', '112.124.3.225:22122', 'sk', 'fdfs', 'master', null, '1', '\0', '2019-12-13 10:13:44', '2019-12-13 10:13:44');
