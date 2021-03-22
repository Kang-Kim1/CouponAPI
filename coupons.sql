CREATE DATABASE  IF NOT EXISTS `couponapi` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `couponapi`;

DROP TABLE IF EXISTS `coupons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupons` (
  `c_UID` int NOT NULL AUTO_INCREMENT,
  `c_couponCode` varchar(16) NOT NULL,
  `c_isUsed` tinyint(1) NOT NULL DEFAULT '0',
  `c_assignedTo` varchar(16) DEFAULT NULL,
  `c_expDate` datetime DEFAULT NULL,
  `c_regiDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`c_UID`,`c_couponCode`),
  UNIQUE KEY `c_UID` (`c_UID`,`c_couponCode`),
  UNIQUE KEY `c_couponCode` (`c_couponCode`),
  UNIQUE KEY `c_UID_2` (`c_UID`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

