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
INSERT INTO `ChatRequest` VALUES ('6','asd556717@gmail.com','binding',NULL,'1643');
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
INSERT INTO `KeyExchange` VALUES ('1643','647B2456060F0D8DA137F9D2E1A858E2100C62BEA5B5DDFF8339D71225B359478CC5936F518FF101BE17BBA6AE90A037A513219C52849EF6C4CC9FFA4283018A9B560C033EF6D1FBDFDF9E4B58195B781A4D5CFA39F1EC4201CDA9ABE353FB93591176CAAC21595441BFF0B1C5BD340EA3578655ABC1A288FDCDC909E8EDBDA3');
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
INSERT INTO `KeyManagment` VALUES ('5','1abd61070eda58d6b3ade5fb7d8218ddf1509a1848287d7509b2c02f7c5af9025c22f475e926401d3d509322afbf358c4d33','<RSAPublicKey><Modulus>mKtip7uf/swsx1ULn0j0cS1MzMhZ8BA3Ang7mS7hMsrfUYvArxlzb4ynu7ofd432pWWflG/l0ujMr4eCmmxY55Fc/1lVeANwUwDQeTZz2Jr/jlOhGKCjAoAKgAEm/CU6UAoNiVeVAwkh3EQafvth5PcHtVmAR9n1S5Gef7iynds=</Modulus><Exponent>AQAB</Exponent></RSAPublicKey>'),('6','89bb47574b9da0c7c3c103bb47b57680c8dd2ff7b30e3d3718154daffd3d8f9916fbc640a6f2411fef91ff8c2e8e2175422d','<RSAPublicKey><Modulus>1tPr9RK8RTqaf2C0vhy1weDeuz/tULIFDe1CyoiX0NVsmkzpyl1klFSaDMmgbl27l9y0ecOSGYN9vYzNZ3eprzOUvwN5CKx8GZ1LCT78kjPSomy+sDkmT7D5u1CxTwHpfNNKnSEQMK33Vu98q4Dh6ItDazxLcWDii6x4ZHooSvE=</Modulus><Exponent>AQAB</Exponent></RSAPublicKey>');
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
INSERT INTO `authenticated_users` VALUES (5,'1abd61070eda58d6b3ade5fb7d8218ddf1509a1848287d7509b2c02f7c5af9025c22f475e926401d3d509322afbf358c4d33'),(6,'89bb47574b9da0c7c3c103bb47b57680c8dd2ff7b30e3d3718154daffd3d8f9916fbc640a6f2411fef91ff8c2e8e2175422d');
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
INSERT INTO `otp` VALUES (5,'8aacee8f209067ac0e479e1a115d2986a8a0d4b123602e82fea464d5e1b5f445497dda8eaa3806c961e72316e56be67ef7d6','3b7cfe','0','2021-12-27 11:24:51'),(6,'331745eeff9a9fb33c940853d341cc87df1f5981a65719ecca482d1d52c3a4b2ffae0872a7120aeb2538129ddc3325dfd5df','3ea4fd','0','2021-12-27 11:25:55'),(5,'6ea187cde97930f7e5df7fc48340a1343bdc201360165573ce81a68602b02074e1a3fd1f72c1e1e8a8e7706142cd2b0cab0f','91c454','0','2021-12-27 11:36:48'),(6,'621c8d0af90973536d1d6c107ecb80bb0b66e57e22dbb1b2df4ac88c1eb0c38195f533f73006c64dd99bf683c841d28fc0e7','fa702d','0','2021-12-27 11:37:22'),(5,'27874debe4cd93ba5d7df468da84fa332073c2f734d155b7c637ca24a9d4575cd0ea2221be660a27d3aee81399492022382d','3bdcd8','0','2021-12-27 12:21:52'),(6,'39d43aafb1e316b079e9ffa5a6ed2f26316afc36c483e3e4f3fb123f16c2f2d17483286c0d74711ec2e410402e1b660aab19','60e37f','0','2021-12-27 12:22:18'),(5,'1abd61070eda58d6b3ade5fb7d8218ddf1509a1848287d7509b2c02f7c5af9025c22f475e926401d3d509322afbf358c4d33','ed35f6','0','2021-12-27 12:25:48'),(6,'89bb47574b9da0c7c3c103bb47b57680c8dd2ff7b30e3d3718154daffd3d8f9916fbc640a6f2411fef91ff8c2e8e2175422d','9417e1','0','2021-12-27 12:26:09');
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (5,'df2dbf2c-43fd-40f5-a104-0d64d70d9e6f','ali','asd556717@gmail.com','b0f0aafab1aef6746ce01ba17efb365ceaf0c6b736e75200bf5a75164f9277da300b363c7dcada277912f78b44bb29a8e6bef2cffe994aaa0ce16fac934913ef','81280b11b4a401f0','2021-12-14 06:26:39','2021-12-14 06:26:39'),(6,'df7064ef-e06e-41c1-9388-5941e016fc9d','abdulwahab','abdalwahab.alessa@gmail.com','1c078893ac30ee1e4da624d32eeb9245f844bea7ec924cf1c4e07bfd8c858c63d93dd1746deb6eb8c4f43c1483a2a3e711381af4be61dad9b3d332cb1c5b77b7','bb07211f3717e7a8','2021-12-16 04:59:47','2021-12-16 04:59:47');
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

-- Dump completed on 2021-12-30 11:38:56
