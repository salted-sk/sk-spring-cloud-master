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
INSERT INTO `oauth_client_details` VALUES ('client-demo', null, '$2a$10$0.PJtYiLwM9dV97xJKGxcej/ncRaw1EnWENEF/T5Xi8Dv9Z52CwyC', 'all,role', 'authorization_code,password,refresh_token,client_credentials,implicit', 'http://localhost:7771/client-demo/login,http://localhost/client-demo/login,http://47.104.23.202:7771/client-demo/login', null, '43200', '43200', null, 'false');
INSERT INTO `oauth_client_details` VALUES ('server-demo', null, '$2a$10$0.PJtYiLwM9dV97xJKGxcej/ncRaw1EnWENEF/T5Xi8Dv9Z52CwyC', 'all,permission', 'authorization_code,password,refresh_token,client_credentials', 'http://localhost:8881/server-demo/login,http://localhost/server-demo/login,http://47.104.23.202:8881/server-demo/login', null, '43200', '43200', null, 'true');

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
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
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
INSERT INTO `sys_role` VALUES ('2', '默认角色', '', '默认用户', '1', '\0', '2019-12-27 16:39:33', '2020-01-10 15:15:30');

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
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `home_url` varchar(200) DEFAULT NULL,
  `screen_name` varchar(32) DEFAULT NULL,
  `created` int(10) unsigned DEFAULT '0',
  `activated` int(10) unsigned DEFAULT '0',
  `logged` int(10) unsigned DEFAULT '0',
  `group_name` varchar(16) DEFAULT 'visitor',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1',
  `status` int(11) NOT NULL DEFAULT '1',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`username`),
  UNIQUE KEY `mail` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '$2a$10$0.PJtYiLwM9dV97xJKGxcej/ncRaw1EnWENEF/T5Xi8Dv9Z52CwyC', '731109627@qq.com', '13275829049', 'http://thirdqq.qlogo.cn/g?b=oidb&k=Y0OCeM3LgLOf03W9ZVsqKw&s=40&t=1569304425', 'admin', '1490756162', '0', '0', 'visitor', '1', '1', '\0', '2020-01-09 02:46:06', '2020-01-09 02:46:06');

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

-- ----------------------------
-- Table structure for t_attach
-- ----------------------------
DROP TABLE IF EXISTS `t_attach`;
CREATE TABLE `t_attach` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `fname` varchar(100) NOT NULL DEFAULT '',
  `ftype` varchar(50) DEFAULT '',
  `fkey` varchar(100) NOT NULL DEFAULT '',
  `author_id` int(10) DEFAULT NULL,
  `created` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_attach
-- ----------------------------

