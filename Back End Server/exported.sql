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
-- Table structure for table `ChatRequest`
--

DROP TABLE IF EXISTS `ChatRequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ChatRequest` (
  `requester_id` varchar(150) DEFAULT NULL,
  `rquested_email` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `chatting_id` varchar(10) DEFAULT NULL,
  `request_id` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ChatRequest`
--

LOCK TABLES `ChatRequest` WRITE;
/*!40000 ALTER TABLE `ChatRequest` DISABLE KEYS */;
/*!40000 ALTER TABLE `ChatRequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `KeyExchange`
--

DROP TABLE IF EXISTS `KeyExchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `KeyExchange` (
  `chatting_id` varchar(100) DEFAULT NULL,
  `secret_key` varchar(3000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `KeyExchange`
--

LOCK TABLES `KeyExchange` WRITE;
/*!40000 ALTER TABLE `KeyExchange` DISABLE KEYS */;
/*!40000 ALTER TABLE `KeyExchange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `KeyManagment`
--

DROP TABLE IF EXISTS `KeyManagment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `KeyManagment` (
  `id` varchar(100) DEFAULT NULL,
  `session_id` varchar(100) DEFAULT NULL,
  `Pubkey` varchar(3000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `KeyManagment`
--

LOCK TABLES `KeyManagment` WRITE;
/*!40000 ALTER TABLE `KeyManagment` DISABLE KEYS */;
/*!40000 ALTER TABLE `KeyManagment` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (11,'03e44b7a-05db-4495-86f4-d1c2add50e47','john','johnvally237@gmail.com','9cced9c634db4236c6ea75b3f16282e03ec59426108a89aa6dfc82970313ea9a5ac490e4d480786a4a8cc8f0040d9f1d58890ca8799f686e6b6fe662c4d02a70','268830688d01baed','2022-01-01 06:37:54','2022-01-01 06:37:54'),(12,'2f33cf56-0c98-47d7-8667-8b518ba767bf','Tom','tomhanks556717@gmail.com','c6574afe99c272fee64b011b2970ea04e2182acd65b236869ef8eba5ac488d3d5d475f3d045c4611d0725a6a2858eb15acd7cc98c3d885fdc5523791a2cc38d9','68d1ce93088c61ca','2022-01-01 06:37:56','2022-01-01 06:37:56');
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

-- Dump completed on 2022-01-03 12:28:55
