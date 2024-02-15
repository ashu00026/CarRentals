CREATE USER 'ashutosh'@'localhost' IDENTIFIED BY 'ashutosh';
 GRANT ALL ON javabase.* TO 'ashutosh'@'localhost' IDENTIFIED BY 'ashutosh';

CREATE DATABASE  IF NOT EXISTS `carrental_directory`;
USE `carrental_directory`;

--
-- Table structure for table `carRental`
--

DROP TABLE IF EXISTS `cars`;

CREATE TABLE `cars` (
  `id` int NOT NULL AUTO_INCREMENT,
  `Type` varchar(45) DEFAULT NULL,
  `color` varchar(45) DEFAULT NULL,
  `cost` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Data for table `employee`
--

INSERT INTO `cars` VALUES
	(1,'hatchback','green',20000),
	(2,'sedan','red',40000),
	(3,'suv','grey',30000),
	(4,'coupe','red',60000),
	(5,'mini','silver',15000);

DROP TABLE IF EXISTS `users`;

CREATE TABLE users(
	`user_name` VARCHAR(45) NOT NULL ,
    `password` VARCHAR(70) DEFAULT NULL,
	`enabled`	tinyint NOT NULL,
    PRIMARY KEY(`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO users VALUES
	('john','{bcrypt}$2a$10$JRja.is.k.jA/BfJWOou2uVpFAvbge5wv3iWKNoZmRn98Oo7va3YW',1),
	('sushant','{bcrypt}$2a$10$Ss6qeocVAppnbR7HTYWVM.96mgzVshMGwdLE8Sr8lmtXaU9hthjEe',1),
    ('miller','{bcrypt}$2a$10$u6q9E3rZxb/ZF59c8HRpgey6AeUx45OFkhYyo.PGsFVkfLGAPxpqe',1),
    ('kevin','{bcrypt}$2a$10$JgcRy8aDJkRMr4dYZnHai..gWfyVzcd7uGnN/787YTh/noSuKE1bW',1),
	('alok','{bcrypt}$2a$10$K/gwcBpQx/ao53QVIYCopuakPa5zsrizdQE2e8wqwhDIc0hUHwvsG',1);