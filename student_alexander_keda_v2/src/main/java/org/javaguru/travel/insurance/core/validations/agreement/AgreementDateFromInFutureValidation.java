package org.javaguru.travel.insurance.core.validations.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.TravelRequestValidation;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AgreementDateFromInFutureValidation implements TravelRequestValidation {

    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement) {
        return (agreement.getAgreementDateFrom() != null &&
                agreement.getAgreementDateFrom().isBefore(LocalDate.now()))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_6"))
                : Optional.empty();
    }

}
