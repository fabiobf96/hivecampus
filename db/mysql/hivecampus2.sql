-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema hivecampus2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema hivecampus2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hivecampus2` ;
USE `hivecampus2` ;

-- -----------------------------------------------------
-- Table `hivecampus2`.`Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hivecampus2`.`Users` (
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `role` ENUM('owner', 'tenant') NOT NULL,
  PRIMARY KEY (`email`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `email_UNIQUE` ON `hivecampus2`.`Users` (`email` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `hivecampus2`.`Account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hivecampus2`.`Account` (
  `email` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `telephone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`email`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `email_UNIQUE` ON `hivecampus2`.`Account` (`email` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `hivecampus2`.`Home`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hivecampus2`.`Home` (
  `idHome` INT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(45) NOT NULL,
  `coordinates` POINT NOT NULL,
  `homeType` VARCHAR(45) NOT NULL,
  `homeSurface` INT NOT NULL,
  `numRooms` INT NOT NULL,
  `numBathrooms` INT NOT NULL,
  `floor` INT NOT NULL,
  `elevator` TINYINT NOT NULL,
  `homeDescription` MEDIUMTEXT NOT NULL,
  `owner` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idHome`),
  CONSTRAINT `fk_Home_Account1`
    FOREIGN KEY (`owner`)
    REFERENCES `hivecampus2`.`Account` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Home_Account1_idx` ON `hivecampus2`.`Home` (`owner` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `hivecampus2`.`Room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hivecampus2`.`Room` (
  `idRoom` INT NOT NULL,
  `home` INT NOT NULL,
  `roomType` VARCHAR(45) NOT NULL,
  `roomSurface` VARCHAR(45) NOT NULL,
  `privateBath` TINYINT NOT NULL,
  `balcony` TINYINT NOT NULL,
  `conditioner` TINYINT NOT NULL,
  `tv` TINYINT NOT NULL,
  `roomDescription` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`idRoom`, `home`),
  CONSTRAINT `fk_Room_Home`
    FOREIGN KEY (`home`)
    REFERENCES `hivecampus2`.`Home` (`idHome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Room_Home_idx` ON `hivecampus2`.`Room` (`home` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `hivecampus2`.`University`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hivecampus2`.`University` (
  `name` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `uniCoordinates` POINT NOT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hivecampus2`.`Home_Images`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hivecampus2`.`Home_Images` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `type` ENUM('png', 'jpg', 'jpeg') NOT NULL,
  `image` MEDIUMBLOB NOT NULL,
  `home` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Images_Home1`
    FOREIGN KEY (`home`)
    REFERENCES `hivecampus2`.`Home` (`idHome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Images_Home1_idx` ON `hivecampus2`.`Home_Images` (`home` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `hivecampus2`.`Room_Images`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hivecampus2`.`Room_Images` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `type` ENUM('png', 'jpg', 'jpeg') NOT NULL,
  `image` MEDIUMBLOB NOT NULL,
  `room` INT NOT NULL,
  `home` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Room_Images_Room1`
    FOREIGN KEY (`room` , `home`)
    REFERENCES `hivecampus2`.`Room` (`idRoom` , `home`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Room_Images_Room1_idx` ON `hivecampus2`.`Room_Images` (`room` ASC, `home` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `hivecampus2`.`Contract`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hivecampus2`.`Contract` (
  `idContract` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NOT NULL DEFAULT 'pdf',
  `data` MEDIUMBLOB NOT NULL,
  `active` TINYINT NOT NULL DEFAULT 0,
  `room` INT NOT NULL,
  `home` INT NOT NULL,
  `tenant` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idContract`),
  CONSTRAINT `fk_Contract_Room1`
    FOREIGN KEY (`room` , `home`)
    REFERENCES `hivecampus2`.`Room` (`idRoom` , `home`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Contract_Account1`
    FOREIGN KEY (`tenant`)
    REFERENCES `hivecampus2`.`Account` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Contract_Room1_idx` ON `hivecampus2`.`Contract` (`room` ASC, `home` ASC) VISIBLE;

CREATE INDEX `fk_Contract_Account1_idx` ON `hivecampus2`.`Contract` (`tenant` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `hivecampus2`.`Installment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hivecampus2`.`Installment` (
  `idInstallment` INT NOT NULL AUTO_INCREMENT,
  `status` TINYINT NOT NULL DEFAULT 0,
  `date` DATE NOT NULL,
  `contract` INT NOT NULL,
  PRIMARY KEY (`idInstallment`),
  CONSTRAINT `fk_Installment_Contract1`
    FOREIGN KEY (`contract`)
    REFERENCES `hivecampus2`.`Contract` (`idContract`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Installment_Contract1_idx` ON `hivecampus2`.`Installment` (`contract` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `hivecampus2`.`Ad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hivecampus2`.`Ad` (
  `idAd` INT NOT NULL AUTO_INCREMENT,
  `price` INT NOT NULL,
  `availability` ENUM('available', 'processing', 'rented', 'leased', 'unavailable') NOT NULL DEFAULT 'available',
  `monthAvailability` ENUM('1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12') NOT NULL,
  `room` INT NOT NULL,
  `home` INT NOT NULL,
  PRIMARY KEY (`idAd`),
  CONSTRAINT `fk_Ad_Room1`
    FOREIGN KEY (`room` , `home`)
    REFERENCES `hivecampus2`.`Room` (`idRoom` , `home`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Ad_Room1_idx` ON `hivecampus2`.`Ad` (`room` ASC, `home` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `hivecampus2`.`Lease_Request`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hivecampus2`.`Lease_Request` (
  `idRequest` INT NOT NULL AUTO_INCREMENT,
  `ad` INT NOT NULL,
  `tenant` VARCHAR(45) NOT NULL,
  `requestStatus` ENUM('1', '2', '3') NOT NULL,
  `startPermanence` ENUM('1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12') NOT NULL,
  `typePermanence` ENUM('6', '12', '24', '36') NOT NULL,
  `message` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`idRequest`),
  CONSTRAINT `fk_Lease_Request_Ad1`
    FOREIGN KEY (`ad`)
    REFERENCES `hivecampus2`.`Ad` (`idAd`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Lease_Request_Account1`
    FOREIGN KEY (`tenant`)
    REFERENCES `hivecampus2`.`Account` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Lease_Request_Ad1_idx` ON `hivecampus2`.`Lease_Request` (`ad` ASC) VISIBLE;

CREATE INDEX `fk_Lease_Request_Account1_idx` ON `hivecampus2`.`Lease_Request` (`tenant` ASC) VISIBLE;

USE `hivecampus2` ;

-- -----------------------------------------------------
-- procedure deleteLeaseRequest
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteLeaseRequest`(IN var_request INT)
BEGIN
	DELETE FROM lease_request WHERE idRequest = var_request;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure getRoomsAlreadyPresent
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getRoomsAlreadyPresent`(IN var_home INT, OUT roomCount INT)
BEGIN
    SELECT COUNT(*) INTO roomCount
    FROM room
    WHERE home = var_home;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insertAccount
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertAccount`(
	IN var_email VARCHAR(45),   
    IN var_name VARCHAR(45),
    IN var_surname VARCHAR(45),
    IN var_telephone VARCHAR(20)
    )
BEGIN
    INSERT INTO account(email, name, surname, telephone) VALUES (var_email, var_name, var_surname, var_telephone);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insertUser
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertUser`(IN var_email VARCHAR(45), IN var_password VARCHAR(45), IN var_role VARCHAR(45))
BEGIN
	INSERT INTO users(email, password, role) VALUES (var_email, var_password, var_role);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure isHomeAlreadyExists
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `isHomeAlreadyExists`(
	IN var_address VARCHAR(255),
    IN var_type VARCHAR(50),
    IN var_surface DOUBLE,
    IN var_nRooms INT,
    IN var_nBathrooms INT,
    IN var_floor INT,
    IN var_elevator INT,
    OUT var_id INT)
BEGIN

    SELECT idHome INTO var_id
    FROM home
    WHERE address = var_address
      AND homeType = var_type
      AND homeSurface = var_surface
      AND numRooms = var_nRooms
      AND numBathrooms = var_nBathrooms
      AND floor = var_floor
      AND elevator = var_elevator
    LIMIT 1;
    
    IF var_id IS NULL THEN
        SET var_id = -1;
    END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure login
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `login`(IN var_Email VARCHAR(45), IN var_Password VARCHAR(45), OUT var_Role INT)
BEGIN
    DECLARE var_user_role ENUM('owner', 'tenant');
    SELECT `role` FROM `Users`
    WHERE `email` = var_Email AND `password` = md5(var_Password)
    INTO var_user_role;

    -- See the corresponding enum in the client
    IF var_user_role = 'owner' THEN SET var_Role = 1;
    ELSEIF var_user_role = 'tenant' THEN SET var_Role = 2;
    ELSE SET var_Role = 3;
    END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure publishAd
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `publishAd`(IN var_home INT, IN var_room INT, IN var_status VARCHAR(45), IN var_month INT, IN var_price INT)
BEGIN
	INSERT INTO ad (price, availability, monthAvailability, room, home) VALUES (var_price, var_status, var_month, var_room, var_home);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure retrieveAccountInfoByEmail
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `retrieveAccountInfoByEmail`(IN var_email VARCHAR(45))
BEGIN
	SELECT *
    FROM account
    WHERE email = var_email;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure retrieveAdsByFilters
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `retrieveAdsByFilters`(IN var_home INT, IN var_room INT, IN var_status VARCHAR(25))
BEGIN
	SELECT A.*, H.owner
    FROM ad AS A
    JOIN home AS H ON A.home = H.idHome
    WHERE A.home = var_home AND A.room = var_room AND A.availability = var_status;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure retrieveHomeImage
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `retrieveHomeImage`(IN var_home INT)
BEGIN
	SELECT *
    FROM home_images
    WHERE home = var_home;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure retrieveHomes
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `retrieveHomes`()
BEGIN 
		SELECT 
			idHome, 
			address, 
			ST_Y(coordinates) AS latitude,
			ST_X(coordinates) AS longitude,
			homeType, 
			homeSurface, 
			numRooms, 
			numBathrooms, 
			floor, 
			elevator, 
			homeDescription
		FROM home;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure retrieveHomesByDistance
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `retrieveHomesByDistance`(
    IN uniLatitude DOUBLE, 
    IN uniLongitude DOUBLE, 
    IN maxDistance DOUBLE)
BEGIN
    DECLARE R DOUBLE DEFAULT 6371; -- Raggio della Terra in chilometri

    SELECT 
        idHome, 
        address, 
        ST_Y(coordinates) AS latitude,
        ST_X(coordinates) AS longitude,
        homeType, 
        homeSurface, 
        numRooms, 
        numBathrooms, 
        floor, 
        elevator, 
        homeDescription,
        (
            R * 2 * ATAN2(
                SQRT(
                    SIN(RADIANS(ST_Y(coordinates) - uniLatitude) / 2) * SIN(RADIANS(ST_Y(coordinates) - uniLatitude) / 2) +
                    COS(RADIANS(uniLatitude)) * COS(RADIANS(ST_Y(coordinates))) *
                    SIN(RADIANS(ST_X(coordinates) - uniLongitude) / 2) * SIN(RADIANS(ST_X(coordinates) - uniLongitude) / 2)
                ),
                SQRT(1 - (
                    SIN(RADIANS(ST_Y(coordinates) - uniLatitude) / 2) * SIN(RADIANS(ST_Y(coordinates) - uniLatitude) / 2) +
                    COS(RADIANS(uniLatitude)) * COS(RADIANS(ST_Y(coordinates))) *
                    SIN(RADIANS(ST_X(coordinates) - uniLongitude) / 2) * SIN(RADIANS(ST_X(coordinates) - uniLongitude) / 2)
                ))
            )
        ) AS distance
    FROM home
    HAVING distance <= maxDistance;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure retrieveHomesByOwner
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `retrieveHomesByOwner`(IN var_email VARCHAR(45))
BEGIN
    SELECT 
        idHome, 
        address, 
        ST_X(coordinates) AS longitude, 
        ST_Y(coordinates) AS latitude, 
        homeType, 
        homeSurface, 
        numRooms, 
        numBathrooms, 
        floor, 
        elevator, 
        homeDescription
    FROM 
        home
    WHERE 
        owner = var_email;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure retrieveLeaseRequestsByTenant
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `retrieveLeaseRequestsByTenant`(IN var_tenant VARCHAR(45))
BEGIN
	SELECT *
    FROM lease_request
    WHERE tenant = var_tenant;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure retrieveRoomImage
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `retrieveRoomImage`(IN var_room INT, IN var_home INT)
BEGIN
	SELECT *
    FROM room_images
    WHERE room = var_room AND home = var_home;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure retrieveRoomsByFilters
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `retrieveRoomsByFilters`(
    IN var_home INT, 
    IN var_bath BOOLEAN, 
    IN var_balcony BOOLEAN, 
    IN var_conditioner BOOLEAN, 
    IN var_tv BOOLEAN
)
BEGIN
    -- Costruisce dinamicamente la query in base ai filtri forniti
    SET @query = 'SELECT * FROM room WHERE home = ?';

    -- Aggiunge i filtri dinamicamente solo se sono impostati a true
    IF var_bath THEN
        SET @query = CONCAT(@query, ' AND privateBath = TRUE');
    END IF;
    IF var_balcony THEN
        SET @query = CONCAT(@query, ' AND balcony = TRUE');
    END IF;
    IF var_conditioner THEN
        SET @query = CONCAT(@query, ' AND conditioner = TRUE');
    END IF;
    IF var_tv THEN
        SET @query = CONCAT(@query, ' AND tv = TRUE');
    END IF;
    
    -- Prepara e esegue la query
    PREPARE stmt FROM @query;
    SET @home = var_home;
    EXECUTE stmt USING @home;
    DEALLOCATE PREPARE stmt;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure retrieveUniversityCoordinates
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `retrieveUniversityCoordinates`(IN var_name VARCHAR(45))
BEGIN
	SELECT 
		ST_Y(uniCoordinates) AS latitude,
		ST_X(uniCoordinates) AS longitude
	FROM university
    WHERE name = var_name;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure retrieveUserByCredentials
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `retrieveUserByCredentials`(IN var_email VARCHAR(45), IN var_password VARCHAR(45))
BEGIN
	SELECT *
	FROM users
    WHERE email = var_email AND password = var_password;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure retrieveUserByEmail
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `retrieveUserByEmail`(IN var_email VARCHAR(45))
BEGIN
	SELECT *
	FROM users
    WHERE email = var_email;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure saveHome
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `saveHome`( 
    IN var_address VARCHAR(45),
    IN var_latitude DOUBLE,
    IN var_longitude DOUBLE,
    IN var_type VARCHAR(45),
    IN var_surface DOUBLE,
    IN var_nRooms INT,
    IN var_nBathrooms INT,
    IN var_floor INT,
    IN var_elevator INT,
    IN var_description TEXT,
    IN var_ownerEmail VARCHAR(45),
    OUT var_id INT
)
BEGIN
    DECLARE lastId INT;

    -- Ottieni l'ultimo ID dalla tabella
    SELECT COALESCE(MAX(idHome), 0) INTO lastId FROM hivecampus2.home;

    -- Inserisci la nuova casa
    INSERT INTO home (
        idHome, owner, coordinates, address, homeType, homeSurface, numRooms, numBathrooms, floor, elevator, homeDescription
    ) VALUES (
        lastId + 1, var_ownerEmail, ST_GeomFromText(CONCAT('POINT(', var_longitude, ' ', var_latitude, ')')),
        var_address, var_type, var_surface, var_nRooms, var_nBathrooms, var_floor, var_elevator, var_description
    );

    -- Restituisci il nuovo ID della casa
    SET var_id = lastId + 1;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure saveHomeImage
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `saveHomeImage`(IN var_name VARCHAR(45), IN var_type VARCHAR(10), IN var_image MEDIUMBLOB, IN var_home INT)
BEGIN
	INSERT INTO home_images (name, type, image, home) VALUES (var_name, var_type, var_image, var_home);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure saveLeaseRequest
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `saveLeaseRequest`(IN var_ad INT, IN var_tenant VARCHAR(45), IN var_status INT, IN var_start INT, IN var_type INT, IN var_message MEDIUMTEXT)
BEGIN
	DECLARE enumValue INT;
	
    -- Mappatura dell'indice ENUM al valore corretto
    CASE var_type
        WHEN 6 THEN SET enumValue = 1;
        WHEN 12 THEN SET enumValue = 2;
        WHEN 24 THEN SET enumValue = 3;
        WHEN 36 THEN SET enumValue = 4;
        ELSE
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Invalid type permanence value.';
    END CASE;
	
	INSERT INTO lease_request (ad, tenant, requestStatus, startPermanence, typePermanence, message) 
    VALUES (var_ad, var_tenant, var_status, var_start, enumValue, var_message);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure saveRoom
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `saveRoom`( 
    IN var_homeID INT,
    IN var_surface INT,
    IN var_type VARCHAR(45),
    IN var_bathroom TINYINT,
    IN var_balcony TINYINT,
    IN var_conditioner TINYINT,
    IN var_tv TINYINT,
    IN var_description MEDIUMTEXT,
    OUT var_idRoom INT
)
BEGIN
    DECLARE lastIdRoom INT;

    -- Ottieni l'ultimo ID della stanza per la casa specificata
    SELECT COALESCE(MAX(idRoom), 0) INTO lastIdRoom
    FROM room
    WHERE home = var_homeID;

    -- Inserisci la nuova stanza
    INSERT INTO hivecampus2.room (
        idRoom, home, roomSurface, roomType, privateBath, balcony, conditioner, tv, roomDescription
    ) VALUES (
        lastIdRoom + 1, var_homeID, var_surface, var_type, var_bathroom, var_balcony, var_conditioner, var_tv, var_description
    );

    -- Restituisci il nuovo ID della stanza
    SET var_idRoom = lastIdRoom + 1;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure saveRoomImage
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `saveRoomImage`(IN var_name VARCHAR(45), IN var_type VARCHAR(10), IN var_image MEDIUMBLOB, IN var_room INT, IN var_home INT)
BEGIN
	INSERT INTO room_images (name, type, image, room, home) VALUES (var_name, var_type, var_image, var_room, var_home);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure signed_contract
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `signed_contract`(IN idContract INT)
BEGIN
	-- Cerca le richieste corrispondenti
    UPDATE Richieste
    SET disponibile = FALSE
    WHERE room = contratto_room;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure validRequest
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `validRequest`(IN var_email VARCHAR(45), IN var_ad INT, OUT requestExists BOOLEAN)
BEGIN
   SELECT EXISTS (
		SELECT ad, tenant
        FROM lease_request
        WHERE ad = var_ad AND tenant = var_email)
	INTO requestExists;    
END$$

DELIMITER ;
USE `hivecampus2`;

DELIMITER $$
USE `hivecampus2`$$
CREATE DEFINER = CURRENT_USER TRIGGER `hivecampus2`.`Account_BEFORE_DELETE` BEFORE DELETE ON `Account` FOR EACH ROW
BEGIN

END
$$

USE `hivecampus2`$$
CREATE DEFINER = CURRENT_USER TRIGGER `hivecampus2`.`Home_BEFORE_DELETE` BEFORE DELETE ON `Home` FOR EACH ROW
BEGIN

END
$$

USE `hivecampus2`$$
CREATE DEFINER = CURRENT_USER TRIGGER `hivecampus2`.`Room_BEFORE_INSERT` BEFORE INSERT ON `Room` FOR EACH ROW
BEGIN
	DECLARE max_idRoom INT;
    -- Verifica se esiste già una stanza per la stessa idHome
    SELECT MAX(idRoom) INTO max_idRoom FROM Room WHERE home = NEW.home;
    -- Se non esiste, assegna 1 come idRoom
    IF max_idRoom IS NULL THEN
        SET NEW.idRoom = 1;
    ELSE
        -- Altrimenti, assegna il prossimo idRoom incrementando di 1
        SET NEW.idRoom = max_idRoom + 1;
    END IF;
END$$


DELIMITER ;
CREATE USER 'login' IDENTIFIED BY 'login';

GRANT EXECUTE ON procedure `hivecampus2`.`login` TO 'login';
CREATE USER 'owner' IDENTIFIED BY 'owner';

CREATE USER 'tenant' IDENTIFIED BY 'tenant';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
