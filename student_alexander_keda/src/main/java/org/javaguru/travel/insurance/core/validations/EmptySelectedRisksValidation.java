package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmptySelectedRisksValidation implements TravelRequestValidation {

    @Override
    public Optional<ValidationError> execute(TravelCalculatePremiumRequest request) {
        return request.getSelectedRisks() != null && !request.getSelectedRisks().isEmpty()
                ? Optional.empty()
                : Optional.of(new ValidationError("selectedRisks", "Must not be empty!"));
    }
}
