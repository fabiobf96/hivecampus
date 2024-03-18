-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema hivecampus_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema hivecampus_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hivecampus_db` DEFAULT CHARACTER SET utf8 ;
USE `hivecampus_db` ;

-- -----------------------------------------------------
-- Table `hivecampus_db`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hivecampus_db`.`User` (
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `role` ENUM('owner', 'tenant') NOT NULL,
  PRIMARY KEY (`email`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hivecampus_db`.`Account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hivecampus_db`.`Account` (
  `email` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `telephone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`email`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;

USE `hivecampus_db` ;

-- -----------------------------------------------------
-- procedure signUp
-- -----------------------------------------------------

DELIMITER $$
USE `hivecampus_db`$$
CREATE PROCEDURE `signUp` (
	IN p_email VARCHAR(255),
    IN var_password VARCHAR(255),
    IN var_role VARCHAR(50),
    IN var_name VARCHAR(100),
    IN var_surname VARCHAR(100),
    IN var_telephone VARCHAR(20)
    )
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'An error occurred, transaction rolled back';
    END;

    START TRANSACTION;

    INSERT INTO hivecampus_db.user(email, password, role) VALUES (var_email, var_password, var_role);
    INSERT INTO hivecampus_db.account(email, name, surname, telephone) VALUES (var_email, var_name, var_surname, var_telephone);

    COMMIT;
END$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
