/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.103
Source Server Version : 50087
Source Host           : 10.0.0.103:3306
Source Database       : ww_tank_global_java

Target Server Type    : MYSQL
Target Server Version : 50087
File Encoding         : 65001

Date: 2018-02-26 17:13:16
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `agentserviceinfo`
-- ----------------------------
DROP TABLE IF EXISTS `agentserviceinfo`;
CREATE TABLE `agentserviceinfo` (
  `id` bigint(20) NOT NULL,
  `clientcount` int(11) NOT NULL,
  `cpu` varchar(255) default NULL,
  `ip` varchar(255) default NULL,
  `mem` varchar(255) default NULL,
  `processid` bigint(20) NOT NULL,
  `processname` varchar(255) default NULL,
  `sampleTime` int(11) NOT NULL,
  `servercount` int(11) NOT NULL,
  `serverid` int(11) NOT NULL,
  `starttime` varchar(255) default NULL,
  `version` varchar(255) default NULL,
  `zoneid` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of agentserviceinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `agentsysteminfo`
-- ----------------------------
DROP TABLE IF EXISTS `agentsysteminfo`;
CREATE TABLE `agentsysteminfo` (
  `id` bigint(20) NOT NULL,
  `cpucount` int(11) NOT NULL,
  `ip` varchar(255) default NULL,
  `network` varchar(255) default NULL,
  `os` varchar(255) default NULL,
  `sampleTime` int(11) NOT NULL,
  `syscpuidle` int(11) NOT NULL,
  `sysmem` bigint(20) NOT NULL,
  `sysmemfree` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of agentsysteminfo
-- ----------------------------

-- ----------------------------
-- Table structure for `nickname_table`
-- ----------------------------
DROP TABLE IF EXISTS `nickname_table`;
CREATE TABLE `nickname_table` (
  `id` bigint(20) NOT NULL,
  `NickName` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of nickname_table
-- ----------------------------
