/*
 Navicat MySQL Data Transfer

 Source Server         : study_sql_local
 Source Server Type    : MySQL
 Source Server Version : 50739
 Source Host           : localhost:3306
 Source Schema         : logistics_system

 Target Server Type    : MySQL
 Target Server Version : 50739
 File Encoding         : 65001

 Date: 30/09/2022 14:37:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for aging_query
-- ----------------------------
DROP TABLE IF EXISTS `aging_query`;
CREATE TABLE `aging_query`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `destination` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `eta` int(11) NOT NULL,
  `origin` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price_per_kilogram` double NOT NULL,
  `origin_price` double NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `origin`(`origin`, `destination`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of aging_query
-- ----------------------------
INSERT INTO `aging_query` VALUES (1, '2022-09-20 20:44:19', '2022-09-20 20:44:21', '区2', 1, '区1', 5, 8);
INSERT INTO `aging_query` VALUES (2, '2022-09-21 19:21:01', '2022-09-21 19:21:03', '城市2', 2, '城市1', 6, 10);
INSERT INTO `aging_query` VALUES (3, '2022-09-21 19:21:43', '2022-09-21 19:21:46', '省2', 3, '省1', 7, 12);
INSERT INTO `aging_query` VALUES (4, '2022-09-29 13:59:08', '2022-09-29 13:59:11', '山东省-济南市-历城区', 3, '河北省-石家庄市-长安区', 8, 12);

-- ----------------------------
-- Table structure for branch_courier
-- ----------------------------
DROP TABLE IF EXISTS `branch_courier`;
CREATE TABLE `branch_courier`  (
  `branch_id` bigint(20) NOT NULL,
  `courier_id` bigint(20) NOT NULL,
  INDEX `branch_id`(`branch_id`) USING BTREE,
  INDEX `courier_id`(`courier_id`) USING BTREE,
  CONSTRAINT `branch_id` FOREIGN KEY (`branch_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `courier_id` FOREIGN KEY (`courier_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of branch_courier
-- ----------------------------
INSERT INTO `branch_courier` VALUES (15, 9);
INSERT INTO `branch_courier` VALUES (50, 16);

-- ----------------------------
-- Table structure for complaint
-- ----------------------------
DROP TABLE IF EXISTS `complaint`;
CREATE TABLE `complaint`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fore_user_type` int(11) NULL DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `complaint_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `waybill_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fore_user_id` bigint(20) NULL DEFAULT NULL,
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fore_user_id`(`fore_user_id`) USING BTREE,
  INDEX `complaint_waybill`(`waybill_number`) USING BTREE,
  CONSTRAINT `complaint_ibfk_1` FOREIGN KEY (`fore_user_id`) REFERENCES `fore_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `complaint_waybill` FOREIGN KEY (`waybill_number`) REFERENCES `express_mail_order` (`waybill_number`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of complaint
-- ----------------------------

-- ----------------------------
-- Table structure for express_mail_order
-- ----------------------------
DROP TABLE IF EXISTS `express_mail_order`;
CREATE TABLE `express_mail_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `item_type` varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `item_weight` double NOT NULL,
  `payment_method` int(11) NOT NULL,
  `price` double NULL DEFAULT NULL,
  `recipients_address` varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `recipients_full_address` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `recipients_mobile` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `recipients_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remark` varchar(266) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `send_address` varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `send_full_address` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sender_mobile` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sender_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `waybill_number` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `fore_user_id` bigint(20) NULL DEFAULT NULL,
  `order_status` int(11) NULL DEFAULT NULL,
  `abolished` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `waybill_number`(`waybill_number`) USING BTREE,
  INDEX `fore_user_id`(`fore_user_id`) USING BTREE,
  INDEX `id`(`id`, `waybill_number`) USING BTREE,
  CONSTRAINT `express_mail_order_ibfk_1` FOREIGN KEY (`fore_user_id`) REFERENCES `fore_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of express_mail_order
-- ----------------------------

-- ----------------------------
-- Table structure for fore_user
-- ----------------------------
DROP TABLE IF EXISTS `fore_user`;
CREATE TABLE `fore_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` int(11) NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `detailed_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fore_user
-- ----------------------------
-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `name` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `routerName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `iconName` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `parent_id` bigint(20) NULL DEFAULT NULL,
  `is_button` int(1) NOT NULL,
  `disabled` int(1) NOT NULL,
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `parent_id`(`parent_id`) USING BTREE,
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '2022-09-01 14:26:03', '2022-09-01 18:24:53', '系统设置', '', 'setting', 0, NULL, 0, 0, 0);
INSERT INTO `menu` VALUES (2, '2022-09-01 14:26:03', '2022-09-01 18:24:53', '权限列表', 'roleManage', 'menu', 1, 1, 0, 0, 0);
INSERT INTO `menu` VALUES (3, '2022-09-01 14:26:03', '2022-09-01 18:24:53', '角色列表', 'managerManage', 'star', 1, 1, 0, 0, 0);
INSERT INTO `menu` VALUES (4, '2022-09-04 17:07:43', '2022-09-05 12:11:25', '首页', 'index', 'home', 1, NULL, 0, 0, 0);
INSERT INTO `menu` VALUES (5, '2022-09-04 17:07:43', '2022-09-05 12:11:25', '用户投诉管理', 'foreUserManage', 'file', 1, 19, 0, 0, 0);
INSERT INTO `menu` VALUES (6, '2022-09-04 17:07:43', '2022-09-05 12:11:25', '前台用户列表', 'netPointManage', 'branch', 1, 19, 0, 0, 0);
INSERT INTO `menu` VALUES (7, '2022-09-04 17:07:43', '2022-09-05 12:11:25', '其他用户管理', 'listTransPoint', 'vant', 1, 18, 0, 0, 0);
INSERT INTO `menu` VALUES (8, '2022-09-04 17:07:43', '2022-09-05 12:11:25', '快递员管理', 'postmanManage', 'user', 1, 18, 0, 0, 0);
INSERT INTO `menu` VALUES (9, '2022-09-04 17:07:43', '2022-09-05 12:11:25', '快递管理', '', 'box', 0, NULL, 0, 0, 0);
INSERT INTO `menu` VALUES (10, '2022-09-04 17:07:43', '2022-09-05 12:11:25', '揽收管理', 'pickupManage', 'present', 1, 9, 0, 0, 0);
INSERT INTO `menu` VALUES (11, '2022-09-04 17:07:43', '2022-09-05 12:11:25', '网点发货管理', 'deliverManage', 'checked', 1, 9, 0, 0, 0);
INSERT INTO `menu` VALUES (12, '2022-09-04 17:07:43', '2022-09-05 12:11:25', '中途收货管理', 'takeoverMedManage', 'place', 1, 9, 0, 0, 0);
INSERT INTO `menu` VALUES (13, '2022-09-04 17:07:43', '2022-09-05 12:11:25', '指定派送管理', 'specifiesSendManage', 'bicycle', 1, 9, 0, 0, 0);
INSERT INTO `menu` VALUES (14, '2022-09-04 17:07:43', '2022-09-05 12:11:25', '中途运输管理', 'transportMedManage', 'location', 1, 9, 0, 0, 0);
INSERT INTO `menu` VALUES (15, '2022-09-04 17:07:43', '2022-09-05 12:11:25', '派送管理', 'sendManage', 'finished', 1, 9, 0, 0, 0);
INSERT INTO `menu` VALUES (16, '2022-09-05 22:41:59', '2022-09-05 22:42:03', '测试demo1', NULL, NULL, 1, 1, 0, 0, 0);
INSERT INTO `menu` VALUES (17, '2022-09-05 22:41:59', '2022-09-05 22:42:03', '测试demo2', NULL, NULL, 1, 1, 0, 0, 0);
INSERT INTO `menu` VALUES (18, '2022-09-06 16:19:51', '2022-09-06 16:19:54', '后台用户管理', NULL, 'zoomin', 0, NULL, 0, 0, 0);
INSERT INTO `menu` VALUES (19, '2022-09-06 16:52:47', '2022-09-06 16:52:51', '前台用户管理', NULL, 'user', 0, NULL, 0, 0, 0);
INSERT INTO `menu` VALUES (20, '2022-09-06 19:28:09', '2022-09-06 19:28:12', '订单列表', 'packageList', 'edit', 1, 9, 0, 0, 0);
INSERT INTO `menu` VALUES (21, '2022-09-06 19:29:26', '2022-09-06 19:29:28', '订单取消管理', 'abolishPackage', 'delete', 1, 9, 0, 0, 0);
INSERT INTO `menu` VALUES (22, '2022-09-26 19:24:14', '2022-09-26 19:24:17', '运费时效管理', 'agingQueryManage', 'ticket', 1, 9, 0, 0, 0);

-- ----------------------------
-- Table structure for process_records
-- ----------------------------
DROP TABLE IF EXISTS `process_records`;
CREATE TABLE `process_records`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `operation` int(11) NULL DEFAULT NULL,
  `travel_position` int(11) NULL DEFAULT NULL,
  `express_mail_order_id` bigint(20) NULL DEFAULT NULL,
  `next_branch_id` bigint(20) NULL DEFAULT NULL,
  `received_branch_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `express_mail_order_id`(`express_mail_order_id`) USING BTREE,
  INDEX `next_branch_id`(`next_branch_id`) USING BTREE,
  INDEX `received_branch_id`(`received_branch_id`) USING BTREE,
  CONSTRAINT `process_records_ibfk_1` FOREIGN KEY (`express_mail_order_id`) REFERENCES `express_mail_order` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `process_records_ibfk_2` FOREIGN KEY (`received_branch_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `process_records_ibfk_3` FOREIGN KEY (`next_branch_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 85 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of process_records
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `name` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (0, '2020-03-15 13:16:38', '2021-02-01 09:39:10', '超级管理员', '超级管理员拥有最高权限。', 1);
INSERT INTO `role` VALUES (1, '2021-01-19 10:14:22', '2021-01-29 14:52:10', '网点', '', 1);
INSERT INTO `role` VALUES (2, '2021-01-19 10:15:51', '2021-01-29 14:52:38', '运输点', '', 1);
INSERT INTO `role` VALUES (3, '2021-01-20 16:08:52', '2021-01-30 18:23:47', '快递员', '', 1);
INSERT INTO `role` VALUES (4, '2022-09-24 18:57:43', '2022-09-24 21:19:03', '测试用例1', NULL, 1);

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `role_id` int(11) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE,
  INDEX `menu_id`(`menu_id`) USING BTREE,
  CONSTRAINT `role_menu_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (0, 1);
INSERT INTO `role_menu` VALUES (0, 2);
INSERT INTO `role_menu` VALUES (0, 3);
INSERT INTO `role_menu` VALUES (0, 4);
INSERT INTO `role_menu` VALUES (1, 4);
INSERT INTO `role_menu` VALUES (2, 4);
INSERT INTO `role_menu` VALUES (3, 4);
INSERT INTO `role_menu` VALUES (0, 5);
INSERT INTO `role_menu` VALUES (0, 6);
INSERT INTO `role_menu` VALUES (0, 7);
INSERT INTO `role_menu` VALUES (0, 8);
INSERT INTO `role_menu` VALUES (1, 8);
INSERT INTO `role_menu` VALUES (0, 9);
INSERT INTO `role_menu` VALUES (1, 9);
INSERT INTO `role_menu` VALUES (2, 9);
INSERT INTO `role_menu` VALUES (3, 9);
INSERT INTO `role_menu` VALUES (1, 10);
INSERT INTO `role_menu` VALUES (1, 11);
INSERT INTO `role_menu` VALUES (1, 12);
INSERT INTO `role_menu` VALUES (2, 12);
INSERT INTO `role_menu` VALUES (1, 13);
INSERT INTO `role_menu` VALUES (2, 14);
INSERT INTO `role_menu` VALUES (3, 15);
INSERT INTO `role_menu` VALUES (0, 16);
INSERT INTO `role_menu` VALUES (0, 17);
INSERT INTO `role_menu` VALUES (0, 18);
INSERT INTO `role_menu` VALUES (0, 19);
INSERT INTO `role_menu` VALUES (0, 20);
INSERT INTO `role_menu` VALUES (0, 22);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `head_pic` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `username` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  `address` varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `office_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `address`(`address`) USING BTREE,
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
SET FOREIGN_KEY_CHECKS = 1;
