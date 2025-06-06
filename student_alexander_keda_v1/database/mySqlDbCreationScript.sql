SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=1;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=1;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


CREATE SCHEMA IF NOT EXISTS `java-real-practice-insurance` DEFAULT CHARACTER SET utf8 ;
USE `java-real-practice-insurance` ;

CREATE TABLE IF NOT EXISTS `classifiers` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(200) NOT NULL,
  `description` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
AUTO_INCREMENT = 1002;

CREATE UNIQUE INDEX `ix_classifiers_title` ON `classifiers` (`title`);

CREATE TABLE IF NOT EXISTS `classifier_values` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `classifier_id` BIGINT NOT NULL,
  `ic` VARCHAR(200) NOT NULL,
  `description` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
AUTO_INCREMENT = 1002;

ALTER TABLE `classifier_values`
ADD FOREIGN KEY (`classifier_id`) REFERENCES `classifiers`(`id`);

CREATE UNIQUE INDEX `ix_classifier_values_ic`
ON `classifier_values` (`ic`);

CREATE TABLE IF NOT EXISTS `country_default_day_rate` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
    `country_ic` VARCHAR(200) UNIQUE NOT NULL,
    `default_day_rate` DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (`id`)
)
ENGINE = InnoDB
AUTO_INCREMENT = 1002;

ALTER TABLE `country_default_day_rate`
ADD FOREIGN KEY (`country_ic`) REFERENCES `classifier_values`(`ic`);

DELIMITER //
CREATE PROCEDURE `check_valid_country_ic`(IN `country_ic` VARCHAR(200))
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM classifier_values AS cv
        WHERE cv.ic = country_ic
          AND cv.classifier_id = (
              SELECT id
              FROM classifiers
              WHERE title = 'COUNTRY'
          )
    ) THEN
		SET @ERROR_MSG = CONCAT('Error: country_ic=', country_ic,' does not exist');
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = @ERROR_MSG;
    END IF;
END //

CREATE TRIGGER `insert_check_valid_country_ic`
BEFORE INSERT ON `country_default_day_rate`
FOR EACH ROW
BEGIN
	CALL check_valid_country_ic (NEW.country_ic);
END //

CREATE TRIGGER `update_check_valid_country_ic`
BEFORE UPDATE ON `country_default_day_rate`
FOR EACH ROW
BEGIN
	CALL check_valid_country_ic (NEW.country_ic);
END //
DELIMITER ;

CREATE TABLE IF NOT EXISTS `age_coefficient` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
    `age_from` INT NOT NULL,
    `age_to` INT NOT NULL,
    `coefficient` DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id)
)
ENGINE = InnoDB
AUTO_INCREMENT = 1002;

CREATE TABLE IF NOT EXISTS `medical_risk_limit_level` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
    `medical_risk_limit_level_ic` VARCHAR(200) UNIQUE NOT NULL,
    `coefficient` DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (`id`)
)
ENGINE = InnoDB
AUTO_INCREMENT = 1002;

ALTER TABLE `medical_risk_limit_level`
ADD FOREIGN KEY (`medical_risk_limit_level_ic`) REFERENCES `classifier_values`(`ic`);

DELIMITER //
CREATE PROCEDURE `check_valid_medical_risk_limit_level`(IN `risk_limit_ic` VARCHAR(200))
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM classifier_values AS cv
        WHERE cv.ic = risk_limit_ic
          AND cv.classifier_id = (
              SELECT id
              FROM classifiers
              WHERE title = 'MEDICAL_RISK_LIMIT_LEVEL'
          )
    ) THEN
		SET @ERROR_MSG = CONCAT('Error: medical_risk_limit_level=', risk_limit_ic,' does not exist');
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = @ERROR_MSG;
    END IF;
END //

CREATE TRIGGER `insert_check_valid_medical_risk_limit_level_ic`
BEFORE INSERT ON `medical_risk_limit_level`
FOR EACH ROW
BEGIN
	CALL check_valid_medical_risk_limit_level (NEW.medical_risk_limit_level_ic);
END //

CREATE TRIGGER `update_check_valid_medical_risk_limit_level_ic`
BEFORE UPDATE ON `medical_risk_limit_level`
FOR EACH ROW
BEGIN
	CALL check_valid_medical_risk_limit_level (NEW.medical_risk_limit_level_ic);
END //
DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
