CREATE TABLE `book` 
(   `Id` int(4) NOT NULL AUTO_INCREMENT,   
`Isbn` int(4) NOT NULL,   
`Name` varchar(60) DEFAULT NULL,   
`Autor` varchar(60) DEFAULT NULL,   
`Price` float DEFAULT NULL,   
`ReleaseDate` date DEFAULT NULL,   
`ImgPath` varchar(60) DEFAULT NULL,   
PRIMARY KEY (`Id`),   
UNIQUE KEY `Isbn` (`Isbn`) ) 
ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `libraryjdbc`.`book` 
VALUES (1, 100000, 'Far Away Lands', 'Rayan Garcia da Cunha',  
29.9, '2018-12-31', '/home/rgarcia/Pictures/bazelplush.jpg'), 
(2, 100001, 'Shadow and Bones', 'Leigh Bardugo',  
39.9, '2010-01-09', '/home/rgarcia/Pictures/bazelplush.jpg'), 
(3, 100002, 'Siege and Storm', 'Leigh Bardugo',  
59.9, '2012-05-23', '/home/rgarcia/Pictures/teste.jpg'), 
(4, 100003, 'Ruin and Ascension', 'Leigh Bardugo',  
59.9, '2015-03-24', '/home/rgarcia/Pictures/teste.jpg');
