/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.103
Source Server Version : 50087
Source Host           : 10.0.0.103:3306
Source Database       : ww_tank_gm_java

Target Server Type    : MYSQL
Target Server Version : 50087
File Encoding         : 65001

Date: 2018-02-26 17:13:43
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL,
  `account` varchar(255) default NULL,
  `lastlogintime` int(11) NOT NULL,
  `password` varchar(255) default NULL,
  `privilege` int(11) NOT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('38001870635009', 'admin', '1518083737', 'e10adc3949ba59abbe56e057f20f883e', '17891601', '0');
INSERT INTO `account` VALUES ('38001870635010', 'zxb', '1517283484', 'e10adc3949ba59abbe56e057f20f883e', '17891601', '0');
INSERT INTO `account` VALUES ('38001870635011', 'suntao', '1513060201', '202cb962ac59075b964b07152d234b70', '65536', '0');
INSERT INTO `account` VALUES ('38001870635012', '%E4%B8%AD%E5%9B%BD', '0', '202cb962ac59075b964b07152d234b70', '16777216', '0');
INSERT INTO `account` VALUES ('38001870635013', '孙涛', '0', '202cb962ac59075b964b07152d234b70', '16', '0');

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
  PRIMARY KEY  (`id`),
  KEY `ip` (`ip`,`processname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of agentserviceinfo
-- ----------------------------
INSERT INTO `agentserviceinfo` VALUES ('472446402561', '5', '0:7', '10.0.0.182', '230M', '4676', 'DBServer', '1517887708', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402562', '0', '0:3', '10.0.0.182', '186M', '3604', 'GameServer', '1517887710', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402563', '0', '0:3', '10.0.0.182', '186M', '3604', 'GameServer', '1517887710', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402564', '11', '0:7', '10.0.0.182', '206M', '4676', 'DBServer', '1517887889', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402565', '0', '0:5', '10.0.0.182', '161M', '3604', 'GameServer', '1517887891', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402566', '11', '0:7', '10.0.0.182', '206M', '4676', 'DBServer', '1517888070', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402567', '0', '0:5', '10.0.0.182', '162M', '3604', 'GameServer', '1517888072', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402568', '11', '0:7', '10.0.0.182', '206M', '4676', 'DBServer', '1517888251', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402569', '0', '0:5', '10.0.0.182', '164M', '3604', 'GameServer', '1517888253', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402570', '11', '0:7', '10.0.0.182', '206M', '4676', 'DBServer', '1517888432', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402571', '0', '0:5', '10.0.0.182', '164M', '3604', 'GameServer', '1517888434', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402572', '11', '0:8', '10.0.0.182', '206M', '4676', 'DBServer', '1517888613', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402573', '0', '0:5', '10.0.0.182', '165M', '3604', 'GameServer', '1517888615', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402574', '11', '0:8', '10.0.0.182', '208M', '4676', 'DBServer', '1517888794', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402575', '0', '0:5', '10.0.0.182', '165M', '3604', 'GameServer', '1517888796', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402576', '11', '0:8', '10.0.0.182', '208M', '4676', 'DBServer', '1517888975', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402577', '0', '0:5', '10.0.0.182', '165M', '3604', 'GameServer', '1517888977', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402578', '11', '0:8', '10.0.0.182', '208M', '4676', 'DBServer', '1517889156', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402579', '0', '0:5', '10.0.0.182', '165M', '3604', 'GameServer', '1517889158', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402580', '11', '0:8', '10.0.0.182', '205M', '4676', 'DBServer', '1517889337', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402581', '0', '0:5', '10.0.0.182', '172M', '3604', 'GameServer', '1517889339', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402582', '11', '0:10', '10.0.0.182', '244M', '4676', 'DBServer', '1517889518', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402583', '0', '0:5', '10.0.0.182', '172M', '3604', 'GameServer', '1517889520', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402584', '11', '0:10', '10.0.0.182', '245M', '4676', 'DBServer', '1517889699', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402585', '0', '0:5', '10.0.0.182', '172M', '3604', 'GameServer', '1517889701', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402586', '11', '0:10', '10.0.0.182', '245M', '4676', 'DBServer', '1517889880', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402587', '0', '0:5', '10.0.0.182', '172M', '3604', 'GameServer', '1517889882', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402588', '11', '0:10', '10.0.0.182', '247M', '4676', 'DBServer', '1517890061', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402589', '0', '0:5', '10.0.0.182', '172M', '3604', 'GameServer', '1517890063', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402590', '11', '0:10', '10.0.0.182', '245M', '4676', 'DBServer', '1517890242', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402591', '0', '0:5', '10.0.0.182', '172M', '3604', 'GameServer', '1517890244', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402592', '11', '0:10', '10.0.0.182', '246M', '4676', 'DBServer', '1517890423', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402593', '0', '0:5', '10.0.0.182', '172M', '3604', 'GameServer', '1517890425', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402594', '11', '0:10', '10.0.0.182', '246M', '4676', 'DBServer', '1517890604', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402595', '0', '0:5', '10.0.0.182', '173M', '3604', 'GameServer', '1517890606', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402596', '11', '0:10', '10.0.0.182', '246M', '4676', 'DBServer', '1517890785', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402597', '0', '0:5', '10.0.0.182', '174M', '3604', 'GameServer', '1517890787', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402598', '11', '0:10', '10.0.0.182', '246M', '4676', 'DBServer', '1517890966', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402599', '0', '0:5', '10.0.0.182', '182M', '3604', 'GameServer', '1517890968', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402600', '11', '0:10', '10.0.0.182', '246M', '4676', 'DBServer', '1517891147', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402601', '0', '0:5', '10.0.0.182', '194M', '3604', 'GameServer', '1517891149', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402602', '11', '0:11', '10.0.0.182', '250M', '4676', 'DBServer', '1517891328', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402603', '0', '0:5', '10.0.0.182', '194M', '3604', 'GameServer', '1517891330', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402604', '11', '0:11', '10.0.0.182', '251M', '4676', 'DBServer', '1517891509', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402605', '0', '0:5', '10.0.0.182', '194M', '3604', 'GameServer', '1517891511', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402606', '11', '0:11', '10.0.0.182', '251M', '4676', 'DBServer', '1517891690', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402607', '0', '0:5', '10.0.0.182', '194M', '3604', 'GameServer', '1517891692', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402608', '11', '0:11', '10.0.0.182', '251M', '4676', 'DBServer', '1517891871', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402609', '0', '0:5', '10.0.0.182', '194M', '3604', 'GameServer', '1517891873', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402610', '11', '0:11', '10.0.0.182', '251M', '4676', 'DBServer', '1517892052', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402611', '0', '0:5', '10.0.0.182', '194M', '3604', 'GameServer', '1517892054', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402612', '11', '0:11', '10.0.0.182', '251M', '4676', 'DBServer', '1517892233', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402613', '0', '0:5', '10.0.0.182', '194M', '3604', 'GameServer', '1517892235', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402614', '11', '0:11', '10.0.0.182', '251M', '4676', 'DBServer', '1517892414', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402615', '0', '0:5', '10.0.0.182', '194M', '3604', 'GameServer', '1517892416', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402616', '11', '0:11', '10.0.0.182', '251M', '4676', 'DBServer', '1517892595', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402617', '0', '0:5', '10.0.0.182', '194M', '3604', 'GameServer', '1517892597', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402618', '11', '0:11', '10.0.0.182', '251M', '4676', 'DBServer', '1517892776', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402619', '0', '0:5', '10.0.0.182', '194M', '3604', 'GameServer', '1517892778', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402620', '11', '0:11', '10.0.0.182', '251M', '4676', 'DBServer', '1517892957', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402621', '0', '0:5', '10.0.0.182', '202M', '3604', 'GameServer', '1517892959', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402622', '11', '0:12', '10.0.0.182', '297M', '4676', 'DBServer', '1517893138', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402623', '0', '0:5', '10.0.0.182', '202M', '3604', 'GameServer', '1517893140', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402624', '11', '0:12', '10.0.0.182', '297M', '4676', 'DBServer', '1517893319', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402625', '0', '0:5', '10.0.0.182', '202M', '3604', 'GameServer', '1517893321', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402626', '11', '0:12', '10.0.0.182', '297M', '4676', 'DBServer', '1517893500', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402627', '0', '0:5', '10.0.0.182', '202M', '3604', 'GameServer', '1517893502', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402628', '11', '0:12', '10.0.0.182', '297M', '4676', 'DBServer', '1517893681', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402629', '0', '0:6', '10.0.0.182', '199M', '3604', 'GameServer', '1517893683', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402630', '11', '0:12', '10.0.0.182', '297M', '4676', 'DBServer', '1517893862', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402631', '0', '0:6', '10.0.0.182', '200M', '3604', 'GameServer', '1517893864', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402632', '11', '0:13', '10.0.0.182', '297M', '4676', 'DBServer', '1517894043', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402633', '0', '0:6', '10.0.0.182', '200M', '3604', 'GameServer', '1517894045', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402634', '11', '0:13', '10.0.0.182', '297M', '4676', 'DBServer', '1517894224', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402635', '0', '0:6', '10.0.0.182', '200M', '3604', 'GameServer', '1517894226', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402636', '11', '0:13', '10.0.0.182', '297M', '4676', 'DBServer', '1517894405', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402637', '1', '0:6', '10.0.0.182', '200M', '3604', 'GameServer', '1517894407', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402638', '11', '0:13', '10.0.0.182', '297M', '4676', 'DBServer', '1517894586', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402639', '0', '0:6', '10.0.0.182', '200M', '3604', 'GameServer', '1517894588', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402640', '11', '0:13', '10.0.0.182', '297M', '4676', 'DBServer', '1517894767', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402641', '1', '0:6', '10.0.0.182', '201M', '3604', 'GameServer', '1517894769', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402642', '11', '0:14', '10.0.0.182', '295M', '4676', 'DBServer', '1517894948', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402643', '0', '0:6', '10.0.0.182', '208M', '3604', 'GameServer', '1517894950', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402644', '11', '0:14', '10.0.0.182', '295M', '4676', 'DBServer', '1517895129', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402645', '0', '0:6', '10.0.0.182', '213M', '3604', 'GameServer', '1517895131', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402646', '11', '0:14', '10.0.0.182', '298M', '4676', 'DBServer', '1517895310', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402647', '0', '0:6', '10.0.0.182', '223M', '3604', 'GameServer', '1517895312', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402648', '11', '0:14', '10.0.0.182', '298M', '4676', 'DBServer', '1517895491', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402649', '0', '0:6', '10.0.0.182', '226M', '3604', 'GameServer', '1517895493', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402650', '11', '0:14', '10.0.0.182', '298M', '4676', 'DBServer', '1517895672', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402651', '0', '0:6', '10.0.0.182', '226M', '3604', 'GameServer', '1517895674', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402652', '11', '0:14', '10.0.0.182', '298M', '4676', 'DBServer', '1517895853', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402653', '0', '0:6', '10.0.0.182', '227M', '3604', 'GameServer', '1517895855', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402654', '11', '0:14', '10.0.0.182', '298M', '4676', 'DBServer', '1517896034', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402655', '0', '0:6', '10.0.0.182', '227M', '3604', 'GameServer', '1517896036', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402656', '11', '0:15', '10.0.0.182', '295M', '4676', 'DBServer', '1517896215', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402657', '1', '0:7', '10.0.0.182', '227M', '3604', 'GameServer', '1517896217', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402658', '11', '0:15', '10.0.0.182', '296M', '4676', 'DBServer', '1517896396', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402659', '0', '0:7', '10.0.0.182', '227M', '3604', 'GameServer', '1517896398', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402660', '11', '0:15', '10.0.0.182', '296M', '4676', 'DBServer', '1517896577', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402661', '0', '0:7', '10.0.0.182', '227M', '3604', 'GameServer', '1517896579', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402662', '11', '0:15', '10.0.0.182', '381M', '4676', 'DBServer', '1517896758', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402663', '0', '0:7', '10.0.0.182', '227M', '3604', 'GameServer', '1517896760', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402664', '11', '0:15', '10.0.0.182', '381M', '4676', 'DBServer', '1517896939', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402665', '0', '0:7', '10.0.0.182', '227M', '3604', 'GameServer', '1517896941', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402666', '11', '0:15', '10.0.0.182', '381M', '4676', 'DBServer', '1517897120', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402667', '0', '0:7', '10.0.0.182', '227M', '3604', 'GameServer', '1517897122', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402668', '11', '0:15', '10.0.0.182', '382M', '4676', 'DBServer', '1517897301', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402669', '0', '0:7', '10.0.0.182', '227M', '3604', 'GameServer', '1517897303', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402670', '11', '0:15', '10.0.0.182', '382M', '4676', 'DBServer', '1517897482', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402671', '0', '0:7', '10.0.0.182', '227M', '3604', 'GameServer', '1517897484', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402672', '11', '0:15', '10.0.0.182', '382M', '4676', 'DBServer', '1517897663', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402673', '0', '0:7', '10.0.0.182', '227M', '3604', 'GameServer', '1517897665', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402674', '11', '0:15', '10.0.0.182', '382M', '4676', 'DBServer', '1517897844', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402675', '0', '0:7', '10.0.0.182', '235M', '3604', 'GameServer', '1517897846', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402676', '11', '0:16', '10.0.0.182', '424M', '4676', 'DBServer', '1517898025', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402677', '0', '0:7', '10.0.0.182', '236M', '3604', 'GameServer', '1517898027', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402678', '11', '0:16', '10.0.0.182', '424M', '4676', 'DBServer', '1517898206', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402679', '0', '0:7', '10.0.0.182', '236M', '3604', 'GameServer', '1517898208', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402680', '11', '0:16', '10.0.0.182', '424M', '4676', 'DBServer', '1517898387', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402681', '0', '0:7', '10.0.0.182', '236M', '3604', 'GameServer', '1517898389', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402682', '11', '0:16', '10.0.0.182', '424M', '4676', 'DBServer', '1517898568', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402683', '0', '0:7', '10.0.0.182', '236M', '3604', 'GameServer', '1517898570', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402684', '11', '0:16', '10.0.0.182', '424M', '4676', 'DBServer', '1517898749', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402685', '0', '0:7', '10.0.0.182', '236M', '3604', 'GameServer', '1517898751', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402686', '11', '0:16', '10.0.0.182', '425M', '4676', 'DBServer', '1517898930', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402687', '0', '0:7', '10.0.0.182', '236M', '3604', 'GameServer', '1517898932', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402688', '11', '0:16', '10.0.0.182', '425M', '4676', 'DBServer', '1517899111', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402689', '0', '0:7', '10.0.0.182', '236M', '3604', 'GameServer', '1517899113', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402690', '11', '0:16', '10.0.0.182', '425M', '4676', 'DBServer', '1517899292', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402691', '0', '0:7', '10.0.0.182', '236M', '3604', 'GameServer', '1517899294', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402692', '11', '0:16', '10.0.0.182', '425M', '4676', 'DBServer', '1517899473', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402693', '0', '0:7', '10.0.0.182', '236M', '3604', 'GameServer', '1517899475', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402694', '11', '0:16', '10.0.0.182', '425M', '4676', 'DBServer', '1517899654', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402695', '0', '0:7', '10.0.0.182', '236M', '3604', 'GameServer', '1517899656', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402696', '11', '0:16', '10.0.0.182', '425M', '4676', 'DBServer', '1517899835', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402697', '0', '0:7', '10.0.0.182', '236M', '3604', 'GameServer', '1517899837', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402698', '11', '0:16', '10.0.0.182', '425M', '4676', 'DBServer', '1517900016', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402699', '0', '0:7', '10.0.0.182', '236M', '3604', 'GameServer', '1517900018', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402700', '11', '0:16', '10.0.0.182', '425M', '4676', 'DBServer', '1517900197', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402701', '0', '0:7', '10.0.0.182', '230M', '3604', 'GameServer', '1517900199', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402702', '11', '0:16', '10.0.0.182', '427M', '4676', 'DBServer', '1517900378', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402703', '0', '0:7', '10.0.0.182', '230M', '3604', 'GameServer', '1517900380', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402704', '11', '0:16', '10.0.0.182', '427M', '4676', 'DBServer', '1517900559', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402705', '0', '0:7', '10.0.0.182', '230M', '3604', 'GameServer', '1517900561', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402706', '11', '0:17', '10.0.0.182', '427M', '4676', 'DBServer', '1517900740', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402707', '0', '0:7', '10.0.0.182', '230M', '3604', 'GameServer', '1517900742', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402708', '11', '0:17', '10.0.0.182', '427M', '4676', 'DBServer', '1517900921', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402709', '0', '0:7', '10.0.0.182', '230M', '3604', 'GameServer', '1517900923', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402710', '11', '0:17', '10.0.0.182', '427M', '4676', 'DBServer', '1517901102', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402711', '0', '0:7', '10.0.0.182', '230M', '3604', 'GameServer', '1517901104', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402712', '11', '0:17', '10.0.0.182', '427M', '4676', 'DBServer', '1517901283', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402713', '0', '0:7', '10.0.0.182', '230M', '3604', 'GameServer', '1517901285', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402714', '11', '0:17', '10.0.0.182', '427M', '4676', 'DBServer', '1517901464', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402715', '0', '0:7', '10.0.0.182', '230M', '3604', 'GameServer', '1517901466', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402716', '11', '0:17', '10.0.0.182', '427M', '4676', 'DBServer', '1517901645', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402717', '0', '0:7', '10.0.0.182', '230M', '3604', 'GameServer', '1517901647', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402718', '11', '0:17', '10.0.0.182', '427M', '4676', 'DBServer', '1517901826', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402719', '0', '0:7', '10.0.0.182', '230M', '3604', 'GameServer', '1517901828', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402720', '11', '0:17', '10.0.0.182', '429M', '4676', 'DBServer', '1517902007', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402721', '0', '0:7', '10.0.0.182', '230M', '3604', 'GameServer', '1517902009', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402722', '11', '0:17', '10.0.0.182', '430M', '4676', 'DBServer', '1517902188', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402723', '0', '0:7', '10.0.0.182', '230M', '3604', 'GameServer', '1517902190', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402724', '11', '0:17', '10.0.0.182', '425M', '4676', 'DBServer', '1517902369', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402725', '0', '0:7', '10.0.0.182', '230M', '3604', 'GameServer', '1517902371', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402726', '11', '0:17', '10.0.0.182', '426M', '4676', 'DBServer', '1517902550', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402727', '0', '0:7', '10.0.0.182', '233M', '3604', 'GameServer', '1517902552', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402728', '11', '0:17', '10.0.0.182', '426M', '4676', 'DBServer', '1517902731', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402729', '0', '0:7', '10.0.0.182', '233M', '3604', 'GameServer', '1517902733', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402730', '11', '0:17', '10.0.0.182', '426M', '4676', 'DBServer', '1517902912', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402731', '0', '0:7', '10.0.0.182', '233M', '3604', 'GameServer', '1517902914', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402732', '11', '0:17', '10.0.0.182', '426M', '4676', 'DBServer', '1517903093', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402733', '0', '0:7', '10.0.0.182', '233M', '3604', 'GameServer', '1517903095', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402734', '11', '0:17', '10.0.0.182', '426M', '4676', 'DBServer', '1517903274', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402735', '0', '0:8', '10.0.0.182', '229M', '3604', 'GameServer', '1517903276', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402736', '11', '0:17', '10.0.0.182', '426M', '4676', 'DBServer', '1517903455', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402737', '0', '0:8', '10.0.0.182', '230M', '3604', 'GameServer', '1517903457', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402738', '11', '0:17', '10.0.0.182', '426M', '4676', 'DBServer', '1517903636', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402739', '0', '0:8', '10.0.0.182', '230M', '3604', 'GameServer', '1517903638', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402740', '11', '0:18', '10.0.0.182', '428M', '4676', 'DBServer', '1517903817', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402741', '0', '0:8', '10.0.0.182', '230M', '3604', 'GameServer', '1517903819', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402742', '11', '0:18', '10.0.0.182', '461M', '4676', 'DBServer', '1517903998', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402743', '0', '0:8', '10.0.0.182', '230M', '3604', 'GameServer', '1517904000', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402744', '11', '0:18', '10.0.0.182', '461M', '4676', 'DBServer', '1517904179', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402745', '0', '0:8', '10.0.0.182', '230M', '3604', 'GameServer', '1517904181', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402746', '11', '0:18', '10.0.0.182', '461M', '4676', 'DBServer', '1517904360', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402747', '0', '0:8', '10.0.0.182', '230M', '3604', 'GameServer', '1517904362', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402748', '11', '0:18', '10.0.0.182', '461M', '4676', 'DBServer', '1517904541', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402749', '0', '0:8', '10.0.0.182', '230M', '3604', 'GameServer', '1517904543', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402750', '11', '0:18', '10.0.0.182', '461M', '4676', 'DBServer', '1517904722', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402751', '0', '0:9', '10.0.0.182', '226M', '3604', 'GameServer', '1517904724', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402752', '11', '0:18', '10.0.0.182', '461M', '4676', 'DBServer', '1517904903', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402753', '0', '0:9', '10.0.0.182', '226M', '3604', 'GameServer', '1517904905', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402754', '11', '0:18', '10.0.0.182', '461M', '4676', 'DBServer', '1517905084', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402755', '0', '0:9', '10.0.0.182', '226M', '3604', 'GameServer', '1517905086', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402756', '11', '0:18', '10.0.0.182', '461M', '4676', 'DBServer', '1517905265', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402757', '0', '0:9', '10.0.0.182', '226M', '3604', 'GameServer', '1517905267', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402758', '11', '0:18', '10.0.0.182', '461M', '4676', 'DBServer', '1517905446', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402759', '0', '0:9', '10.0.0.182', '226M', '3604', 'GameServer', '1517905448', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402760', '11', '0:18', '10.0.0.182', '461M', '4676', 'DBServer', '1517905627', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402761', '0', '0:9', '10.0.0.182', '226M', '3604', 'GameServer', '1517905629', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402762', '11', '0:18', '10.0.0.182', '463M', '4676', 'DBServer', '1517905808', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402763', '0', '0:9', '10.0.0.182', '226M', '3604', 'GameServer', '1517905810', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402764', '11', '0:18', '10.0.0.182', '463M', '4676', 'DBServer', '1517905989', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402765', '0', '0:9', '10.0.0.182', '226M', '3604', 'GameServer', '1517905991', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402766', '11', '0:18', '10.0.0.182', '463M', '4676', 'DBServer', '1517906170', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402767', '0', '0:9', '10.0.0.182', '226M', '3604', 'GameServer', '1517906172', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402768', '11', '0:18', '10.0.0.182', '463M', '4676', 'DBServer', '1517906351', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402769', '0', '0:9', '10.0.0.182', '226M', '3604', 'GameServer', '1517906353', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402770', '11', '0:18', '10.0.0.182', '463M', '4676', 'DBServer', '1517906532', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402771', '0', '0:9', '10.0.0.182', '222M', '3604', 'GameServer', '1517906534', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402772', '11', '0:18', '10.0.0.182', '463M', '4676', 'DBServer', '1517906713', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402773', '0', '0:9', '10.0.0.182', '222M', '3604', 'GameServer', '1517906715', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402774', '11', '0:18', '10.0.0.182', '463M', '4676', 'DBServer', '1517906894', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402775', '0', '0:9', '10.0.0.182', '222M', '3604', 'GameServer', '1517906896', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402776', '11', '0:19', '10.0.0.182', '463M', '4676', 'DBServer', '1517907075', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402777', '0', '0:10', '10.0.0.182', '222M', '3604', 'GameServer', '1517907077', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402778', '11', '0:19', '10.0.0.182', '463M', '4676', 'DBServer', '1517907256', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402779', '0', '0:10', '10.0.0.182', '222M', '3604', 'GameServer', '1517907258', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402780', '11', '0:19', '10.0.0.182', '460M', '4676', 'DBServer', '1517907437', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402781', '0', '0:10', '10.0.0.182', '222M', '3604', 'GameServer', '1517907439', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402782', '11', '0:19', '10.0.0.182', '460M', '4676', 'DBServer', '1517907618', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402783', '0', '0:10', '10.0.0.182', '222M', '3604', 'GameServer', '1517907620', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402784', '11', '0:19', '10.0.0.182', '461M', '4676', 'DBServer', '1517907799', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402785', '0', '0:10', '10.0.0.182', '222M', '3604', 'GameServer', '1517907801', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402786', '11', '0:19', '10.0.0.182', '461M', '4676', 'DBServer', '1517907980', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402787', '0', '0:10', '10.0.0.182', '222M', '3604', 'GameServer', '1517907982', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402788', '11', '0:19', '10.0.0.182', '461M', '4676', 'DBServer', '1517908161', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402789', '0', '0:10', '10.0.0.182', '222M', '3604', 'GameServer', '1517908163', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402790', '11', '0:19', '10.0.0.182', '461M', '4676', 'DBServer', '1517908342', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402791', '0', '0:10', '10.0.0.182', '222M', '3604', 'GameServer', '1517908344', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402792', '11', '0:19', '10.0.0.182', '461M', '4676', 'DBServer', '1517908523', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402793', '0', '0:10', '10.0.0.182', '219M', '3604', 'GameServer', '1517908525', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402794', '11', '0:19', '10.0.0.182', '461M', '4676', 'DBServer', '1517908704', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402795', '0', '0:11', '10.0.0.182', '219M', '3604', 'GameServer', '1517908706', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402796', '11', '0:19', '10.0.0.182', '461M', '4676', 'DBServer', '1517908885', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402797', '0', '0:11', '10.0.0.182', '219M', '3604', 'GameServer', '1517908887', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402798', '11', '0:19', '10.0.0.182', '459M', '4676', 'DBServer', '1517909066', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402799', '0', '0:11', '10.0.0.182', '219M', '3604', 'GameServer', '1517909068', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402800', '11', '0:19', '10.0.0.182', '462M', '4676', 'DBServer', '1517909247', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402801', '0', '0:11', '10.0.0.182', '219M', '3604', 'GameServer', '1517909249', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402802', '11', '0:19', '10.0.0.182', '537M', '4676', 'DBServer', '1517909428', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402803', '0', '0:11', '10.0.0.182', '219M', '3604', 'GameServer', '1517909430', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402804', '11', '0:19', '10.0.0.182', '537M', '4676', 'DBServer', '1517909609', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402805', '0', '0:11', '10.0.0.182', '219M', '3604', 'GameServer', '1517909611', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402806', '11', '0:19', '10.0.0.182', '537M', '4676', 'DBServer', '1517909790', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402807', '0', '0:11', '10.0.0.182', '219M', '3604', 'GameServer', '1517909792', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402808', '11', '0:19', '10.0.0.182', '537M', '4676', 'DBServer', '1517909971', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402809', '0', '0:11', '10.0.0.182', '219M', '3604', 'GameServer', '1517909973', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402810', '11', '0:19', '10.0.0.182', '537M', '4676', 'DBServer', '1517910152', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402811', '0', '0:11', '10.0.0.182', '219M', '3604', 'GameServer', '1517910154', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402812', '11', '0:19', '10.0.0.182', '537M', '4676', 'DBServer', '1517910333', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402813', '0', '0:11', '10.0.0.182', '215M', '3604', 'GameServer', '1517910335', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402814', '11', '0:19', '10.0.0.182', '537M', '4676', 'DBServer', '1517910514', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402815', '0', '0:11', '10.0.0.182', '215M', '3604', 'GameServer', '1517910516', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402816', '11', '0:19', '10.0.0.182', '537M', '4676', 'DBServer', '1517910695', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402817', '0', '0:11', '10.0.0.182', '215M', '3604', 'GameServer', '1517910697', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402818', '11', '0:19', '10.0.0.182', '537M', '4676', 'DBServer', '1517910876', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402819', '0', '0:11', '10.0.0.182', '215M', '3604', 'GameServer', '1517910878', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402820', '11', '0:20', '10.0.0.182', '534M', '4676', 'DBServer', '1517911057', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402821', '0', '0:11', '10.0.0.182', '215M', '3604', 'GameServer', '1517911059', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402822', '11', '0:20', '10.0.0.182', '534M', '4676', 'DBServer', '1517911238', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402823', '0', '0:11', '10.0.0.182', '215M', '3604', 'GameServer', '1517911240', '0', '110', '11:25', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402824', '11', '0:7', '10.0.0.182', '207M', '4412', 'DBServer', '1517965781', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402825', '0', '0:3', '10.0.0.182', '161M', '3304', 'GameServer', '1517965781', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402826', '11', '0:8', '10.0.0.182', '236M', '4412', 'DBServer', '1517965962', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402827', '0', '0:5', '10.0.0.182', '173M', '3304', 'GameServer', '1517965962', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402828', '11', '0:8', '10.0.0.182', '234M', '4412', 'DBServer', '1517966143', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402829', '0', '0:5', '10.0.0.182', '181M', '3304', 'GameServer', '1517966143', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402830', '11', '0:9', '10.0.0.182', '236M', '4412', 'DBServer', '1517966324', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402831', '0', '0:6', '10.0.0.182', '194M', '3304', 'GameServer', '1517966324', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402832', '0', '0:6', '10.0.0.182', '206M', '3304', 'GameServer', '1517966505', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402833', '11', '0:9', '10.0.0.182', '235M', '4412', 'DBServer', '1517966505', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402834', '11', '0:9', '10.0.0.182', '235M', '4412', 'DBServer', '1517966686', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402835', '0', '0:6', '10.0.0.182', '220M', '3304', 'GameServer', '1517966686', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402836', '0', '0:6', '10.0.0.182', '220M', '3304', 'GameServer', '1517966867', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402837', '11', '0:9', '10.0.0.182', '235M', '4412', 'DBServer', '1517966867', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402838', '11', '0:9', '10.0.0.182', '236M', '4412', 'DBServer', '1517967048', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402839', '0', '0:6', '10.0.0.182', '221M', '3304', 'GameServer', '1517967048', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402840', '0', '0:6', '10.0.0.182', '221M', '3304', 'GameServer', '1517967229', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402841', '11', '0:9', '10.0.0.182', '236M', '4412', 'DBServer', '1517967229', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402842', '0', '0:6', '10.0.0.182', '221M', '3304', 'GameServer', '1517967410', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402843', '11', '0:9', '10.0.0.182', '237M', '4412', 'DBServer', '1517967410', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402844', '0', '0:6', '10.0.0.182', '222M', '3304', 'GameServer', '1517967591', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402845', '11', '0:10', '10.0.0.182', '239M', '4412', 'DBServer', '1517967591', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402846', '1', '0:6', '10.0.0.182', '225M', '3304', 'GameServer', '1517967772', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402847', '11', '0:10', '10.0.0.182', '239M', '4412', 'DBServer', '1517967772', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402848', '11', '0:10', '10.0.0.182', '238M', '4412', 'DBServer', '1517967953', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402849', '0', '0:6', '10.0.0.182', '227M', '3304', 'GameServer', '1517967953', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402850', '11', '0:10', '10.0.0.182', '240M', '4412', 'DBServer', '1517968134', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402851', '0', '0:6', '10.0.0.182', '229M', '3304', 'GameServer', '1517968134', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402852', '11', '0:10', '10.0.0.182', '240M', '4412', 'DBServer', '1517968315', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402853', '0', '0:6', '10.0.0.182', '231M', '3304', 'GameServer', '1517968315', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402854', '11', '0:10', '10.0.0.182', '241M', '4412', 'DBServer', '1517968496', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402855', '0', '0:6', '10.0.0.182', '231M', '3304', 'GameServer', '1517968496', '0', '110', '09:06', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402856', '0', '0:7', '10.0.0.182', '259M', '5328', 'DBServer', '1517968597', '0', '110', '09:53', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402857', '0', '0:6', '10.0.0.182', '205M', '6448', 'DBServer', '1517968683', '0', '110', '09:55', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402858', '0', '0:3', '10.0.0.182', '160M', '3600', 'GameServer', '1517968695', '0', '110', '09:55', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402859', '11', '0:6', '10.0.0.182', '211M', '1284', 'DBServer', '1517969114', '0', '110', '10:02', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402860', '0', '0:3', '10.0.0.182', '160M', '4340', 'GameServer', '1517969118', '0', '110', '10:02', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402861', '11', '0:6', '10.0.0.182', '212M', '1284', 'DBServer', '1517969295', '0', '110', '10:02', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402862', '0', '0:3', '10.0.0.182', '162M', '4340', 'GameServer', '1517969299', '0', '110', '10:02', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402863', '11', '0:7', '10.0.0.182', '213M', '1284', 'DBServer', '1517969476', '0', '110', '10:02', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402864', '0', '0:3', '10.0.0.182', '162M', '4340', 'GameServer', '1517969480', '0', '110', '10:02', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402865', '5', '0:6', '10.0.0.182', '251M', '6256', 'DBServer', '1517969559', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402866', '0', '0:3', '10.0.0.182', '181M', '7088', 'GameServer', '1517969568', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402867', '11', '0:7', '10.0.0.182', '207M', '6256', 'DBServer', '1517969740', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402868', '1', '0:4', '10.0.0.182', '162M', '7088', 'GameServer', '1517969749', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402869', '11', '0:7', '10.0.0.182', '207M', '6256', 'DBServer', '1517969921', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402870', '1', '0:4', '10.0.0.182', '162M', '7088', 'GameServer', '1517969930', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402871', '11', '0:7', '10.0.0.182', '207M', '6256', 'DBServer', '1517970102', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402872', '1', '0:4', '10.0.0.182', '165M', '7088', 'GameServer', '1517970111', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402873', '11', '0:7', '10.0.0.182', '209M', '6256', 'DBServer', '1517970283', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402874', '1', '0:4', '10.0.0.182', '165M', '7088', 'GameServer', '1517970292', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402875', '11', '0:7', '10.0.0.182', '210M', '6256', 'DBServer', '1517970464', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402876', '0', '0:4', '10.0.0.182', '165M', '7088', 'GameServer', '1517970473', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402877', '11', '0:8', '10.0.0.182', '210M', '6256', 'DBServer', '1517970645', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402878', '0', '0:5', '10.0.0.182', '172M', '7088', 'GameServer', '1517970654', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402879', '11', '0:8', '10.0.0.182', '210M', '6256', 'DBServer', '1517970826', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402880', '0', '0:5', '10.0.0.182', '174M', '7088', 'GameServer', '1517970835', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402881', '11', '0:8', '10.0.0.182', '210M', '6256', 'DBServer', '1517971007', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402882', '0', '0:5', '10.0.0.182', '174M', '7088', 'GameServer', '1517971016', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402883', '11', '0:8', '10.0.0.182', '211M', '6256', 'DBServer', '1517971188', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402884', '0', '0:5', '10.0.0.182', '174M', '7088', 'GameServer', '1517971197', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402885', '11', '0:9', '10.0.0.182', '236M', '6256', 'DBServer', '1517971369', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402886', '0', '0:5', '10.0.0.182', '174M', '7088', 'GameServer', '1517971378', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402887', '11', '0:9', '10.0.0.182', '236M', '6256', 'DBServer', '1517971550', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402888', '0', '0:5', '10.0.0.182', '176M', '7088', 'GameServer', '1517971559', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402889', '11', '0:9', '10.0.0.182', '237M', '6256', 'DBServer', '1517971731', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402890', '0', '0:5', '10.0.0.182', '184M', '7088', 'GameServer', '1517971740', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402891', '11', '0:9', '10.0.0.182', '237M', '6256', 'DBServer', '1517971912', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402892', '0', '0:5', '10.0.0.182', '184M', '7088', 'GameServer', '1517971921', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402893', '11', '0:10', '10.0.0.182', '239M', '6256', 'DBServer', '1517972093', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402894', '0', '0:5', '10.0.0.182', '184M', '7088', 'GameServer', '1517972102', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402895', '11', '0:10', '10.0.0.182', '239M', '6256', 'DBServer', '1517972274', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402896', '0', '0:5', '10.0.0.182', '184M', '7088', 'GameServer', '1517972283', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402897', '11', '0:10', '10.0.0.182', '239M', '6256', 'DBServer', '1517972455', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402898', '0', '0:5', '10.0.0.182', '184M', '7088', 'GameServer', '1517972464', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402899', '11', '0:10', '10.0.0.182', '239M', '6256', 'DBServer', '1517972636', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402900', '0', '0:5', '10.0.0.182', '184M', '7088', 'GameServer', '1517972645', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402901', '11', '0:10', '10.0.0.182', '237M', '6256', 'DBServer', '1517972817', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402902', '1', '0:5', '10.0.0.182', '185M', '7088', 'GameServer', '1517972826', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402903', '11', '0:10', '10.0.0.182', '236M', '6256', 'DBServer', '1517972998', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402904', '0', '0:6', '10.0.0.182', '185M', '7088', 'GameServer', '1517973007', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402905', '11', '0:10', '10.0.0.182', '283M', '6256', 'DBServer', '1517973179', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402906', '1', '0:6', '10.0.0.182', '187M', '7088', 'GameServer', '1517973188', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402907', '11', '0:10', '10.0.0.182', '285M', '6256', 'DBServer', '1517973360', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402908', '1', '0:6', '10.0.0.182', '186M', '7088', 'GameServer', '1517973369', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402909', '11', '0:11', '10.0.0.182', '304M', '6256', 'DBServer', '1517973541', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402910', '0', '0:6', '10.0.0.182', '187M', '7088', 'GameServer', '1517973550', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402911', '11', '0:11', '10.0.0.182', '305M', '6256', 'DBServer', '1517973722', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402912', '0', '0:6', '10.0.0.182', '187M', '7088', 'GameServer', '1517973731', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402913', '11', '0:11', '10.0.0.182', '303M', '6256', 'DBServer', '1517973903', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402914', '0', '0:6', '10.0.0.182', '188M', '7088', 'GameServer', '1517973912', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402915', '11', '0:11', '10.0.0.182', '303M', '6256', 'DBServer', '1517974084', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402916', '0', '0:6', '10.0.0.182', '188M', '7088', 'GameServer', '1517974093', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402917', '11', '0:11', '10.0.0.182', '304M', '6256', 'DBServer', '1517974265', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402918', '0', '0:6', '10.0.0.182', '189M', '7088', 'GameServer', '1517974274', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402919', '11', '0:11', '10.0.0.182', '304M', '6256', 'DBServer', '1517974446', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402920', '0', '0:6', '10.0.0.182', '189M', '7088', 'GameServer', '1517974455', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402921', '11', '0:11', '10.0.0.182', '304M', '6256', 'DBServer', '1517974627', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402922', '0', '0:6', '10.0.0.182', '189M', '7088', 'GameServer', '1517974636', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402923', '11', '0:11', '10.0.0.182', '304M', '6256', 'DBServer', '1517974808', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402924', '0', '0:6', '10.0.0.182', '189M', '7088', 'GameServer', '1517974817', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402925', '11', '0:12', '10.0.0.182', '304M', '6256', 'DBServer', '1517974989', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402926', '0', '0:6', '10.0.0.182', '189M', '7088', 'GameServer', '1517974998', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402927', '11', '0:12', '10.0.0.182', '307M', '6256', 'DBServer', '1517975170', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402928', '0', '0:7', '10.0.0.182', '188M', '7088', 'GameServer', '1517975179', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402929', '11', '0:12', '10.0.0.182', '307M', '6256', 'DBServer', '1517975351', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402930', '0', '0:7', '10.0.0.182', '188M', '7088', 'GameServer', '1517975360', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402931', '11', '0:12', '10.0.0.182', '307M', '6256', 'DBServer', '1517975532', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402932', '0', '0:7', '10.0.0.182', '187M', '7088', 'GameServer', '1517975541', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402933', '11', '0:12', '10.0.0.182', '307M', '6256', 'DBServer', '1517975713', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402934', '0', '0:7', '10.0.0.182', '187M', '7088', 'GameServer', '1517975722', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402935', '11', '0:12', '10.0.0.182', '307M', '6256', 'DBServer', '1517975894', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402936', '0', '0:7', '10.0.0.182', '187M', '7088', 'GameServer', '1517975903', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402937', '11', '0:12', '10.0.0.182', '307M', '6256', 'DBServer', '1517976075', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402938', '0', '0:7', '10.0.0.182', '186M', '7088', 'GameServer', '1517976084', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402939', '11', '0:12', '10.0.0.182', '308M', '6256', 'DBServer', '1517976256', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402940', '0', '0:7', '10.0.0.182', '186M', '7088', 'GameServer', '1517976265', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402941', '11', '0:12', '10.0.0.182', '308M', '6256', 'DBServer', '1517976437', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402942', '0', '0:7', '10.0.0.182', '186M', '7088', 'GameServer', '1517976446', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402943', '11', '0:12', '10.0.0.182', '308M', '6256', 'DBServer', '1517976618', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402944', '0', '0:7', '10.0.0.182', '186M', '7088', 'GameServer', '1517976627', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402945', '11', '0:13', '10.0.0.182', '331M', '6256', 'DBServer', '1517976799', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402946', '0', '0:7', '10.0.0.182', '186M', '7088', 'GameServer', '1517976808', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402947', '11', '0:13', '10.0.0.182', '331M', '6256', 'DBServer', '1517976980', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402948', '0', '0:7', '10.0.0.182', '186M', '7088', 'GameServer', '1517976989', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402949', '11', '0:13', '10.0.0.182', '331M', '6256', 'DBServer', '1517977161', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402950', '0', '0:7', '10.0.0.182', '186M', '7088', 'GameServer', '1517977170', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402951', '11', '0:13', '10.0.0.182', '331M', '6256', 'DBServer', '1517977342', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402952', '0', '0:7', '10.0.0.182', '186M', '7088', 'GameServer', '1517977351', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402953', '11', '0:13', '10.0.0.182', '333M', '6256', 'DBServer', '1517977523', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402954', '0', '0:7', '10.0.0.182', '185M', '7088', 'GameServer', '1517977532', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402955', '11', '0:13', '10.0.0.182', '333M', '6256', 'DBServer', '1517977704', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402956', '0', '0:7', '10.0.0.182', '186M', '7088', 'GameServer', '1517977713', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402957', '11', '0:13', '10.0.0.182', '333M', '6256', 'DBServer', '1517977885', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402958', '0', '0:7', '10.0.0.182', '186M', '7088', 'GameServer', '1517977894', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402959', '11', '0:13', '10.0.0.182', '333M', '6256', 'DBServer', '1517978066', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402960', '0', '0:7', '10.0.0.182', '186M', '7088', 'GameServer', '1517978075', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402961', '11', '0:13', '10.0.0.182', '334M', '6256', 'DBServer', '1517978247', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402962', '0', '0:7', '10.0.0.182', '186M', '7088', 'GameServer', '1517978256', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402963', '11', '0:13', '10.0.0.182', '334M', '6256', 'DBServer', '1517978428', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402964', '0', '0:7', '10.0.0.182', '186M', '7088', 'GameServer', '1517978437', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402965', '11', '0:14', '10.0.0.182', '331M', '6256', 'DBServer', '1517978609', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402966', '0', '0:7', '10.0.0.182', '186M', '7088', 'GameServer', '1517978618', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402967', '11', '0:14', '10.0.0.182', '331M', '6256', 'DBServer', '1517978790', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402968', '0', '0:7', '10.0.0.182', '185M', '7088', 'GameServer', '1517978799', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402969', '11', '0:14', '10.0.0.182', '331M', '6256', 'DBServer', '1517978971', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402970', '0', '0:7', '10.0.0.182', '185M', '7088', 'GameServer', '1517978980', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402971', '11', '0:14', '10.0.0.182', '331M', '6256', 'DBServer', '1517979152', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402972', '0', '0:7', '10.0.0.182', '185M', '7088', 'GameServer', '1517979161', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402973', '11', '0:14', '10.0.0.182', '331M', '6256', 'DBServer', '1517979333', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402974', '0', '0:7', '10.0.0.182', '185M', '7088', 'GameServer', '1517979342', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402975', '11', '0:14', '10.0.0.182', '331M', '6256', 'DBServer', '1517979514', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402976', '0', '0:7', '10.0.0.182', '185M', '7088', 'GameServer', '1517979523', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402977', '11', '0:14', '10.0.0.182', '331M', '6256', 'DBServer', '1517979695', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402978', '0', '0:7', '10.0.0.182', '185M', '7088', 'GameServer', '1517979704', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402979', '11', '0:14', '10.0.0.182', '331M', '6256', 'DBServer', '1517979876', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402980', '0', '0:7', '10.0.0.182', '186M', '7088', 'GameServer', '1517979885', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402981', '11', '0:14', '10.0.0.182', '331M', '6256', 'DBServer', '1517980057', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402982', '0', '0:7', '10.0.0.182', '185M', '7088', 'GameServer', '1517980066', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402983', '11', '0:14', '10.0.0.182', '332M', '6256', 'DBServer', '1517980238', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402984', '0', '0:7', '10.0.0.182', '185M', '7088', 'GameServer', '1517980247', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402985', '11', '0:14', '10.0.0.182', '339M', '6256', 'DBServer', '1517980419', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402986', '0', '0:7', '10.0.0.182', '185M', '7088', 'GameServer', '1517980428', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402987', '11', '0:14', '10.0.0.182', '339M', '6256', 'DBServer', '1517980600', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402988', '0', '0:7', '10.0.0.182', '185M', '7088', 'GameServer', '1517980609', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402989', '11', '0:15', '10.0.0.182', '340M', '6256', 'DBServer', '1517980781', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402990', '0', '0:7', '10.0.0.182', '185M', '7088', 'GameServer', '1517980790', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402991', '11', '0:15', '10.0.0.182', '340M', '6256', 'DBServer', '1517980962', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402992', '0', '0:7', '10.0.0.182', '185M', '7088', 'GameServer', '1517980971', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402993', '11', '0:15', '10.0.0.182', '340M', '6256', 'DBServer', '1517981143', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402994', '1', '0:7', '10.0.0.182', '186M', '7088', 'GameServer', '1517981152', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402995', '11', '0:15', '10.0.0.182', '340M', '6256', 'DBServer', '1517981324', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402996', '1', '0:7', '10.0.0.182', '184M', '7088', 'GameServer', '1517981333', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402997', '11', '0:15', '10.0.0.182', '340M', '6256', 'DBServer', '1517981505', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402998', '1', '0:7', '10.0.0.182', '184M', '7088', 'GameServer', '1517981514', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446402999', '11', '0:15', '10.0.0.182', '341M', '6256', 'DBServer', '1517981686', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403000', '1', '0:7', '10.0.0.182', '184M', '7088', 'GameServer', '1517981695', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403001', '11', '0:15', '10.0.0.182', '341M', '6256', 'DBServer', '1517981867', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403002', '0', '0:7', '10.0.0.182', '184M', '7088', 'GameServer', '1517981876', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403003', '11', '0:15', '10.0.0.182', '341M', '6256', 'DBServer', '1517982048', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403004', '0', '0:7', '10.0.0.182', '184M', '7088', 'GameServer', '1517982057', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403005', '11', '0:15', '10.0.0.182', '336M', '6256', 'DBServer', '1517982229', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403006', '0', '0:7', '10.0.0.182', '184M', '7088', 'GameServer', '1517982238', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403007', '11', '0:15', '10.0.0.182', '336M', '6256', 'DBServer', '1517982410', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403008', '0', '0:7', '10.0.0.182', '184M', '7088', 'GameServer', '1517982419', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403009', '11', '0:15', '10.0.0.182', '339M', '6256', 'DBServer', '1517982591', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403010', '0', '0:7', '10.0.0.182', '184M', '7088', 'GameServer', '1517982600', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403011', '11', '0:15', '10.0.0.182', '339M', '6256', 'DBServer', '1517982772', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403012', '0', '0:7', '10.0.0.182', '184M', '7088', 'GameServer', '1517982781', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403013', '11', '0:15', '10.0.0.182', '339M', '6256', 'DBServer', '1517982953', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403014', '0', '0:7', '10.0.0.182', '185M', '7088', 'GameServer', '1517982962', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403015', '11', '0:15', '10.0.0.182', '340M', '6256', 'DBServer', '1517983134', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403016', '0', '0:7', '10.0.0.182', '185M', '7088', 'GameServer', '1517983143', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403017', '11', '0:15', '10.0.0.182', '340M', '6256', 'DBServer', '1517983315', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403018', '0', '0:8', '10.0.0.182', '185M', '7088', 'GameServer', '1517983324', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403019', '11', '0:16', '10.0.0.182', '346M', '6256', 'DBServer', '1517983496', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403020', '0', '0:8', '10.0.0.182', '185M', '7088', 'GameServer', '1517983505', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403021', '11', '0:16', '10.0.0.182', '339M', '6256', 'DBServer', '1517983677', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403022', '0', '0:8', '10.0.0.182', '185M', '7088', 'GameServer', '1517983686', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403023', '11', '0:16', '10.0.0.182', '373M', '6256', 'DBServer', '1517983858', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403024', '1', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517983867', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403025', '11', '0:17', '10.0.0.182', '370M', '6256', 'DBServer', '1517984039', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403026', '0', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517984048', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403027', '11', '0:17', '10.0.0.182', '368M', '6256', 'DBServer', '1517984220', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403028', '1', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517984229', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403029', '11', '0:17', '10.0.0.182', '370M', '6256', 'DBServer', '1517984401', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403030', '1', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517984410', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403031', '11', '0:17', '10.0.0.182', '370M', '6256', 'DBServer', '1517984582', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403032', '0', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517984591', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403033', '11', '0:17', '10.0.0.182', '370M', '6256', 'DBServer', '1517984763', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403034', '0', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517984772', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403035', '11', '0:17', '10.0.0.182', '370M', '6256', 'DBServer', '1517984944', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403036', '0', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517984953', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403037', '11', '0:17', '10.0.0.182', '370M', '6256', 'DBServer', '1517985125', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403038', '0', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517985134', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403039', '11', '0:17', '10.0.0.182', '370M', '6256', 'DBServer', '1517985306', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403040', '0', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517985315', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403041', '11', '0:17', '10.0.0.182', '370M', '6256', 'DBServer', '1517985487', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403042', '0', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517985496', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403043', '11', '0:18', '10.0.0.182', '370M', '6256', 'DBServer', '1517985668', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403044', '0', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517985677', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403045', '11', '0:18', '10.0.0.182', '370M', '6256', 'DBServer', '1517985849', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403046', '0', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517985858', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403047', '11', '0:18', '10.0.0.182', '370M', '6256', 'DBServer', '1517986030', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403048', '0', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517986039', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403049', '11', '0:18', '10.0.0.182', '370M', '6256', 'DBServer', '1517986211', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403050', '0', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517986220', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403051', '11', '0:18', '10.0.0.182', '370M', '6256', 'DBServer', '1517986392', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403052', '0', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517986401', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403053', '11', '0:18', '10.0.0.182', '370M', '6256', 'DBServer', '1517986573', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403054', '0', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517986582', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403055', '11', '0:18', '10.0.0.182', '370M', '6256', 'DBServer', '1517986754', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403056', '0', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517986763', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403057', '11', '0:18', '10.0.0.182', '370M', '6256', 'DBServer', '1517986935', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403058', '0', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517986944', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403059', '11', '0:18', '10.0.0.182', '370M', '6256', 'DBServer', '1517987116', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403060', '0', '0:8', '10.0.0.182', '186M', '7088', 'GameServer', '1517987125', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403061', '11', '0:18', '10.0.0.182', '370M', '6256', 'DBServer', '1517987297', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403062', '0', '0:9', '10.0.0.182', '193M', '7088', 'GameServer', '1517987306', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403063', '11', '0:19', '10.0.0.182', '372M', '6256', 'DBServer', '1517987478', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403064', '0', '0:9', '10.0.0.182', '193M', '7088', 'GameServer', '1517987487', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403065', '11', '0:19', '10.0.0.182', '372M', '6256', 'DBServer', '1517987659', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403066', '0', '0:9', '10.0.0.182', '193M', '7088', 'GameServer', '1517987668', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403067', '11', '0:19', '10.0.0.182', '371M', '6256', 'DBServer', '1517987840', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403068', '0', '0:9', '10.0.0.182', '193M', '7088', 'GameServer', '1517987849', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403069', '11', '0:19', '10.0.0.182', '373M', '6256', 'DBServer', '1517988021', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403070', '0', '0:9', '10.0.0.182', '193M', '7088', 'GameServer', '1517988030', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403071', '11', '0:19', '10.0.0.182', '373M', '6256', 'DBServer', '1517988202', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403072', '0', '0:9', '10.0.0.182', '193M', '7088', 'GameServer', '1517988211', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403073', '11', '0:19', '10.0.0.182', '373M', '6256', 'DBServer', '1517988383', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403074', '0', '0:9', '10.0.0.182', '193M', '7088', 'GameServer', '1517988392', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403075', '11', '0:19', '10.0.0.182', '373M', '6256', 'DBServer', '1517988564', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403076', '0', '0:9', '10.0.0.182', '193M', '7088', 'GameServer', '1517988573', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403077', '11', '0:19', '10.0.0.182', '373M', '6256', 'DBServer', '1517988745', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403078', '0', '0:9', '10.0.0.182', '193M', '7088', 'GameServer', '1517988754', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403079', '11', '0:19', '10.0.0.182', '373M', '6256', 'DBServer', '1517988926', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403080', '0', '0:9', '10.0.0.182', '193M', '7088', 'GameServer', '1517988935', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403081', '11', '0:19', '10.0.0.182', '373M', '6256', 'DBServer', '1517989107', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403082', '1', '0:9', '10.0.0.182', '193M', '7088', 'GameServer', '1517989116', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403083', '11', '0:20', '10.0.0.182', '374M', '6256', 'DBServer', '1517989288', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403084', '1', '0:9', '10.0.0.182', '193M', '7088', 'GameServer', '1517989297', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403085', '11', '0:20', '10.0.0.182', '388M', '6256', 'DBServer', '1517989469', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403086', '1', '0:9', '10.0.0.182', '194M', '7088', 'GameServer', '1517989478', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403087', '11', '0:20', '10.0.0.182', '388M', '6256', 'DBServer', '1517989650', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403088', '1', '0:9', '10.0.0.182', '195M', '7088', 'GameServer', '1517989659', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403089', '11', '0:20', '10.0.0.182', '388M', '6256', 'DBServer', '1517989831', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403090', '1', '0:9', '10.0.0.182', '195M', '7088', 'GameServer', '1517989840', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403091', '11', '0:20', '10.0.0.182', '388M', '6256', 'DBServer', '1517990012', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403092', '1', '0:9', '10.0.0.182', '195M', '7088', 'GameServer', '1517990021', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403093', '11', '0:20', '10.0.0.182', '388M', '6256', 'DBServer', '1517990193', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403094', '0', '0:9', '10.0.0.182', '195M', '7088', 'GameServer', '1517990202', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403095', '11', '0:20', '10.0.0.182', '388M', '6256', 'DBServer', '1517990374', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403096', '0', '0:9', '10.0.0.182', '195M', '7088', 'GameServer', '1517990383', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403097', '11', '0:20', '10.0.0.182', '388M', '6256', 'DBServer', '1517990555', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403098', '0', '0:9', '10.0.0.182', '195M', '7088', 'GameServer', '1517990564', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403099', '11', '0:20', '10.0.0.182', '388M', '6256', 'DBServer', '1517990736', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403100', '0', '0:9', '10.0.0.182', '194M', '7088', 'GameServer', '1517990745', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403101', '11', '0:20', '10.0.0.182', '388M', '6256', 'DBServer', '1517990917', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403102', '0', '0:9', '10.0.0.182', '194M', '7088', 'GameServer', '1517990926', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403103', '11', '0:21', '10.0.0.182', '383M', '6256', 'DBServer', '1517991098', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403104', '0', '0:9', '10.0.0.182', '194M', '7088', 'GameServer', '1517991107', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403105', '11', '0:21', '10.0.0.182', '384M', '6256', 'DBServer', '1517991279', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403106', '0', '0:9', '10.0.0.182', '194M', '7088', 'GameServer', '1517991288', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403107', '11', '0:21', '10.0.0.182', '384M', '6256', 'DBServer', '1517991460', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403108', '0', '0:9', '10.0.0.182', '194M', '7088', 'GameServer', '1517991469', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403109', '11', '0:21', '10.0.0.182', '384M', '6256', 'DBServer', '1517991641', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403110', '0', '0:9', '10.0.0.182', '194M', '7088', 'GameServer', '1517991650', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403111', '11', '0:21', '10.0.0.182', '384M', '6256', 'DBServer', '1517991822', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403112', '0', '0:9', '10.0.0.182', '195M', '7088', 'GameServer', '1517991831', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403113', '11', '0:21', '10.0.0.182', '384M', '6256', 'DBServer', '1517992003', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403114', '0', '0:9', '10.0.0.182', '195M', '7088', 'GameServer', '1517992012', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403115', '11', '0:21', '10.0.0.182', '384M', '6256', 'DBServer', '1517992184', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403116', '0', '0:9', '10.0.0.182', '195M', '7088', 'GameServer', '1517992193', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403117', '11', '0:21', '10.0.0.182', '384M', '6256', 'DBServer', '1517992365', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403118', '0', '0:9', '10.0.0.182', '195M', '7088', 'GameServer', '1517992374', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403119', '11', '0:21', '10.0.0.182', '384M', '6256', 'DBServer', '1517992546', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403120', '0', '0:9', '10.0.0.182', '195M', '7088', 'GameServer', '1517992555', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403121', '11', '0:21', '10.0.0.182', '384M', '6256', 'DBServer', '1517992727', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403122', '0', '0:9', '10.0.0.182', '195M', '7088', 'GameServer', '1517992736', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403123', '11', '0:21', '10.0.0.182', '385M', '6256', 'DBServer', '1517992908', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403124', '0', '0:9', '10.0.0.182', '194M', '7088', 'GameServer', '1517992917', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403125', '11', '0:21', '10.0.0.182', '385M', '6256', 'DBServer', '1517993089', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403126', '0', '0:9', '10.0.0.182', '194M', '7088', 'GameServer', '1517993098', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403127', '11', '0:21', '10.0.0.182', '385M', '6256', 'DBServer', '1517993270', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403128', '0', '0:9', '10.0.0.182', '194M', '7088', 'GameServer', '1517993279', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403129', '11', '0:21', '10.0.0.182', '386M', '6256', 'DBServer', '1517993451', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403130', '0', '0:9', '10.0.0.182', '194M', '7088', 'GameServer', '1517993460', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403131', '11', '0:21', '10.0.0.182', '386M', '6256', 'DBServer', '1517993632', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403132', '1', '0:9', '10.0.0.182', '194M', '7088', 'GameServer', '1517993641', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403133', '11', '0:21', '10.0.0.182', '384M', '6256', 'DBServer', '1517993813', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403134', '0', '0:9', '10.0.0.182', '194M', '7088', 'GameServer', '1517993822', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403135', '11', '0:21', '10.0.0.182', '384M', '6256', 'DBServer', '1517993994', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403136', '0', '0:9', '10.0.0.182', '194M', '7088', 'GameServer', '1517994003', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403137', '11', '0:21', '10.0.0.182', '384M', '6256', 'DBServer', '1517994175', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403138', '0', '0:9', '10.0.0.182', '194M', '7088', 'GameServer', '1517994184', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403139', '11', '0:21', '10.0.0.182', '387M', '6256', 'DBServer', '1517994356', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403140', '0', '0:9', '10.0.0.182', '193M', '7088', 'GameServer', '1517994365', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403141', '11', '0:21', '10.0.0.182', '387M', '6256', 'DBServer', '1517994537', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403142', '0', '0:9', '10.0.0.182', '193M', '7088', 'GameServer', '1517994546', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403143', '11', '0:22', '10.0.0.182', '387M', '6256', 'DBServer', '1517994718', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403144', '1', '0:9', '10.0.0.182', '193M', '7088', 'GameServer', '1517994727', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403145', '11', '0:22', '10.0.0.182', '388M', '6256', 'DBServer', '1517994899', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403146', '1', '0:10', '10.0.0.182', '193M', '7088', 'GameServer', '1517994908', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403147', '11', '0:22', '10.0.0.182', '383M', '6256', 'DBServer', '1517995080', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403148', '0', '0:10', '10.0.0.182', '192M', '7088', 'GameServer', '1517995089', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403149', '11', '0:22', '10.0.0.182', '385M', '6256', 'DBServer', '1517995261', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403150', '0', '0:10', '10.0.0.182', '192M', '7088', 'GameServer', '1517995270', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403151', '11', '0:22', '10.0.0.182', '385M', '6256', 'DBServer', '1517995442', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403152', '0', '0:10', '10.0.0.182', '192M', '7088', 'GameServer', '1517995451', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403153', '11', '0:22', '10.0.0.182', '385M', '6256', 'DBServer', '1517995623', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403154', '0', '0:10', '10.0.0.182', '192M', '7088', 'GameServer', '1517995632', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403155', '11', '0:22', '10.0.0.182', '385M', '6256', 'DBServer', '1517995804', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403156', '0', '0:10', '10.0.0.182', '192M', '7088', 'GameServer', '1517995813', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403157', '11', '0:22', '10.0.0.182', '385M', '6256', 'DBServer', '1517995985', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403158', '0', '0:10', '10.0.0.182', '188M', '7088', 'GameServer', '1517995994', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403159', '11', '0:22', '10.0.0.182', '386M', '6256', 'DBServer', '1517996166', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403160', '0', '0:10', '10.0.0.182', '187M', '7088', 'GameServer', '1517996175', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403161', '11', '0:22', '10.0.0.182', '386M', '6256', 'DBServer', '1517996347', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403162', '0', '0:10', '10.0.0.182', '187M', '7088', 'GameServer', '1517996356', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403163', '11', '0:23', '10.0.0.182', '386M', '6256', 'DBServer', '1517996528', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403164', '0', '0:10', '10.0.0.182', '187M', '7088', 'GameServer', '1517996537', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403165', '11', '0:23', '10.0.0.182', '386M', '6256', 'DBServer', '1517996709', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403166', '0', '0:10', '10.0.0.182', '187M', '7088', 'GameServer', '1517996718', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403167', '11', '0:23', '10.0.0.182', '386M', '6256', 'DBServer', '1517996890', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403168', '0', '0:10', '10.0.0.182', '187M', '7088', 'GameServer', '1517996899', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403169', '11', '0:23', '10.0.0.182', '386M', '6256', 'DBServer', '1517997071', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403170', '0', '0:10', '10.0.0.182', '186M', '7088', 'GameServer', '1517997080', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403171', '11', '0:23', '10.0.0.182', '384M', '6256', 'DBServer', '1517997252', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403172', '0', '0:10', '10.0.0.182', '186M', '7088', 'GameServer', '1517997261', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403173', '11', '0:23', '10.0.0.182', '384M', '6256', 'DBServer', '1517997433', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403174', '0', '0:10', '10.0.0.182', '186M', '7088', 'GameServer', '1517997442', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403175', '11', '0:23', '10.0.0.182', '383M', '6256', 'DBServer', '1517997614', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403176', '1', '0:10', '10.0.0.182', '188M', '7088', 'GameServer', '1517997623', '0', '110', '10:09', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403177', '11', '0:7', '10.0.0.182', '209M', '3940', 'DBServer', '1518052596', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403178', '0', '0:4', '10.0.0.182', '159M', '4008', 'GameServer', '1518052601', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403179', '11', '0:8', '10.0.0.182', '213M', '3940', 'DBServer', '1518052777', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403180', '0', '0:5', '10.0.0.182', '160M', '4008', 'GameServer', '1518052782', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403181', '11', '0:8', '10.0.0.182', '213M', '3940', 'DBServer', '1518052958', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403182', '0', '0:5', '10.0.0.182', '161M', '4008', 'GameServer', '1518052963', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403183', '11', '0:8', '10.0.0.182', '217M', '3940', 'DBServer', '1518053139', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403184', '0', '0:5', '10.0.0.182', '162M', '4008', 'GameServer', '1518053144', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403185', '11', '0:8', '10.0.0.182', '241M', '3940', 'DBServer', '1518053320', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403186', '0', '0:5', '10.0.0.182', '163M', '4008', 'GameServer', '1518053325', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403187', '11', '0:8', '10.0.0.182', '240M', '3940', 'DBServer', '1518053501', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403188', '0', '0:6', '10.0.0.182', '164M', '4008', 'GameServer', '1518053506', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403189', '11', '0:8', '10.0.0.182', '240M', '3940', 'DBServer', '1518053682', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403190', '0', '0:6', '10.0.0.182', '164M', '4008', 'GameServer', '1518053687', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403191', '11', '0:8', '10.0.0.182', '240M', '3940', 'DBServer', '1518053863', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403192', '0', '0:6', '10.0.0.182', '165M', '4008', 'GameServer', '1518053868', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403193', '11', '0:8', '10.0.0.182', '240M', '3940', 'DBServer', '1518054044', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403194', '0', '0:6', '10.0.0.182', '165M', '4008', 'GameServer', '1518054049', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403195', '11', '0:8', '10.0.0.182', '240M', '3940', 'DBServer', '1518054225', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403196', '0', '0:6', '10.0.0.182', '165M', '4008', 'GameServer', '1518054230', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403197', '11', '0:9', '10.0.0.182', '240M', '3940', 'DBServer', '1518054406', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403198', '0', '0:6', '10.0.0.182', '165M', '4008', 'GameServer', '1518054411', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403199', '11', '0:9', '10.0.0.182', '241M', '3940', 'DBServer', '1518054587', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403200', '0', '0:6', '10.0.0.182', '165M', '4008', 'GameServer', '1518054592', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403201', '11', '0:10', '10.0.0.182', '242M', '3940', 'DBServer', '1518054768', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403202', '0', '0:6', '10.0.0.182', '163M', '4008', 'GameServer', '1518054773', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403203', '11', '0:10', '10.0.0.182', '244M', '3940', 'DBServer', '1518054949', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403204', '0', '0:6', '10.0.0.182', '164M', '4008', 'GameServer', '1518054954', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403205', '11', '0:10', '10.0.0.182', '245M', '3940', 'DBServer', '1518055130', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403206', '0', '0:6', '10.0.0.182', '164M', '4008', 'GameServer', '1518055135', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403207', '11', '0:10', '10.0.0.182', '242M', '3940', 'DBServer', '1518055311', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403208', '0', '0:6', '10.0.0.182', '165M', '4008', 'GameServer', '1518055316', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403209', '11', '0:10', '10.0.0.182', '241M', '3940', 'DBServer', '1518055492', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403210', '0', '0:7', '10.0.0.182', '165M', '4008', 'GameServer', '1518055497', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403211', '11', '0:10', '10.0.0.182', '241M', '3940', 'DBServer', '1518055673', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403212', '0', '0:7', '10.0.0.182', '165M', '4008', 'GameServer', '1518055678', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403213', '11', '0:10', '10.0.0.182', '242M', '3940', 'DBServer', '1518055854', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403214', '1', '0:7', '10.0.0.182', '165M', '4008', 'GameServer', '1518055859', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403215', '11', '0:10', '10.0.0.182', '242M', '3940', 'DBServer', '1518056035', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403216', '0', '0:7', '10.0.0.182', '165M', '4008', 'GameServer', '1518056040', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403217', '11', '0:11', '10.0.0.182', '313M', '3940', 'DBServer', '1518056216', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403218', '0', '0:7', '10.0.0.182', '165M', '4008', 'GameServer', '1518056221', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403219', '11', '0:11', '10.0.0.182', '316M', '3940', 'DBServer', '1518056397', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403220', '0', '0:7', '10.0.0.182', '166M', '4008', 'GameServer', '1518056402', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403221', '11', '0:11', '10.0.0.182', '316M', '3940', 'DBServer', '1518056578', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403222', '1', '0:7', '10.0.0.182', '167M', '4008', 'GameServer', '1518056583', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403223', '11', '0:11', '10.0.0.182', '316M', '3940', 'DBServer', '1518056759', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403224', '0', '0:7', '10.0.0.182', '167M', '4008', 'GameServer', '1518056764', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403225', '11', '0:11', '10.0.0.182', '316M', '3940', 'DBServer', '1518056940', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403226', '0', '0:7', '10.0.0.182', '167M', '4008', 'GameServer', '1518056945', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403227', '11', '0:11', '10.0.0.182', '316M', '3940', 'DBServer', '1518057121', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403228', '0', '0:7', '10.0.0.182', '167M', '4008', 'GameServer', '1518057126', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403229', '11', '0:11', '10.0.0.182', '316M', '3940', 'DBServer', '1518057302', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403230', '0', '0:7', '10.0.0.182', '167M', '4008', 'GameServer', '1518057307', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403231', '11', '0:11', '10.0.0.182', '316M', '3940', 'DBServer', '1518057483', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403232', '0', '0:7', '10.0.0.182', '167M', '4008', 'GameServer', '1518057488', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403233', '11', '0:11', '10.0.0.182', '317M', '3940', 'DBServer', '1518057664', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403234', '0', '0:7', '10.0.0.182', '168M', '4008', 'GameServer', '1518057669', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403235', '11', '0:11', '10.0.0.182', '317M', '3940', 'DBServer', '1518057845', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403236', '0', '0:7', '10.0.0.182', '168M', '4008', 'GameServer', '1518057850', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403237', '11', '0:12', '10.0.0.182', '318M', '3940', 'DBServer', '1518058026', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403238', '0', '0:7', '10.0.0.182', '168M', '4008', 'GameServer', '1518058031', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403239', '11', '0:12', '10.0.0.182', '318M', '3940', 'DBServer', '1518058207', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403240', '0', '0:7', '10.0.0.182', '168M', '4008', 'GameServer', '1518058212', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403241', '11', '0:13', '10.0.0.182', '318M', '3940', 'DBServer', '1518058388', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403242', '0', '0:7', '10.0.0.182', '168M', '4008', 'GameServer', '1518058393', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403243', '11', '0:13', '10.0.0.182', '319M', '3940', 'DBServer', '1518058569', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403244', '0', '0:7', '10.0.0.182', '166M', '4008', 'GameServer', '1518058574', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403245', '11', '0:13', '10.0.0.182', '318M', '3940', 'DBServer', '1518058750', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403246', '0', '0:7', '10.0.0.182', '167M', '4008', 'GameServer', '1518058755', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403247', '11', '0:13', '10.0.0.182', '319M', '3940', 'DBServer', '1518058931', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403248', '0', '0:7', '10.0.0.182', '167M', '4008', 'GameServer', '1518058936', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403249', '11', '0:13', '10.0.0.182', '319M', '3940', 'DBServer', '1518059112', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403250', '0', '0:7', '10.0.0.182', '167M', '4008', 'GameServer', '1518059117', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403251', '11', '0:13', '10.0.0.182', '319M', '3940', 'DBServer', '1518059293', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403252', '0', '0:7', '10.0.0.182', '167M', '4008', 'GameServer', '1518059298', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403253', '11', '0:13', '10.0.0.182', '319M', '3940', 'DBServer', '1518059474', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403254', '0', '0:8', '10.0.0.182', '166M', '4008', 'GameServer', '1518059479', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403255', '11', '0:13', '10.0.0.182', '320M', '3940', 'DBServer', '1518059655', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403256', '1', '0:8', '10.0.0.182', '166M', '4008', 'GameServer', '1518059660', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403257', '11', '0:13', '10.0.0.182', '376M', '3940', 'DBServer', '1518059836', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403258', '0', '0:8', '10.0.0.182', '166M', '4008', 'GameServer', '1518059841', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403259', '11', '0:13', '10.0.0.182', '376M', '3940', 'DBServer', '1518060017', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403260', '0', '0:8', '10.0.0.182', '166M', '4008', 'GameServer', '1518060022', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403261', '11', '0:14', '10.0.0.182', '376M', '3940', 'DBServer', '1518060198', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403262', '1', '0:8', '10.0.0.182', '166M', '4008', 'GameServer', '1518060203', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403263', '11', '0:14', '10.0.0.182', '380M', '3940', 'DBServer', '1518060379', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403264', '0', '0:8', '10.0.0.182', '165M', '4008', 'GameServer', '1518060384', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403265', '11', '0:14', '10.0.0.182', '379M', '3940', 'DBServer', '1518060560', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403266', '0', '0:8', '10.0.0.182', '165M', '4008', 'GameServer', '1518060565', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403267', '11', '0:14', '10.0.0.182', '380M', '3940', 'DBServer', '1518060741', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403268', '1', '0:8', '10.0.0.182', '165M', '4008', 'GameServer', '1518060746', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403269', '11', '0:14', '10.0.0.182', '379M', '3940', 'DBServer', '1518060922', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403270', '1', '0:8', '10.0.0.182', '165M', '4008', 'GameServer', '1518060927', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403271', '11', '0:14', '10.0.0.182', '380M', '3940', 'DBServer', '1518061103', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403272', '0', '0:8', '10.0.0.182', '165M', '4008', 'GameServer', '1518061108', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403273', '11', '0:14', '10.0.0.182', '379M', '3940', 'DBServer', '1518061284', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403274', '0', '0:8', '10.0.0.182', '164M', '4008', 'GameServer', '1518061289', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403275', '11', '0:14', '10.0.0.182', '380M', '3940', 'DBServer', '1518061465', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403276', '1', '0:8', '10.0.0.182', '166M', '4008', 'GameServer', '1518061470', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403277', '11', '0:15', '10.0.0.182', '378M', '3940', 'DBServer', '1518061646', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403278', '1', '0:8', '10.0.0.182', '166M', '4008', 'GameServer', '1518061651', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403279', '11', '0:15', '10.0.0.182', '378M', '3940', 'DBServer', '1518061827', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403280', '0', '0:8', '10.0.0.182', '166M', '4008', 'GameServer', '1518061832', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403281', '11', '0:15', '10.0.0.182', '378M', '3940', 'DBServer', '1518062008', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403282', '0', '0:8', '10.0.0.182', '166M', '4008', 'GameServer', '1518062013', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403283', '11', '0:15', '10.0.0.182', '378M', '3940', 'DBServer', '1518062189', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403284', '0', '0:8', '10.0.0.182', '166M', '4008', 'GameServer', '1518062194', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403285', '11', '0:15', '10.0.0.182', '378M', '3940', 'DBServer', '1518062370', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403286', '0', '0:8', '10.0.0.182', '166M', '4008', 'GameServer', '1518062375', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403287', '11', '0:15', '10.0.0.182', '378M', '3940', 'DBServer', '1518062551', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403288', '0', '0:8', '10.0.0.182', '166M', '4008', 'GameServer', '1518062556', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403289', '11', '0:15', '10.0.0.182', '378M', '3940', 'DBServer', '1518062732', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403290', '0', '0:8', '10.0.0.182', '166M', '4008', 'GameServer', '1518062737', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403291', '11', '0:15', '10.0.0.182', '378M', '3940', 'DBServer', '1518062913', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403292', '0', '0:8', '10.0.0.182', '166M', '4008', 'GameServer', '1518062918', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403293', '11', '0:15', '10.0.0.182', '378M', '3940', 'DBServer', '1518063094', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403294', '0', '0:8', '10.0.0.182', '165M', '4008', 'GameServer', '1518063099', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403295', '11', '0:15', '10.0.0.182', '378M', '3940', 'DBServer', '1518063275', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403296', '0', '0:8', '10.0.0.182', '165M', '4008', 'GameServer', '1518063280', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403297', '11', '0:15', '10.0.0.182', '379M', '3940', 'DBServer', '1518063456', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403298', '0', '0:8', '10.0.0.182', '165M', '4008', 'GameServer', '1518063461', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403299', '11', '0:15', '10.0.0.182', '379M', '3940', 'DBServer', '1518063637', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403300', '0', '0:8', '10.0.0.182', '165M', '4008', 'GameServer', '1518063642', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403301', '11', '0:15', '10.0.0.182', '380M', '3940', 'DBServer', '1518063818', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403302', '0', '0:9', '10.0.0.182', '165M', '4008', 'GameServer', '1518063823', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403303', '11', '0:16', '10.0.0.182', '380M', '3940', 'DBServer', '1518063999', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403304', '0', '0:9', '10.0.0.182', '165M', '4008', 'GameServer', '1518064004', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403305', '11', '0:16', '10.0.0.182', '380M', '3940', 'DBServer', '1518064180', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403306', '0', '0:9', '10.0.0.182', '165M', '4008', 'GameServer', '1518064185', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403307', '11', '0:16', '10.0.0.182', '380M', '3940', 'DBServer', '1518064361', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403308', '0', '0:9', '10.0.0.182', '165M', '4008', 'GameServer', '1518064366', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403309', '11', '0:16', '10.0.0.182', '380M', '3940', 'DBServer', '1518064542', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403310', '0', '0:9', '10.0.0.182', '165M', '4008', 'GameServer', '1518064547', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403311', '11', '0:16', '10.0.0.182', '379M', '3940', 'DBServer', '1518064723', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403312', '0', '0:9', '10.0.0.182', '165M', '4008', 'GameServer', '1518064728', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403313', '11', '0:16', '10.0.0.182', '380M', '3940', 'DBServer', '1518064904', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403314', '0', '0:9', '10.0.0.182', '164M', '4008', 'GameServer', '1518064909', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403315', '11', '0:16', '10.0.0.182', '380M', '3940', 'DBServer', '1518065085', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403316', '0', '0:9', '10.0.0.182', '164M', '4008', 'GameServer', '1518065090', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403317', '11', '0:16', '10.0.0.182', '398M', '3940', 'DBServer', '1518065266', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403318', '0', '0:9', '10.0.0.182', '164M', '4008', 'GameServer', '1518065271', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403319', '11', '0:16', '10.0.0.182', '398M', '3940', 'DBServer', '1518065447', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403320', '0', '0:9', '10.0.0.182', '164M', '4008', 'GameServer', '1518065452', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403321', '11', '0:16', '10.0.0.182', '399M', '3940', 'DBServer', '1518065628', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403322', '0', '0:9', '10.0.0.182', '164M', '4008', 'GameServer', '1518065633', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403323', '11', '0:16', '10.0.0.182', '399M', '3940', 'DBServer', '1518065809', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403324', '0', '0:9', '10.0.0.182', '163M', '4008', 'GameServer', '1518065814', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403325', '11', '0:16', '10.0.0.182', '400M', '3940', 'DBServer', '1518065990', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403326', '0', '0:9', '10.0.0.182', '163M', '4008', 'GameServer', '1518065995', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403327', '11', '0:16', '10.0.0.182', '400M', '3940', 'DBServer', '1518066171', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403328', '0', '0:9', '10.0.0.182', '163M', '4008', 'GameServer', '1518066176', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403329', '11', '0:16', '10.0.0.182', '400M', '3940', 'DBServer', '1518066352', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403330', '0', '0:9', '10.0.0.182', '163M', '4008', 'GameServer', '1518066357', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403331', '11', '0:16', '10.0.0.182', '400M', '3940', 'DBServer', '1518066533', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403332', '0', '0:9', '10.0.0.182', '170M', '4008', 'GameServer', '1518066538', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403333', '11', '0:16', '10.0.0.182', '400M', '3940', 'DBServer', '1518066714', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403334', '0', '0:9', '10.0.0.182', '170M', '4008', 'GameServer', '1518066719', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403335', '11', '0:17', '10.0.0.182', '412M', '3940', 'DBServer', '1518066895', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403336', '0', '0:9', '10.0.0.182', '170M', '4008', 'GameServer', '1518066900', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403337', '11', '0:17', '10.0.0.182', '511M', '3940', 'DBServer', '1518067076', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403338', '1', '0:9', '10.0.0.182', '171M', '4008', 'GameServer', '1518067081', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403339', '11', '0:17', '10.0.0.182', '511M', '3940', 'DBServer', '1518067257', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403340', '1', '0:10', '10.0.0.182', '171M', '4008', 'GameServer', '1518067262', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403341', '11', '0:17', '10.0.0.182', '506M', '3940', 'DBServer', '1518067438', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403342', '1', '0:10', '10.0.0.182', '172M', '4008', 'GameServer', '1518067443', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403343', '11', '0:17', '10.0.0.182', '506M', '3940', 'DBServer', '1518067619', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403344', '1', '0:10', '10.0.0.182', '172M', '4008', 'GameServer', '1518067624', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403345', '11', '0:17', '10.0.0.182', '506M', '3940', 'DBServer', '1518067800', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403346', '1', '0:10', '10.0.0.182', '172M', '4008', 'GameServer', '1518067805', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403347', '11', '0:17', '10.0.0.182', '506M', '3940', 'DBServer', '1518067981', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403348', '0', '0:10', '10.0.0.182', '172M', '4008', 'GameServer', '1518067986', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403349', '11', '0:17', '10.0.0.182', '506M', '3940', 'DBServer', '1518068162', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403350', '0', '0:10', '10.0.0.182', '172M', '4008', 'GameServer', '1518068167', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403351', '11', '0:17', '10.0.0.182', '506M', '3940', 'DBServer', '1518068343', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403352', '0', '0:10', '10.0.0.182', '172M', '4008', 'GameServer', '1518068348', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403353', '11', '0:17', '10.0.0.182', '506M', '3940', 'DBServer', '1518068524', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403354', '0', '0:10', '10.0.0.182', '172M', '4008', 'GameServer', '1518068529', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403355', '11', '0:18', '10.0.0.182', '507M', '3940', 'DBServer', '1518068705', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403356', '1', '0:10', '10.0.0.182', '172M', '4008', 'GameServer', '1518068710', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403357', '11', '0:18', '10.0.0.182', '507M', '3940', 'DBServer', '1518068886', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403358', '0', '0:10', '10.0.0.182', '172M', '4008', 'GameServer', '1518068891', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403359', '11', '0:18', '10.0.0.182', '507M', '3940', 'DBServer', '1518069067', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403360', '0', '0:10', '10.0.0.182', '173M', '4008', 'GameServer', '1518069072', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403361', '11', '0:18', '10.0.0.182', '507M', '3940', 'DBServer', '1518069248', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403362', '0', '0:10', '10.0.0.182', '173M', '4008', 'GameServer', '1518069253', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403363', '11', '0:18', '10.0.0.182', '507M', '3940', 'DBServer', '1518069429', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403364', '0', '0:10', '10.0.0.182', '173M', '4008', 'GameServer', '1518069434', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403365', '11', '0:18', '10.0.0.182', '507M', '3940', 'DBServer', '1518069610', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403366', '0', '0:10', '10.0.0.182', '173M', '4008', 'GameServer', '1518069615', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403367', '11', '0:18', '10.0.0.182', '507M', '3940', 'DBServer', '1518069791', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403368', '0', '0:10', '10.0.0.182', '174M', '4008', 'GameServer', '1518069796', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403369', '11', '0:18', '10.0.0.182', '507M', '3940', 'DBServer', '1518069972', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403370', '0', '0:10', '10.0.0.182', '174M', '4008', 'GameServer', '1518069977', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403371', '11', '0:18', '10.0.0.182', '507M', '3940', 'DBServer', '1518070153', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403372', '0', '0:10', '10.0.0.182', '174M', '4008', 'GameServer', '1518070158', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403373', '11', '0:18', '10.0.0.182', '507M', '3940', 'DBServer', '1518070334', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403374', '0', '0:10', '10.0.0.182', '174M', '4008', 'GameServer', '1518070339', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403375', '11', '0:18', '10.0.0.182', '509M', '3940', 'DBServer', '1518070515', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403376', '0', '0:10', '10.0.0.182', '174M', '4008', 'GameServer', '1518070520', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403377', '11', '0:18', '10.0.0.182', '510M', '3940', 'DBServer', '1518070696', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403378', '0', '0:11', '10.0.0.182', '174M', '4008', 'GameServer', '1518070701', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403379', '11', '0:18', '10.0.0.182', '510M', '3940', 'DBServer', '1518070877', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403380', '0', '0:11', '10.0.0.182', '174M', '4008', 'GameServer', '1518070882', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403381', '11', '0:18', '10.0.0.182', '510M', '3940', 'DBServer', '1518071058', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403382', '0', '0:11', '10.0.0.182', '174M', '4008', 'GameServer', '1518071063', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403383', '11', '0:18', '10.0.0.182', '510M', '3940', 'DBServer', '1518071239', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403384', '0', '0:11', '10.0.0.182', '174M', '4008', 'GameServer', '1518071244', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403385', '11', '0:18', '10.0.0.182', '510M', '3940', 'DBServer', '1518071420', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403386', '0', '0:11', '10.0.0.182', '174M', '4008', 'GameServer', '1518071425', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403387', '11', '0:18', '10.0.0.182', '510M', '3940', 'DBServer', '1518071601', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403388', '0', '0:11', '10.0.0.182', '174M', '4008', 'GameServer', '1518071606', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403389', '11', '0:18', '10.0.0.182', '505M', '3940', 'DBServer', '1518071782', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403390', '0', '0:11', '10.0.0.182', '174M', '4008', 'GameServer', '1518071787', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403391', '11', '0:18', '10.0.0.182', '506M', '3940', 'DBServer', '1518071963', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403392', '0', '0:11', '10.0.0.182', '174M', '4008', 'GameServer', '1518071968', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403393', '11', '0:18', '10.0.0.182', '506M', '3940', 'DBServer', '1518072144', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403394', '0', '0:11', '10.0.0.182', '174M', '4008', 'GameServer', '1518072149', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403395', '11', '0:19', '10.0.0.182', '507M', '3940', 'DBServer', '1518072325', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403396', '0', '0:11', '10.0.0.182', '174M', '4008', 'GameServer', '1518072330', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403397', '11', '0:19', '10.0.0.182', '507M', '3940', 'DBServer', '1518072506', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403398', '0', '0:11', '10.0.0.182', '174M', '4008', 'GameServer', '1518072511', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403399', '11', '0:19', '10.0.0.182', '507M', '3940', 'DBServer', '1518072687', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403400', '0', '0:11', '10.0.0.182', '174M', '4008', 'GameServer', '1518072692', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403401', '11', '0:19', '10.0.0.182', '507M', '3940', 'DBServer', '1518072868', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403402', '0', '0:11', '10.0.0.182', '174M', '4008', 'GameServer', '1518072873', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403403', '11', '0:19', '10.0.0.182', '508M', '3940', 'DBServer', '1518073049', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403404', '0', '0:11', '10.0.0.182', '174M', '4008', 'GameServer', '1518073054', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403405', '11', '0:19', '10.0.0.182', '508M', '3940', 'DBServer', '1518073230', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403406', '0', '0:11', '10.0.0.182', '174M', '4008', 'GameServer', '1518073235', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403407', '11', '0:19', '10.0.0.182', '508M', '3940', 'DBServer', '1518073411', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403408', '0', '0:11', '10.0.0.182', '174M', '4008', 'GameServer', '1518073416', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403409', '11', '0:19', '10.0.0.182', '508M', '3940', 'DBServer', '1518073592', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403410', '0', '0:11', '10.0.0.182', '174M', '4008', 'GameServer', '1518073597', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403411', '11', '0:19', '10.0.0.182', '508M', '3940', 'DBServer', '1518073773', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403412', '0', '0:11', '10.0.0.182', '174M', '4008', 'GameServer', '1518073778', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403413', '11', '0:19', '10.0.0.182', '509M', '3940', 'DBServer', '1518073954', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403414', '0', '0:12', '10.0.0.182', '174M', '4008', 'GameServer', '1518073959', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403415', '11', '0:19', '10.0.0.182', '594M', '3940', 'DBServer', '1518074135', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403416', '0', '0:12', '10.0.0.182', '174M', '4008', 'GameServer', '1518074140', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403417', '11', '0:19', '10.0.0.182', '594M', '3940', 'DBServer', '1518074316', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403418', '0', '0:12', '10.0.0.182', '174M', '4008', 'GameServer', '1518074321', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403419', '11', '0:19', '10.0.0.182', '594M', '3940', 'DBServer', '1518074497', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403420', '0', '0:12', '10.0.0.182', '174M', '4008', 'GameServer', '1518074502', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403421', '11', '0:19', '10.0.0.182', '594M', '3940', 'DBServer', '1518074678', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403422', '0', '0:12', '10.0.0.182', '174M', '4008', 'GameServer', '1518074683', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403423', '11', '0:19', '10.0.0.182', '594M', '3940', 'DBServer', '1518074859', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403424', '0', '0:12', '10.0.0.182', '174M', '4008', 'GameServer', '1518074864', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403425', '11', '0:19', '10.0.0.182', '594M', '3940', 'DBServer', '1518075040', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403426', '0', '0:12', '10.0.0.182', '174M', '4008', 'GameServer', '1518075045', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403427', '11', '0:19', '10.0.0.182', '594M', '3940', 'DBServer', '1518075221', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403428', '0', '0:12', '10.0.0.182', '172M', '4008', 'GameServer', '1518075226', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403429', '11', '0:19', '10.0.0.182', '594M', '3940', 'DBServer', '1518075402', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403430', '0', '0:12', '10.0.0.182', '172M', '4008', 'GameServer', '1518075407', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403431', '11', '0:19', '10.0.0.182', '594M', '3940', 'DBServer', '1518075583', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403432', '0', '0:12', '10.0.0.182', '172M', '4008', 'GameServer', '1518075588', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403433', '11', '0:19', '10.0.0.182', '594M', '3940', 'DBServer', '1518075764', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403434', '0', '0:12', '10.0.0.182', '172M', '4008', 'GameServer', '1518075769', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403435', '11', '0:20', '10.0.0.182', '594M', '3940', 'DBServer', '1518075945', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403436', '0', '0:12', '10.0.0.182', '170M', '4008', 'GameServer', '1518075950', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403437', '11', '0:20', '10.0.0.182', '594M', '3940', 'DBServer', '1518076126', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403438', '0', '0:12', '10.0.0.182', '170M', '4008', 'GameServer', '1518076131', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403439', '11', '0:20', '10.0.0.182', '594M', '3940', 'DBServer', '1518076307', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403440', '0', '0:12', '10.0.0.182', '170M', '4008', 'GameServer', '1518076312', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403441', '11', '0:20', '10.0.0.182', '594M', '3940', 'DBServer', '1518076488', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403442', '0', '0:12', '10.0.0.182', '170M', '4008', 'GameServer', '1518076493', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403443', '11', '0:20', '10.0.0.182', '594M', '3940', 'DBServer', '1518076669', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403444', '0', '0:12', '10.0.0.182', '168M', '4008', 'GameServer', '1518076674', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403445', '11', '0:20', '10.0.0.182', '594M', '3940', 'DBServer', '1518076850', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403446', '0', '0:12', '10.0.0.182', '168M', '4008', 'GameServer', '1518076855', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403447', '11', '0:20', '10.0.0.182', '594M', '3940', 'DBServer', '1518077031', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403448', '0', '0:12', '10.0.0.182', '168M', '4008', 'GameServer', '1518077036', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403449', '11', '0:20', '10.0.0.182', '594M', '3940', 'DBServer', '1518077212', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403450', '0', '0:12', '10.0.0.182', '168M', '4008', 'GameServer', '1518077217', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403451', '11', '0:20', '10.0.0.182', '594M', '3940', 'DBServer', '1518077393', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403452', '0', '0:12', '10.0.0.182', '167M', '4008', 'GameServer', '1518077398', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403453', '11', '0:20', '10.0.0.182', '594M', '3940', 'DBServer', '1518077574', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403454', '0', '0:12', '10.0.0.182', '167M', '4008', 'GameServer', '1518077579', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403455', '11', '0:20', '10.0.0.182', '595M', '3940', 'DBServer', '1518077755', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403456', '0', '0:12', '10.0.0.182', '167M', '4008', 'GameServer', '1518077760', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403457', '11', '0:20', '10.0.0.182', '595M', '3940', 'DBServer', '1518077936', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403458', '0', '0:12', '10.0.0.182', '167M', '4008', 'GameServer', '1518077941', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403459', '11', '0:20', '10.0.0.182', '595M', '3940', 'DBServer', '1518078117', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403460', '0', '0:12', '10.0.0.182', '165M', '4008', 'GameServer', '1518078122', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403461', '11', '0:20', '10.0.0.182', '595M', '3940', 'DBServer', '1518078298', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403462', '0', '0:13', '10.0.0.182', '165M', '4008', 'GameServer', '1518078303', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403463', '11', '0:20', '10.0.0.182', '595M', '3940', 'DBServer', '1518078479', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403464', '0', '0:13', '10.0.0.182', '165M', '4008', 'GameServer', '1518078484', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403465', '11', '0:20', '10.0.0.182', '595M', '3940', 'DBServer', '1518078660', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403466', '0', '0:13', '10.0.0.182', '165M', '4008', 'GameServer', '1518078665', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403467', '11', '0:20', '10.0.0.182', '595M', '3940', 'DBServer', '1518078841', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403468', '0', '0:13', '10.0.0.182', '164M', '4008', 'GameServer', '1518078846', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403469', '11', '0:20', '10.0.0.182', '595M', '3940', 'DBServer', '1518079022', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403470', '0', '0:13', '10.0.0.182', '164M', '4008', 'GameServer', '1518079027', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403471', '11', '0:20', '10.0.0.182', '595M', '3940', 'DBServer', '1518079203', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403472', '0', '0:13', '10.0.0.182', '164M', '4008', 'GameServer', '1518079208', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403473', '11', '0:20', '10.0.0.182', '595M', '3940', 'DBServer', '1518079384', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403474', '0', '0:13', '10.0.0.182', '164M', '4008', 'GameServer', '1518079389', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403475', '11', '0:20', '10.0.0.182', '595M', '3940', 'DBServer', '1518079565', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403476', '0', '0:13', '10.0.0.182', '161M', '4008', 'GameServer', '1518079570', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403477', '11', '0:20', '10.0.0.182', '595M', '3940', 'DBServer', '1518079746', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403478', '0', '0:13', '10.0.0.182', '161M', '4008', 'GameServer', '1518079751', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403479', '11', '0:20', '10.0.0.182', '595M', '3940', 'DBServer', '1518079927', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403480', '0', '0:13', '10.0.0.182', '161M', '4008', 'GameServer', '1518079932', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403481', '11', '0:20', '10.0.0.182', '595M', '3940', 'DBServer', '1518080108', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403482', '0', '0:13', '10.0.0.182', '160M', '4008', 'GameServer', '1518080113', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403483', '11', '0:20', '10.0.0.182', '595M', '3940', 'DBServer', '1518080289', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403484', '0', '0:13', '10.0.0.182', '160M', '4008', 'GameServer', '1518080294', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403485', '11', '0:21', '10.0.0.182', '595M', '3940', 'DBServer', '1518080470', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403486', '0', '0:13', '10.0.0.182', '160M', '4008', 'GameServer', '1518080475', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403487', '11', '0:21', '10.0.0.182', '595M', '3940', 'DBServer', '1518080651', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403488', '0', '0:13', '10.0.0.182', '160M', '4008', 'GameServer', '1518080656', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403489', '11', '0:21', '10.0.0.182', '595M', '3940', 'DBServer', '1518080832', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403490', '0', '0:13', '10.0.0.182', '158M', '4008', 'GameServer', '1518080837', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403491', '11', '0:21', '10.0.0.182', '595M', '3940', 'DBServer', '1518081013', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403492', '0', '0:13', '10.0.0.182', '158M', '4008', 'GameServer', '1518081018', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403493', '11', '0:21', '10.0.0.182', '596M', '3940', 'DBServer', '1518081194', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403494', '0', '0:13', '10.0.0.182', '159M', '4008', 'GameServer', '1518081199', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403495', '11', '0:21', '10.0.0.182', '596M', '3940', 'DBServer', '1518081375', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403496', '0', '0:13', '10.0.0.182', '159M', '4008', 'GameServer', '1518081380', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403497', '11', '0:21', '10.0.0.182', '596M', '3940', 'DBServer', '1518081556', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403498', '0', '0:14', '10.0.0.182', '158M', '4008', 'GameServer', '1518081561', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403499', '11', '0:21', '10.0.0.182', '596M', '3940', 'DBServer', '1518081737', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403500', '0', '0:14', '10.0.0.182', '155M', '4008', 'GameServer', '1518081742', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403501', '11', '0:21', '10.0.0.182', '596M', '3940', 'DBServer', '1518081918', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403502', '0', '0:14', '10.0.0.182', '155M', '4008', 'GameServer', '1518081923', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403503', '11', '0:21', '10.0.0.182', '596M', '3940', 'DBServer', '1518082099', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403504', '0', '0:14', '10.0.0.182', '155M', '4008', 'GameServer', '1518082104', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403505', '11', '0:21', '10.0.0.182', '594M', '3940', 'DBServer', '1518082280', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403506', '0', '0:14', '10.0.0.182', '154M', '4008', 'GameServer', '1518082285', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403507', '11', '0:21', '10.0.0.182', '594M', '3940', 'DBServer', '1518082461', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403508', '0', '0:14', '10.0.0.182', '154M', '4008', 'GameServer', '1518082466', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403509', '11', '0:21', '10.0.0.182', '594M', '3940', 'DBServer', '1518082642', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403510', '0', '0:14', '10.0.0.182', '154M', '4008', 'GameServer', '1518082647', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403511', '11', '0:21', '10.0.0.182', '594M', '3940', 'DBServer', '1518082823', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403512', '0', '0:14', '10.0.0.182', '152M', '4008', 'GameServer', '1518082828', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403513', '11', '0:22', '10.0.0.182', '595M', '3940', 'DBServer', '1518083004', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403514', '0', '0:14', '10.0.0.182', '152M', '4008', 'GameServer', '1518083009', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403515', '11', '0:22', '10.0.0.182', '705M', '3940', 'DBServer', '1518083185', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403516', '0', '0:14', '10.0.0.182', '152M', '4008', 'GameServer', '1518083190', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403517', '11', '0:22', '10.0.0.182', '706M', '3940', 'DBServer', '1518083366', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403518', '0', '0:14', '10.0.0.182', '152M', '4008', 'GameServer', '1518083371', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403519', '11', '0:22', '10.0.0.182', '706M', '3940', 'DBServer', '1518083547', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403520', '0', '0:14', '10.0.0.182', '151M', '4008', 'GameServer', '1518083552', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403521', '11', '0:22', '10.0.0.182', '706M', '3940', 'DBServer', '1518083728', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403522', '0', '0:14', '10.0.0.182', '151M', '4008', 'GameServer', '1518083733', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403523', '11', '0:22', '10.0.0.182', '706M', '3940', 'DBServer', '1518083909', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403524', '1', '0:15', '10.0.0.182', '151M', '4008', 'GameServer', '1518083914', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403525', '11', '0:22', '10.0.0.182', '706M', '3940', 'DBServer', '1518084090', '0', '110', '09:13', '\"1.0 alpha\"', '110');
INSERT INTO `agentserviceinfo` VALUES ('472446403526', '0', '0:15', '10.0.0.182', '150M', '4008', 'GameServer', '1518084095', '0', '110', '09:13', '\"1.0 alpha\"', '110');

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
  PRIMARY KEY  (`id`),
  KEY `ip` (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of agentsysteminfo
-- ----------------------------

-- ----------------------------
-- Table structure for `gmcase`
-- ----------------------------
DROP TABLE IF EXISTS `gmcase`;
CREATE TABLE `gmcase` (
  `id` bigint(20) NOT NULL,
  `account` varchar(255) default NULL,
  `closetime` int(11) NOT NULL,
  `createtime` int(11) NOT NULL,
  `detail` longtext,
  `processgm` varchar(255) default NULL,
  `qq` bigint(20) NOT NULL,
  `roleid` bigint(20) NOT NULL,
  `rolename` varchar(255) default NULL,
  `tel` varchar(255) default NULL,
  `title` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gmcase
-- ----------------------------

-- ----------------------------
-- Table structure for `gmprocess`
-- ----------------------------
DROP TABLE IF EXISTS `gmprocess`;
CREATE TABLE `gmprocess` (
  `id` bigint(20) NOT NULL,
  `acttype` int(11) NOT NULL,
  `caseid` bigint(20) NOT NULL,
  `gmname` varchar(255) default NULL,
  `processtime` int(11) NOT NULL,
  `remarks` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `caseid` (`caseid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gmprocess
-- ----------------------------
