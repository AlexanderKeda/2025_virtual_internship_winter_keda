package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;

import java.math.BigDecimal;

interface MedicalRiskElement {

    BigDecimal calculate(TravelCalculatePremiumRequestV1 request);

}
