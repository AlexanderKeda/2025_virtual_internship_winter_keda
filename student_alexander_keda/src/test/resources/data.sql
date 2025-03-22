INSERT INTO classifiers (title, description)
VALUES ('RISK_TYPE', 'travel policy risk type classifier');

SET @RISK_TYPE_ID = (SELECT id FROM classifiers WHERE title = 'RISK_TYPE');

INSERT INTO classifier_values (classifier_id, ic, description)
VALUES (@RISK_TYPE_ID, 'TRAVEL_MEDICAL', 'travel policy medical risk'),
	(@RISK_TYPE_ID, 'TRAVEL_CANCELLATION', 'travel policy trip cancellation risk'),
	(@RISK_TYPE_ID, 'TRAVEL_LOSS_BAGGAGE', 'travel policy baggage lose risk'),
	(@RISK_TYPE_ID, 'TRAVEL_THIRD_PARTY_LIABILITY', 'travel policy third party liability risk'),
	(@RISK_TYPE_ID, 'TRAVEL_EVACUATION', 'travel policy evacuation risk'),
	(@RISK_TYPE_ID, 'TRAVEL_SPORT_ACTIVITIES', 'travel policy sport activities risk');

INSERT INTO classifiers (title, description)
VALUES ('COUNTRY', 'Country classifier');

SET @COUNTRY_TYPE_ID = (SELECT id FROM classifiers WHERE title = 'COUNTRY');

INSERT INTO classifier_values (classifier_id, ic, description)
VALUES (@COUNTRY_TYPE_ID, 'LATVIA', 'Country Latvia'),
	(@COUNTRY_TYPE_ID, 'SPAIN', 'Country Spain'),
	(@COUNTRY_TYPE_ID, 'JAPAN', 'Country Japan');

INSERT INTO country_default_day_rate (country_ic, default_day_rate)
VALUES ('LATVIA', 1.00),
	('SPAIN', 2.50),
    ('JAPAN', 3.50);

INSERT INTO age_coefficient (age_from, age_to, coefficient)
VALUES (0, 5, 1.10),
	(6, 10, 0.70),
    (11, 17, 1.00),
    (18, 40, 1.10),
    (41, 65, 1.20),
    (66, 150, 1.50);

INSERT INTO classifiers (title, description)
VALUES ('MEDICAL_RISK_LIMIT_LEVEL', 'Medical risk limit level classifier');

SET @LIMIT_TYPE_ID = (SELECT id FROM classifiers WHERE title = 'MEDICAL_RISK_LIMIT_LEVEL');

INSERT INTO classifier_values (classifier_id, ic, description)
VALUES (@LIMIT_TYPE_ID, 'LEVEL_10000', 'medical risk limit level 10000 euro'),
    (@LIMIT_TYPE_ID, 'LEVEL_15000', 'medical risk limit level 15000 euro'),
    (@LIMIT_TYPE_ID, 'LEVEL_20000', 'medical risk limit level 20000 euro'),
    (@LIMIT_TYPE_ID, 'LEVEL_50000', 'medical risk limit level 50000 euro');

INSERT INTO medical_risk_limit_level (medical_risk_limit_ic, coefficient)
VALUES ('LEVEL_10000', 1.00),
	('LEVEL_15000', 1.20),
    ('LEVEL_20000', 1.50),
    ('LEVEL_50000', 2.00);