-- ----------------------------
-- Table structure for t_comments
-- ----------------------------
DROP TABLE IF EXISTS `t_comments`;
CREATE TABLE `t_comments` (
  `coid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cid` int(10) unsigned DEFAULT '0',
  `created` int(10) unsigned DEFAULT '0',
  `author` varchar(200) DEFAULT NULL,
  `author_id` int(10) unsigned DEFAULT '0',
  `owner_id` int(10) unsigned DEFAULT '0',
  `mail` varchar(200) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `ip` varchar(64) DEFAULT NULL,
  `agent` varchar(200) DEFAULT NULL,
  `content` text,
  `type` varchar(16) DEFAULT 'comment',
  `status` varchar(16) DEFAULT 'approved',
  `parent` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`coid`),
  KEY `cid` (`cid`),
  KEY `created` (`created`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_comments
-- ----------------------------

-- ----------------------------
-- Table structure for t_contents
-- ----------------------------
DROP TABLE IF EXISTS `t_contents`;
CREATE TABLE `t_contents` (
  `cid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `slug` varchar(200) DEFAULT NULL,
  `created` int(10) unsigned DEFAULT '0',
  `modified` int(10) unsigned DEFAULT '0',
  `content` text COMMENT '内容文字',
  `author_id` int(10) unsigned DEFAULT '0',
  `type` varchar(16) DEFAULT 'post',
  `status` varchar(16) DEFAULT 'publish',
  `tags` varchar(200) DEFAULT NULL,
  `categories` varchar(200) DEFAULT NULL,
  `hits` int(10) unsigned DEFAULT '0',
  `comments_num` int(10) unsigned DEFAULT '0',
  `allow_comment` tinyint(1) DEFAULT '1',
  `allow_ping` tinyint(1) DEFAULT '1',
  `allow_feed` tinyint(1) DEFAULT '1',
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cid`),
  UNIQUE KEY `slug` (`slug`),
  KEY `created` (`created`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_contents
-- ----------------------------
INSERT INTO `t_contents` VALUES ('5', '工作996，生病ICU', 'about', '1578523960', '1578625754', '### 来跟我一起念：\r\n工作996，生病icu。\r\n工作996，生病icu。\r\n工作996，生病icu。![请输入图片描述][1]\r\n工作996，生病icu。\r\n工作996，生病icu。\r\n工作996，生病icu。\r\n\r\n\r\n  [1]: http://112.124.3.225:8888/group1/M00/00/00/rBB8el4WtnqAQLSCAAALNBOTSB8156.jpg', '2', 'page', 'publish', null, null, '0', '0', '1', '1', '1', null);

-- ----------------------------
-- Table structure for t_logs
-- ----------------------------
DROP TABLE IF EXISTS `t_logs`;
CREATE TABLE `t_logs` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `action` varchar(100) DEFAULT NULL,
  `data` varchar(2000) DEFAULT NULL,
  `author_id` int(10) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `created` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_logs
-- ----------------------------
-- ----------------------------
-- Table structure for t_metas
-- ----------------------------
DROP TABLE IF EXISTS `t_metas`;
CREATE TABLE `t_metas` (
  `mid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `slug` varchar(200) DEFAULT NULL,
  `type` varchar(32) NOT NULL DEFAULT '',
  `description` varchar(200) DEFAULT NULL,
  `sort` int(10) unsigned DEFAULT '0',
  `parent` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`mid`),
  KEY `slug` (`slug`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_metas
-- ----------------------------
INSERT INTO `t_metas` VALUES ('6', 'my github', 'https://github.com/salted-sk/sk-spring-cloud-master', 'link', 'http://112.124.3.225:8888/group1/M00/00/00/rBB8el4X9juASb8aAACB9r5lJdo461.png', '999999', '0');
-- ----------------------------
-- Table structure for t_options
-- ----------------------------
DROP TABLE IF EXISTS `t_options`;
CREATE TABLE `t_options` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `value` varchar(1000) DEFAULT '',
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_options
-- ----------------------------
INSERT INTO `t_options` VALUES ('allow_install', '', null);
INSERT INTO `t_options` VALUES ('site_description', '工作996，生病ICU', null);
INSERT INTO `t_options` VALUES ('site_keywords', 'sk的Blog', null);
INSERT INTO `t_options` VALUES ('site_record', '', '备案号');
INSERT INTO `t_options` VALUES ('site_theme', 'default', null);
INSERT INTO `t_options` VALUES ('site_title', 'sk的博客', '');
INSERT INTO `t_options` VALUES ('social_github', '731109627@qq.com', null);
INSERT INTO `t_options` VALUES ('social_twitter', '', null);
INSERT INTO `t_options` VALUES ('social_weibo', '', null);
INSERT INTO `t_options` VALUES ('social_zhihu', '', null);

-- ----------------------------
-- Table structure for t_relationships
-- ----------------------------
DROP TABLE IF EXISTS `t_relationships`;
CREATE TABLE `t_relationships` (
  `cid` int(10) unsigned NOT NULL,
  `mid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`cid`,`mid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_relationships
-- ----------------------------

-- ----------------------------
-- Table structure for UserConnection
-- ----------------------------
DROP TABLE IF EXISTS `UserConnection`;
CREATE TABLE `UserConnection` (
  `userId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `providerId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `providerUserId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sequence` int(11) NOT NULL COMMENT '因为mysql中rank为关键字，所以将此字段改为了这个',
  `displayName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `profileUrl` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `imageUrl` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `accessToken` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `secret` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `refreshToken` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `expireTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`userId`,`providerId`,`providerUserId`),
  UNIQUE KEY `UserConnectionRank` (`userId`,`providerId`,`sequence`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of UserConnection
-- ----------------------------
-- ----------------------------
-- View structure for user_permission
-- ----------------------------
DROP VIEW IF EXISTS `user_permission`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `user_permission` AS select `sys_user`.`id` AS `id`,`sys_user`.`username` AS `username`,`sys_user`.`password` AS `password`,`sys_user`.`email` AS `email`,`sys_user`.`phone` AS `phone`,`sys_user`.`home_url` AS `home_url`,`sys_user`.`screen_name` AS `screen_name`,`sys_user`.`created` AS `created`,`sys_user`.`activated` AS `activated`,`sys_user`.`logged` AS `logged`,`sys_user`.`group_name` AS `group_name`,`sys_user`.`sex` AS `sex`,`sys_user`.`status` AS `status`,`sys_user`.`deleted` AS `deleted`,`sys_user`.`create_time` AS `create_time`,`sys_user`.`update_time` AS `update_time` from `sys_user` ;
