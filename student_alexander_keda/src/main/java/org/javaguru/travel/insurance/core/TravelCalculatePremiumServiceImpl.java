package org.javaguru.travel.insurance.core;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumResponse;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelCalculatePremiumServiceImpl implements TravelCalculatePremiumService {

    private final DateTimeService dateTimeService;
    private final TravelCalculatePremiumRequestValidator requestValidator;

    @Override
    public TravelCalculatePremiumResponse calculatePremium(TravelCalculatePremiumRequest request) {
        List<ValidationError> errors = requestValidator.validate(request);

        return errors.isEmpty()
                ? buildResponse(request)
                : buildResponse(errors);
    }

    private TravelCalculatePremiumResponse buildResponse(List<ValidationError> errors) {
        return new TravelCalculatePremiumResponse(errors);
    }

    private TravelCalculatePremiumResponse buildResponse(TravelCalculatePremiumRequest request) {
        BigDecimal agreementPrice = new BigDecimal(dateTimeService.calculateDaysBetween(
                request.getAgreementDateFrom(),
                request.getAgreementDateTo()));

        return TravelCalculatePremiumResponse.builder()
                .personFirstName(request.getPersonFirstName())
                .personLastName(request.getPersonLastName())
                .agreementDateFrom(request.getAgreementDateFrom())
                .agreementDateTo(request.getAgreementDateTo())
                .agreementPrice(agreementPrice)
                .build();
    }

}
