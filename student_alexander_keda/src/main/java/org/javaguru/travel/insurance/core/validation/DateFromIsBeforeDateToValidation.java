package org.javaguru.travel.insurance.core.validation;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
class DateFromIsBeforeDateToValidation {

    Optional<ValidationError> validateDateFromIsBeforeDateTo(TravelCalculatePremiumRequest request) {
        LocalDate dateFrom = request.getAgreementDateFrom();
        LocalDate dateTo = request.getAgreementDateTo();
        return (dateFrom != null &&
                dateTo != null &&
                dateTo.isBefore(dateFrom))
                ? Optional.of(new ValidationError("agreementDateTo", "Must be after DataFrom!"))
                : Optional.empty();
    }
}
