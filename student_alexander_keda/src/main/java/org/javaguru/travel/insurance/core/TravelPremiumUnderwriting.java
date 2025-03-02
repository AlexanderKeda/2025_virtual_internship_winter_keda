package org.javaguru.travel.insurance.core;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelPremiumUnderwriting {

    private final DateTimeUtil dateTimeUtil;

    BigDecimal underwrite(TravelCalculatePremiumRequest request) {
        return new BigDecimal(dateTimeUtil.calculateDaysBetween(
                request.getAgreementDateFrom(),
                request.getAgreementDateTo()));
    }

}
