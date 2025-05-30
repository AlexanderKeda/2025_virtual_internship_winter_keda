package org.javaguru.travel.insurance.core.underwriting.calculators;

import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;

import java.math.BigDecimal;

public interface TravelRiskPremiumCalculator {

    default BigDecimal calculatePremium(TravelCalculatePremiumRequestV1 request) {
        return BigDecimal.ZERO;
    }

    String getRiskIc();

}
