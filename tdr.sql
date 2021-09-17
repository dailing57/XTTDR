/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80023
Source Host           : localhost:3306
Source Database       : tdr

Target Server Type    : MYSQL
Target Server Version : 80023
File Encoding         : 65001

Date: 2021-09-17 11:24:27
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
INSERT INTO `account` VALUES ('xxx', 'xxx', 'teacher');

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
INSERT INTO `comment` VALUES ('3c592910d39a4fb79ef3d6908cf9998e', '啦啦啦', 'teacher', '2021-09-13 12:43:06', '1', null);
INSERT INTO `comment` VALUES ('4e1abc294bb34eba803ade47580f7ca2', '同学讲一讲哈哈哈', 'teacher', '2021-09-13 21:23:38', '1', null);
INSERT INTO `comment` VALUES ('6d931433f013455583d9f328da6ffdd7', '老师好', 'student', '2021-09-14 07:40:09', '1', '4e1abc294bb34eba803ade47580f7ca2');
INSERT INTO `comment` VALUES ('c503fb38f97944348d4596c28d420fad', '耶耶耶', 'teacher', '2021-09-13 12:43:11', '1', null);
INSERT INTO `comment` VALUES ('ca2a7d22698e428a9c629b6a46e40243', '好耶', 'student', '2021-09-14 07:57:02', '1', null);
INSERT INTO `comment` VALUES ('f689ad03992344a59f19e4fa8c987132', '耶耶耶', 'teacher', '2021-09-13 21:23:44', '1', '4e1abc294bb34eba803ade47580f7ca2');

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
INSERT INTO `course` VALUES ('1', '课程1', 'teacher', '2021-09-04 14:55:35');
INSERT INTO `course` VALUES ('2', '课程2', 'teacher', '2021-09-04 15:33:59');
INSERT INTO `course` VALUES ('3969c962161c47f59596a84c32bd2cf8', 'kk', 'teacher', '2021-09-13 22:09:53');
INSERT INTO `course` VALUES ('4389307617a54b489799e3902a84e2af', '云计算', 'teacher', '2021-09-14 11:33:22');
INSERT INTO `course` VALUES ('f601433079d44ee6bc266b5420a021ce', '算法设计', 'teacher', '2021-09-14 15:33:48');
INSERT INTO `course` VALUES ('f84afc1149864548a86284cb318cdb3a', '计算机网络', 'teacher', '2021-09-13 21:00:25');

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
INSERT INTO `course_material` VALUES ('oakCenter1.png', '1oakCenter1.png', '1', 'H:\\课设一\\TDR\\xttdr\\backend\\tdr/files/1oakCenter1.png', 'teacher', '2021-09-11 10:55:28');

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
INSERT INTO `docourse` VALUES ('2', 'teacher');
INSERT INTO `docourse` VALUES ('1', 'teacher');
INSERT INTO `docourse` VALUES ('f84afc1149864548a86284cb318cdb3a', 'teacher');
INSERT INTO `docourse` VALUES ('3969c962161c47f59596a84c32bd2cf8', 'teacher');
INSERT INTO `docourse` VALUES ('1', 'student');
INSERT INTO `docourse` VALUES ('4389307617a54b489799e3902a84e2af', 'teacher');
INSERT INTO `docourse` VALUES ('f601433079d44ee6bc266b5420a021ce', 'teacher');

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
INSERT INTO `doexam` VALUES ('aafbda09b0e44a65a63947405b85bbe3', 'teacher', '10.00');
INSERT INTO `doexam` VALUES ('aafbda09b0e44a65a63947405b85bbe3', 'student', '10.00');
INSERT INTO `doexam` VALUES ('df6b12024a1348c6b26b1d33638ac627', 'teacher', '20.00');
INSERT INTO `doexam` VALUES ('df6b12024a1348c6b26b1d33638ac627', 'student', '-1.00');

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
INSERT INTO `exam` VALUES ('28fbb66181ca4fcfb1488f5656edbdbf', '1', '1', '2021-09-15 16:27:11', 'teacher', '2021-09-15 16:27:09', '1');
INSERT INTO `exam` VALUES ('aafbda09b0e44a65a63947405b85bbe3', '阿斯蒂芬', '1', '2021-09-16 09:27:25', 'teacher', '2021-09-16 09:27:16', '2.5');
INSERT INTO `exam` VALUES ('df6b12024a1348c6b26b1d33638ac627', 'sdaf', '1', '2021-09-16 23:27:48', 'teacher', '2021-09-16 23:27:45', '2.5');

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
INSERT INTO `homework` VALUES ('1', '作业测试1', 'teacher', '2021-09-09 20:04:29', '2021-09-10 20:04:32', '耶耶耶', '1');
INSERT INTO `homework` VALUES ('3', '作业测试3', 'teacher', '2021-09-09 20:04:29', '2021-09-10 20:04:32', '耶耶耶', '2');
INSERT INTO `homework` VALUES ('4942af3382c7451891f693ea11d91924', '测试作业2', 'teacher', '2021-09-11 08:00:00', '2021-09-12 08:00:00', '<p>嘿嘿嘿咕咕咕</p>', '1');

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
INSERT INTO `paper` VALUES ('28fbb66181ca4fcfb1488f5656edbdbf', 'eb7edd54b6f448148e26ec70d5c61d6f');
INSERT INTO `paper` VALUES ('28fbb66181ca4fcfb1488f5656edbdbf', '0ea2eea138714a2cb345e76c4c8bc463');
INSERT INTO `paper` VALUES ('aafbda09b0e44a65a63947405b85bbe3', '90d77c1f940f42a8b53c206e0b93768f');
INSERT INTO `paper` VALUES ('aafbda09b0e44a65a63947405b85bbe3', '443d076cad754f8cae8645b6e13d8de5');
INSERT INTO `paper` VALUES ('aafbda09b0e44a65a63947405b85bbe3', '0ea2eea138714a2cb345e76c4c8bc463');
INSERT INTO `paper` VALUES ('aafbda09b0e44a65a63947405b85bbe3', '28cb9b50df324473b0e263048d20c809');
INSERT INTO `paper` VALUES ('df6b12024a1348c6b26b1d33638ac627', '0ea2eea138714a2cb345e76c4c8bc463');
INSERT INTO `paper` VALUES ('df6b12024a1348c6b26b1d33638ac627', '28cb9b50df324473b0e263048d20c809');
INSERT INTO `paper` VALUES ('df6b12024a1348c6b26b1d33638ac627', '406824faa39b40aeb5bcb759861b94be');
INSERT INTO `paper` VALUES ('df6b12024a1348c6b26b1d33638ac627', '443d076cad754f8cae8645b6e13d8de5');

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
INSERT INTO `problem` VALUES ('0ea2eea138714a2cb345e76c4c8bc463', 'dsf', 'teacher', '2021-09-15 22:44:25', 'as', 'f', 'b', 'd', 'A', 'saf');
INSERT INTO `problem` VALUES ('28cb9b50df324473b0e263048d20c809', '1+1=?', 'teacher', '2021-09-16 20:10:12', '1', '2', '3', '4', 'D', '1+1=4');
INSERT INTO `problem` VALUES ('406824faa39b40aeb5bcb759861b94be', '圆锥曲线', 'teacher', '2021-09-16 20:10:12', 'x^2+y^2=1', '这是个椭圆', '这是人类历史上发现的第一个椭圆', '这不是椭圆', 'D', '显然');
INSERT INTO `problem` VALUES ('443d076cad754f8cae8645b6e13d8de5', 'dddd', 'teacher', '2021-09-16 11:29:58', 'saf', 'sdfsdf', 'asdfg', 'sadf', 'B', 'sadf');
INSERT INTO `problem` VALUES ('47cc40aeb4e84b489ebe1256b0b0dee2', '1', 'teacher', '2021-09-15 17:13:11', '1', '1', '1', '1', 'B', 'dsaf');
INSERT INTO `problem` VALUES ('90d77c1f940f42a8b53c206e0b93768f', 'asD', 'teacher', '2021-09-16 10:45:20', 'A', 'AD', 'F', 'SD', 'A', 'SDF');
INSERT INTO `problem` VALUES ('a815dd1365d546c5b6463e24c60ca832', '比较难', 'teacher', '2021-09-16 20:10:12', 'x=1', 'y=1', 'x=1且y=1', '以上均正确', 'D', '很明显');
INSERT INTO `problem` VALUES ('ac920a02cba6421a94b28573370e95cd', 's', 'teacher', '2021-09-15 22:44:08', 'a', 'a', 'f', 'b', 'B', 'sdf');
INSERT INTO `problem` VALUES ('c6710c266f854741912eabcd542d738d', '很难', 'teacher', '2021-09-16 20:10:12', 'A', 'B', 'C', 'D', 'A', '很难所以老师也不知道');
INSERT INTO `problem` VALUES ('eb7edd54b6f448148e26ec70d5c61d6f', 's', 'teacher', '2021-09-15 19:26:15', 'a', 'a', 'a', 'a', 'B', 'xc');

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
INSERT INTO `user` VALUES ('1', '1', 'dl', '1', null);
INSERT INTO `user` VALUES ('admin', 'admin', null, null, null);
INSERT INTO `user` VALUES ('student', 'S0', 'sss', '1', null);
INSERT INTO `user` VALUES ('teacher', 'T0', 'ttt', '1', 'http://localhost:9090/files/avatar/75c87d67598a479ebe4575b46f8677f3oakCenter1.png');
INSERT INTO `user` VALUES ('1438656966569836546', 'T2', 'sdf', '1', null);
INSERT INTO `user` VALUES ('1438675537718935553', 'T5', 'XX', '1', null);
INSERT INTO `user` VALUES ('1438675537718935553', 'T6', 'SS', '1', null);
INSERT INTO `user` VALUES ('1438675537718935553', 'T7', 'DD', '1', null);
INSERT INTO `user` VALUES ('1438675537718935553', 'T8', 'BB', '1', null);
INSERT INTO `user` VALUES ('1438675537718935553', 'T9', 'QQ', '1', null);
INSERT INTO `user` VALUES ('xxx', 'xxx', null, null, null);
INSERT INTO `user` VALUES ('1438671105895747585', '圆锥曲线', 'x^2+y^2=1', '3', null);
INSERT INTO `user` VALUES ('1438671105895747585', '很难', 'A', '1', null);
INSERT INTO `user` VALUES ('1438671105895747585', '比较难', 'x=1', '2', null);

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
INSERT INTO `video` VALUES ('514c54b440194aeebe468067435c8d7a嘿嘿嘿.mp4', '1', 'http://localhost:9090/files/video/514c54b440194aeebe468067435c8d7a嘿嘿嘿.mp4', '2021-09-13', '测试一波', '1');
