package org.javaguru.travel.insurance.core.underwriting;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelPremiumUnderwritingImp implements TravelPremiumUnderwriting {

    private final List<TravelRiskPremiumCalculator> travelRiskPremiumCalculators;

    @Override
    public BigDecimal underwrite(TravelCalculatePremiumRequest request) {
        return travelRiskPremiumCalculators.stream()
                .filter(riskCalculator -> request.getSelectedRisks().contains(riskCalculator.getRiskIc()))
                .map(riskCalculator -> riskCalculator.calculatePremium(request))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
