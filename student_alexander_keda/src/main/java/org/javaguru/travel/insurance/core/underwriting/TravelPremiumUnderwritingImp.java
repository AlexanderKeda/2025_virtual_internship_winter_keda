package org.javaguru.travel.insurance.core.underwriting;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelPremiumUnderwritingImp implements TravelPremiumUnderwriting {

    private final DateTimeUtil dateTimeUtil;

    @Override
    public BigDecimal underwrite(TravelCalculatePremiumRequest request) {
        return new BigDecimal(dateTimeUtil.calculateDaysBetween(
                request.getAgreementDateFrom(),
                request.getAgreementDateTo()));
    }

}
