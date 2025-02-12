package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TravelCalculatePremiumRequestValidator {

    private final String EMPTY_ERROR_MESSAGE = "Must not be empty!";

    public List<ValidationError> validate(TravelCalculatePremiumRequest request) {
        List<ValidationError> errors = new ArrayList<>();
        validatePersonFirstName(request).ifPresent(errors::add);
        validatePersonLastName(request).ifPresent(errors::add);
        validateDateFrom(request).ifPresent(errors::add);
        validateDateTo(request).ifPresent(errors::add);
        validateDateFromLessThenDateTo(request).ifPresent(errors::add);
        validateDateFromInFuture(request).ifPresent(errors::add);
        validateDateToInFuture(request).ifPresent(errors::add);
        return errors;
    }

    private Optional<ValidationError> validatePersonFirstName(TravelCalculatePremiumRequest request) {
        return (request.getPersonFirstName() == null || request.getPersonFirstName().isBlank())
                ? Optional.of(new ValidationError("personFirstName", EMPTY_ERROR_MESSAGE))
                : Optional.empty();
    }

    private Optional<ValidationError> validatePersonLastName(TravelCalculatePremiumRequest request) {
        return (request.getPersonLastName() == null || request.getPersonLastName().isBlank())
                ? Optional.of(new ValidationError("personLastName", EMPTY_ERROR_MESSAGE))
                : Optional.empty();
    }

    private Optional<ValidationError> validateDateFrom(TravelCalculatePremiumRequest request) {
        return (request.getAgreementDateFrom() == null)
                ? Optional.of(new ValidationError("agreementDateFrom", EMPTY_ERROR_MESSAGE))
                : Optional.empty();
    }

    private Optional<ValidationError> validateDateTo(TravelCalculatePremiumRequest request) {
        return (request.getAgreementDateTo() == null)
                ? Optional.of(new ValidationError("agreementDateTo", EMPTY_ERROR_MESSAGE))
                : Optional.empty();
    }

    private Optional<ValidationError> validateDateFromLessThenDateTo(TravelCalculatePremiumRequest request) {
        LocalDate dateFrom = request.getAgreementDateFrom();
        LocalDate dateTo = request.getAgreementDateTo();

        if (dateFrom != null && dateTo != null && dateTo.isBefore(dateFrom)) {
            return Optional.of(new ValidationError("agreementDateTo", "Must be after DataFrom!"));
        }
        return Optional.empty();
    }

    private Optional<ValidationError> validateDateFromInFuture(TravelCalculatePremiumRequest request) {
        return (request.getAgreementDateFrom() != null &&
                request.getAgreementDateFrom().isBefore(LocalDate.now()))
                ? Optional.of(new ValidationError("agreementDateFrom", "Must not be in the past!"))
                : Optional.empty();
    }

    private Optional<ValidationError> validateDateToInFuture(TravelCalculatePremiumRequest request) {
        return (request.getAgreementDateTo() != null &&
                request.getAgreementDateTo().isBefore(LocalDate.now()))
                ? Optional.of(new ValidationError("agreementDateTo", "Must not be in the past!"))
                : Optional.empty();
    }

}
