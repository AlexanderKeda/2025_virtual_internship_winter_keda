package org.javaguru.travel.insurance.core.underwriting;

import org.springframework.stereotype.Component;

@Component
public class TravelSportActivitiesRiskPremiumCalculator implements TravelRiskPremiumCalculator {

    @Override
    public String getRiskIc() {
        return "TRAVEL_SPORT_ACTIVITIES";
    }
}
