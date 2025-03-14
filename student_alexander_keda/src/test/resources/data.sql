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