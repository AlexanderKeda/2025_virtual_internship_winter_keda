
CREATE TABLE classifiers (
  id BIGINT NOT NULL AUTO_INCREMENT,
  title VARCHAR(200) NOT NULL,
  description VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
);

CREATE UNIQUE INDEX ix_classifiers_title ON classifiers(title);

CREATE TABLE classifier_values (
  id BIGINT NOT NULL AUTO_INCREMENT,
  classifier_id BIGINT NOT NULL,
  ic VARCHAR(200) NOT NULL,
  description VARCHAR(500) NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE classifier_values
ADD FOREIGN KEY (classifier_id) REFERENCES classifiers(id);

CREATE UNIQUE INDEX ix_classifier_values_ic
ON classifier_values(ic);

CREATE TABLE country_default_day_rate (
	id BIGINT NOT NULL AUTO_INCREMENT,
    country_ic VARCHAR(200) UNIQUE NOT NULL,
    default_day_rate NUMERIC(10,2) NOT NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX ix_country_default_day_rate_country_ic
ON country_default_day_rate (country_ic);
