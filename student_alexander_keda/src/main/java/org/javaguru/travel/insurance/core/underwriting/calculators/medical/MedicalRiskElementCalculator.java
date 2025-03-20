package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;

import java.math.BigDecimal;

interface MedicalRiskElementCalculator {

    BigDecimal calculate(TravelCalculatePremiumRequest request);

}
