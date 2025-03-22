package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(access = AccessLevel.MODULE)
class DayCountElement implements MedicalRiskElement {

    private final DateTimeUtil dateTimeUtil;

    @Override
    public BigDecimal calculate(TravelCalculatePremiumRequest request) {
        return new BigDecimal(dateTimeUtil.calculateDaysBetween(
                request.getAgreementDateFrom(),
                request.getAgreementDateTo()));
    }
}
