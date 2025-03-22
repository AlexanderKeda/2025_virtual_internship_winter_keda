package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.underwriting.calculators.TravelRiskPremiumCalculator;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.MODULE)
class TravelMedicalRiskPremiumCalculator implements TravelRiskPremiumCalculator {

    private final List<MedicalRiskElement> medicalRiskElements;

    @Override
    public BigDecimal calculatePremium(TravelCalculatePremiumRequest request) {
        return medicalRiskElements.stream()
                .map(medicalRiskElement -> medicalRiskElement.calculate(request))
                .reduce(BigDecimal.ONE, BigDecimal::multiply)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getRiskIc() {
        return "TRAVEL_MEDICAL";
    }

}
