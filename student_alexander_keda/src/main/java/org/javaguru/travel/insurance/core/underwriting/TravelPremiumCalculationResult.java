package org.javaguru.travel.insurance.core.underwriting;

import lombok.*;
import org.javaguru.travel.insurance.dto.RiskPremium;

import java.math.BigDecimal;
import java.util.List;

@Getter
@EqualsAndHashCode
public final class TravelPremiumCalculationResult {

    private final BigDecimal totalPremium;

    private final List<RiskPremium> riskPremiums;

    TravelPremiumCalculationResult(BigDecimal totalPremium, List<RiskPremium> riskPremiums) {
        this.totalPremium = totalPremium;
        this.riskPremiums = List.copyOf(riskPremiums);
    }
}
