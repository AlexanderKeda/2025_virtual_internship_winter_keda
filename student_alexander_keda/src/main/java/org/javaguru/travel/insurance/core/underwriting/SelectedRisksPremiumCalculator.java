package org.javaguru.travel.insurance.core.underwriting;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.underwriting.calculators.TravelRiskPremiumCalculator;
import org.javaguru.travel.insurance.dto.RiskPremium;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SelectedRisksPremiumCalculator {

    private final List<TravelRiskPremiumCalculator> travelRiskPremiumCalculators;

    List<RiskPremium> calculatePremiumForAllRisks(TravelCalculatePremiumRequestV1 request) {
        return travelRiskPremiumCalculators.stream()
                .filter(riskCalculator -> request.getSelectedRisks().contains(riskCalculator.getRiskIc()))
                .map(riskPremiumCalculator -> new RiskPremium(
                        riskPremiumCalculator.getRiskIc(),
                        riskPremiumCalculator.calculatePremium(request)))
                .toList();
    }

}
