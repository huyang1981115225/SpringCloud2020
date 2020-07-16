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

 Date: 09/07/2020 15:05:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for batch_step_execution
-- ----------------------------
DROP TABLE IF EXISTS `batch_step_execution`;
CREATE TABLE `batch_step_execution`  (
  `STEP_EXECUTION_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) NOT NULL,
  `STEP_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `START_TIME` datetime(0) NULL,
  `END_TIME` datetime(0) NULL DEFAULT NULL,
  `STATUS` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `COMMIT_COUNT` bigint(20) NULL DEFAULT NULL,
  `READ_COUNT` bigint(20) NULL DEFAULT NULL,
  `FILTER_COUNT` bigint(20) NULL DEFAULT NULL,
  `WRITE_COUNT` bigint(20) NULL DEFAULT NULL,
  `READ_SKIP_COUNT` bigint(20) NULL DEFAULT NULL,
  `WRITE_SKIP_COUNT` bigint(20) NULL DEFAULT NULL,
  `PROCESS_SKIP_COUNT` bigint(20) NULL DEFAULT NULL,
  `ROLLBACK_COUNT` bigint(20) NULL DEFAULT NULL,
  `EXIT_CODE` varchar(2500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `EXIT_MESSAGE` varchar(2500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `LAST_UPDATED` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`STEP_EXECUTION_ID`) USING BTREE,
  INDEX `JOB_EXEC_STEP_FK`(`JOB_EXECUTION_ID`) USING BTREE,
  CONSTRAINT `JOB_EXEC_STEP_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `batch_job_execution` (`JOB_EXECUTION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
