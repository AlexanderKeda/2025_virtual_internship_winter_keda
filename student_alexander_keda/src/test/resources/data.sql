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