package org.javaguru.travel.insurance.core.underwriting;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.underwriting.calculators.TravelRiskPremiumCalculator;
import org.javaguru.travel.insurance.dto.RiskPremium;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelPremiumUnderwritingImp implements TravelPremiumUnderwriting {

    private final List<TravelRiskPremiumCalculator> travelRiskPremiumCalculators;

    @Override
    public TravelPremiumCalculationResult calculatePremium(TravelCalculatePremiumRequest request) {
        List<RiskPremium> riskPremiums = getRiskPremiumList(request);
        BigDecimal totalPremium = getTotalPremium(riskPremiums);
        return new TravelPremiumCalculationResult(totalPremium, riskPremiums);
    }

    private List<RiskPremium> getRiskPremiumList(TravelCalculatePremiumRequest request) {
        return travelRiskPremiumCalculators.stream()
                .filter(riskCalculator -> request.getSelectedRisks().contains(riskCalculator.getRiskIc()))
                .map(riskPremiumCalculator -> new RiskPremium(
                        riskPremiumCalculator.getRiskIc(),
                        riskPremiumCalculator.calculatePremium(request)))
                .toList();
    }

    private BigDecimal getTotalPremium(List<RiskPremium> riskPremiums) {
        return riskPremiums.stream()
                .map(RiskPremium::premium)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
