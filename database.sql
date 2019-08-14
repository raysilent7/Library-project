CREATE DATABASE `libraryjdbc` 
/*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ 
/*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `book` (
  `Id` int(7) NOT NULL AUTO_INCREMENT,
  `Isbn` int(11) NOT NULL,
  `Name` varchar(60) DEFAULT NULL,
  `Autor` varchar(60) DEFAULT NULL,
  `Price` float DEFAULT NULL,
  `ReleaseDate` date DEFAULT NULL,
  `ImgPath` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`Id`, `Isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
