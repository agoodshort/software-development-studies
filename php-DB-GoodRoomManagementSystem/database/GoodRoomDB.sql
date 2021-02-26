CREATE DATABASE GoodRoom;

/* CREATE TABLES */

CREATE TABLE `Persons` (
  `personID` INT NOT NULL AUTO_INCREMENT, -- Primary Key
  `firstName` VARCHAR(50) NOT NULL, -- Mandatory
  `surname` VARCHAR(50) NOT NULL, -- Mandatory
  `phoneNo` VARCHAR(15) NOT NULL, -- Mandatory
  `emailAddress` VARCHAR(100),
  `addressLine1` VARCHAR(100),
  `addressLine2` VARCHAR(100),
  `town` VARCHAR(60),
  `county` VARCHAR(15),
  `eircode` VARCHAR(10),
  `country` VARCHAR(20),
  PRIMARY KEY(`personID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8; -- This needs to be specify to use variable in queries

CREATE TABLE `Rates` (
  `rateID` INT NOT NULL AUTO_INCREMENT, -- Primary Key
  `price` DECIMAL(10,2),
  `description` VARCHAR(255),
  PRIMARY KEY(`rateID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8; -- This needs to be specify to use variable in queries

CREATE TABLE `Rooms` (
  `roomID` INT NOT NULL AUTO_INCREMENT, -- Primary Key
  `rateID` INT, -- Foreign Key
  `roomType` VARCHAR(20),
  `roomNo` INT,
  `adultsNb` INT,
  `childrenNb` INT,
  PRIMARY KEY(`roomID`),
  FOREIGN KEY(`rateID`) REFERENCES `Rates`(`rateID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8; -- This needs to be specify to use variable in queries

CREATE TABLE `Bookings` (
  `bookingID` INT NOT NULL AUTO_INCREMENT, -- Primary Key
  `personID` INT, -- Foreign Key
  `roomID` INT, -- Foreign Key
  `roomType` VARCHAR(20), -- Will need to create an extra table for this
  `arrivalDate` date,
  `arrivalTime` time, -- change to datetime value
  `departureDate` date,
  `departureTime` time,
  `comments` VARCHAR(255),
  PRIMARY KEY(`bookingID`),
  FOREIGN KEY(`personID`) REFERENCES `Persons`(`personID`),
  FOREIGN KEY(`roomID`) REFERENCES `Rooms`(`roomID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8; -- This needs to be specify to use variable in queries

CREATE TABLE `Users` (
  `userID` INT NOT NULL AUTO_INCREMENT, -- Primary Key
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  PRIMARY KEY(`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8; -- This needs to be specify to use variable in queries

-- ----------------------------------------------------------------------------

/* INSERT DETAILS */

-- Insert persons
INSERT INTO `Persons`(`firstName`,`surname`,`phoneNo`,`addressLine1`,`addressLine2`,`town`,`county`,`eircode`,`country`) VALUES
('Adrien','Biencourt','+353836578790','15 Shop Street',NULL,'Galway','Galway','H91 P9D9','Ireland'),
('Izabela','Cierechowicz','+353894456789','15 Shop Street',NULL,'Galway','Galway','H91 P9D9','Ireland'),
('Robert','Jackson','+353831234567','Apt 5 Reb Abbey Hall','Abbey Street','Cork','Cork',NULL,'Ireland');

-- Insert rates
INSERT INTO `Rates`(`price`,`description`) VALUES
('35.00','Basic rate'),
('70.00','Deluxe rate');

-- Insert rooms
INSERT INTO `Rooms`(`rateID`,`roomType`,`roomNo`,`adultsNb`,`childrenNb`) VALUES
(1,'Basic','1','2','2'),
(2,'Deluxe','2','4','4'),
(1,'Basic','3','2','0');

-- Insert a reservation
INSERT INTO `Bookings`(`personID`,`roomID`,`arrivalDate`,`departureDate`) VALUES
(1,1,'2020-05-10','2020-05-15');

-- Insert username / password
INSERT INTO `Users`(`username`,`password`) VALUES
('admin','admin');