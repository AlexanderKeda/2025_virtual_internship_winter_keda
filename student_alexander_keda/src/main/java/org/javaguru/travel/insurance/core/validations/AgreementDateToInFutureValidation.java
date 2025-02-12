package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
class AgreementDateToInFutureValidation {

    Optional<ValidationError> validateDateToInFuture(TravelCalculatePremiumRequest request) {
        return (request.getAgreementDateTo() != null &&
                request.getAgreementDateTo().isBefore(LocalDate.now()))
                ? Optional.of(new ValidationError("agreementDateTo", "Must not be in the past!"))
                : Optional.empty();
    }
}
