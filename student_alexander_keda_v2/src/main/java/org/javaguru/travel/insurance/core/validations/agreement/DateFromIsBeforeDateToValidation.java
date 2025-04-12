package org.javaguru.travel.insurance.core.validations.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DateFromIsBeforeDateToValidation implements TravelAgreementValidation {

    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement) {
        LocalDate dateFrom = agreement.getAgreementDateFrom();
        LocalDate dateTo = agreement.getAgreementDateTo();
        return (dateFrom != null &&
                dateTo != null &&
                dateTo.isBefore(dateFrom))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_8"))
                : Optional.empty();
    }

}
