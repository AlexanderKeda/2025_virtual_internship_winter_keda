package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class EmptyCountryValidation implements TravelRequestValidation {

    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequest request) {
        return hasRequiredRisks(request)
                && (request.getCountry() == null || request.getCountry().isBlank())
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_10"))
                : Optional.empty();
    }

    private boolean hasRequiredRisks(TravelCalculatePremiumRequest request) {
        List<String> risks = request.getSelectedRisks();
        return risks != null && risks.contains("TRAVEL_MEDICAL");
    }
}
