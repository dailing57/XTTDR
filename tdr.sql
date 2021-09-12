/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80023
Source Host           : localhost:3306
Source Database       : tdr

Target Server Type    : MYSQL
Target Server Version : 80023
File Encoding         : 65001

Date: 2021-09-12 16:52:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `ID` varchar(32) NOT NULL COMMENT '账号',
  `PWD` varchar(60) DEFAULT NULL COMMENT '密码',
  `USER_TYPE` varchar(60) DEFAULT NULL COMMENT '用户类型',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户';

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('admin', 'random', 'admin');
INSERT INTO `account` VALUES ('student', 'random', 'student');
INSERT INTO `account` VALUES ('student1', 'random', 'student');
INSERT INTO `account` VALUES ('teacher', 'random', 'teacher');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `COMMENT_ID` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论编号',
  `CONTENT` varchar(900) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '评论内容',
  `ID` varchar(32) DEFAULT NULL COMMENT '账号',
  `CREATED_TIME` datetime DEFAULT NULL COMMENT '发表时间',
  `COURSE_ID` varchar(60) DEFAULT NULL COMMENT '课程编号',
  `PARENT_ID` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`COMMENT_ID`),
  KEY `ID` (`ID`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `account` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论';

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('118d1210ec00432e932faa853498c4af', 'hhh', 'teacher', '2021-09-12 13:56:24', '1', '23ed18b9250a42cdb50e6289ee5ece73');
INSERT INTO `comment` VALUES ('23ed18b9250a42cdb50e6289ee5ece73', '9999', 'teacher', '2021-09-12 13:55:37', '1', '756f1ad092da4c83b7f4f7118089fce8');
INSERT INTO `comment` VALUES ('756f1ad092da4c83b7f4f7118089fce8', '222', 'teacher', '2021-09-12 13:14:44', '1', 'teacher');
INSERT INTO `comment` VALUES ('a11e5f497e074d1da3f66d49acd74085', '111', 'teacher', '2021-09-11 22:59:13', '1', null);
INSERT INTO `comment` VALUES ('d051a7a0e20f4d4093c50b626f69b21b', '111', 'teacher', '2021-09-11 22:58:17', '1', null);
INSERT INTO `comment` VALUES ('da16e32b5be844079d44875824a26f6a', '111', 'teacher', '2021-09-11 22:58:22', '1', null);

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `COURSE_ID` varchar(32) NOT NULL COMMENT '课程编号',
  `NAME` varchar(32) DEFAULT NULL COMMENT '课程名称',
  `TEACHER_ID` varchar(32) DEFAULT NULL COMMENT '教师账号',
  `CREATED_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`COURSE_ID`),
  KEY `TEACHER_ID` (`TEACHER_ID`),
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`TEACHER_ID`) REFERENCES `account` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程';

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1', '课程1', 'teacher', '2021-09-04 14:55:35');
INSERT INTO `course` VALUES ('2', '课程2', 'teacher', '2021-09-04 15:33:59');
INSERT INTO `course` VALUES ('3', 'test', 'teacher', '2021-09-05 15:47:03');
INSERT INTO `course` VALUES ('4', '4', 'teacher', '2021-09-07 08:22:49');
INSERT INTO `course` VALUES ('5', '5', 'teacher', '2021-09-07 08:22:52');
INSERT INTO `course` VALUES ('6', '6', 'teacher', '2021-09-07 08:22:54');
INSERT INTO `course` VALUES ('7', '7', 'teacher', '2021-09-07 08:22:56');

-- ----------------------------
-- Table structure for course_material
-- ----------------------------
DROP TABLE IF EXISTS `course_material`;
CREATE TABLE `course_material` (
  `NAME` varchar(32) DEFAULT NULL,
  `MATERIAL_ID` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资料编号',
  `COURSE_ID` varchar(32) DEFAULT NULL COMMENT '课程编号',
  `MATERIAL_PATH` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '资料路径',
  `TEACHER_ID` varchar(32) DEFAULT NULL COMMENT '教师编号',
  `CREATED_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`MATERIAL_ID`),
  KEY `TEACHER_ID` (`TEACHER_ID`),
  KEY `COURSE_ID` (`COURSE_ID`),
  CONSTRAINT `course_material_ibfk_1` FOREIGN KEY (`TEACHER_ID`) REFERENCES `account` (`ID`),
  CONSTRAINT `course_material_ibfk_2` FOREIGN KEY (`COURSE_ID`) REFERENCES `course` (`COURSE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程资料';

-- ----------------------------
-- Records of course_material
-- ----------------------------
INSERT INTO `course_material` VALUES ('oakCenter1.png', '1oakCenter1.png', '1', 'H:\\课设一\\TDR\\xttdr\\backend\\tdr/files/1oakCenter1.png', 'teacher', '2021-09-11 10:55:28');

-- ----------------------------
-- Table structure for docourse
-- ----------------------------
DROP TABLE IF EXISTS `docourse`;
CREATE TABLE `docourse` (
  `COURSE_ID` varchar(32) DEFAULT NULL COMMENT '课程编号',
  `STUDENT_ID` varchar(32) DEFAULT NULL COMMENT '学生编号',
  KEY `COURSE_ID` (`COURSE_ID`),
  KEY `STUDENT_ID` (`STUDENT_ID`),
  CONSTRAINT `docourse_ibfk_1` FOREIGN KEY (`COURSE_ID`) REFERENCES `course` (`COURSE_ID`),
  CONSTRAINT `docourse_ibfk_2` FOREIGN KEY (`STUDENT_ID`) REFERENCES `account` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='上课';

-- ----------------------------
-- Records of docourse
-- ----------------------------
INSERT INTO `docourse` VALUES ('1', 'student');
INSERT INTO `docourse` VALUES ('2', 'teacher');
INSERT INTO `docourse` VALUES ('3', 'teacher');
INSERT INTO `docourse` VALUES ('4', 'teacher');
INSERT INTO `docourse` VALUES ('5', 'teacher');
INSERT INTO `docourse` VALUES ('6', 'teacher');
INSERT INTO `docourse` VALUES ('7', 'teacher');
INSERT INTO `docourse` VALUES ('1', 'teacher');

-- ----------------------------
-- Table structure for doexam
-- ----------------------------
DROP TABLE IF EXISTS `doexam`;
CREATE TABLE `doexam` (
  `EXAM_ID` varchar(32) DEFAULT NULL COMMENT '考试编号',
  `ID` varchar(32) DEFAULT NULL COMMENT '账号',
  `SCORE` decimal(24,2) DEFAULT NULL COMMENT '得分',
  KEY `ID` (`ID`),
  CONSTRAINT `doexam_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `account` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='进行考试';

-- ----------------------------
-- Records of doexam
-- ----------------------------

-- ----------------------------
-- Table structure for dohomework
-- ----------------------------
DROP TABLE IF EXISTS `dohomework`;
CREATE TABLE `dohomework` (
  `HOMEWORK_ID` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '作业编号',
  `ID` varchar(32) DEFAULT NULL COMMENT '账号',
  `PATH` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '作业路径',
  `SCORE` decimal(24,6) DEFAULT NULL COMMENT '得分',
  KEY `ID` (`ID`),
  CONSTRAINT `dohomework_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `account` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='完成作业';

-- ----------------------------
-- Records of dohomework
-- ----------------------------
INSERT INTO `dohomework` VALUES ('4942af3382c7451891f693ea11d91924', 'teacher', '6e5771ee3a5e4878ad3bcd424f24c89doakCenter1.png', '4.000000');

-- ----------------------------
-- Table structure for exam
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam` (
  `EXAM_ID` varchar(32) NOT NULL COMMENT '考试编号',
  `NAME` varchar(32) DEFAULT NULL COMMENT '考试名称',
  `COURSE_ID` varchar(32) DEFAULT NULL COMMENT '课程编号',
  `CREATED_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `TEACHER_ID` varchar(32) DEFAULT NULL COMMENT '教师账号',
  `BEGINTIME` datetime DEFAULT NULL COMMENT '开始时间',
  `LASTTIME` varchar(60) DEFAULT NULL COMMENT '考试时长',
  PRIMARY KEY (`EXAM_ID`),
  KEY `COURSE_ID` (`COURSE_ID`),
  KEY `TEACHER_ID` (`TEACHER_ID`),
  CONSTRAINT `exam_ibfk_1` FOREIGN KEY (`COURSE_ID`) REFERENCES `course` (`COURSE_ID`),
  CONSTRAINT `exam_ibfk_2` FOREIGN KEY (`TEACHER_ID`) REFERENCES `account` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考试';

-- ----------------------------
-- Records of exam
-- ----------------------------

-- ----------------------------
-- Table structure for homework
-- ----------------------------
DROP TABLE IF EXISTS `homework`;
CREATE TABLE `homework` (
  `HOMEWORK_ID` varchar(32) NOT NULL COMMENT '作业编号',
  `NAME` varchar(32) DEFAULT NULL COMMENT '作业名称',
  `TEACHER_ID` varchar(32) DEFAULT NULL COMMENT '教师编号',
  `CREATED_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DEADLINE` datetime DEFAULT NULL COMMENT '结束时间',
  `CONTENT` varchar(900) DEFAULT NULL COMMENT '作业内容',
  `COURSE_ID` varchar(32) DEFAULT NULL COMMENT '课程编号',
  PRIMARY KEY (`HOMEWORK_ID`),
  KEY `COURSE_ID` (`COURSE_ID`),
  KEY `TEACHER_ID` (`TEACHER_ID`),
  CONSTRAINT `homework_ibfk_1` FOREIGN KEY (`COURSE_ID`) REFERENCES `course` (`COURSE_ID`),
  CONSTRAINT `homework_ibfk_2` FOREIGN KEY (`TEACHER_ID`) REFERENCES `account` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='作业';

-- ----------------------------
-- Records of homework
-- ----------------------------
INSERT INTO `homework` VALUES ('1', '作业测试1', 'teacher', '2021-09-09 20:04:29', '2021-09-10 20:04:32', '耶耶耶', '1');
INSERT INTO `homework` VALUES ('3', '作业测试3', 'teacher', '2021-09-09 20:04:29', '2021-09-10 20:04:32', '耶耶耶', '2');
INSERT INTO `homework` VALUES ('4942af3382c7451891f693ea11d91924', '测试作业2', 'teacher', '2021-09-11 08:00:00', '2021-09-12 08:00:00', '<p>嘿嘿嘿</p>', '1');

-- ----------------------------
-- Table structure for paper
-- ----------------------------
DROP TABLE IF EXISTS `paper`;
CREATE TABLE `paper` (
  `EXAM_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '考试编号',
  `PROBLEM_ID` varchar(32) DEFAULT NULL COMMENT '题目编号',
  KEY `EXAM_ID` (`EXAM_ID`),
  KEY `PROBLEM_ID` (`PROBLEM_ID`),
  CONSTRAINT `paper_ibfk_1` FOREIGN KEY (`EXAM_ID`) REFERENCES `exam` (`EXAM_ID`),
  CONSTRAINT `paper_ibfk_2` FOREIGN KEY (`PROBLEM_ID`) REFERENCES `problem` (`PROBLEM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='试卷';

-- ----------------------------
-- Records of paper
-- ----------------------------

-- ----------------------------
-- Table structure for problem
-- ----------------------------
DROP TABLE IF EXISTS `problem`;
CREATE TABLE `problem` (
  `PROBLEM_ID` varchar(32) NOT NULL COMMENT '题目编号',
  `DESCRIBES` varchar(300) DEFAULT NULL COMMENT '题目描述',
  `TEACHER_ID` varchar(32) DEFAULT NULL COMMENT '教师账号',
  `CREATED_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `OPTION_A` varchar(60) DEFAULT NULL COMMENT '选项A',
  `OPTION_B` varchar(60) DEFAULT NULL COMMENT '选项B',
  `OPTION_C` varchar(60) DEFAULT NULL COMMENT '选项C',
  `OPTION_D` varchar(60) DEFAULT NULL COMMENT '选项D',
  `ANWSER` varchar(60) DEFAULT NULL COMMENT '答案',
  `PARSE` varchar(60) DEFAULT NULL COMMENT '解析',
  PRIMARY KEY (`PROBLEM_ID`),
  KEY `TEACHER_ID` (`TEACHER_ID`),
  CONSTRAINT `problem_ibfk_1` FOREIGN KEY (`TEACHER_ID`) REFERENCES `account` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='题目';

-- ----------------------------
-- Records of problem
-- ----------------------------

-- ----------------------------
-- Table structure for school
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school` (
  `SCHOOL_ID` varchar(32) NOT NULL COMMENT '学院编号',
  `NAME` varchar(60) DEFAULT NULL COMMENT '学院名称',
  PRIMARY KEY (`SCHOOL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学院';

-- ----------------------------
-- Records of school
-- ----------------------------
INSERT INTO `school` VALUES ('1', '戳戳学院');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `ID` varchar(32) DEFAULT NULL COMMENT '账号',
  `WORK_ID` varchar(32) NOT NULL COMMENT '学工号',
  `NAME` varchar(60) DEFAULT NULL COMMENT '姓名',
  `SCHOOL_ID` varchar(32) DEFAULT NULL COMMENT '学院编号',
  `AVATAR` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`WORK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '1', 'dl', '1', null);
INSERT INTO `user` VALUES ('student', 'S0', 'sss', '1', null);
INSERT INTO `user` VALUES ('teacher', 'T0', 'ttt', '1', 'http://localhost:9090/files/avatar/75c87d67598a479ebe4575b46f8677f3oakCenter1.png');
