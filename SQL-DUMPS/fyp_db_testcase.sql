-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: fyp_db
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `testcase`
--

DROP TABLE IF EXISTS `testcase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `testcase` (
  `testcase_id` int(11) NOT NULL AUTO_INCREMENT,
  `no` varchar(45) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `userstory_id` int(11) DEFAULT NULL,
  `ac_id` int(11) DEFAULT NULL,
  `dateGenerated` varchar(45) DEFAULT NULL,
  `lastExecutedDate` varchar(45) DEFAULT NULL,
  `active` tinyint(4) DEFAULT '1',
  `expectedResult` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`testcase_id`),
  KEY `fk_userstory_testcase_idx` (`userstory_id`),
  KEY `fk_acceptancecriteria_testcase_idx` (`ac_id`),
  CONSTRAINT `fk_acceptancecriteria_testcase` FOREIGN KEY (`ac_id`) REFERENCES `acceptancecriteria` (`acceptancecriteria_id`),
  CONSTRAINT `fk_userstory_testcase` FOREIGN KEY (`userstory_id`) REFERENCES `userstory` (`userstory_id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testcase`
--

LOCK TABLES `testcase` WRITE;
/*!40000 ALTER TABLE `testcase` DISABLE KEYS */;
INSERT INTO `testcase` VALUES (1,'tc_1','email is not entered',1,1,'2019/05/23 13:49:55',NULL,0,NULL),(2,'tc_2','pass is not entered',1,1,'2019/05/23 13:49:56',NULL,0,NULL),(3,'tc_1','email is not entered',1,1,'2019/05/23 13:56:05',NULL,0,'FAIL'),(4,'tc_2','pass is not entered',1,1,'2019/05/23 13:56:06',NULL,0,'FAIL'),(5,'tc_1','email is not entered',1,1,'2019/05/23 13:58:44',NULL,0,'FAIL'),(6,'tc_2','pass is not entered',1,1,'2019/05/23 13:58:44',NULL,0,'FAIL'),(7,'tc_1','email is not entered',1,1,'2019/05/23 14:00:29',NULL,0,'FAIL'),(8,'tc_2','pass is not entered',1,1,'2019/05/23 14:00:30',NULL,0,'FAIL'),(9,'tc_1','email is not entered',1,1,'2019/05/23 22:54:44',NULL,0,'FAIL'),(10,'tc_2','pass is not entered',1,1,'2019/05/23 22:54:45',NULL,0,'FAIL'),(11,'tc_1','email is not entered',1,1,'2019/05/23 23:23:55',NULL,0,'FAIL'),(12,'tc_2','pass is not entered',1,1,'2019/05/23 23:23:56',NULL,0,'FAIL'),(13,'tc_1','email is not entered',1,1,'2019/05/23 23:49:53',NULL,0,'FAIL'),(14,'tc_2','pass is not entered',1,1,'2019/05/23 23:49:53',NULL,0,'FAIL'),(15,'tc_1','email is not entered',1,1,'2019/05/23 23:51:37',NULL,0,'FAIL'),(16,'tc_2','pass is not entered',1,1,'2019/05/23 23:51:37',NULL,0,'FAIL'),(17,'tc_1','email is not entered',1,1,'2019/05/23 23:52:10',NULL,0,'FAIL'),(18,'tc_2','pass is not entered',1,1,'2019/05/23 23:52:10',NULL,0,'FAIL'),(19,'tc_1','email is not entered',1,1,'2019/05/24 14:52:50',NULL,0,'FAIL'),(20,'tc_2','pass is not entered',1,3,'2019/05/24 14:52:55',NULL,0,'FAIL'),(21,'tc_1','email is not entered',1,1,'2019/05/24 15:13:41',NULL,0,'FAIL'),(22,'tc_1','email is not entered',1,1,'2019/05/24 15:25:32',NULL,0,'FAIL'),(23,'tc_2','pass is not entered',1,3,'2019/05/24 15:25:33',NULL,0,'FAIL'),(24,'tc_1','email is not entered',1,1,'2019/05/24 15:26:45',NULL,0,'FAIL'),(25,'tc_1','email is not entered',1,1,'2019/05/24 15:28:01',NULL,0,'FAIL'),(26,'tc_2','pass is not entered',1,3,'2019/05/24 15:28:02',NULL,0,'FAIL'),(27,'tc_1','email is not entered',1,1,'2019/05/24 15:33:58',NULL,0,'FAIL'),(28,'tc_2','pass is not entered',1,3,'2019/05/24 15:33:58',NULL,0,'FAIL'),(29,'tc_1','email is not entered',1,1,'2019/05/24 15:36:04',NULL,0,'FAIL'),(30,'tc_1','email is not entered',1,1,'2019/05/24 17:03:48',NULL,0,'FAIL'),(31,'tc_2','pass is not entered',1,3,'2019/05/24 17:03:48',NULL,0,'FAIL'),(32,'tc_1','email is not entered',1,1,'2019/05/24 17:10:16',NULL,0,'FAIL'),(33,'tc_2','pass is not entered',1,3,'2019/05/24 17:10:16',NULL,0,'FAIL'),(34,'tc_1','email is not entered',1,1,'2019/05/24 17:10:45','2019/05/25 11:22:16',0,'FAIL'),(35,'tc_2','pass is not entered',1,3,'2019/05/24 17:10:46','2019/05/25 11:22:24',0,'FAIL'),(36,'tc_1','email is not entered',1,1,'2019/05/25 17:19:54',NULL,0,'FAIL'),(37,'tc_2','pass is not entered',1,3,'2019/05/25 17:19:54',NULL,0,'FAIL'),(38,'tc_1','email is not entered',1,1,'2019/05/25 18:13:21',NULL,0,'FAIL'),(39,'tc_2','pass is not entered',1,3,'2019/05/25 18:13:28',NULL,0,'FAIL'),(40,'tc_1','email is not entered',1,1,'2019/05/25 18:16:56',NULL,0,'FAIL'),(41,'tc_1','email is not entered',1,1,'2019/05/25 18:20:18',NULL,0,'FAIL'),(42,'tc_2','pass is not entered',1,3,'2019/05/25 18:20:19',NULL,0,'FAIL'),(43,'tc_1','email is not entered',1,1,'2019/05/25 18:25:34',NULL,0,'FAIL'),(44,'tc_2','pass is not entered',1,3,'2019/05/25 18:25:35',NULL,0,'FAIL'),(45,'tc_1','email is not entered',1,1,'2019/05/25 18:39:30',NULL,0,'FAIL'),(46,'tc_1','username is not entered',1,1,'2019/05/25 19:36:28','2019/05/25 19:37:42',0,'FAIL'),(47,'tc_2','pass is not entered',1,3,'2019/05/25 19:36:28',NULL,0,'FAIL'),(48,'tc_1','password is not entered',2,4,'2019/05/25 19:45:25','2019/05/25 19:46:10',0,'FAIL'),(49,'tc_1','res_code is not entered',2,4,'2019/05/25 20:07:33',NULL,0,'FAIL'),(50,'tc_1','name is not entered',2,4,'2019/05/25 20:11:37',NULL,0,'FAIL'),(51,'tc_1','psw is not entered',2,4,'2019/05/25 20:29:17','2019/05/25 20:31:14',0,'FAIL'),(52,'tc_1','psw is not entered',2,4,'2019/05/25 20:47:06',NULL,0,'FAIL'),(53,'tc_1','email is not entered',1,1,'2019/05/25 20:57:33',NULL,0,'FAIL'),(54,'tc_1','psw is not entered',2,4,'2019/05/25 21:40:49','2019/05/25 21:45:09',0,'FAIL'),(55,'tc_1','psw is not entered',2,4,'2019/05/25 22:06:53',NULL,0,'FAIL'),(56,'tc_1','psw is not entered',2,4,'2019/05/25 22:08:55','2019/05/25 22:09:35',1,'FAIL'),(57,'tc_1','email is not entered',1,1,'2019/05/25 22:16:16',NULL,1,'FAIL'),(58,'tc_1','pass is not entered',4,6,'2019/05/26 11:53:19','2019/05/26 12:05:14',1,'FAIL');
/*!40000 ALTER TABLE `testcase` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-26 13:32:16
