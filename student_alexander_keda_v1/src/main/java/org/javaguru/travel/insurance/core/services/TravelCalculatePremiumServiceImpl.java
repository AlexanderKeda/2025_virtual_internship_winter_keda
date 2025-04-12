package org.javaguru.travel.insurance.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.underwriting.TravelPremiumCalculationResult;
import org.javaguru.travel.insurance.core.underwriting.TravelPremiumUnderwriting;
import org.javaguru.travel.insurance.core.validations.TravelCalculatePremiumRequestValidator;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelCalculatePremiumServiceImpl implements TravelCalculatePremiumService {

    private final TravelPremiumUnderwriting underwriting;
    private final TravelCalculatePremiumRequestValidator requestValidator;

    @Override
    public TravelCalculatePremiumResponseV1 calculatePremium(TravelCalculatePremiumRequestV1 request) {
        List<ValidationError> errors = requestValidator.validate(request);

        return errors.isEmpty()
                ? buildResponse(request)
                : buildResponse(errors);
    }

    private TravelCalculatePremiumResponseV1 buildResponse(List<ValidationError> errors) {
        return new TravelCalculatePremiumResponseV1(errors);
    }

    private TravelCalculatePremiumResponseV1 buildResponse(TravelCalculatePremiumRequestV1 request) {
        TravelPremiumCalculationResult calculationResult = underwriting.calculatePremium(request);
        return new TravelCalculatePremiumResponseV1(
                request.getPersonFirstName(),
                request.getPersonLastName(),
                request.getPersonBirthDate(),
                request.getAgreementDateFrom(),
                request.getAgreementDateTo(),
                request.getCountry(),
                request.getMedicalRiskLimitLevel(),
                calculationResult.getTotalPremium(),
                calculationResult.getRiskPremiums()
        );
    }

}
