package org.javaguru.travel.insurance.core.validation;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
class AgreementDateFromInFutureValidation {

    Optional<ValidationError> validateDateFromInFuture(TravelCalculatePremiumRequest request) {
        return (request.getAgreementDateFrom() != null &&
                request.getAgreementDateFrom().isBefore(LocalDate.now()))
                ? Optional.of(new ValidationError("agreementDateFrom", "Must not be in the past!"))
                : Optional.empty();
    }
}
