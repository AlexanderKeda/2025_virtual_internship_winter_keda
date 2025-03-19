package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor(access = AccessLevel.MODULE)
class EmptyPersonBirthDateValidation implements TravelRequestValidation {

    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequest request) {
        return request.getPersonBirthDate() == null
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_12"))
                : Optional.empty();
    }
}
