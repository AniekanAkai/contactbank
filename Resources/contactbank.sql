CREATE DATABASE  IF NOT EXISTS `contact_bank` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `contact_bank`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: contact_bank
-- ------------------------------------------------------
-- Server version	5.5.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `all_contacts`
--

DROP TABLE IF EXISTS `all_contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `all_contacts` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `position` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `address` text,
  `country` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` bigint(20) unsigned NOT NULL,
  `projects` text,
  `groups` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `all_contacts`
--

LOCK TABLES `all_contacts` WRITE;
/*!40000 ALTER TABLE `all_contacts` DISABLE KEYS */;
INSERT INTO `all_contacts` VALUES (1,'Aniekan Akai','Software Developer','Intel Corporation','7 Alfred Rewane Road, Ikoyi','Nigeria','aeakai@intel.com',2348055384116,'ContactBank, COP Program','Intel Staff'),(2,'Olubunmi Ekundare','Country Manager','Intel Corporation','7 Alfred Rewane Road, Ikoyi','Nigeria','olubunmi.ekundare@intel.com',2348055455900,'COP Program','Intel Staff'),(4,'Colin Ryan','Air Conditioning Specialist','LG','21 Harvard Avenue','Nigeria','cryan@lg.com.ng',2348662134,'Renovation','Specialist'),(5,'Colin Mochrie','comedian','','3 Morano Drive, Ottawa, Ontario','Canada','cm@mochrie.ca',6138662134,'Bluesfest','Artist'),(6,'Joe Turner','TV Host','','3 Colonel By Drive, Ottawa, Ontario','Canada','jt@turner.ca',6138552134,'Bluesfest','Artist');
/*!40000 ALTER TABLE `all_contacts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-09 23:18:20
