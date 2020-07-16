/*
 Navicat Premium Data Transfer

 Source Server         : mysql5.7主数据库（本地）
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : test1289

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 09/07/2020 15:05:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for batch_job_execution_params
-- ----------------------------
DROP TABLE IF EXISTS `batch_job_execution_params`;
CREATE TABLE `batch_job_execution_params`  (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `TYPE_CD` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `KEY_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `STRING_VAL` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DATE_VAL` datetime(0) NULL DEFAULT NULL,
  `LONG_VAL` bigint(20) NULL DEFAULT NULL,
  `DOUBLE_VAL` double NULL DEFAULT NULL,
  `IDENTIFYING` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  INDEX `JOB_EXEC_PARAMS_FK`(`JOB_EXECUTION_ID`) USING BTREE,
  CONSTRAINT `JOB_EXEC_PARAMS_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `batch_job_execution` (`JOB_EXECUTION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
