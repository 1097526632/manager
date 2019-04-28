/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.188_3306
Source Server Version : 50724
Source Host           : 192.168.0.188:3306
Source Database       : manage

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-04-12 11:10:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_data_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_office`;
CREATE TABLE `sys_data_office` (
  `ID` int(64) NOT NULL AUTO_INCREMENT,
  `DATA_ID` varchar(64) NOT NULL COMMENT '角色编号',
  `OFFICE_ID` varchar(64) NOT NULL COMMENT '机构编号',
  `TYPE` varchar(32) DEFAULT NULL COMMENT '类型 role:角色  user:用户',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据-机构';

-- ----------------------------
-- Records of sys_data_office
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `ID` varchar(64) NOT NULL COMMENT '编号',
  `VALUE` varchar(100) NOT NULL COMMENT '数据值',
  `LABEL` varchar(100) NOT NULL COMMENT '标签名',
  `TYPE` varchar(100) NOT NULL COMMENT '类型',
  `DESCRIPTION` varchar(100) NOT NULL COMMENT '描述',
  `SORT` decimal(10,0) NOT NULL COMMENT '排序（升序）',
  `PARENT_ID` varchar(64) DEFAULT '0' COMMENT '父级编号',
  `CREATE_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(64) NOT NULL COMMENT '更新者',
  `UPDATE_DATE` datetime NOT NULL COMMENT '更新时间',
  `REMARKS` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `DEL_FLAG` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `SYS_DICT_VALUE` (`VALUE`) USING BTREE,
  KEY `SYS_DICT_LABEL` (`LABEL`) USING BTREE,
  KEY `SYS_DICT_DEL_FLAG` (`DEL_FLAG`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('0a736739a72c46ee83392692ca332895', '9', '店铺员工', 'sys_user_type', '用户类型', '50', '0', 'u_1', '2018-10-08 04:23:34', 'u_1', '2018-10-08 04:23:34', '用户类型', '1');
INSERT INTO `sys_dict` VALUES ('1', '0', '正常', 'del_flag', '删除标记', '10', '0', '1', '2013-05-27 00:00:00', 'u_01', '2019-03-13 16:05:40', '', '0');
INSERT INTO `sys_dict` VALUES ('100', 'java.util.Date', 'Date', 'gen_java_type', 'Java类型', '50', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('101', '', 'User', 'gen_java_type', 'Java类型', '60', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('102', '', 'Office', 'gen_java_type', 'Java类型', '70', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('103', '', 'Area', 'gen_java_type', 'Java类型', '80', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('104', 'Custom', 'Custom', 'gen_java_type', 'Java类型', '90', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('105', '1', '会议通告', 'sys_notify_type', '通知通告类型', '10', '0', '1', '2013-11-08 00:00:00', '1', '2013-11-08 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('106', '2', '奖惩通告', 'sys_notify_type', '通知通告类型', '20', '0', '1', '2013-11-08 00:00:00', '1', '2013-11-08 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('107', '3', '活动通告', 'sys_notify_type', '通知通告类型', '30', '0', '1', '2013-11-08 00:00:00', '1', '2013-11-08 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('108', '0', '草稿', 'sys_notify_status', '通知通告状态', '10', '0', '1', '2013-11-08 00:00:00', '1', '2013-11-08 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('109', '1', '发布', 'sys_notify_status', '通知通告状态', '20', '0', '1', '2013-11-08 00:00:00', '1', '2013-11-08 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('110', '0', '未读', 'sys_notify_read', '通知通告状态', '10', '0', '1', '2013-11-08 00:00:00', '1', '2013-11-08 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('111', '1', '已读', 'sys_notify_read', '通知通告状态', '20', '0', '1', '2013-11-08 00:00:00', '1', '2013-11-08 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('1251d18eb5874dabbb24a20941d3af0e', '1', '成品分类', 'erp_category_type', 'erp商品类别', '10', '0', 'u_1', '2018-09-12 03:00:35', 'u_1', '2018-09-12 03:00:35', '', '0');
INSERT INTO `sys_dict` VALUES ('16', 'flat', 'Flat主题', 'theme', '主题方案', '60', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('17', '1', '国家', 'sys_area_type', '区域类型', '10', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('18', '2', '省份、直辖市', 'sys_area_type', '区域类型', '20', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('19', '3', '地市', 'sys_area_type', '区域类型', '30', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('2', '1', '删除', 'del_flag', '删除标记', '20', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('20', '4', '区县', 'sys_area_type', '区域类型', '40', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('21', '1', '公司', 'sys_office_type', '机构类型', '60', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('22', '2', '部门', 'sys_office_type', '机构类型', '70', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('23', '8', '连锁', 'sys_office_type', '机构类型', '80', '0', '1', '2013-05-27 00:00:00', 'u_1', '2018-10-08 05:10:24', '', '0');
INSERT INTO `sys_dict` VALUES ('24', '9', '门店', 'sys_office_type', '机构类型', '90', '0', '1', '2013-05-27 00:00:00', 'u_1', '2018-10-08 05:10:38', '', '1');
INSERT INTO `sys_dict` VALUES ('25', '1', '综合部', 'sys_office_common', '快捷通用部门', '30', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('26', '2', '开发部', 'sys_office_common', '快捷通用部门', '40', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('27', '3', '人力部', 'sys_office_common', '快捷通用部门', '50', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('28', '1', '一级', 'sys_office_grade', '机构等级', '10', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('29', '2', '二级', 'sys_office_grade', '机构等级', '20', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('3', '1', '显示', 'show_hide', '显示/隐藏', '10', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('30', '3', '三级', 'sys_office_grade', '机构等级', '30', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('31', '4', '四级', 'sys_office_grade', '机构等级', '40', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('32', '1', '所有数据', 'sys_data_scope', '数据范围', '10', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('33', '2', '所在公司及以下数据', 'sys_data_scope', '数据范围', '20', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('34', '3', '所在公司数据', 'sys_data_scope', '数据范围', '30', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('34f0fb811922465186cda2666ddd3650', '4', '组合原料', 'erp_mp_type', 'erp商品类型', '40', '0', 'u_1', '2018-09-12 20:29:03', 'u_1', '2018-09-12 20:29:03', '', '1');
INSERT INTO `sys_dict` VALUES ('35', '4', '所在部门及以下数据', 'sys_data_scope', '数据范围', '40', '0', '1', '2013-05-27 00:00:00', 'u_1', '2018-10-04 08:55:28', '', '0');
INSERT INTO `sys_dict` VALUES ('36', '5', '所在部门数据', 'sys_data_scope', '数据范围', '50', '0', '1', '2013-05-27 00:00:00', 'u_1', '2018-10-04 08:55:36', '', '0');
INSERT INTO `sys_dict` VALUES ('37', '8', '仅本人数据', 'sys_data_scope', '数据范围', '90', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('38', '9', '按明细设置', 'sys_data_scope', '数据范围', '100', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('39', '1', '系统管理', 'sys_user_type', '用户类型', '10', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('4', '0', '隐藏', 'show_hide', '显示/隐藏', '20', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('40', '2', '部门经理', 'sys_user_type', '用户类型', '20', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('41', '3', '普通用户', 'sys_user_type', '用户类型', '30', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('43dd6ca9d9fd44a48fca0f9bf147c0bd', '2', '成品', 'erp_mp_type', 'erp商品类型', '20', '0', 'u_1', '2018-09-12 20:24:29', 'u_1', '2018-09-12 20:24:29', '', '0');
INSERT INTO `sys_dict` VALUES ('5', '1', '是', 'yes_no', '是/否', '10', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('6', '0', '否', 'yes_no', '是/否', '20', '0', '1', '2013-05-27 00:00:00', '1', '2013-05-27 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('67', 'SYSTEM_LOG', '系统日志', 'sys_log_type', '日志类型', '30', '0', '1', '2013-06-03 00:00:00', 'u_01', '2019-03-27 17:00:31', '', '0');
INSERT INTO `sys_dict` VALUES ('68', 'bus_api_log', '业务接口日志', 'sys_log_type', '日志类型', '40', '0', '1', '2013-06-03 00:00:00', 'u_01', '2019-03-28 11:56:48', '', '0');
INSERT INTO `sys_dict` VALUES ('6864d4b5c84d4ec5b6cdd7c705de1410', '1', '原料', 'erp_mp_type', 'erp商品类型', '10', '0', 'u_1', '2018-09-12 20:23:52', 'u_1', '2018-09-12 20:23:52', '', '0');
INSERT INTO `sys_dict` VALUES ('73', 'crud', '增删改查', 'gen_category', '代码生成分类', '10', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('74', 'crud_many', '增删改查（包含从表）', 'gen_category', '代码生成分类', '20', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('75', 'tree', '树结构', 'gen_category', '代码生成分类', '30', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('76', '=', '=', 'gen_query_type', '查询方式', '10', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('769f084be5534d2abdcdcedfca479d4f', '2', '原料分类', 'erp_category_type', 'erp商品类别', '20', '0', 'u_1', '2018-09-12 03:01:20', 'u_1', '2018-09-12 03:12:15', '', '0');
INSERT INTO `sys_dict` VALUES ('77', '!=', '!=', 'gen_query_type', '查询方式', '20', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('78', '&gt;', '&gt;', 'gen_query_type', '查询方式', '30', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('79', '&lt;', '&lt;', 'gen_query_type', '查询方式', '40', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('80', 'between', 'Between', 'gen_query_type', '查询方式', '50', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('81', 'like', 'Like', 'gen_query_type', '查询方式', '60', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('82', 'left_like', 'Left Like', 'gen_query_type', '查询方式', '70', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('83', 'right_like', 'Right Like', 'gen_query_type', '查询方式', '80', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('84', 'input', '文本框', 'gen_show_type', '字段生成方案', '10', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('85', 'textarea', '文本域', 'gen_show_type', '字段生成方案', '20', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('86', 'select', '下拉框', 'gen_show_type', '字段生成方案', '30', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('87', 'checkbox', '复选框', 'gen_show_type', '字段生成方案', '40', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('88', 'radiobox', '单选框', 'gen_show_type', '字段生成方案', '50', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('89', 'dateselect', '日期选择', 'gen_show_type', '字段生成方案', '60', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('8ace7c551bab448ea6e25612cb962323', '3', '半成品', 'erp_mp_type', 'erp商品类型', '30', '0', 'u_1', '2018-09-12 20:24:43', 'u_1', '2018-09-12 20:24:43', '', '0');
INSERT INTO `sys_dict` VALUES ('90', 'userselect', '人员选择', 'gen_show_type', '字段生成方案', '70', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('91', 'officeselect', '部门选择', 'gen_show_type', '字段生成方案', '80', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('92', 'areaselect', '区域选择', 'gen_show_type', '字段生成方案', '90', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('92ee7838979540cf9e43e3cfdb1b9bc7', '8', '店铺老板', 'sys_user_type', '用户类型', '40', '0', 'u_1', '2018-10-08 04:23:22', 'u_1', '2018-10-08 04:23:22', '', '0');
INSERT INTO `sys_dict` VALUES ('93', 'String', 'String', 'gen_java_type', 'Java类型', '10', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('94', 'Long', 'Long', 'gen_java_type', 'Java类型', '20', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('95', 'dao', '仅持久层', 'gen_category', '代码生成分类', '40', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('96', '1', '男', 'sex', '性别', '10', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('97', '2', '女', 'sex', '性别', '20', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '0');
INSERT INTO `sys_dict` VALUES ('98', 'Integer', 'Integer', 'gen_java_type', 'Java类型', '30', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');
INSERT INTO `sys_dict` VALUES ('99', 'Double', 'Double', 'gen_java_type', 'Java类型', '40', '0', '1', '2013-10-28 00:00:00', '1', '2013-10-28 00:00:00', '', '1');

-- ----------------------------
-- Table structure for sys_file_category
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_category`;
CREATE TABLE `sys_file_category` (
  `ID` varchar(64) NOT NULL DEFAULT '' COMMENT '主键',
  `PARENT_ID` varchar(64) DEFAULT NULL COMMENT '父ID',
  `PARENT_IDS` varchar(400) DEFAULT NULL COMMENT '所有父ID',
  `NAME` varchar(100) DEFAULT NULL COMMENT '名称',
  `PARENT_NAMES` varchar(600) DEFAULT NULL COMMENT '所有父名称',
  `CREATE_BY` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(64) DEFAULT NULL COMMENT '更新人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  `DEL_FLAG` varchar(32) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_file_category
-- ----------------------------

-- ----------------------------
-- Table structure for sys_file_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_info`;
CREATE TABLE `sys_file_info` (
  `ID` varchar(64) NOT NULL DEFAULT '' COMMENT '主键',
  `CATEGORY_ID` varchar(64) DEFAULT NULL COMMENT '目录ID',
  `NAME` varchar(100) DEFAULT NULL COMMENT '名称',
  `EXT_TYPE` varchar(32) DEFAULT NULL COMMENT '文件后缀',
  `FILE_TYPE` varchar(32) DEFAULT NULL COMMENT '文件类型',
  `REAL_NAME` varchar(200) DEFAULT NULL COMMENT '实际名称',
  `FILE_SIZE` int(11) DEFAULT NULL COMMENT '文件大小',
  `CSS_CLASS` varchar(100) DEFAULT NULL COMMENT '样式',
  `DEL_FLAG` varchar(2) DEFAULT NULL COMMENT '删除标志',
  `REMARKS` varchar(100) DEFAULT NULL COMMENT ' 备注',
  `CREATE_BY` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(64) DEFAULT NULL COMMENT '更新人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_file_info
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `ID` varchar(64) NOT NULL COMMENT '编号',
  `TYPE` varchar(100) DEFAULT '1' COMMENT '日志类型',
  `TITLE` varchar(255) DEFAULT '' COMMENT '日志标题',
  `CREATE_BY` varchar(64) DEFAULT NULL COMMENT '创建者',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `REMOTE_ADDR` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `USER_AGENT` text COMMENT '用户代理',
  `REQUEST_URI` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `METHOD` varchar(200) DEFAULT NULL COMMENT '操作方式',
  `PARAMS` text COMMENT '操作提交的数据',
  `RETURN_RESULT` text COMMENT '返回结果',
  `TIMES` double DEFAULT NULL COMMENT '消耗时间',
  `EXCEPTION` text COMMENT '异常信息',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `SYS_LOG_CREATE_BY` (`CREATE_BY`) USING BTREE,
  KEY `SYS_LOG_REQUEST_URI` (`REQUEST_URI`) USING BTREE,
  KEY `SYS_LOG_TYPE` (`TYPE`) USING BTREE,
  KEY `SYS_LOG_CREATE_DATE` (`CREATE_DATE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='日志表';

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `ID` varchar(64) NOT NULL COMMENT '编号',
  `PARENT_ID` varchar(64) NOT NULL COMMENT '父级编号',
  `PARENT_IDS` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `NAME` varchar(100) NOT NULL COMMENT '名称',
  `SORT` decimal(10,0) NOT NULL COMMENT '排序',
  `HREF` varchar(2000) DEFAULT NULL COMMENT '链接',
  `ICON` varchar(100) DEFAULT NULL COMMENT '图标',
  `IS_SHOW` char(1) NOT NULL COMMENT '是否在菜单中显示',
  `PERMISSION` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `CREATE_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(64) NOT NULL COMMENT '更新者',
  `UPDATE_DATE` datetime NOT NULL COMMENT '更新时间',
  `REMARKS` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `DEL_FLAG` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `SYS_MENU_PARENT_ID` (`PARENT_ID`) USING BTREE,
  KEY `SYS_MENU_DEL_FLAG` (`DEL_FLAG`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('168524232f76497489258fd1f4f0d58e', '84a779e43d4848d49e2586b8596f72b2', '0,d7fad8a10f5643a58020885a9ad2292e,84a779e43d4848d49e2586b8596f72b2', '日志查看', '90', 'sysLog', 'fa-info', '1', '', 'u_01', '2019-03-26 18:40:40', 'u_01', '2019-04-11 16:51:27', '', '0');
INSERT INTO `sys_menu` VALUES ('17d654670be34aa480122d99029d5d4d', '84a779e43d4848d49e2586b8596f72b2', '0,d7fad8a10f5643a58020885a9ad2292e', '字典管理', '60', 'dict', 'fa-file-text-o', '1', null, 'u_01', '2019-03-15 18:31:12', 'u_01', '2019-03-15 18:52:27', null, '0');
INSERT INTO `sys_menu` VALUES ('1bb9ccb165414ee99da77f38b1d73b14', 'd5f703640a0c4dbab522243496552f95', '0,d7fad8a10f5643a58020885a9ad2292e', '查看', '30', null, null, '0', 'view:sys:sysParam', 'u_01', '2019-03-15 19:02:24', 'u_01', '2019-03-15 19:02:24', null, '0');
INSERT INTO `sys_menu` VALUES ('1bf38a0233144481944f8858ee162218', '69a41500d5f14292816558d6d1efa546', '0,d7fad8a10f5643a58020885a9ad2292e,69a41500d5f14292816558d6d1efa546', '查看', '30', null, null, '0', 'view:sys:sysUser', 'u_01', '2019-04-11 15:44:07', 'u_01', '2019-04-11 15:44:07', null, '0');
INSERT INTO `sys_menu` VALUES ('2636042af8384afa87462f185a1df067', '17d654670be34aa480122d99029d5d4d', '0,d7fad8a10f5643a58020885a9ad2292e', '编辑', '60', null, null, '0', 'edit:sys:sysDict', 'u_01', '2019-03-15 19:00:34', 'u_01', '2019-03-15 19:00:34', null, '0');
INSERT INTO `sys_menu` VALUES ('4e05ef8b82b844b1817c4155f92a5d4c', '84a779e43d4848d49e2586b8596f72b2', '0,d7fad8a10f5643a58020885a9ad2292e', '菜单管理', '30', 'menu', 'fa-leaf', '1', null, 'u_01', '2019-03-15 18:30:15', 'u_01', '2019-03-15 18:36:49', null, '0');
INSERT INTO `sys_menu` VALUES ('54e60bc24d84477d8a65c9d7e9e733db', 'c7bec286492c411298218ead881e1e96', '0,d7fad8a10f5643a58020885a9ad2292e', '机构管理', '30', '/sysOffice', 'fa-users', '1', null, 'u_01', '2019-03-26 18:36:19', 'u_01', '2019-03-26 18:36:19', null, '0');
INSERT INTO `sys_menu` VALUES ('69a41500d5f14292816558d6d1efa546', 'c7bec286492c411298218ead881e1e96', '0,d7fad8a10f5643a58020885a9ad2292e', '用户管理', '60', 'sysUser', 'fa-address-book-o', '1', null, 'u_01', '2019-03-26 18:36:56', 'u_01', '2019-03-26 18:40:01', null, '0');
INSERT INTO `sys_menu` VALUES ('84a779e43d4848d49e2586b8596f72b2', 'd7fad8a10f5643a58020885a9ad2292e', '0,d7fad8a10f5643a58020885a9ad2292e', '系统配置', '60', null, 'fa-cog', '1', null, 'u_01', '2019-03-15 18:36:24', 'u_01', '2019-03-15 18:36:24', null, '0');
INSERT INTO `sys_menu` VALUES ('8b765cf61d35409b87d2aa177f2d4367', '17d654670be34aa480122d99029d5d4d', '0,d7fad8a10f5643a58020885a9ad2292e', '删除', '60', null, null, '0', 'del:sys:sysDict', 'u_01', '2019-03-15 19:00:49', 'u_01', '2019-03-15 19:00:49', null, '0');
INSERT INTO `sys_menu` VALUES ('8bfd50f524d049e8bce461e5af823de9', '4e05ef8b82b844b1817c4155f92a5d4c', '0,d7fad8a10f5643a58020885a9ad2292e', '编辑', '30', null, null, '0', 'edit:sys:menu', 'u_01', '2019-03-15 18:53:32', 'u_01', '2019-03-15 18:57:43', null, '0');
INSERT INTO `sys_menu` VALUES ('9f0e2df5da584bc1898efb12dd8fbafe', 'd5f703640a0c4dbab522243496552f95', '0,d7fad8a10f5643a58020885a9ad2292e', '删除', '90', null, null, '0', 'del:sys:sysParam', 'u_01', '2019-03-15 19:03:05', 'u_01', '2019-03-15 19:03:05', null, '0');
INSERT INTO `sys_menu` VALUES ('a72f2f1c085d401b82f69affb946e7d7', '4e05ef8b82b844b1817c4155f92a5d4c', '0,d7fad8a10f5643a58020885a9ad2292e', '查看', '30', null, null, '0', 'view:sys:menu', 'u_01', '2019-03-15 18:53:06', 'u_01', '2019-03-15 18:53:06', null, '0');
INSERT INTO `sys_menu` VALUES ('bdfb8847299242c0b16de24139ad3dfd', '4e05ef8b82b844b1817c4155f92a5d4c', '0,d7fad8a10f5643a58020885a9ad2292e', '删除', '60', null, null, '0', 'del:sys:menu', 'u_01', '2019-03-15 18:59:49', 'u_01', '2019-03-15 18:59:49', null, '0');
INSERT INTO `sys_menu` VALUES ('beec50675aef49f28766664c44bdb800', 'f7f577487ba34078b13a8a3f2041e84b', '0,d7fad8a10f5643a58020885a9ad2292e,f7f577487ba34078b13a8a3f2041e84b', '编辑', '30', null, null, '0', 'edit:sys:sysRole', 'u_01', '2019-04-11 15:49:39', 'u_01', '2019-04-11 15:49:39', null, '0');
INSERT INTO `sys_menu` VALUES ('c7bec286492c411298218ead881e1e96', 'd7fad8a10f5643a58020885a9ad2292e', '0,d7fad8a10f5643a58020885a9ad2292e', '组织机构', '90', null, 'fa-group ', '1', null, 'u_01', '2019-03-26 18:35:30', 'u_01', '2019-03-26 18:35:30', null, '0');
INSERT INTO `sys_menu` VALUES ('ccdd008937514972a645abf82c79a57f', 'f7f577487ba34078b13a8a3f2041e84b', '0,d7fad8a10f5643a58020885a9ad2292e,f7f577487ba34078b13a8a3f2041e84b', '查看', '30', null, null, '0', 'view:sys:sysRole', 'u_01', '2019-04-11 15:49:10', 'u_01', '2019-04-11 15:49:10', null, '0');
INSERT INTO `sys_menu` VALUES ('d5f703640a0c4dbab522243496552f95', '84a779e43d4848d49e2586b8596f72b2', '0,d7fad8a10f5643a58020885a9ad2292e', '参数配置', '60', 'sysParam', 'fa-book', '1', null, 'u_01', '2019-03-15 19:01:42', 'u_01', '2019-03-15 19:01:42', null, '0');
INSERT INTO `sys_menu` VALUES ('d70bd96bb6f84f9796bf4f75113c8b39', '69a41500d5f14292816558d6d1efa546', '0,d7fad8a10f5643a58020885a9ad2292e,69a41500d5f14292816558d6d1efa546', '编辑', '30', null, null, '0', 'edit:sys:sysUser', 'u_01', '2019-04-11 15:45:22', 'u_01', '2019-04-11 15:45:22', null, '0');
INSERT INTO `sys_menu` VALUES ('d7fad8a10f5643a58020885a9ad2292e', '0', '0', '系统管理', '30', null, 'fa-cogs', '1', null, 'u_01', '2019-03-14 19:56:01', 'u_01', '2019-03-27 15:43:08', null, '0');
INSERT INTO `sys_menu` VALUES ('d84bf3d571b949039d43cdc7280f875f', '69a41500d5f14292816558d6d1efa546', '0,d7fad8a10f5643a58020885a9ad2292e,69a41500d5f14292816558d6d1efa546', '删除', '30', null, null, '0', 'del:sys:sysUser', 'u_01', '2019-04-11 15:46:00', 'u_01', '2019-04-11 15:46:00', null, '0');
INSERT INTO `sys_menu` VALUES ('e0e471540c9e4ac1819ce7bd6dbaed36', 'd5f703640a0c4dbab522243496552f95', '0,d7fad8a10f5643a58020885a9ad2292e', '编辑', '60', null, null, '0', 'edit:sys:sysParam', 'u_01', '2019-03-15 19:02:48', 'u_01', '2019-03-15 19:02:48', null, '0');
INSERT INTO `sys_menu` VALUES ('e285bd79b4f24bdd88a2ebc9bbc59830', '17d654670be34aa480122d99029d5d4d', '0,d7fad8a10f5643a58020885a9ad2292e', '查看', '30', null, null, '0', 'view:sys:sysDict', 'u_01', '2019-03-15 19:00:17', 'u_01', '2019-03-15 19:00:17', null, '0');
INSERT INTO `sys_menu` VALUES ('f30d879cf53f45da8c64cb8fc7ee90bb', 'f7f577487ba34078b13a8a3f2041e84b', '0,d7fad8a10f5643a58020885a9ad2292e,f7f577487ba34078b13a8a3f2041e84b', '分配', '30', null, null, '0', 'assign:sys:sysRole', 'u_01', '2019-04-11 15:53:34', 'u_01', '2019-04-11 15:53:34', null, '0');
INSERT INTO `sys_menu` VALUES ('f7f577487ba34078b13a8a3f2041e84b', 'c7bec286492c411298218ead881e1e96', '0,d7fad8a10f5643a58020885a9ad2292e', '角色管理', '90', 'sysRole', 'fa-user-plus', '1', null, 'u_01', '2019-03-26 18:39:49', 'u_01', '2019-03-26 18:39:49', null, '0');
INSERT INTO `sys_menu` VALUES ('f81dd8b6fd4d49bb8269143117402392', 'f7f577487ba34078b13a8a3f2041e84b', '0,d7fad8a10f5643a58020885a9ad2292e,f7f577487ba34078b13a8a3f2041e84b', '删除', '30', null, null, '0', 'del:sys:sysRole', 'u_01', '2019-04-11 15:49:59', 'u_01', '2019-04-11 15:49:59', null, '0');

-- ----------------------------
-- Table structure for sys_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_office`;
CREATE TABLE `sys_office` (
  `ID` varchar(64) NOT NULL COMMENT '编号',
  `PARENT_ID` varchar(64) NOT NULL COMMENT '父级编号',
  `PARENT_IDS` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `NAME` varchar(100) NOT NULL COMMENT '名称',
  `SORT` decimal(10,0) NOT NULL COMMENT '排序',
  `TYPE` varchar(32) NOT NULL COMMENT '机构类型',
  `ADDRESS` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `ZIP_CODE` varchar(100) DEFAULT NULL COMMENT '邮政编码',
  `USEABLE` varchar(64) DEFAULT NULL COMMENT '是否启用',
  `CREATE_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(64) NOT NULL COMMENT '更新者',
  `UPDATE_DATE` datetime NOT NULL COMMENT '更新时间',
  `REMARKS` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `DEL_FLAG` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `SYS_OFFICE_PARENT_ID` (`PARENT_ID`) USING BTREE,
  KEY `SYS_OFFICE_DEL_FLAG` (`DEL_FLAG`) USING BTREE,
  KEY `SYS_OFFICE_TYPE` (`TYPE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='机构表';

-- ----------------------------
-- Records of sys_office
-- ----------------------------
INSERT INTO `sys_office` VALUES ('o_1', 'o_0', 'o_0', 'xxx公司', '10', '1', '', '', '1', '1', '2013-05-27 00:00:00', 'u_01', '2019-03-29 18:28:05', '', '0');

-- ----------------------------
-- Table structure for sys_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `ID` varchar(64) NOT NULL DEFAULT '' COMMENT '主键',
  `CODE` varchar(50) NOT NULL COMMENT '编码',
  `VALUE` varchar(300) DEFAULT NULL COMMENT '值',
  `DESCRIPTION` varchar(500) DEFAULT NULL COMMENT '描述',
  `TYPE` int(11) DEFAULT NULL COMMENT '参数类型',
  `CREATE_BY` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(64) DEFAULT NULL COMMENT '更新人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  `DEL_FLAG` varchar(32) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统参数表';

-- ----------------------------
-- Records of sys_param
-- ----------------------------
INSERT INTO `sys_param` VALUES ('0f7d9c1106d04e7693edbf63ea00e98d', '1', '1', '1', '1', 'u_01', '2019-03-12 18:00:13', 'u_01', '2019-03-12 18:00:13', '1');
INSERT INTO `sys_param` VALUES ('11653f79a51549a5a03bf2868fcce333', 'hotline', '020-8633438', '热线', '1', '1', '2016-08-30 14:09:23', 'u_01', '2019-03-13 11:50:19', '0');
INSERT INTO `sys_param` VALUES ('168871c31b5d4b659c7e0be9642e50fd', 'mail.service.type', 'SMTP', '邮件服务类型', '0', '1', '2016-03-05 15:00:46', 'u_01', '2019-03-13 14:52:17', '0');
INSERT INTO `sys_param` VALUES ('168871c31b5d4b659c7e0be9643450fd', 'productName', '茶货铺子', '系统名称', '0', '1', '2016-03-05 14:03:24', '1', '2019-01-14 12:02:37', '0');
INSERT INTO `sys_param` VALUES ('192f33102d3542b48ee4272b43efe363', 'page.pageSize', '10', '每页显示的记录条数', '0', '1', '2016-03-05 14:06:41', 'u_01', '2019-03-13 16:10:43', '0');
INSERT INTO `sys_param` VALUES ('1fc0c5067c85451683f1cbe63328e993', 'sqb_vendor_sn', '91800546', '收钱吧vendorSn123', '0', 'u_1', '2018-11-26 09:57:40', 'u_01', '2019-03-13 14:07:40', '0');
INSERT INTO `sys_param` VALUES ('2171e7d354774492add3ebe8c5496ecf', 'sqb_vendor_key', '704ca56f345ba9bd66a58eba834db531', '收钱吧vendorKey', '0', 'u_1', '2018-11-26 09:58:19', 'u_1', '2018-11-26 09:58:19', '0');
INSERT INTO `sys_param` VALUES ('3cf0241e36b14f7a872f671a69d355e7', 'mail.password', 'tpblxdclgnolhffc', '邮件密码', '0', '1', '2016-03-05 15:01:50', '1', '2016-08-16 13:47:11', '0');
INSERT INTO `sys_param` VALUES ('42f04c73432b4f4495c32211a5ce63f3', 'socketPort', '10080', 'socket端口号(重启系统生效)', '0', '1', '2016-03-05 14:50:02', '1', '2016-03-05 14:50:30', '0');
INSERT INTO `sys_param` VALUES ('4b3d6e9baea744cc9457e570ed373e8d', 'mail.smtp.host', 'smtp.qq.com', '邮件服务器', '0', '1', '2016-03-05 15:02:36', '1', '2016-03-05 15:02:36', '0');
INSERT INTO `sys_param` VALUES ('5b3499ab0bd14654be6275d2f5b78ff2', 'copyrightYear', '2016', '版权年份', '0', '1', '2016-03-05 14:47:41', '1', '2016-03-05 14:58:20', '0');
INSERT INTO `sys_param` VALUES ('8142f0e85639424cb64878067be77827', 'wx_token', 'wwwyishuntechcom', '微信token', '0', 'u_1', '2018-12-17 18:10:49', 'u_1', '2018-12-17 18:10:49', '0');
INSERT INTO `sys_param` VALUES ('8420a465e32f44bb8b8ba40316d40ffa', 'wx_appid', 'wx70664e2f32b080f6', '微信AppId', '0', 'u_1', '2018-12-17 18:09:39', 'u_1', '2018-12-17 18:09:39', '0');
INSERT INTO `sys_param` VALUES ('ac48ba4df76649889702d8c1999503a9', 'datePath', 'yyyyMMdd', '生成文件按日期格式生成（以java中的日期格式）', '0', '1', '2016-03-05 14:53:04', '1', '2016-03-05 14:53:50', '0');
INSERT INTO `sys_param` VALUES ('af0311c10fa94988a469f89682c4c6c4', 'version', '1.0.0', '系统版本', '0', '1', '2016-03-05 14:48:21', '1', '2016-03-05 14:48:21', '0');
INSERT INTO `sys_param` VALUES ('af4bd821210345a9b7161cf9c787c9ee', 'qcloud.SecretId', 'AKID0qRq1xB6IFwFKBhBKEuVryqNhokTIHLD', '腾讯云SecretId', '1', '1', '2018-02-09 17:33:07', '1', '2018-02-09 17:33:07', '0');
INSERT INTO `sys_param` VALUES ('b0eef68c44744e49b65432aef8d74859', 'wx_appsecret', 'b08ae9e218bcebb05ed5d181fdb71ba1', '微信AppSecret', '0', 'u_1', '2018-12-17 18:10:20', 'u_1', '2018-12-17 18:10:20', '0');
INSERT INTO `sys_param` VALUES ('b118e583e9c5413eaa8fda1226d573c2', 'sqb_app_id', '2018112600001142', '收钱吧 AppId', '0', 'u_1', '2018-11-26 10:00:41', 'u_1', '2018-11-26 10:00:41', '0');
INSERT INTO `sys_param` VALUES ('bdee0ccd3c6e485cb4104f6710801c75', 'lucene.path', '/jeesys/lucene/', 'lucene生成的路径', '0', '1', '2016-03-05 14:51:12', '1', '2016-03-05 14:51:12', '0');
INSERT INTO `sys_param` VALUES ('be008f2299734b22909c9b02ebb57a8a', 'mail.username', '1097526632@qq.com', '邮件账号', '0', '1', '2016-03-05 15:01:16', '1', '2016-08-16 13:46:03', '0');
INSERT INTO `sys_param` VALUES ('c3f9694ca1cc496992da755ada6a0f57', 'qcloud.SecretKey', 'nNjtqXzB7nEPKzPYT8YidJo33oJZZKp4', '腾讯云SecretKey', '1', '1', '2018-02-09 17:33:51', '1', '2018-02-09 17:33:51', '0');
INSERT INTO `sys_param` VALUES ('c9f09a5b3f574caaae2de5f16d62dd1b', 'mail.smtp.port', '25', '邮件SSL端口号', '0', '1', '2016-03-05 15:03:04', '1', '2016-03-05 15:03:04', '0');
INSERT INTO `sys_param` VALUES ('d19186638e2b493083b8da38e99a1656', 'wx_domain', 'http://ysadmin.yishuntech.com.cn/base', '微信重定向域名', '0', 'u_1', '2018-12-17 18:17:25', 'u_1', '2018-12-17 18:17:25', '0');
INSERT INTO `sys_param` VALUES ('d8be348c9f6c44e1af9a27a666eda21f', 'userfiles.basedir', '/jeesys', '用户文件生成目录(以前生成的文件需要手动拷贝)', '0', '1', '2016-03-05 14:52:15', '1', '2016-03-05 16:14:39', '0');
INSERT INTO `sys_param` VALUES ('dcecb68e70624a64b0520030c752bd43', 'storeType', 'file', '文件存储类型(cloud/file)', '0', '1', '2018-02-28 14:24:37', '1', '2018-02-28 14:24:42', '0');
INSERT INTO `sys_param` VALUES ('dcecb68e70624a64b0520030c752bf4a', 'tablePrefix', 'FORM_', '表单生成表前缀', '1', '1', '2016-07-27 21:47:26', '1', '2016-07-27 21:47:26', '0');
INSERT INTO `sys_param` VALUES ('dcecb68e70624a64b0520030c752bf4b', 'serverBasePath', 'http://10.0.0.23/', '服务器访问前缀', '1', '1', '2016-08-16 17:18:10', '1', '2016-08-29 11:02:06', '0');
INSERT INTO `sys_param` VALUES ('dcecb68e70624a64b0522430c752bf4b', 'qcloud.bucket', 'basesystem-1255579372', '文件存储桶', '0', '1', '2018-02-28 14:29:47', '1', '2018-02-28 14:29:54', '0');
INSERT INTO `sys_param` VALUES ('dcecb68e70624a64b0522890c752bf4b', 'qcloud.region', 'ap-guangzhou', '区域', '0', '1', '2018-02-28 14:29:49', '1', '2018-02-28 14:29:57', '0');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ID` varchar(64) NOT NULL COMMENT '编号',
  `NAME` varchar(100) NOT NULL COMMENT '角色名称',
  `DATA_SCOPE` varchar(255) DEFAULT NULL COMMENT '数据范围',
  `IS_SYS` varchar(64) DEFAULT '0' COMMENT '是否系统数据',
  `USEABLE` varchar(64) DEFAULT NULL COMMENT '是否可用',
  `CREATE_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(64) NOT NULL COMMENT '更新者',
  `UPDATE_DATE` datetime NOT NULL COMMENT '更新时间',
  `REMARKS` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `DEL_FLAG` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `SYS_ROLE_DEL_FLAG` (`DEL_FLAG`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `ROLE_ID` varchar(64) NOT NULL COMMENT '角色编号',
  `MENU_ID` varchar(64) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`ROLE_ID`,`MENU_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色-菜单';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `ID` varchar(64) NOT NULL COMMENT '编号',
  `OFFICE_ID` varchar(64) NOT NULL COMMENT '归属部门',
  `USERNAME` varchar(100) NOT NULL COMMENT '登录名',
  `PASSWORD` varchar(100) NOT NULL COMMENT '密码',
  `NO` varchar(100) DEFAULT NULL COMMENT '工号',
  `NAME` varchar(100) NOT NULL COMMENT '姓名',
  `EMAIL` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `MOBILE` varchar(200) DEFAULT NULL COMMENT '手机',
  `PHOTO` varchar(1000) DEFAULT NULL COMMENT '用户头像',
  `PERSONALITY` varchar(500) DEFAULT NULL COMMENT '个性配置',
  `LOGIN_IP` varchar(100) DEFAULT NULL COMMENT '最后登陆IP',
  `LOGIN_DATE` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `LOGIN_FLAG` varchar(64) DEFAULT NULL COMMENT '是否可登录',
  `CREATE_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(64) NOT NULL COMMENT '更新者',
  `UPDATE_DATE` datetime NOT NULL COMMENT '更新时间',
  `REMARKS` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `DEL_FLAG` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `SYS_USER_OFFICE_ID` (`OFFICE_ID`) USING BTREE,
  KEY `SYS_USER_USERNAME` (`USERNAME`) USING BTREE,
  KEY `SYS_USER_UPDATE_DATE` (`UPDATE_DATE`) USING BTREE,
  KEY `SYS_USER_DEL_FLAG` (`DEL_FLAG`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('u_01', 'o_1', 'system', '4a8ecb75aa501750bd13d3d73b7d62cf4b9ff98b6f39ff70a8198d14', '01', '开发管理员', '', '', 'u_01//4b661acb6455450ab64601fce3fbfb99.jpg', '', '0:0:0:0:0:0:0:1', '2019-04-12 11:00:21', '1', '1', '2019-03-04 18:10:17', '1', '2019-03-04 18:10:17', '', '0');

-- ----------------------------
-- Table structure for sys_user_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_menu`;
CREATE TABLE `sys_user_menu` (
  `ID` varchar(64) NOT NULL COMMENT '主键',
  `USER_ID` varchar(64) DEFAULT NULL COMMENT '用户ID',
  `MENU_ID` varchar(64) DEFAULT NULL COMMENT '菜单ID',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='用户菜单表';

-- ----------------------------
-- Records of sys_user_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `USER_ID` varchar(64) NOT NULL COMMENT '用户编号',
  `ROLE_ID` varchar(64) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`USER_ID`,`ROLE_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户-角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
