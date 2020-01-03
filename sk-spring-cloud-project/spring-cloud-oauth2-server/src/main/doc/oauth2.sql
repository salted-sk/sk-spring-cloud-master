/*
Navicat MySQL Data Transfer

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-01-03 14:31:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_access_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals` (
  `userId` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `clientId` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `expiresAt` timestamp NULL DEFAULT NULL,
  `lastModifiedAt` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_approvals
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('client-demo', null, '$2a$10$0.PJtYiLwM9dV97xJKGxcej/ncRaw1EnWENEF/T5Xi8Dv9Z52CwyC', 'all,role', 'authorization_code,password,refresh_token,client_credentials,implicit', 'http://localhost:7771/client-demo/login,http://localhost/client-demo/login', null, '43200', '43200', null, 'true');
INSERT INTO `oauth_client_details` VALUES ('server-demo', '', '$2a$10$0.PJtYiLwM9dV97xJKGxcej/ncRaw1EnWENEF/T5Xi8Dv9Z52CwyC', 'all,permission', 'authorization_code,password,refresh_token,client_credentials', 'http://localhost:8881/server-demo/login,http://localhost/server-demo/login', '', '43200', '43200', null, 'true');

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token` (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
  `code` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_code
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_refresh_token
-- ----------------------------

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `series` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `token` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='浏览器记住我功能表';

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限代码',
  `menu` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否菜单 1是 0否',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `status` int(11) DEFAULT '1' COMMENT '状态 0禁用 1启用',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0正常 1删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='系统权限';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '0', '权限查询', 'PERMISSION:QUERY', '\0', '权限查询', '1', '\0', '2019-11-19 16:47:16', '2019-11-19 16:47:16');
INSERT INTO `sys_permission` VALUES ('2', '0', '权限保存', 'PERMISSION:SAVE', '\0', '权限保存权限', '1', '\0', '2019-11-19 16:47:34', '2019-12-27 15:22:04');
INSERT INTO `sys_permission` VALUES ('3', '0', '权限删除', 'PERMISSION:DELETE', '\0', '权限删除权限', '1', '\0', '2019-12-27 15:22:27', '2019-12-27 15:22:27');
INSERT INTO `sys_permission` VALUES ('4', '0', '角色查询', 'ROLE:QUERY', '\0', '角色查询权限', '1', '\0', '2019-12-27 15:24:50', '2019-12-27 15:24:50');
INSERT INTO `sys_permission` VALUES ('5', '0', '角色保存', 'ROLE:SAVE', '\0', '角色保存权限', '1', '\0', '2019-12-27 15:25:10', '2019-12-27 15:25:10');
INSERT INTO `sys_permission` VALUES ('6', '0', '角色删除', 'ROLE:DELETE', '\0', '角色删除权限', '1', '\0', '2019-12-27 15:25:29', '2019-12-30 12:34:43');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名称',
  `default_role` bit(1) NOT NULL DEFAULT b'0' COMMENT '默认角色 0否 1是',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `status` int(11) DEFAULT '1' COMMENT '状态 0禁用 1启用',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0正常 1删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员', '\0', '我是管理员', '1', '\0', '2019-11-19 16:43:45', '2019-12-27 15:29:37');
INSERT INTO `sys_role` VALUES ('2', '默认角色', '\0', '默认用户', '1', '\0', '2019-12-27 16:39:33', '2019-12-30 13:57:39');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  `status` int(11) DEFAULT '1' COMMENT '状态 0禁用 1启用',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0正常 1删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1', '1', '1', '1', '\0', '2019-11-19 16:44:24', '2019-11-19 16:44:24');
INSERT INTO `sys_role_permission` VALUES ('2', '1', '2', '1', '\0', '2019-11-19 16:44:29', '2019-11-19 16:44:29');
INSERT INTO `sys_role_permission` VALUES ('3', '1', '3', '1', '\0', '2019-12-27 15:25:45', '2019-12-27 15:25:45');
INSERT INTO `sys_role_permission` VALUES ('4', '1', '4', '1', '\0', '2019-12-27 15:25:45', '2019-12-27 15:25:45');
INSERT INTO `sys_role_permission` VALUES ('5', '1', '5', '1', '\0', '2019-12-27 15:25:45', '2019-12-27 15:25:45');
INSERT INTO `sys_role_permission` VALUES ('6', '1', '6', '1', '\0', '2019-12-27 15:25:47', '2019-12-27 15:25:47');
INSERT INTO `sys_role_permission` VALUES ('7', '2', '1', '1', '\0', '2019-12-27 16:39:56', '2019-12-27 16:39:56');
INSERT INTO `sys_role_permission` VALUES ('8', '2', '4', '1', '\0', '2019-12-27 16:39:59', '2019-12-27 16:39:59');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登陆账号',
  `password` varchar(98) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登陆密码',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '性别 0女 1男',
  `true_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '真实姓名',
  `status` int(11) DEFAULT '1' COMMENT '状态 0禁用 1启用',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0正常 1删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'zhangsan', '$2a$10$0.PJtYiLwM9dV97xJKGxcej/ncRaw1EnWENEF/T5Xi8Dv9Z52CwyC', '13275829049', '0', '张三', '1', '\0', '2019-11-19 16:32:51', '2020-01-03 02:25:07');
INSERT INTO `sys_user` VALUES ('2', 'lisi', '$2a$10$0.PJtYiLwM9dV97xJKGxcej/ncRaw1EnWENEF/T5Xi8Dv9Z52CwyC', '13275829048', '1', '李四', '1', '\0', '2019-11-20 16:03:33', '2019-11-20 16:03:33');
INSERT INTO `sys_user` VALUES ('3', 'wangwu', '$2a$10$0.PJtYiLwM9dV97xJKGxcej/ncRaw1EnWENEF/T5Xi8Dv9Z52CwyC', '13275829047', '0', '王五', '1', '\0', '2019-12-27 16:54:33', '2019-12-27 16:54:33');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `status` int(11) DEFAULT '1' COMMENT '状态 0禁用 1启用',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0正常 1删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1', '1', '\0', '2019-11-19 16:44:44', '2019-11-19 16:44:44');
