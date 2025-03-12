package org.javaguru.travel.insurance.core.underwriting;

import org.springframework.stereotype.Component;

@Component
public class TravelEvacuationRiskPremiumCalculator implements TravelRiskPremiumCalculator {

    @Override
    public String getRiskIc() {
        return "TRAVEL_EVACUATION";
    }
}
