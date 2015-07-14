SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `mycluby-schema` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`USER` (
  `ID_USER` INT(11) NOT NULL AUTO_INCREMENT,
  `ID_ACCOUNT_INFO` INT(11) NOT NULL,
  `FIRSTNAME` VARCHAR(45) NOT NULL,
  `LASTNAME` VARCHAR(45) NOT NULL,
  `PASSWORD` TEXT NOT NULL,
  `BIRTHDATE` DATE NOT NULL,
  `GENDER` VARCHAR(1) NULL DEFAULT NULL,
  `COUNTRY` VARCHAR(45) NULL DEFAULT NULL,
  `STATE` VARCHAR(45) NULL DEFAULT NULL,
  `CITY` VARCHAR(45) NULL DEFAULT NULL,
  `ADDRESS` VARCHAR(45) NULL DEFAULT NULL,
  `COMPLEMENT` VARCHAR(45) NULL DEFAULT NULL,
  `RES_PHONE` VARCHAR(15) NULL DEFAULT NULL,
  `CELL_PHONE` VARCHAR(15) NULL DEFAULT NULL,
  `JOB_PHONE` VARCHAR(15) NULL DEFAULT NULL,
  `PROFILE_IMAGE` LONGBLOB NULL DEFAULT NULL,
  `ABOUT` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`ID_USER`),
  INDEX `fk_USER_ACCOUNT_INFO1_idx` (`ID_ACCOUNT_INFO` ASC),
  CONSTRAINT `fk_USER_ACCOUNT_INFO1`
    FOREIGN KEY (`ID_ACCOUNT_INFO`)
    REFERENCES `mycluby-schema`.`INFO` (`ID_ACCOUNT_INFO`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`INFO` (
  `ID_ACCOUNT_INFO` INT(11) NOT NULL AUTO_INCREMENT,
  `CREATION` DATETIME NOT NULL,
  `ACTIVE` TINYINT(1) NOT NULL,
  `ACTIVE_EMAIL_RECEIVER` TINYINT(1) NOT NULL,
  `LANGUAGE` VARCHAR(5) NOT NULL,
  `USER_KEY` TEXT NOT NULL,
  `FIRST_STEP` TINYINT(1) NOT NULL,
  `TIME_ZONE` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID_ACCOUNT_INFO`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`MAIL` (
  `ID_MAIL` INT(11) NOT NULL AUTO_INCREMENT,
  `VALUE` VARCHAR(45) NOT NULL,
  `ACTIVE_LOGIN` TINYINT(1) NOT NULL,
  PRIMARY KEY (`ID_MAIL`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`ROLE` (
  `ID_ROLE` INT(11) NOT NULL AUTO_INCREMENT,
  `TITLE` VARCHAR(45) NOT NULL,
  `DESCRIPTION` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`ID_ROLE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`USER_ROLE` (
  `ID_ROLE` INT(11) NOT NULL,
  `ID_USER` INT(11) NOT NULL,
  INDEX `fk_ROLE_has_USER_USER1_idx` (`ID_USER` ASC),
  INDEX `fk_ROLE_has_USER_ROLE1_idx` (`ID_ROLE` ASC),
  CONSTRAINT `fk_ROLE_has_USER_ROLE1`
    FOREIGN KEY (`ID_ROLE`)
    REFERENCES `mycluby-schema`.`ROLE` (`ID_ROLE`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ROLE_has_USER_USER1`
    FOREIGN KEY (`ID_USER`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`USER_FRIENDS` (
  `ID_PRIMARY` INT(11) NOT NULL,
  `ID_SECONDARY` INT(11) NOT NULL,
  `FRIENDSHIP_CONFIRM` TINYINT(1) NULL DEFAULT NULL,
  `BLOCK_FRIENDSHIP` TINYINT(1) NULL DEFAULT NULL,
  INDEX `fk_USER_has_USER_USER2_idx` (`ID_SECONDARY` ASC),
  INDEX `fk_USER_has_USER_USER1_idx` (`ID_PRIMARY` ASC),
  CONSTRAINT `fk_USER_has_USER_USER1`
    FOREIGN KEY (`ID_PRIMARY`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_has_USER_USER2`
    FOREIGN KEY (`ID_SECONDARY`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`NOTIFICATION` (
  `ID_NOTIFICATION` INT(11) NOT NULL AUTO_INCREMENT,
  `ID_USER` INT(11) NOT NULL,
  `TYPE` VARCHAR(45) NOT NULL,
  `ACTION` VARCHAR(45) NOT NULL,
  `DESCRIPTION` TEXT NULL DEFAULT NULL,
  `NEW` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_NOTIFICATION`),
  INDEX `fk_EVENTS_USER1_idx` (`ID_USER` ASC),
  CONSTRAINT `fk_EVENTS_USER1`
    FOREIGN KEY (`ID_USER`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`CONTACT_US` (
  `ID_CONTACT_US` INT(11) NOT NULL AUTO_INCREMENT,
  `SUBJECT` VARCHAR(45) NOT NULL,
  `TEXT` TEXT NOT NULL,
  `ID_USER` INT(11) NOT NULL,
  PRIMARY KEY (`ID_CONTACT_US`),
  UNIQUE INDEX `ID_CONTACT_US_UNIQUE` (`ID_CONTACT_US` ASC),
  INDEX `fk_CONTACT_US_USER1_idx` (`ID_USER` ASC),
  CONSTRAINT `fk_CONTACT_US_USER1`
    FOREIGN KEY (`ID_USER`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`PICTURE` (
  `ID_PICTURES` INT(11) NOT NULL AUTO_INCREMENT,
  `PICTURE` LONGBLOB NOT NULL,
  `DESCRIPTION` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`ID_PICTURES`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`GROUP` (
  `ID_GROUP` INT(11) NOT NULL AUTO_INCREMENT,
  `NAME_GROUP` VARCHAR(45) NOT NULL,
  `OWNER` INT(11) NOT NULL,
  PRIMARY KEY (`ID_GROUP`),
  INDEX `fk_GROUP_USER1_idx` (`OWNER` ASC),
  CONSTRAINT `fk_GROUP_USER1`
    FOREIGN KEY (`OWNER`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`MESSAGES` (
  `ID_MESSAGES` INT(11) NOT NULL AUTO_INCREMENT,
  `MESSAGE` TEXT NOT NULL,
  `SEND_DATE` DATETIME NOT NULL,
  PRIMARY KEY (`ID_MESSAGES`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`EVENT` (
  `ID_EVENT` INT(11) NOT NULL AUTO_INCREMENT,
  `EVENT_NAME` VARCHAR(45) NOT NULL,
  `DESCRIPTION` TEXT NULL DEFAULT NULL,
  `IMAGE_EVENT` LONGBLOB NULL DEFAULT NULL,
  `DATE_EVENT` DATETIME NOT NULL,
  `DATE_CREATE` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`ID_EVENT`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`CARD` (
  `ID_REGISTER_CARD` INT(11) NOT NULL AUTO_INCREMENT,
  `NAME_TYPED` VARCHAR(45) NOT NULL,
  `NUMBER_CARD` MEDIUMTEXT NOT NULL,
  `VALIDATOR_CARD` INT(11) NOT NULL,
  `VALIDATE_DATE` DATE NOT NULL,
  PRIMARY KEY (`ID_REGISTER_CARD`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`ESTABLISHMENT` (
  `ID_ESTABLISHMENT` INT(11) NOT NULL AUTO_INCREMENT,
  `ESTABLISHMENT_NAME` VARCHAR(45) NOT NULL,
  `PASSWORD` VARCHAR(45) NOT NULL,
  `BIRTH_DATE` DATE NULL DEFAULT NULL,
  `COUNTRY` VARCHAR(45) NULL DEFAULT NULL,
  `STATE` VARCHAR(30) NULL DEFAULT NULL,
  `CITY` VARCHAR(45) NULL DEFAULT NULL,
  `ADDRESS` VARCHAR(45) NULL DEFAULT NULL,
  `PHONE` VARCHAR(15) NULL DEFAULT NULL,
  `IMAGE` LONGBLOB NULL DEFAULT NULL,
  `ABOUT` TEXT NULL DEFAULT NULL,
  `ID_ESTABLISHMENT_INFO` INT(11) NOT NULL,
  PRIMARY KEY (`ID_ESTABLISHMENT`),
  INDEX `fk_ESTABLISHMENT_ESTABLISHMENT_INFO1_idx` (`ID_ESTABLISHMENT_INFO` ASC),
  UNIQUE INDEX `COUNTRY_UNIQUE` (`COUNTRY` ASC),
  CONSTRAINT `fk_ESTABLISHMENT_ESTABLISHMENT_INFO1`
    FOREIGN KEY (`ID_ESTABLISHMENT_INFO`)
    REFERENCES `mycluby-schema`.`ESTABLISHMENT_INFO` (`ID_ESTABLISHMENT_INFO`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`EMPLOYEE` (
  `ID_EMPLOYEE` INT(11) NOT NULL AUTO_INCREMENT,
  `NAME_EMPLOYEE` VARCHAR(45) NOT NULL,
  `LOGIN` VARCHAR(45) NOT NULL,
  `PASSWORD` VARCHAR(45) NOT NULL,
  `ID_ESTABLISHMENT` INT(11) NOT NULL,
  PRIMARY KEY (`ID_EMPLOYEE`),
  INDEX `fk_EMPLOYEE_ESTABLISHMENT1_idx` (`ID_ESTABLISHMENT` ASC),
  CONSTRAINT `fk_EMPLOYEE_ESTABLISHMENT1`
    FOREIGN KEY (`ID_ESTABLISHMENT`)
    REFERENCES `mycluby-schema`.`ESTABLISHMENT` (`ID_ESTABLISHMENT`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`CATEGORIES` (
  `ID_CATEGORIES` INT(11) NOT NULL AUTO_INCREMENT,
  `NAME_CATEGORIE` VARCHAR(45) NULL DEFAULT NULL,
  `TYPE_CATEGORIE` VARCHAR(30) NULL DEFAULT NULL,
  `ID_ESTABLISHMENT` INT(11) NOT NULL,
  PRIMARY KEY (`ID_CATEGORIES`),
  INDEX `fk_CATEGORIES_ESTABLISHMENT1_idx` (`ID_ESTABLISHMENT` ASC),
  CONSTRAINT `fk_CATEGORIES_ESTABLISHMENT1`
    FOREIGN KEY (`ID_ESTABLISHMENT`)
    REFERENCES `mycluby-schema`.`ESTABLISHMENT` (`ID_ESTABLISHMENT`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`PRODUCTS` (
  `ID_PRODUCTS` INT(11) NOT NULL AUTO_INCREMENT,
  `NAME_PRODUCTS` VARCHAR(45) NOT NULL,
  `PRICE` DOUBLE NOT NULL,
  `PROMOTIONAL_PRICE` DOUBLE NULL DEFAULT NULL,
  `ID_CATEGORIES` INT(11) NOT NULL,
  PRIMARY KEY (`ID_PRODUCTS`),
  INDEX `fk_PRODUCTS_CATEGORIES1_idx` (`ID_CATEGORIES` ASC),
  CONSTRAINT `fk_PRODUCTS_CATEGORIES1`
    FOREIGN KEY (`ID_CATEGORIES`)
    REFERENCES `mycluby-schema`.`CATEGORIES` (`ID_CATEGORIES`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`CONSUMMATION` (
  `ID_CONSUMMATION` INT(11) NOT NULL AUTO_INCREMENT,
  `ITEM` VARCHAR(45) NULL DEFAULT NULL,
  `VALOR` DOUBLE NULL DEFAULT NULL,
  `TOTAL` DOUBLE NULL DEFAULT NULL,
  `ID_USER` INT(11) NOT NULL,
  `ID_PRODUCTS` INT(11) NOT NULL,
  PRIMARY KEY (`ID_CONSUMMATION`),
  INDEX `fk_CONSUMMATION_USER1_idx` (`ID_USER` ASC),
  INDEX `fk_CONSUMMATION_PRODUCTS1_idx` (`ID_PRODUCTS` ASC),
  CONSTRAINT `fk_CONSUMMATION_USER1`
    FOREIGN KEY (`ID_USER`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CONSUMMATION_PRODUCTS1`
    FOREIGN KEY (`ID_PRODUCTS`)
    REFERENCES `mycluby-schema`.`PRODUCTS` (`ID_PRODUCTS`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`USER_CARD` (
  `ID_USER` INT(11) NOT NULL,
  `ID_REGISTER_CARD` INT(11) NOT NULL,
  INDEX `fk_USER_has_CARD_CARD1_idx` (`ID_REGISTER_CARD` ASC),
  INDEX `fk_USER_has_CARD_USER1_idx` (`ID_USER` ASC),
  CONSTRAINT `fk_USER_has_CARD_USER1`
    FOREIGN KEY (`ID_USER`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_has_CARD_CARD1`
    FOREIGN KEY (`ID_REGISTER_CARD`)
    REFERENCES `mycluby-schema`.`CARD` (`ID_REGISTER_CARD`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`USER_ESTABLISHMENT` (
  `ID_USER` INT(11) NOT NULL,
  `ID_ESTABLISHMENT` INT(11) NOT NULL,
  `JOIN` TINYINT(1) NULL DEFAULT NULL,
  `FAVORITE` TINYINT(1) NULL DEFAULT NULL,
  `COMMENT` TEXT NULL DEFAULT NULL,
  INDEX `fk_USER_has_ESTABLISHMENT_ESTABLISHMENT1_idx` (`ID_ESTABLISHMENT` ASC),
  INDEX `fk_USER_has_ESTABLISHMENT_USER1_idx` (`ID_USER` ASC),
  CONSTRAINT `fk_USER_has_ESTABLISHMENT_USER1`
    FOREIGN KEY (`ID_USER`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_has_ESTABLISHMENT_ESTABLISHMENT1`
    FOREIGN KEY (`ID_ESTABLISHMENT`)
    REFERENCES `mycluby-schema`.`ESTABLISHMENT` (`ID_ESTABLISHMENT`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`USER_PICTURE` (
  `ID_USER` INT(11) NOT NULL,
  `ID_PICTURES` INT(11) NOT NULL,
  INDEX `fk_USER_has_PICTURE_PICTURE1_idx` (`ID_PICTURES` ASC),
  INDEX `fk_USER_has_PICTURE_USER1_idx` (`ID_USER` ASC),
  CONSTRAINT `fk_USER_has_PICTURE_USER1`
    FOREIGN KEY (`ID_USER`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_has_PICTURE_PICTURE1`
    FOREIGN KEY (`ID_PICTURES`)
    REFERENCES `mycluby-schema`.`PICTURE` (`ID_PICTURES`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`LIKE` (
  `ID_LIKE` INT(11) NOT NULL AUTO_INCREMENT,
  `STATUS` TINYINT(1) NULL DEFAULT NULL,
  `STATUS_DATE` DATETIME NULL DEFAULT NULL,
  `ID_USER` INT(11) NOT NULL,
  PRIMARY KEY (`ID_LIKE`),
  INDEX `fk_LIKE_USER1_idx` (`ID_USER` ASC),
  CONSTRAINT `fk_LIKE_USER1`
    FOREIGN KEY (`ID_USER`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`LIKE_PICTURE` (
  `ID_LIKE` INT(11) NOT NULL,
  `ID_PICTURES` INT(11) NOT NULL,
  INDEX `fk_LIKE_has_PICTURE_PICTURE1_idx` (`ID_PICTURES` ASC),
  INDEX `fk_LIKE_has_PICTURE_LIKE1_idx` (`ID_LIKE` ASC),
  CONSTRAINT `fk_LIKE_has_PICTURE_LIKE1`
    FOREIGN KEY (`ID_LIKE`)
    REFERENCES `mycluby-schema`.`LIKE` (`ID_LIKE`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_LIKE_has_PICTURE_PICTURE1`
    FOREIGN KEY (`ID_PICTURES`)
    REFERENCES `mycluby-schema`.`PICTURE` (`ID_PICTURES`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`COMMENT` (
  `ID_COMMENT` INT(11) NOT NULL AUTO_INCREMENT,
  `COMMENT` TEXT NOT NULL,
  `DATE_COMMENT` DATETIME NULL DEFAULT NULL,
  `ID_USER` INT(11) NOT NULL,
  PRIMARY KEY (`ID_COMMENT`),
  INDEX `fk_COMMENT_USER1_idx` (`ID_USER` ASC),
  CONSTRAINT `fk_COMMENT_USER1`
    FOREIGN KEY (`ID_USER`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`PICTURE_COMMENT` (
  `ID_PICTURES` INT(11) NOT NULL,
  `ID_COMMENT` INT(11) NOT NULL,
  INDEX `fk_PICTURE_has_COMMENT_COMMENT1_idx` (`ID_COMMENT` ASC),
  INDEX `fk_PICTURE_has_COMMENT_PICTURE1_idx` (`ID_PICTURES` ASC),
  CONSTRAINT `fk_PICTURE_has_COMMENT_PICTURE1`
    FOREIGN KEY (`ID_PICTURES`)
    REFERENCES `mycluby-schema`.`PICTURE` (`ID_PICTURES`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PICTURE_has_COMMENT_COMMENT1`
    FOREIGN KEY (`ID_COMMENT`)
    REFERENCES `mycluby-schema`.`COMMENT` (`ID_COMMENT`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`ESTABLISHMENT_INFO` (
  `ID_ESTABLISHMENT_INFO` INT(11) NOT NULL AUTO_INCREMENT,
  `CREATION` DATETIME NOT NULL,
  `ACTIVE` TINYINT(1) NOT NULL,
  `FIRST_STEP` TINYINT(1) NOT NULL,
  `KEY_RECOVERY` TEXT NOT NULL,
  PRIMARY KEY (`ID_ESTABLISHMENT_INFO`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`USER_MAIL` (
  `ID_MAIL` INT(11) NOT NULL,
  `ID_USER` INT(11) NOT NULL,
  INDEX `fk_MAIL_has_USER_USER1_idx` (`ID_USER` ASC),
  INDEX `fk_MAIL_has_USER_MAIL1_idx` (`ID_MAIL` ASC),
  CONSTRAINT `fk_MAIL_has_USER_MAIL1`
    FOREIGN KEY (`ID_MAIL`)
    REFERENCES `mycluby-schema`.`MAIL` (`ID_MAIL`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MAIL_has_USER_USER1`
    FOREIGN KEY (`ID_USER`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`ESTABLISHMENT_MAIL` (
  `ID_MAIL` INT(11) NOT NULL,
  `ID_ESTABLISHMENT` INT(11) NOT NULL,
  INDEX `fk_MAIL_has_ESTABLISHMENT_ESTABLISHMENT1_idx` (`ID_ESTABLISHMENT` ASC),
  INDEX `fk_MAIL_has_ESTABLISHMENT_MAIL1_idx` (`ID_MAIL` ASC),
  CONSTRAINT `fk_MAIL_has_ESTABLISHMENT_MAIL1`
    FOREIGN KEY (`ID_MAIL`)
    REFERENCES `mycluby-schema`.`MAIL` (`ID_MAIL`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MAIL_has_ESTABLISHMENT_ESTABLISHMENT1`
    FOREIGN KEY (`ID_ESTABLISHMENT`)
    REFERENCES `mycluby-schema`.`ESTABLISHMENT` (`ID_ESTABLISHMENT`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`GEOLOCATION` (
  `ID_GEOLOCATION` INT(11) NOT NULL AUTO_INCREMENT,
  `LATITUDE` MEDIUMTEXT NOT NULL,
  `LONGITUDE` MEDIUMTEXT NOT NULL,
  `ID_ESTABLISHMENT` INT(11) NOT NULL,
  PRIMARY KEY (`ID_GEOLOCATION`),
  INDEX `fk_GEOLOCATION_ESTABLISHMENT1_idx` (`ID_ESTABLISHMENT` ASC),
  CONSTRAINT `fk_GEOLOCATION_ESTABLISHMENT1`
    FOREIGN KEY (`ID_ESTABLISHMENT`)
    REFERENCES `mycluby-schema`.`ESTABLISHMENT` (`ID_ESTABLISHMENT`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`ESTABLISHMENT_PICTURE` (
  `ID_ESTABLISHMENT` INT(11) NOT NULL,
  `ID_PICTURES` INT(11) NOT NULL,
  INDEX `fk_ESTABLISHMENT_has_PICTURE_PICTURE1_idx` (`ID_PICTURES` ASC),
  INDEX `fk_ESTABLISHMENT_has_PICTURE_ESTABLISHMENT1_idx` (`ID_ESTABLISHMENT` ASC),
  CONSTRAINT `fk_ESTABLISHMENT_has_PICTURE_ESTABLISHMENT1`
    FOREIGN KEY (`ID_ESTABLISHMENT`)
    REFERENCES `mycluby-schema`.`ESTABLISHMENT` (`ID_ESTABLISHMENT`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ESTABLISHMENT_has_PICTURE_PICTURE1`
    FOREIGN KEY (`ID_PICTURES`)
    REFERENCES `mycluby-schema`.`PICTURE` (`ID_PICTURES`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`ONSITE` (
  `ID_ONSITE` INT(11) NOT NULL AUTO_INCREMENT,
  `CHECKIN` DATETIME NULL DEFAULT NULL,
  `CHECKOUT` DATETIME NULL DEFAULT NULL,
  `VISIBLE` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_ONSITE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`ESTABLISHMENT_ONSITE` (
  `ID_ESTABLISHMENT` INT(11) NOT NULL,
  `ID_ONSITE` INT(11) NOT NULL,
  INDEX `fk_ESTABLISHMENT_has_ONSITE_ONSITE1_idx` (`ID_ONSITE` ASC),
  INDEX `fk_ESTABLISHMENT_has_ONSITE_ESTABLISHMENT1_idx` (`ID_ESTABLISHMENT` ASC),
  CONSTRAINT `fk_ESTABLISHMENT_has_ONSITE_ESTABLISHMENT1`
    FOREIGN KEY (`ID_ESTABLISHMENT`)
    REFERENCES `mycluby-schema`.`ESTABLISHMENT` (`ID_ESTABLISHMENT`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ESTABLISHMENT_has_ONSITE_ONSITE1`
    FOREIGN KEY (`ID_ONSITE`)
    REFERENCES `mycluby-schema`.`ONSITE` (`ID_ONSITE`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`USER_ONSITE` (
  `ID_ONSITE` INT(11) NOT NULL,
  `ID_USER` INT(11) NOT NULL,
  INDEX `fk_ONSITE_has_USER_USER1_idx` (`ID_USER` ASC),
  INDEX `fk_ONSITE_has_USER_ONSITE1_idx` (`ID_ONSITE` ASC),
  CONSTRAINT `fk_ONSITE_has_USER_ONSITE1`
    FOREIGN KEY (`ID_ONSITE`)
    REFERENCES `mycluby-schema`.`ONSITE` (`ID_ONSITE`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ONSITE_has_USER_USER1`
    FOREIGN KEY (`ID_USER`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`USER_MESSAGES` (
  `ID_USER_PRIMARY` INT(11) NOT NULL,
  `ID_MESSAGES` INT(11) NOT NULL,
  `ID_USER_SECUDARY` INT(11) NOT NULL,
  INDEX `fk_USER_has_MESSAGES_MESSAGES1_idx` (`ID_MESSAGES` ASC),
  INDEX `fk_USER_has_MESSAGES_USER1_idx` (`ID_USER_PRIMARY` ASC),
  INDEX `fk_USER_MESSAGES_USER1_idx` (`ID_USER_SECUDARY` ASC),
  CONSTRAINT `fk_USER_has_MESSAGES_USER1`
    FOREIGN KEY (`ID_USER_PRIMARY`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_has_MESSAGES_MESSAGES1`
    FOREIGN KEY (`ID_MESSAGES`)
    REFERENCES `mycluby-schema`.`MESSAGES` (`ID_MESSAGES`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_MESSAGES_USER1`
    FOREIGN KEY (`ID_USER_SECUDARY`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`USER_GROUP` (
  `ID_USER` INT(11) NOT NULL,
  `ID_GROUP` INT(11) NOT NULL,
  `DATE_JOIN` DATETIME NOT NULL,
  INDEX `fk_USER_has_GROUP_GROUP1_idx` (`ID_GROUP` ASC),
  INDEX `fk_USER_has_GROUP_USER1_idx` (`ID_USER` ASC),
  CONSTRAINT `fk_USER_has_GROUP_USER1`
    FOREIGN KEY (`ID_USER`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_has_GROUP_GROUP1`
    FOREIGN KEY (`ID_GROUP`)
    REFERENCES `mycluby-schema`.`GROUP` (`ID_GROUP`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`GROUP_MESSAGES` (
  `ID_GROUP` INT(11) NOT NULL,
  `ID_MESSAGES` INT(11) NOT NULL,
  `OWNER` INT(11) NOT NULL,
  INDEX `fk_GROUP_has_MESSAGES_MESSAGES1_idx` (`ID_MESSAGES` ASC),
  INDEX `fk_GROUP_has_MESSAGES_GROUP1_idx` (`ID_GROUP` ASC),
  INDEX `fk_GROUP_has_MESSAGES_USER1_idx` (`OWNER` ASC),
  CONSTRAINT `fk_GROUP_has_MESSAGES_GROUP1`
    FOREIGN KEY (`ID_GROUP`)
    REFERENCES `mycluby-schema`.`GROUP` (`ID_GROUP`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_GROUP_has_MESSAGES_MESSAGES1`
    FOREIGN KEY (`ID_MESSAGES`)
    REFERENCES `mycluby-schema`.`MESSAGES` (`ID_MESSAGES`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_GROUP_has_MESSAGES_USER1`
    FOREIGN KEY (`OWNER`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`ESTABLISHMENT_EVENT` (
  `ID_ESTABLISHMENT` INT(11) NOT NULL,
  `ID_EVENT` INT(11) NOT NULL,
  INDEX `fk_ESTABLISHMENT_has_EVENT_EVENT1_idx` (`ID_EVENT` ASC),
  INDEX `fk_ESTABLISHMENT_has_EVENT_ESTABLISHMENT1_idx` (`ID_ESTABLISHMENT` ASC),
  CONSTRAINT `fk_ESTABLISHMENT_has_EVENT_ESTABLISHMENT1`
    FOREIGN KEY (`ID_ESTABLISHMENT`)
    REFERENCES `mycluby-schema`.`ESTABLISHMENT` (`ID_ESTABLISHMENT`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ESTABLISHMENT_has_EVENT_EVENT1`
    FOREIGN KEY (`ID_EVENT`)
    REFERENCES `mycluby-schema`.`EVENT` (`ID_EVENT`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`USER_EVENT` (
  `ID_USER` INT(11) NOT NULL,
  `ID_EVENT` INT(11) NOT NULL,
  `STATUS` VARCHAR(1) NOT NULL,
  `DATE_JOIN` DATETIME NOT NULL,
  INDEX `fk_USER_has_EVENT_EVENT1_idx` (`ID_EVENT` ASC),
  INDEX `fk_USER_has_EVENT_USER1_idx` (`ID_USER` ASC),
  CONSTRAINT `fk_USER_has_EVENT_USER1`
    FOREIGN KEY (`ID_USER`)
    REFERENCES `mycluby-schema`.`USER` (`ID_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_has_EVENT_EVENT1`
    FOREIGN KEY (`ID_EVENT`)
    REFERENCES `mycluby-schema`.`EVENT` (`ID_EVENT`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mycluby-schema`.`ESTABLISHMENT_MAIL` (
  `ESTABLISHMENT_ID_ESTABLISHMENT` INT(11) NOT NULL,
  `MAIL_ID_MAIL` INT(11) NOT NULL,
  PRIMARY KEY (`ESTABLISHMENT_ID_ESTABLISHMENT`, `MAIL_ID_MAIL`),
  INDEX `fk_ESTABLISHMENT_has_MAIL_MAIL1_idx` (`MAIL_ID_MAIL` ASC),
  INDEX `fk_ESTABLISHMENT_has_MAIL_ESTABLISHMENT1_idx` (`ESTABLISHMENT_ID_ESTABLISHMENT` ASC),
  CONSTRAINT `fk_ESTABLISHMENT_has_MAIL_ESTABLISHMENT1`
    FOREIGN KEY (`ESTABLISHMENT_ID_ESTABLISHMENT`)
    REFERENCES `mycluby-schema`.`ESTABLISHMENT` (`ID_ESTABLISHMENT`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ESTABLISHMENT_has_MAIL_MAIL1`
    FOREIGN KEY (`MAIL_ID_MAIL`)
    REFERENCES `mycluby-schema`.`MAIL` (`ID_MAIL`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;