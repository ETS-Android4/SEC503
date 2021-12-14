-- MariaDB dump 10.18  Distrib 10.5.8-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: Project
-- ------------------------------------------------------
-- Server version	10.5.8-MariaDB-3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Keys_Managements`
--

DROP TABLE IF EXISTS `Keys_Managements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Keys_Managements` (
  `id` int(11) DEFAULT NULL,
  `session_id` varchar(100) DEFAULT NULL,
  `Public_key` varchar(1000) DEFAULT NULL,
  UNIQUE KEY `session_id` (`session_id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `session_id_2` (`session_id`),
  CONSTRAINT `Keys_Managements_FK` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Keys_Managements`
--

LOCK TABLES `Keys_Managements` WRITE;
/*!40000 ALTER TABLE `Keys_Managements` DISABLE KEYS */;
INSERT INTO `Keys_Managements` VALUES (4,'9eedc100df87f0307abf76d63e3d279d2af51b22d0785e3094c9e2871a4debb7e7bccc5b7504201b07b9045098905fca5dc6','LS0tLS1CRUdJTiBQVUJMSUMgS0VZLS0tLS0KTUlJQklqQU5CZ2txaGtpRzl3MEJBUUVGQUFPQ0FROEFNSUlCQ2dLQ0FRRUFrbi9Ld2xxVEVDSkVjVk9BZG5ldwpyditJRGM3VzY1ZjVxREtTREY0UjJtWUFWWlBIWkxvNUdaSUN3OGo2NmlNcUQxSURaU3dzd0F0aUZ6bU5MR21tClJKR0svdWNsUmhKM3R4Z3ExZGZWNWFFTHgvbVdjUHBob1BLQVp6eGt1clEwSmE3OTEvNEpPMTBLYTFMOWNxSXcKc3FCbzE2UmJMNTZseVJLeDB2MXN3ZGpkOTZZQXp1aUVmL1E5Rkswc09SZ2RPWG9QY1pwSTBvRjdQVE9hajczWgpMM3BtT25wVlM3RTdnMTU1MVRkNEhxeVFFVXh2bEFEQlpodWp1WXJiM2p2c2pUWFlxYlVRMHpUcVVLTG5RaVpFCmNIa0JUV2hTTGtYcFJvSkcvU0dzdmQ3Q0dkcDZRRVd4dTdjTUNjTHhIQ3p1SkN2NHhkM1NpdDBVVndlU1FqVjcKb1FJREFRQUIKLS0tLS1FTkQgUFVCTElDIEtFWS0tLS0tCg==');
/*!40000 ALTER TABLE `Keys_Managements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authenticated_users`
--

DROP TABLE IF EXISTS `authenticated_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authenticated_users` (
  `id` int(11) DEFAULT NULL,
  `session_id` varchar(100) DEFAULT NULL,
  UNIQUE KEY `session_id` (`session_id`),
  KEY `authenticated_users_FK` (`id`),
  CONSTRAINT `authenticated_users_FK` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authenticated_users`
--

