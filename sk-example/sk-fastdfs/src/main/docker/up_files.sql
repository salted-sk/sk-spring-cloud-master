/*
Navicat MySQL Data Transfer

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2019-12-20 14:31:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for up_files
-- ----------------------------
DROP TABLE IF EXISTS `up_files`;
CREATE TABLE `up_files` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `url` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态 0禁用 1启用',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除状态 0正常 1删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
