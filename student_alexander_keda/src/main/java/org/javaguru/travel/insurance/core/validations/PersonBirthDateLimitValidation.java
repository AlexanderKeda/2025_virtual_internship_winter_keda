package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.util.AgeCalculator;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.MODULE)
class PersonBirthDateLimitValidation implements TravelRequestValidation {

    private final ValidationErrorFactory validationErrorFactory;
    private final AgeCalculator ageCalculator;

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequest request) {
        return (request.getPersonBirthDate() != null
                && ageCalculator.calculate(request.getPersonBirthDate()) > 150L)
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_14"))
                : Optional.empty();
    }
}