LOCK TABLES `authenticated_users` WRITE;
/*!40000 ALTER TABLE `authenticated_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `authenticated_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_Management`
--

DROP TABLE IF EXISTS `chat_Management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chat_Management` (
  `request_ID` int(11) DEFAULT NULL,
  `requester_Party` varchar(100) DEFAULT NULL,
  `requested_party` varchar(100) DEFAULT NULL,
  `status` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_Management`
--

LOCK TABLES `chat_Management` WRITE;
/*!40000 ALTER TABLE `chat_Management` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_Management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_sessions`
--

DROP TABLE IF EXISTS `chat_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chat_sessions` (
  `ID` int(11) NOT NULL,
  `Room_Number` int(11) DEFAULT NULL,
  `Participant1` varchar(100) DEFAULT NULL,
  `Participant2` varchar(100) DEFAULT NULL,
  `PORT` int(11) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_sessions`
--

LOCK TABLES `chat_sessions` WRITE;
/*!40000 ALTER TABLE `chat_sessions` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `key_exchange`
--

DROP TABLE IF EXISTS `key_exchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `key_exchange` (
  `ID` int(11) DEFAULT NULL,
  `secret_key` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `key_exchange`
--

LOCK TABLES `key_exchange` WRITE;
/*!40000 ALTER TABLE `key_exchange` DISABLE KEYS */;
/*!40000 ALTER TABLE `key_exchange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otp`
--

DROP TABLE IF EXISTS `otp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `otp` (
  `id` int(11) DEFAULT NULL,
  `session_id` varchar(100) DEFAULT NULL,
  `otp` varchar(6) DEFAULT NULL,
  `state` varchar(100) DEFAULT '0',
  `create_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otp`
--

LOCK TABLES `otp` WRITE;
/*!40000 ALTER TABLE `otp` DISABLE KEYS */;
INSERT INTO `otp` VALUES (4,'9eedc100df87f0307abf76d63e3d279d2af51b22d0785e3094c9e2871a4debb7e7bccc5b7504201b07b9045098905fca5dc6','a56ea2','0','2021-11-24 13:28:00'),(5,'b013521df7f624c27a212daa3955bf24f3c8541587f2c3ac112c1f318c4cd24f60786c03c3b7f90cbd192ff7077abcc4dcf0','b3ffd7','0','2021-12-14 06:26:46'),(5,'c7856d88f4d44ffa66fb72100a89182b508cd3ebeeba8ce690c34814f4999dbe2c114f379196df10a9a7298985caf83ce8b2','46fd91','0','2021-12-14 12:18:51'),(5,'3f15bd32c88bde2e1951f6f4ba628dc1519e43bfabc41d8422da0127f89414c693d1303e2c92e95c52407abf659100287e53','9c07bb','0','2021-12-14 12:18:52'),(5,'924340657456c1058e3dfa1a3020ee8d6690d8fc7950a2d265398332270c97d00fb1c5410e595d052996bd862d2242b24bd5','f75c21','0','2021-12-14 12:18:53'),(5,'14ce84106fca893afcd2804271542e1d3e14fc74966781425bc77867edabc5162aa6852d17ff5e3dcde812c664ea708c1943','3a977c','0','2021-12-14 12:18:54'),(5,'5aadb4911518a55bed8262c782d51093fd90636b6c95af16a953a886fe50080eda7f271b24bf0d94804b2e78e666ca5ecb88','5a724e','0','2021-12-14 12:18:55'),(5,'76352b599a789fa99bb8979a46f6bf208c310a07b9c939036c6fb854960b27d4624bb3bf1d4cfca7a185d5a4f5791505b831','2b8e1f','0','2021-12-14 12:38:36');
/*!40000 ALTER TABLE `otp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `unique_id` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `encrypted_password` varchar(128) DEFAULT NULL,
  `salt` varchar(16) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_id` (`unique_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (4,'2a45c568-ac1f-4c4f-8f69-4b8bbe919c8c','abduwalahab','asd566717@gmail.com','f882a4ba179009f45551b6d65c2d270086e4861edf7e80021d7a4c59c150d270c5ff3cdaeb88c3e1e8170e79b0c74e43bd45433412d004804e52858cb6088823','23df99ee504454db','2021-11-24 13:25:59','2021-11-24 13:25:59'),(5,'df2dbf2c-43fd-40f5-a104-0d64d70d9e6f','ali','asd556717@gmail.com','b0f0aafab1aef6746ce01ba17efb365ceaf0c6b736e75200bf5a75164f9277da300b363c7dcada277912f78b44bb29a8e6bef2cffe994aaa0ce16fac934913ef','81280b11b4a401f0','2021-12-14 06:26:39','2021-12-14 06:26:39');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-14 12:47:01
