package org.javaguru.travel.insurance.core.underwriting;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;

import java.math.BigDecimal;
import java.util.List;

@Getter
@EqualsAndHashCode
public final class TravelPremiumCalculationResult {

    private final BigDecimal totalPremium;

    private final List<RiskDTO> riskPremiums;

    TravelPremiumCalculationResult(BigDecimal totalPremium, List<RiskDTO> riskPremiums) {
        this.totalPremium = totalPremium;
        this.riskPremiums = List.copyOf(riskPremiums);
    }
}
