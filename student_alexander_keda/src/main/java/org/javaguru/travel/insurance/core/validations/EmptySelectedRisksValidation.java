package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class EmptySelectedRisksValidation implements TravelRequestValidation {

    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationError> execute(TravelCalculatePremiumRequest request) {
        return request.getSelectedRisks() == null || request.getSelectedRisks().isEmpty()
                ? Optional.of(validationErrorFactory.getValidationError("ERROR_CODE_5"))
                : Optional.empty();
    }
}
