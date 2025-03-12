package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;

import java.math.BigDecimal;

interface TravelRiskPremiumCalculator {

    default BigDecimal calculatePremium(TravelCalculatePremiumRequest request) {
        return BigDecimal.ZERO;
    }

    String getRiskIc();

}
