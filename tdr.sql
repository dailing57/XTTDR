/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80023
Source Host           : localhost:3306
Source Database       : tdr

Target Server Type    : MYSQL
Target Server Version : 80023
File Encoding         : 65001

Date: 2021-09-29 22:25:39
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
INSERT INTO `account` VALUES ('teacher', 'random', 'teacher');
INSERT INTO `account` VALUES ('XUESHENG', '123456', 'student');

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
  `PARENT_ID` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`COMMENT_ID`),
  KEY `parent_id` (`PARENT_ID`),
  KEY `id` (`ID`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `account` (`ID`),
  CONSTRAINT `id` FOREIGN KEY (`ID`) REFERENCES `account` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `parent_id` FOREIGN KEY (`PARENT_ID`) REFERENCES `comment` (`COMMENT_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论';

-- ----------------------------
-- Records of comment
-- ----------------------------

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
  KEY `teacher_id` (`TEACHER_ID`),
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`TEACHER_ID`) REFERENCES `account` (`ID`),
  CONSTRAINT `teacher_id` FOREIGN KEY (`TEACHER_ID`) REFERENCES `account` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程';

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('095a0d0d5d8b4eb18d6f2cfb1fe2c1e2', 'new', 'teacher', '2021-09-29 21:13:05');
INSERT INTO `course` VALUES ('1', '课程1', 'teacher', '2021-09-04 14:55:35');

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
  KEY `teacher_id_del` (`TEACHER_ID`),
  KEY `course_id_del` (`COURSE_ID`),
  CONSTRAINT `course_id_del` FOREIGN KEY (`COURSE_ID`) REFERENCES `course` (`COURSE_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_material_ibfk_1` FOREIGN KEY (`TEACHER_ID`) REFERENCES `account` (`ID`),
  CONSTRAINT `course_material_ibfk_2` FOREIGN KEY (`COURSE_ID`) REFERENCES `course` (`COURSE_ID`),
  CONSTRAINT `teacher_id_del` FOREIGN KEY (`TEACHER_ID`) REFERENCES `account` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程资料';

-- ----------------------------
-- Records of course_material
-- ----------------------------
INSERT INTO `course_material` VALUES ('学生表.xlsx', '095a0d0d5d8b4eb18d6f2cfb1fe2c1e2学生表.xlsx', '095a0d0d5d8b4eb18d6f2cfb1fe2c1e2', 'I:\\QQ临时文件\\2376593337\\FileRecv\\XTTDR\\backend\\tdr/files/095a0d0d5d8b4eb18d6f2cfb1fe2c1e2学生表.xlsx', 'teacher', '2021-09-29 21:16:06');
INSERT INTO `course_material` VALUES ('计算机网络测验.xlsx', '095a0d0d5d8b4eb18d6f2cfb1fe2c1e2计算机网络测验.xlsx', '095a0d0d5d8b4eb18d6f2cfb1fe2c1e2', 'I:\\QQ临时文件\\2376593337\\FileRecv\\XTTDR\\backend\\tdr/files/095a0d0d5d8b4eb18d6f2cfb1fe2c1e2计算机网络测验.xlsx', 'teacher', '2021-09-29 21:16:06');

-- ----------------------------
-- Table structure for docourse
-- ----------------------------
DROP TABLE IF EXISTS `docourse`;
CREATE TABLE `docourse` (
  `COURSE_ID` varchar(32) DEFAULT NULL COMMENT '课程编号',
  `STUDENT_ID` varchar(32) DEFAULT NULL COMMENT '学生编号',
  KEY `do_course_id` (`COURSE_ID`),
  KEY `do_student_id` (`STUDENT_ID`),
  CONSTRAINT `do_course_id` FOREIGN KEY (`COURSE_ID`) REFERENCES `course` (`COURSE_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `do_student_id` FOREIGN KEY (`STUDENT_ID`) REFERENCES `account` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `docourse_ibfk_1` FOREIGN KEY (`COURSE_ID`) REFERENCES `course` (`COURSE_ID`),
  CONSTRAINT `docourse_ibfk_2` FOREIGN KEY (`STUDENT_ID`) REFERENCES `account` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='上课';

-- ----------------------------
-- Records of docourse
-- ----------------------------
INSERT INTO `docourse` VALUES ('095a0d0d5d8b4eb18d6f2cfb1fe2c1e2', 'teacher');
INSERT INTO `docourse` VALUES ('095a0d0d5d8b4eb18d6f2cfb1fe2c1e2', 'XUESHENG');

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
INSERT INTO `doexam` VALUES ('f03ec6be96284411aff08788b9e747cf', 'XUESHENG', '100.00');

-- ----------------------------
-- Table structure for dohomework
-- ----------------------------
DROP TABLE IF EXISTS `dohomework`;
CREATE TABLE `dohomework` (
  `HOMEWORK_ID` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '作业编号',
  `ID` varchar(32) DEFAULT NULL COMMENT '账号',
  `PATH` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '作业路径',
  `SCORE` decimal(24,6) DEFAULT NULL COMMENT '得分',
  KEY `do_homework_studentid` (`ID`),
  KEY `do_homework_id` (`HOMEWORK_ID`),
  CONSTRAINT `do_homework_id` FOREIGN KEY (`HOMEWORK_ID`) REFERENCES `homework` (`HOMEWORK_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `do_homework_studentid` FOREIGN KEY (`ID`) REFERENCES `account` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `dohomework_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `account` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='完成作业';

-- ----------------------------
-- Records of dohomework
-- ----------------------------
INSERT INTO `dohomework` VALUES ('a9e4e25c80a84bbca84f79588c6eeb94', 'XUESHENG', '0b77035575334bc2814d61cf8a94aab3XUESHENG课设.docx', '5.000000');

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
  `LASTTIME` double DEFAULT NULL COMMENT '考试时长',
  PRIMARY KEY (`EXAM_ID`),
  KEY `exam_course_id` (`COURSE_ID`),
  KEY `exam_teacher_id` (`TEACHER_ID`),
  CONSTRAINT `exam_course_id` FOREIGN KEY (`COURSE_ID`) REFERENCES `course` (`COURSE_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `exam_ibfk_1` FOREIGN KEY (`COURSE_ID`) REFERENCES `course` (`COURSE_ID`),
  CONSTRAINT `exam_ibfk_2` FOREIGN KEY (`TEACHER_ID`) REFERENCES `account` (`ID`),
  CONSTRAINT `exam_teacher_id` FOREIGN KEY (`TEACHER_ID`) REFERENCES `account` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考试';

-- ----------------------------
-- Records of exam
-- ----------------------------
INSERT INTO `exam` VALUES ('f03ec6be96284411aff08788b9e747cf', 'test1', '095a0d0d5d8b4eb18d6f2cfb1fe2c1e2', '2021-09-29 21:52:40', 'teacher', '2021-09-29 21:53:35', '1');

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
  KEY `course_id` (`COURSE_ID`),
  KEY `homework_teacherid` (`TEACHER_ID`),
  CONSTRAINT `course_id` FOREIGN KEY (`COURSE_ID`) REFERENCES `course` (`COURSE_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `homework_ibfk_1` FOREIGN KEY (`COURSE_ID`) REFERENCES `course` (`COURSE_ID`),
  CONSTRAINT `homework_ibfk_2` FOREIGN KEY (`TEACHER_ID`) REFERENCES `account` (`ID`),
  CONSTRAINT `homework_teacherid` FOREIGN KEY (`TEACHER_ID`) REFERENCES `account` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='作业';

-- ----------------------------
-- Records of homework
-- ----------------------------
INSERT INTO `homework` VALUES ('a9e4e25c80a84bbca84f79588c6eeb94', '作业1', 'teacher', '2021-09-29 08:00:00', '2021-10-01 08:00:00', '<p>请提交课设报告！！</p>', '095a0d0d5d8b4eb18d6f2cfb1fe2c1e2');

-- ----------------------------
-- Table structure for paper
-- ----------------------------
DROP TABLE IF EXISTS `paper`;
CREATE TABLE `paper` (
  `EXAM_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '考试编号',
  `PROBLEM_ID` varchar(32) DEFAULT NULL COMMENT '题目编号',
  KEY `PROBLEM_ID` (`PROBLEM_ID`),
  KEY `exam_id` (`EXAM_ID`),
  CONSTRAINT `exam_id` FOREIGN KEY (`EXAM_ID`) REFERENCES `exam` (`EXAM_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `paper_ibfk_1` FOREIGN KEY (`EXAM_ID`) REFERENCES `exam` (`EXAM_ID`),
  CONSTRAINT `paper_ibfk_2` FOREIGN KEY (`PROBLEM_ID`) REFERENCES `problem` (`PROBLEM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='试卷';

-- ----------------------------
-- Records of paper
-- ----------------------------
INSERT INTO `paper` VALUES ('f03ec6be96284411aff08788b9e747cf', '9a4919826d4d4358b5db96fe81511973');
INSERT INTO `paper` VALUES ('f03ec6be96284411aff08788b9e747cf', '3e3b58d740124000ba01b31e700c4d07');
INSERT INTO `paper` VALUES ('f03ec6be96284411aff08788b9e747cf', '5690c7e8f58a4a56b5979d616fb336e4');
INSERT INTO `paper` VALUES ('f03ec6be96284411aff08788b9e747cf', '7ab64b0fa6404cf48cc57e65ba5d521e');
INSERT INTO `paper` VALUES ('f03ec6be96284411aff08788b9e747cf', '9c52769c22b14570a846e6373a4b1d19');
INSERT INTO `paper` VALUES ('f03ec6be96284411aff08788b9e747cf', 'e7f1f789efc94c4fad2513d1ceab3062');
INSERT INTO `paper` VALUES ('f03ec6be96284411aff08788b9e747cf', 'f5a8617125f3477c964cc10f7236d6f9');

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
  `ANSWER` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '答案',
  `PARSE` varchar(60) DEFAULT NULL COMMENT '解析',
  PRIMARY KEY (`PROBLEM_ID`),
  KEY `TEACHER_ID` (`TEACHER_ID`),
  CONSTRAINT `problem_ibfk_1` FOREIGN KEY (`TEACHER_ID`) REFERENCES `account` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='题目';

-- ----------------------------
-- Records of problem
-- ----------------------------
INSERT INTO `problem` VALUES ('3e3b58d740124000ba01b31e700c4d07', '协议三要素不包括哪项', 'teacher', '2021-09-29 21:53:06', '语法', '语义', '定时', '制定人', 'D', '协议三要素：语法、语义、定时');
INSERT INTO `problem` VALUES ('5690c7e8f58a4a56b5979d616fb336e4', 'TCP协议具有哪些特点：', 'teacher', '2021-09-29 21:53:06', '面向连接', '流量控制', '拥塞控制', '以上都有', 'D', 'TCP：可靠传输、流量控制、拥塞控制、面向连接');
INSERT INTO `problem` VALUES ('7ab64b0fa6404cf48cc57e65ba5d521e', '因特网协议栈有几层', 'teacher', '2021-09-29 21:53:06', '3', '4', '5', '7', 'C', '因特网协议栈是一个五层模型');
INSERT INTO `problem` VALUES ('9a4919826d4d4358b5db96fe81511973', '123', 'teacher', '2021-09-29 21:52:55', '1', '2', '2', '3', 'A', 'A');
INSERT INTO `problem` VALUES ('9c52769c22b14570a846e6373a4b1d19', 'TCP/IP模型几层', 'teacher', '2021-09-29 21:53:06', '3', '4', '6', '7', 'B', 'TCP/IP模型是一个四层模型');
INSERT INTO `problem` VALUES ('e7f1f789efc94c4fad2513d1ceab3062', 'IPv4地址的长度是', 'teacher', '2021-09-29 21:53:06', '2字节', '4字节', '8字节', '16字节', 'B', 'IPv4地址的长度是4字节32比特');
INSERT INTO `problem` VALUES ('f5a8617125f3477c964cc10f7236d6f9', '网络按拓扑结构划分包括：', 'teacher', '2021-09-29 21:53:06', '总线型', '星型', '环形', '以上都是', 'D', '按拓扑结构：总线型、星型、环形、树形、网状网');

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
INSERT INTO `school` VALUES ('2', '牛牛学院');
INSERT INTO `school` VALUES ('3', '喵喵学院');
INSERT INTO `school` VALUES ('4', '猪猪学院');
INSERT INTO `school` VALUES ('5', '汪汪学院');

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
  PRIMARY KEY (`WORK_ID`),
  KEY `school_id` (`SCHOOL_ID`),
  CONSTRAINT `school_id` FOREIGN KEY (`SCHOOL_ID`) REFERENCES `school` (`SCHOOL_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('admin', 'admin', null, null, 'http://localhost:9090/files/avatar/a7f6f8e5fd5b4dbbb862f0e9bcd71316adminAvatar.jpg');
INSERT INTO `user` VALUES ('XUESHENG', 'S101', '喵喵', '1', 'http://localhost:9090/files/avatar/ece5c32c0483418eb70445e136b17d74studentAvatar.jpg');
INSERT INTO `user` VALUES ('S102', 'S102', '戳戳', '1', null);
INSERT INTO `user` VALUES ('S103', 'S103', '汪汪', '1', null);
INSERT INTO `user` VALUES ('S201', 'S201', '香香', '2', null);
INSERT INTO `user` VALUES ('S202', 'S202', '可可', '2', null);
INSERT INTO `user` VALUES ('teacher', 'T0', 'ttt', '1', 'http://localhost:9090/files/avatar/63c08844c4f3474886570cb318877effteacherAvatar.jpg');

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `VIDEO_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `COURSE_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `PATH` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `CREATED_TIME` date DEFAULT NULL,
  `NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `SEQ` int DEFAULT NULL,
  PRIMARY KEY (`VIDEO_ID`),
  KEY `video_course_id` (`COURSE_ID`),
  CONSTRAINT `video_course_id` FOREIGN KEY (`COURSE_ID`) REFERENCES `course` (`COURSE_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video
-- ----------------------------
INSERT INTO `video` VALUES ('956f76808ddd45708382fa2fe46bad5dvideoTest.mp4', '095a0d0d5d8b4eb18d6f2cfb1fe2c1e2', 'http://localhost:9090/files/video/956f76808ddd45708382fa2fe46bad5dvideoTest.mp4', '2021-09-29', 'shipin', '1');
