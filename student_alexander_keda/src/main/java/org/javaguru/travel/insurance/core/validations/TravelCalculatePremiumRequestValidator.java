package org.javaguru.travel.insurance.core.validations;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TravelCalculatePremiumRequestValidator {

    private final PersonFirstNameValidation personFirstNameValidation;
    private final PersonLastNameValidation personLastNameValidation;
    private final AgreementDateFromValidation agreementDateFromValidation;
    private final AgreementDateToValidation agreementDateToValidation;
    private final AgreementDateFromInFutureValidation agreementDateFromInFutureValidation;
    private final AgreementDateToInFutureValidation agreementDateToInFutureValidation;
    private final DateFromIsBeforeDateToValidation dateFromIsBeforeDateToValidation;

    public List<ValidationError> validate(TravelCalculatePremiumRequest request) {
        List<ValidationError> errors = new ArrayList<>();
        personFirstNameValidation.validatePersonFirstName(request).ifPresent(errors::add);
        personLastNameValidation.validatePersonLastName(request).ifPresent(errors::add);
        agreementDateFromValidation.validateDateFrom(request).ifPresent(errors::add);
        agreementDateToValidation.validateDateTo(request).ifPresent(errors::add);
        agreementDateFromInFutureValidation.validateDateFromInFuture(request).ifPresent(errors::add);
        agreementDateToInFutureValidation.validateDateToInFuture(request).ifPresent(errors::add);
        dateFromIsBeforeDateToValidation.validateDateFromIsBeforeDateTo(request).ifPresent(errors::add);
        return errors;
    }

}
